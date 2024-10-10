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
        //1.读取数据库po
        AdminPO po=adminRepository.getAdminById(id);
        //2.转化成vo VO就是PO的复制改名 属性几乎一模一样的
        AdminVO vo=null;
        if (po!=null){
            vo=new AdminVO();
            //密码最好设置成空
            BeanUtils.copyProperties(po,vo);
        }
        return vo;
    }
}



















