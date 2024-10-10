package com.tarena.lbs.basic.web.service;

import com.tarena.lbs.base.common.utils.Asserts;
import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.basic.web.repository.AdminRepository;
import com.tarena.lbs.basic.web.utils.AuthenticationContextUtils;
import com.tarena.lbs.common.passport.principle.UserPrinciple;
import com.tarena.lbs.pojo.basic.po.AdminPO;
import com.tarena.lbs.pojo.basic.vo.AdminVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    public AdminVO detail() throws BusinessException {
        //1.作为过滤器的下游代码 可以直接获取threadLocal里的认证对象
        UserPrinciple userPrinciple = AuthenticationContextUtils.get();
        //断言异常
        Asserts.isTrue(userPrinciple==null,new BusinessException("-2","认证解析错误"));
        Integer id=userPrinciple.getId();
        //2.读取数据库po
        AdminPO po=adminRepository.getAdminById(id);
        //3.转化成vo VO就是PO的复制改名 属性几乎一模一样的
        AdminVO vo=null;
        if (po!=null){
            vo=new AdminVO();
            //密码最好设置成空
            BeanUtils.copyProperties(po,vo);
        }
        return vo;
    }
}



















