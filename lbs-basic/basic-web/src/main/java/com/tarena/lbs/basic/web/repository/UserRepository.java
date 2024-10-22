package com.tarena.lbs.basic.web.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tarena.lbs.basic.web.mapper.UserMapper;
import com.tarena.lbs.pojo.basic.po.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    @Autowired
    private UserMapper userMapper;

    public Long countUserByPhone(String phone) {
        //select count(1) from lbs_user where phone=#{phone}
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("phone",phone);
        return userMapper.selectCount(queryWrapper);
    }

    public void save(UserPO poParam) {
        userMapper.insert(poParam);
    }

    public UserPO getByPhone(String phone) {
        QueryWrapper<UserPO> queryWrapper=new QueryWrapper();
        queryWrapper.eq("phone",phone);
        return userMapper.selectOne(queryWrapper);
    }

    public UserPO getById(Integer userId) {
        return userMapper.selectById(userId);
    }

    public void updateStatus(Integer userId, int i) {
        //update lbs_user set status=1 where user_id=#{}
        UserPO poParam=new UserPO();
        poParam.setId(userId);
        poParam.setStatus(i);
        userMapper.updateById(poParam);
    }
}
