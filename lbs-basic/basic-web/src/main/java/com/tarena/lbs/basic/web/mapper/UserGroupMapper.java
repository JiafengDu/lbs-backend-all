package com.tarena.lbs.basic.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tarena.lbs.pojo.basic.po.UserGroupPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserGroupMapper extends BaseMapper<UserGroupPO> {
    //当扩展的方法有多个参数 必须使用@Param指定一下mybatis的拼接变量名称
    List<Integer> findUserGroupIdsByTagIdsAndBizId(
            @Param("tagIds")List<Integer> userTagIds,
            @Param("businessId") Integer businessId);
}
