package com.tarena.lbs.basic.web.service;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.github.pagehelper.PageInfo;
import com.tarena.lbs.attach.api.AttachApi;
import com.tarena.lbs.base.common.utils.Asserts;
import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.basic.web.repository.AdminRepository;
import com.tarena.lbs.basic.web.repository.BusinessRepository;
import com.tarena.lbs.basic.web.repository.StoreRepository;
import com.tarena.lbs.basic.web.utils.AuthenticationContextUtils;
import com.tarena.lbs.common.passport.enums.Roles;
import com.tarena.lbs.common.passport.principle.UserPrinciple;
import com.tarena.lbs.pojo.attach.param.PicUpdateParam;
import com.tarena.lbs.pojo.basic.entity.StoreSearchEntity;
import com.tarena.lbs.pojo.basic.event.LocationEvent;
import com.tarena.lbs.pojo.basic.param.StoreParam;
import com.tarena.lbs.pojo.basic.param.UserLocationParam;
import com.tarena.lbs.pojo.basic.po.AdminPO;
import com.tarena.lbs.pojo.basic.po.BusinessPO;
import com.tarena.lbs.pojo.basic.po.StorePO;
import com.tarena.lbs.pojo.basic.query.AreaStoreQuery;
import com.tarena.lbs.pojo.basic.query.StoreQuery;
import com.tarena.lbs.pojo.basic.vo.AdminVO;
import com.tarena.lbs.pojo.basic.vo.StoreVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private BusinessRepository businessRepository;
    @DubboReference
    private AttachApi attachApi;
    public PageResult<StoreVO> pageList(StoreQuery query) throws BusinessException {
        //1.获取登录用户的认证对象
        UserPrinciple userPrinciple=getUserPrinciple();
        //2.获取角色判断是否要在query条件中 添加businessId
        Roles role = userPrinciple.getRole();//ADMIN SHOP
        if (role==Roles.SHOP){
            log.info("当前角色是SHOP,需要拼接商家ID");
            //3.利用账号的id 查询账号详情 详情里包含所属商家 busnessId
            AdminPO adminPO = adminRepository.getAdminById(userPrinciple.getId());
            Asserts.isTrue(adminPO==null,new BusinessException("-2","商家账号不存在"));
            //babusinessId填补到查询条件query
            query.setBusinessId(adminPO.getBusinessId());
        }
        //4.封装完整的查询分页的业务逻辑
        PageResult<StoreVO> voPage=doPageList(query);
        return voPage;
    }

    private PageResult<StoreVO> doPageList(StoreQuery query) {
        PageResult<StoreVO> voPage=new PageResult<>();
        //total objects pageNo pageSize totalPage
        PageInfo<StorePO> pageInfo= storeRepository.getPages(query);
        //pageNo pageSize total
        voPage.setPageNo(pageInfo.getPageNum());
        voPage.setPageSize(pageInfo.getPageSize());
        voPage.setTotal(pageInfo.getTotal());
        List<StoreVO> vos=null;
        List<StorePO> pos = pageInfo.getList();
        if (CollectionUtils.isNotEmpty(pos)){
            vos=pos.stream().map(po->{
                StoreVO vo=new StoreVO();
                BeanUtils.copyProperties(po,vo);
                return vo;
            }).collect(Collectors.toList());
        }
        voPage.setObjects(vos);
        return voPage;
    }

    private UserPrinciple getUserPrinciple() throws BusinessException {
        //1.解析
        UserPrinciple userPrinciple = AuthenticationContextUtils.get();
        Asserts.isTrue(userPrinciple==null,new BusinessException("-2","用户认证解析失败"));
        return userPrinciple;
    }

    public void save(StoreParam param) throws BusinessException {
        //1.业务权限验证
        checkRole(Roles.SHOP);
        //2.验证商家是否合法
        checkBusiness(param.getBusinessId());
        //3.封装补充完成po status(0启用 1禁用) createTime updateTime
        StorePO poParam=storeParam2po(param);
        //4.到底写入 es还是数据库 不是业务层考虑的问题
        storeRepository.save(poParam);
        //5.上面都没问题 绑定图片 最终一致性 生产发送可靠消息 confirm消息 TODO
        bindPicture(param,poParam.getId());
    }

    private void bindPicture(StoreParam param, Integer id) {
        //绑定图片的业务 是根据入参中的图片ids logo 照片 绑定的当前店铺
        //1.准备封装一个调用绑定图片的参数
        List<PicUpdateParam> picParams=new ArrayList<>();
        //2.封装一个logo参数
        PicUpdateParam logoParam=new PicUpdateParam();
        logoParam.setBusinessId(id);
        logoParam.setBusinessType(300);
        logoParam.setId(Integer.valueOf(param.getStoreLogo()));
        picParams.add(logoParam);
        //3.封装一组 店铺门店图片
        List<String> storeImagesIds = param.getStoreImagesIds();
        List<PicUpdateParam> storeImagesParams=
                storeImagesIds.stream().map(storeImageId->{
                    PicUpdateParam storeImageParam=new PicUpdateParam();
                    storeImageParam.setBusinessType(400);
                    storeImageParam.setBusinessId(id);
                    storeImageParam.setId(Integer.valueOf(storeImageId));
                    return storeImageParam;
                }).collect(Collectors.toList());
        //将门店图片 参数 全部 加入到 远程调用的入参里
        picParams.addAll(storeImagesParams);
        //发起远程dubbo调用
        boolean result = attachApi.batchUpdateBusiness(picParams);
        /*try{
            List<PicUpdateParam> picParams=assemblePicUpdateParams(param,id,300,400);
            boolean result=sendMessage(picParams);//抛异常
            if (!result){
                reSend(picParams);//重试发送
            }
        }catch (Exception e){
            log.info("尝试多次发送图片绑定消息失败 记录日志 param:{}....");
            //对接监控预警
            alarm();//调用一次alarm接口 实现逻辑 是将信息发送给监控预警系统 如果发送信息 触发阈值
            // (100次 消息发送失败3次) 监控预警系统就会短信 电话通知开发
        }*/
    }

    private StorePO storeParam2po(StoreParam param) {
        //1.准备一个po返回值
        StorePO po=new StorePO();
        //2.能拷贝的先拷贝
        BeanUtils.copyProperties(param,po);
        //status 0 createTime update now
        po.setStoreStatus(0);
        Date now=new Date();//创建和更新一致
        po.setCreateTime(now);
        po.setUpdateTime(now);
        //地理位置
        po.setStoreLongitude(param.getLongitude());
        po.setStoreLatitude(param.getLatitude());
        //imagesId 入参 List<String> [1,2,3,4,5] po String=1,2,3,4,5
        List<String> storeImagesIds = param.getStoreImagesIds();
        String imageIds="";
        if (CollectionUtils.isNotEmpty(storeImagesIds)){
            //循环拼接 "id," 如果最后一个元素 "id"
            for (int i=0;i<storeImagesIds.size();i++){
                if (i==storeImagesIds.size()-1){
                    //最后一个元素
                    imageIds=imageIds+storeImagesIds.get(i);
                }else{
                    imageIds=imageIds+storeImagesIds.get(i)+",";
                }
            }
        }
        log.info("当前转化拼接的图片ids的字符串:{}",imageIds);
        po.setStoreImagesId(imageIds);
        return po;
    }

    private void checkBusiness(Integer businessId) throws BusinessException {
        //1.检查商家是否 启用 检查商家是否存在 等
        BusinessPO bizPo = businessRepository.getBusinessById(businessId);
        Asserts.isTrue(bizPo==null,new BusinessException("-2","商家不存在"));
    }

    private void checkRole(Roles role) throws BusinessException {
        //和当前登录的用户做对比
        //1.先拿到登录认证对象
        UserPrinciple userPrinciple = AuthenticationContextUtils.get();
        //断言==null 抛异常
        Asserts.isTrue(userPrinciple==null,new BusinessException("-2","用户认证解析失败"));
        //2.拿到认证登录角色 和 业务需要的角色对比
        Roles loginRole = userPrinciple.getRole();
        //断言不相等 抛异常
        Asserts.isTrue(loginRole!=role,new BusinessException("-2","用户角色权限不足"));
    }

    public PageResult<StoreVO> getStoreByCity(AreaStoreQuery query) throws BusinessException {
        //使用登录用户 拿到用户所属商家id 结合城市cityIdList构造sql查询数据
        //1.解析认证对象 拿到adminId 查询businessId
        UserPrinciple userPrinciple = AuthenticationContextUtils.get();
        Asserts.isTrue(userPrinciple==null,new BusinessException("-2","用户认证解析失败"));
        Integer adminId = userPrinciple.getId();
        //2. 查询商家账号详情
        AdminPO adminPo = adminRepository.getAdminById(adminId);
        Asserts.isTrue(adminPo==null,new BusinessException("-2","商家账号不存在"));
        Integer businessId = adminPo.getBusinessId();
        //3. 调用仓储层 查询店铺列表
        List<StorePO> pos=storeRepository.getAreaStores(query.getCityIdList(),businessId);
        //4.封装分页结果
        PageResult<StoreVO> voPage=new PageResult<>();
        voPage.setPageSize(10);
        voPage.setPageNo(1);
        voPage.setTotal(100l);
        //pos店铺列表不空是 封装vos
        List<StoreVO> vos=null;
        if (CollectionUtils.isNotEmpty(pos)){
            vos=pos.stream().map(po->{
                StoreVO vo=new StoreVO();
                BeanUtils.copyProperties(po,vo);
                return vo;
            }).collect(Collectors.toList());
        }
        voPage.setObjects(vos);
        return voPage;
    }

    public void location(UserLocationParam param) throws BusinessException {
        //1.解析认证拿到userId
        Integer userId=getUserId();
        //2.调用仓储层 查询定位中心点5公里|10公里|20公里的店铺 最多5个 size=5
        List<StoreSearchEntity> stores=storeRepository.getNearStores(param.getLatitude(),param.getLongitude(),50d);
        //3.定义组织消息对象 使用stream发送到目标
        if (CollectionUtils.isNotEmpty(stores)){
            stores.forEach(store->{
                //组织消息
                LocationEvent event=new LocationEvent();
                event.setUserId(userId);
                event.setStoreId(store.getId());
                //TODO 将消息发送
            });
        }
    }

    private Integer getUserId() throws BusinessException {
        UserPrinciple userPrinciple = AuthenticationContextUtils.get();
        Asserts.isTrue(userPrinciple==null,new BusinessException("-2","用户认证解析失败"));
        return userPrinciple.getId();
    }
}
