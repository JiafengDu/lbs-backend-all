package com.tarena.lbs.basic.web.service;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.github.pagehelper.PageInfo;
import com.tarena.lbs.base.common.utils.Asserts;
import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.basic.web.repository.AdminRepository;
import com.tarena.lbs.basic.web.repository.StoreRepository;
import com.tarena.lbs.basic.web.utils.AuthenticationContextUtils;
import com.tarena.lbs.common.passport.enums.Roles;
import com.tarena.lbs.common.passport.principle.UserPrinciple;
import com.tarena.lbs.pojo.basic.po.AdminPO;
import com.tarena.lbs.pojo.basic.po.StorePO;
import com.tarena.lbs.pojo.basic.query.AdminQuery;
import com.tarena.lbs.pojo.basic.query.StoreQuery;
import com.tarena.lbs.pojo.basic.vo.AdminVO;
import com.tarena.lbs.pojo.basic.vo.StoreVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private AdminRepository adminRepository;
    public PageResult<StoreVO> pageList(StoreQuery query) throws BusinessException {

        UserPrinciple userPrinciple = getUserPrinciple();
        Roles role = userPrinciple.getRole();
        if (role==Roles.SHOP) {
            log.info("current role is shop, need to check businessId");
            AdminPO adminPO = adminRepository.getAdminById(userPrinciple.getId());
            Asserts.isTrue(adminPO==null, new BusinessException("-2","login error, jwt token invalid"));
            query.setBusinessId(adminPO.getBusinessId());
        }
        PageResult<StoreVO> voPage = doPageList(query);
        return voPage;
    }

    private UserPrinciple getUserPrinciple() throws BusinessException {
        UserPrinciple userPrinciple = AuthenticationContextUtils.getPrinciple();
        Asserts.isTrue(userPrinciple==null, new BusinessException("-2","login error, jwt token invalid"));
        return userPrinciple;
    }

    public PageResult<StoreVO> doPageList (StoreQuery query) {
        PageResult<StoreVO> voPages = new PageResult<>();
        PageInfo<StorePO> pageInfo = storeRepository.getPages(query);
        voPages.setPageNo(pageInfo.getPageNum());
        voPages.setPageSize(pageInfo.getPageSize());
        voPages.setTotal(pageInfo.getTotal());
        List<StoreVO> vos = null;
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            vos = pageInfo.getList().stream().map(po -> {
                StoreVO vo = new StoreVO();
                BeanUtils.copyProperties(po, vo);
                return vo;
            }).collect(Collectors.toList());
        }
        voPages.setObjects(vos);
        return voPages;
    }
}
