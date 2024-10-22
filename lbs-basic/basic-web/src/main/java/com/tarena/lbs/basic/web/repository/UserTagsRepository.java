package com.tarena.lbs.basic.web.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tarena.lbs.basic.web.mapper.UserTagsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserTagsRepository {
    @Autowired
    private UserTagsMapper userTagsMapper;

    public void deleteByUserId(Integer userId) {
        //delete * from lbs_user_tags where user_id=#{}
        QueryWrapper query=new QueryWrapper();
        query.eq("user_id",userId);
        userTagsMapper.delete(query);
    }
}
