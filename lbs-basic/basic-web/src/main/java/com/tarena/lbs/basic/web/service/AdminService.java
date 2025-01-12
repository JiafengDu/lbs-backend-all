package com.tarena.lbs.basic.web.service;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.github.pagehelper.PageInfo;
import com.tarena.lbs.base.common.utils.Asserts;
import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.basic.web.repository.AdminRepository;
import com.tarena.lbs.basic.web.utils.AuthenticationContextUtils;
import com.tarena.lbs.common.passport.principle.UserPrinciple;
import com.tarena.lbs.pojo.basic.po.AdminPO;
import com.tarena.lbs.pojo.basic.query.AdminQuery;
import com.tarena.lbs.pojo.basic.vo.AdminVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
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
}
