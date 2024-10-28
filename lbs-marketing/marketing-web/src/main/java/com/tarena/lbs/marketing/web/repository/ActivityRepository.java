package com.tarena.lbs.marketing.web.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tarena.lbs.marketing.web.mapper.ActivityMapper;
import com.tarena.lbs.pojo.marketing.po.ActivityPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ActivityRepository {
    @Autowired
    private ActivityMapper activityMapper;

    public List<ActivityPO> getActivities(Integer businessId) {
        //select * from activity where business_id=#{}
        QueryWrapper<ActivityPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("business_id", businessId);
        return activityMapper.selectList(queryWrapper);
    }
    public void save(ActivityPO poParam) {
        activityMapper.insert(poParam);
    }

    public ActivityPO getActivityById(Integer activityId) {
        return activityMapper.selectById(activityId);
    }

    public List<ActivityPO> getActivityByShopId(Integer shopId) {
        //select * from activity where FIND_IN_SET(#{shopId},shop_ids)
        //入参 3号店铺 有个活动绑定了 1,3,5 命中 绑定 3,6,9 命中
        return activityMapper.selectActivitiesByShopId(shopId);
    }
}
