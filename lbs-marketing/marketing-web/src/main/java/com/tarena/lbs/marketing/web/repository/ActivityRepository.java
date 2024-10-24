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
}
