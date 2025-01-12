package com.tarena.lbs.basic.web.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tarena.lbs.basic.web.mapper.AdminMapper;
import com.tarena.lbs.pojo.basic.po.AdminPO;
import com.tarena.lbs.pojo.basic.query.AdminQuery;
import com.tarena.lbs.pojo.basic.vo.AdminVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminRepository {
    @Autowired
    private AdminMapper adminMapper;

    public AdminPO getAdminByPhone(String phone) {
        QueryWrapper<AdminPO> query = new QueryWrapper<>();
        query.eq("account_phone", phone);
        return adminMapper.selectOne(query);
    }

    public AdminPO getAdminById(Integer id) {
        return adminMapper.selectById(id);
    }

    public PageInfo<AdminPO> getPages(AdminQuery query) {
        QueryWrapper<AdminPO> queryWrapper = assembleAdminQuery(query);
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<AdminPO> pos = adminMapper.selectList(queryWrapper);
        return new PageInfo<>(pos);
    }

    private QueryWrapper<AdminPO> assembleAdminQuery(AdminQuery query) {
        QueryWrapper<AdminPO> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(query.getAccountPhone())) {
            queryWrapper.eq("account_phone", query.getAccountPhone());
        }
        if (StringUtils.isNotBlank(query.getNickname())) {
            queryWrapper.like("account_name", query.getNickname());
        }
        return queryWrapper;
    }
}
