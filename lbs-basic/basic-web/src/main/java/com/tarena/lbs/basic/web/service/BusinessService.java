package com.tarena.lbs.basic.web.service;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.tarena.lbs.attach.api.AttachApi;
import com.tarena.lbs.base.common.utils.Asserts;
import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.basic.web.constant.BusinessTypes;
import com.tarena.lbs.basic.web.repository.AdminRepository;
import com.tarena.lbs.basic.web.repository.BusinessRepository;
import com.tarena.lbs.basic.web.repository.StoreRepository;
import com.tarena.lbs.basic.web.utils.AuthenticationContextUtils;
import com.tarena.lbs.common.passport.enums.Roles;
import com.tarena.lbs.common.passport.principle.UserPrinciple;
import com.tarena.lbs.pojo.attach.param.PicUpdateParam;
import com.tarena.lbs.pojo.basic.param.BusinessParam;
import com.tarena.lbs.pojo.basic.po.AdminPO;
import com.tarena.lbs.pojo.basic.po.BusinessPO;
import com.tarena.lbs.pojo.basic.po.StorePO;
import com.tarena.lbs.pojo.basic.query.BusinessQuery;
import com.tarena.lbs.pojo.basic.vo.BusiStoreVO;
import com.tarena.lbs.pojo.basic.vo.BusinessVO;
import com.tarena.lbs.pojo.basic.vo.StoreVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BusinessService {
    @Autowired
    private BusinessRepository businessRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Resource
    @Qualifier("myExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    //注入attachApi
    @DubboReference
    private AttachApi attachApi;
    public PageResult<BusinessVO> pageList(BusinessQuery query) throws BusinessException {
        //1.取 认证对象 验证
        UserPrinciple userPrinciple= getUserPrinciple();
        //2.取 角色
        Roles role = userPrinciple.getRole();
        if (role==Roles.SHOP){
            //1.取得商家账号的id adminId 查询admin详情 拿到businessId
            Integer adminId = userPrinciple.getId();
            AdminPO adminPO = adminRepository.getAdminById(adminId);
            Asserts.isTrue(adminPO==null,new BusinessException("-2","商家不存在"));
            //2.在query中 为当前商家账号的查询 补充一个businessId
            query.setBusinessId(adminPO.getBusinessId());
        }
        //3.分页查询
        return doPageList(query);
    }

    private UserPrinciple getUserPrinciple() throws BusinessException {
        UserPrinciple userPrinciple = AuthenticationContextUtils.get();
        Asserts.isTrue(userPrinciple==null,new BusinessException("-2","用户认证解析失败"));
        return userPrinciple;
    }

    public PageResult<BusinessVO> doPageList(BusinessQuery query) {
        //1.封装分页对象返回
        PageResult<BusinessVO> voPages= new PageResult<>();
        //2.调用仓储层 使用pageHelper查询 获取返回结果
        PageInfo<BusinessPO> pageInfo=businessRepository.getPages(query);
        //3.pageNum pageSize total可以直接封装
        voPages.setTotal(pageInfo.getTotal());
        voPages.setPageNo(pageInfo.getPageNum());
        voPages.setPageSize(pageInfo.getPageSize());
        //4.vos需要判断非空pos 转化
        List<BusinessVO> vos=null;
        if (CollectionUtils.isNotEmpty(pageInfo.getList())){
            vos=pageInfo.getList().stream().map(po->{
                BusinessVO vo=new BusinessVO();
                BeanUtils.copyProperties(po,vo);
                return vo;
            }).collect(Collectors.toList());
        }
        voPages.setObjects(vos);
        return voPages;
        /* voPages.setPageNo(query.getPageNo());
        voPages.setPageSize(query.getPageSize());
        //2.voPages需要封装5个属性才算完毕
        //2.1 查询总条数 以当前条件 查所有 条件先忽略 select count(*) from lbs_business
        Long total=businessRepository.count(query);
        //2.2 查询分页数据 返回List<PO> select * from lbs_business limit from,size
        List<BusinessPO> pos= businessRepository.getBusinessByPage(query);
        //3. 将po的分页列表转化成vo
        List<BusinessVO> vos=null;
        if (CollectionUtils.isNotEmpty(pos)){
            // 将pos转化成vo 使用 list的stream()的api
            vos=pos.stream().map(po->{
                BusinessVO vo=new BusinessVO();
                BeanUtils.copyProperties(po,vo);
                return vo;
            }).collect(Collectors.toList());
        }
        //剩余属性封装完毕
        voPages.setTotal(total);
        voPages.setObjects(vos);*/

    }
    //@Transactional默认 回滚的异常是Runtime
    @Transactional(rollbackFor = Exception.class)
    public void save(BusinessParam param) throws BusinessException {
        //1.检查当前登录用户的角色是否符合我当前业务的角色要求  ADMIN
        checkRole(Roles.ADMIN);
        //2.验证检查幂等 是否存在相同名称的商家数据
        //3.幂等验证正常 可以新增 封装 数据对象PO 执行save新增
        Integer id=saveBusiness(param);
        //4.新增之后的商家 有了id 定义 type 100营业执照 200logo调用图片绑定
        bindPictures(id,param);
    }

    private void bindPictures(Integer businessId, BusinessParam param) throws BusinessException {
        //商家新增 图片 要绑定两张 一张营业执照 一张logo
        //准备一下 接口的入参
        List<PicUpdateParam> picParams=new ArrayList<>();
        //准备一个PicUpdateParam 存储营业执照 的绑定参数
        PicUpdateParam licenseParam=new PicUpdateParam();
        //bizType 100 表示商家营业执照
        licenseParam.setBusinessType(BusinessTypes.BIZ_LICENSE);
        //bizId 商家id
        licenseParam.setBusinessId(businessId);
        //可以在对象里绑定图片id或者图片fileUuid 当前业务无法获取图片id只能获取fileUuid
        //http://localhost:9081/static/19fh93gr931h10rf3h29r.png 最后的值是fileUuid的拼接 19fh93gr931h10rf3h29r.png
        String licenseUrl = param.getBusinessLicense();
        licenseParam.setFileUuid(getFileUuidFromUrl(licenseUrl));
        //准备第二个绑定图片的入参元素 绑定logo
        PicUpdateParam logoParam=new PicUpdateParam();
        logoParam.setBusinessType(BusinessTypes.BIZ_LOGO);
        logoParam.setBusinessId(businessId);
        String logoUrl = param.getBusinessLogo();
        logoParam.setFileUuid(getFileUuidFromUrl(logoUrl));
        //将营业执照图片数据 和log图片数据放到list中
        picParams.add(licenseParam);
        picParams.add(logoParam);
        boolean result = attachApi.batchUpdateBusiness(picParams);
        Asserts.isTrue(!result,new BusinessException("-2","业务绑定失败"));
    }

    private String getFileUuidFromUrl(String url) {
        //http://localhost:9081/static/19fh93gr931h10rf3h29r.png
        //截取
        String fileUuid= url.split("/")[4];
        log.info("获取当前url:{};的fileUuid:{}",url,fileUuid);
        return fileUuid;
    }

    private Integer saveBusiness(BusinessParam param) throws BusinessException {
        //考虑 幂等方案 防止出现多个同名的商家
        //1 先利用param携带的商家名称检查 是否存在数据
        String businessName = param.getBusinessName();
        Long count=businessRepository.countBusinessName(businessName);
        //2 判断 如果存在结束 异常结束
        Asserts.isTrue(count>0,new BusinessException("-2","商家名称重复"));
        //3 不存在 封装po新增 数据层 写数据到表格 方法入参 就是PO
        //3.1先拷贝
        BusinessPO po=new BusinessPO();
        BeanUtils.copyProperties(param,po);
        //3.2 注册时间 entiryTime 审核状态 1待审核2通过3驳回 remarks 审核意见
        po.setEntryTime(new Date());
        po.setBusinessStatus(2);
        po.setAuditRemarks("通过");
        //po目前的id是空的 mybatis-plus根据po对象属性注解 会自动在新增结束后填补id回对象
        log.info("当前po对象新增之前id:{}",po.getId());
        businessRepository.save(po);
        log.info("当前po对象新增之后id:{}",po.getId());
        return po.getId();
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

    public BusiStoreVO busiStoreDetail(Integer businessId) throws BusinessException{
        //调用多线程 并发执行下面的2个方法 把并行的方法各自放到一个容器线程池的线程封装方法执行
        CompletableFuture<BusiStoreVO> businessFuture
                = CompletableFuture.supplyAsync(() -> {
            return getBusiVO(businessId);
        },threadPoolTaskExecutor);
        CompletableFuture<List<StoreVO>> storesFuture
                = CompletableFuture.supplyAsync(() -> {
            return getStoreVOS(businessId);
        },threadPoolTaskExecutor);
        //每一个上述执行的方法 都是异步执行,需要线程阻塞的逻辑 获取他们的执行结果
        CompletableFuture<Void> allFuture = CompletableFuture.allOf(businessFuture, storesFuture);
        //从异步线程返回值中获取结果
        try{
            BusiStoreVO vo = businessFuture.get();
            List<StoreVO> storeVOS = storesFuture.get();
            if (vo!=null){
                vo.setStoreVOList(storeVOS);
                return vo;
            }
        }catch (Exception e){
            log.error("并发调用查询失败",e);
        }
        return null;
    }
    private List<StoreVO> getStoreVOS(Integer businessId){
        //select * from lbs_store where business_id=#{}
        List<StorePO> storePOS=storeRepository.getStoresByBusinessId(businessId);
        //如果商家下有店铺
        List<StoreVO> storeVos=null;
        if (CollectionUtils.isNotEmpty(storePOS)){
            storeVos=storePOS.stream().map(po->{
                StoreVO vo1=new StoreVO();
                BeanUtils.copyProperties(po,vo1);
                return vo1;
            }).collect(Collectors.toList());
        }
        return storeVos;
    }
    //是一个线程可以执行的方法 返回值 2种 一种vo非空 一种vo为空
    private BusiStoreVO getBusiVO(Integer businessId){
        //查询2批数据 一批是商家详情 一批是商家下的店铺列表
        //select * from lbs_business where id=#{}
        BusinessPO businessPO = businessRepository.getBusinessById(businessId);
        BusiStoreVO vo=null;
        if (businessPO!=null){
            //使用上述2个返回值 封装vo
            vo=new BusiStoreVO();
            BeanUtils.copyProperties(businessPO,vo);
        }
        return vo;
    }
}
