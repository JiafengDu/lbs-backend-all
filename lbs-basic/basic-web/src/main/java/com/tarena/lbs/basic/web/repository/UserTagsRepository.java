package com.tarena.lbs.basic.web.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tarena.lbs.basic.web.mapper.UserTagsMapper;
import com.tarena.lbs.pojo.basic.po.UserTagsPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * ServiceImpl 是mybatis-plus做的持久层上层封装
 * 里面包含BaseMapper的方法 扩展 比如 批量新增
 * 需要根据持久层表格 和封装数据 PO MAPPER 提供2个泛型
 * MAPPER 当前这个类型代理实现的时候 使用的持久层mapper
 * PO 当前业务 持久化数据
 */
@Repository
public class UserTagsRepository  extends ServiceImpl<UserTagsMapper, UserTagsPO> {
    @Autowired
    private UserTagsMapper userTagsMapper;

    public void deleteByUserId(Integer userId) {
        //delete * from lbs_user_tags where user_id=#{}
        QueryWrapper query=new QueryWrapper();
        query.eq("user_id",userId);
        userTagsMapper.delete(query);
    }
}
