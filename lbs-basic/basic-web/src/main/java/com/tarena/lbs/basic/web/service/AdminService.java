package com.tarena.lbs.basic.web.service;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.github.pagehelper.PageInfo;
import com.tarena.lbs.base.common.utils.Asserts;
import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.basic.web.repository.AdminRepository;
import com.tarena.lbs.basic.web.repository.BusinessRepository;
import com.tarena.lbs.basic.web.utils.AuthenticationContextUtils;
import com.tarena.lbs.common.passport.enums.Roles;
import com.tarena.lbs.common.passport.principle.UserPrinciple;
import com.tarena.lbs.pojo.basic.param.AdminParam;
import com.tarena.lbs.pojo.basic.po.AdminPO;
import com.tarena.lbs.pojo.basic.po.BusinessPO;
import com.tarena.lbs.pojo.basic.query.AdminQuery;
import com.tarena.lbs.pojo.basic.vo.AdminVO;
import com.tarena.lbs.pojo.basic.vo.BusinessVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private BusinessRepository businessRepository;

    public AdminVO detail() throws BusinessException {
        UserPrinciple userPrinciple = AuthenticationContextUtils.getPrinciple();
        // 0.1. check if the user is logged in
        Asserts.isTrue(userPrinciple==null, new BusinessException("-2","login error, jwt token invalid"));;
        Integer id = userPrinciple.getId();
        // 1. read database PO
        AdminPO po = adminRepository.getAdminById(id);
        // 2. convert PO to VO, properties should stay the same
        AdminVO vo = null;
        if (po!=null) {
            vo = new AdminVO();
            BeanUtils.copyProperties(po, vo);
        }
        return vo;
    }

    public PageResult<AdminVO> pageList(AdminQuery query) {
        PageResult<AdminVO> voPages = new PageResult<>();
        PageInfo<AdminPO> pageInfo = adminRepository.getPages(query);
        voPages.setPageNo(pageInfo.getPageNum());
        voPages.setPageSize(pageInfo.getPageSize());
        voPages.setTotal(pageInfo.getTotal());
        List<AdminVO> vos = null;
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            vos = pageInfo.getList().stream().map(po -> {
                AdminVO vo = new AdminVO();
                BeanUtils.copyProperties(po, vo);
                return vo;
            }).collect(Collectors.toList());
        }
        voPages.setObjects(vos);
        return voPages;
    }

    public void save(AdminParam param) throws BusinessException {
        checkRole(Roles.ADMIN);
        checkBusinessExist(param);
        checkPhoneReuse(param);
        saveAdmin(param);
    }

    private void saveAdmin(AdminParam param) {
        AdminPO po = new AdminPO();
        BeanUtils.copyProperties(param, po);
        po.setAccountStatus(0);
        po.setCreateTime(new Date());
        po.setBusinessId(param.getAccountType()==0?0:param.getBusinessId());
        adminRepository.save(po);
    }

    private void checkPhoneReuse(AdminParam param) throws BusinessException {
        Long count = adminRepository.countByPhone(param.getAccountPhone());
        Asserts.isTrue(count>0, new BusinessException("-2", "phone already exist"));
    }

    private void checkBusinessExist(AdminParam param) throws BusinessException {
        // only when accountType=1, then check business exist
        if (param.getAccountType() == 1) {
            BusinessPO bizPO = businessRepository.getBusinessById(param.getBusinessId());
            Asserts.isTrue(bizPO==null, new BusinessException("-2", "business not exist"));
        }
    }

    private void checkRole(Roles roles) throws BusinessException {
        UserPrinciple userPrinciple = AuthenticationContextUtils.getPrinciple();
        Asserts.isTrue(userPrinciple==null, new BusinessException("-2", "user authentication failed, no user info in request"));
        Roles loginRole = userPrinciple.getRole();
        Asserts.isTrue(loginRole.equals(roles), new BusinessException("-2", "user doesn't have the role to be authorized"));
    }
}
