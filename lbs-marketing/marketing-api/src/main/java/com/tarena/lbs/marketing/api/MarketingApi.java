package com.tarena.lbs.marketing.api;

public interface MarketingApi {
    //用户是否满足活动目标人群范围 true满足 false不满足
    Boolean activityVisible(Integer userId,Integer activityId);
}
