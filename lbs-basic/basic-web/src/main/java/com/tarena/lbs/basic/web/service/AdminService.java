package com.tarena.lbs.basic.web.service;

import com.tarena.lbs.basic.web.repository.AdminRepository;
import com.tarena.lbs.pojo.basic.po.AdminPO;
import com.tarena.lbs.pojo.basic.vo.AdminVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    public AdminVO detail(Integer id) {
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
}
