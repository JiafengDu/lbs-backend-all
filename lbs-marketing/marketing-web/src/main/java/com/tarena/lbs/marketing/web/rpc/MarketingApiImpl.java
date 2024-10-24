package com.tarena.lbs.marketing.web.rpc;

import com.tarena.lbs.marketing.api.MarketingApi;
import com.tarena.lbs.marketing.web.service.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MarketingApiImpl implements MarketingApi {
    @Autowired
    private ActivityService activityService;
    @Override
    public Boolean activityVisible(Integer userId, Integer activityId) {
        log.info("正在检查用户:{},是否符合活动:{},的目标人群");
        try{
            return activityService.isTargetConsumer(userId,activityId);
        }catch (Exception e){
            log.error("检查用户所属活动目标人群失败",e);
        }
        return false;
    }
}
