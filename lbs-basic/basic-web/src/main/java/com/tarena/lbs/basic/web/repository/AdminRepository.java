package com.tarena.lbs.basic.web.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tarena.lbs.basic.web.mapper.AdminMapper;
import com.tarena.lbs.pojo.basic.po.AdminPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
//从数据层开始,我们要准备mybatis-plus的框架
//减少单表的 读写sql编写成本
//你曾经用过的 BaseMapper<PO>
@Repository
public class AdminRepository {
    @Autowired
    private AdminMapper adminMapper;

    public AdminPO getAdminByPhone(String phone) {
        //select * from lbs_admin where account_phone=#{phone}
        QueryWrapper<AdminPO> query=new QueryWrapper<>();
        query.eq("account_phone",phone);
        return adminMapper.selectOne(query);
    }

    public AdminPO getAdminById(Integer id) {
        return adminMapper.selectById(id);
    }
}
