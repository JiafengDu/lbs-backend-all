package com.tarena.lbs.marketing.web.service;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.tarena.lbs.attach.api.AttachApi;
import com.tarena.lbs.base.common.utils.Asserts;
import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.basic.api.BasicApi;
import com.tarena.lbs.common.passport.principle.UserPrinciple;
import com.tarena.lbs.common.security.utils.LbsSecurityContenxt;
import com.tarena.lbs.marketing.web.repository.ActivityRepository;
import com.tarena.lbs.marketing.web.utils.AuthenticationContextUtils;
import com.tarena.lbs.pojo.attach.param.PicUpdateParam;
import com.tarena.lbs.pojo.basic.dto.AdminDTO;
import com.tarena.lbs.pojo.marketing.param.ActivityParam;
import com.tarena.lbs.pojo.marketing.po.ActivityPO;
import com.tarena.lbs.pojo.marketing.vo.ActivityVO;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;
    @DubboReference
    private BasicApi basicApi;
    @DubboReference
    private AttachApi attachApi;
    public void activitySave(ActivityParam param) throws BusinessException, ParseException {
        //1.解析认证
        UserPrinciple userPrinciple = AuthenticationContextUtils.get();
        Asserts.isTrue(userPrinciple==null, new BusinessException("-2","用户认证解析失败"));
        //2.查询商家详情
        AdminDTO adminDto = basicApi.getAdminDetail(userPrinciple.getId());
        Asserts.isTrue(adminDto==null, new BusinessException("-2","商家信息不存在"));
        //3.补充活动poParam
        ActivityPO poParam=assembleActivityPo(param,adminDto.getBusinessId());
        //4.本地写入数据库
        activityRepository.save(poParam);
        //5.绑定图片 bizType=700
        bindPics(poParam);
    }
    private ActivityPO assembleActivityPo(ActivityParam param, Integer businessId) throws ParseException {
        //1.准备一个po
        ActivityPO poParam=new ActivityPO();
        //2.拷贝
        BeanUtils.copyProperties(param,poParam);
        //3.补充特殊属性 大部分跟优惠券类似
        poParam.setBusinessId(businessId);
        poParam.setStatus(0);//0待开始 1已发布 2已超时
        poParam.setEnableStatus(0);//0启用 1禁用
        //createAt updateAt
        poParam.setCreateAt(new Date());
        poParam.setUpdateAt(poParam.getCreateAt());
        //时间段 start end
        poParam.setStartDate(DateUtils.parseDate(param.getStartDate(),"yyyy-MM-dd HH:mm:ss"));
        poParam.setEndDate(DateUtils.parseDate(param.getEndDate(),"yyyy-MM-dd HH:mm:ss"));
        //奖励类型 数据库 非空处理
        poParam.setRewardType(1);
        return poParam;
    }

    private void bindPics(ActivityPO poParam) {
        //活动上传了多少张图片 就绑定多少张 每个图片id 拼接imgIds属性里
        String[] imgIds = poParam.getImgIds().split(",");
        List<PicUpdateParam> picParams = Arrays.stream(imgIds).map(imgId -> {
            PicUpdateParam param = new PicUpdateParam();
            param.setBusinessId(poParam.getId());
            param.setBusinessType(700);
            param.setId(Integer.valueOf(imgId));
            return param;
        }).collect(Collectors.toList());
        attachApi.batchUpdateBusiness(picParams);
    }
    public PageResult<ActivityVO> pageList() throws BusinessException {
        //1.准备一个分页对象 封装3个属性 no size total
        PageResult<ActivityVO> voPage=new PageResult<>();
        voPage.setPageNo(1);
        voPage.setPageSize(10);
        voPage.setTotal(100L);
        //2.解析当前认证对象 拿到id 读取admin详情 获取businessId
        UserPrinciple userPrinciple = parseUserPrinciple();
        Integer businessId = getAdminBusinessId(userPrinciple.getId());
        //3.调用仓储层查询该商家下所有数据
        List<ActivityPO> pos=activityRepository.getActivities(businessId);
        //4.封装vos返回
        List<ActivityVO> vos=null;
        if (CollectionUtils.isNotEmpty(pos)){
            vos=pos.stream().map(po->{
                ActivityVO vo=new ActivityVO();
                BeanUtils.copyProperties(po,vo);
                return vo;
            }).collect(Collectors.toList());
        }
        voPage.setObjects(vos);
        return voPage;
    }
    private Integer getAdminBusinessId(Integer adminId) throws BusinessException {
        AdminDTO dto = basicApi.getAdminDetail(adminId);
        Asserts.isTrue(dto==null,
                new BusinessException("-2","管理员信息不存在"));
        return dto.getBusinessId();
    }

    private UserPrinciple parseUserPrinciple() throws BusinessException {
        //1.解析
        UserPrinciple userPrinciple = AuthenticationContextUtils.get();
        Asserts.isTrue(userPrinciple==null,new BusinessException("-2","用户认证解析失败"));
        return userPrinciple;
    }
}
