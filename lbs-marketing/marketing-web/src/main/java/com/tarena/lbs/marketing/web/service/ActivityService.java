package com.tarena.lbs.marketing.web.service;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.tarena.lbs.base.common.utils.Asserts;
import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.basic.api.BasicApi;
import com.tarena.lbs.common.passport.principle.UserPrinciple;
import com.tarena.lbs.marketing.web.repository.ActivityRepository;
import com.tarena.lbs.marketing.web.utils.AuthenticationContextUtils;
import com.tarena.lbs.pojo.basic.dto.AdminDTO;
import com.tarena.lbs.pojo.marketing.po.ActivityPO;
import com.tarena.lbs.pojo.marketing.vo.ActivityVO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;
    @DubboReference
    private BasicApi basicApi;
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
