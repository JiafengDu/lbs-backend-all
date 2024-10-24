package com.tarena.lbs.basic.web.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tarena.lbs.basic.web.mapper.UserGroupMapper;
import com.tarena.lbs.pojo.basic.po.UserGroupPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserGroupRepository {
    @Autowired
    private UserGroupMapper userGroupMapper;

    public List<UserGroupPO> getGroupsByBusinessId(Integer businessId) {
        QueryWrapper<UserGroupPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("business_id", businessId);
        //select * from lbs_user_group where business_id=#{}
        return userGroupMapper.selectList(queryWrapper);
    }

    public void save(UserGroupPO po) {
        userGroupMapper.insert(po);
    }

    public List<Integer> getUserGroupsByTagIdsAndBusinessId(List<Integer> userTagIds, Integer businessId) {
        //入参 是一个数组[1,3] 还有一个商家id 49
        /*
        select id from lbs_user_group where business_id=49
        and (FIND_IN_SET(1,tag_ids) or FIND_IN_SET(3,tag_ids))
         */
        return userGroupMapper.findUserGroupIdsByTagIdsAndBizId(userTagIds,businessId);

    }
}
