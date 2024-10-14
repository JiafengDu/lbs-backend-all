package com.tarena.lbs.basic.web.repository;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tarena.lbs.basic.web.mapper.AdminMapper;
import com.tarena.lbs.pojo.basic.po.AdminPO;
import com.tarena.lbs.pojo.basic.query.AdminQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public PageInfo<AdminPO> gePages(AdminQuery query) {
        //PageHelper
        QueryWrapper<AdminPO> queryWrapper=assembleAdminQuery(query);
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<AdminPO> pos = adminMapper.selectList(queryWrapper);
        return new PageInfo<>(pos);
    }

    private QueryWrapper<AdminPO> assembleAdminQuery(AdminQuery query) {
        QueryWrapper<AdminPO> queryWrapper=new QueryWrapper<>();
        //1.accountPhone 账号手机号 相等查询 where account_phone=#{phone}
        if (StringUtils.isNotBlank(query.getAccountPhone())){
            queryWrapper.eq("account_phone",query.getAccountPhone());
        }
        //2.nickname 昵称 模糊查询 非空 where nickname like(%#{}%)
        if (StringUtils.isNotBlank(query.getNickname())){
            queryWrapper.like("nickname",query.getNickname());
        }
        return queryWrapper;
    }
}
