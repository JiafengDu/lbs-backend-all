package com.tarena.lbs.marketing.web.consumer;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.marketing.web.service.ActivityService;
import com.tarena.lbs.marketing.web.service.CouponService;
import com.tarena.lbs.pojo.basic.event.LocationEvent;
import com.tarena.lbs.pojo.marketing.param.UserCouponsParam;
import com.tarena.lbs.pojo.marketing.po.ActivityPO;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StoreLocationConsumer {
    @Autowired
    private ActivityService activityService;
    @Autowired
    private CouponService couponService;
    //消费方法
    @StreamListener("store-location-input")
    public void consume(LocationEvent event){
        try{
            //1.拿到event的storeId
            Integer shopId=event.getStoreId();
            List<ActivityPO> activities = activityService.getActivityByShopId(shopId);
            if (CollectionUtils.isNotEmpty(activities)){
                //循环活动 组织优惠券领取入参UserCouponsParam 调用couponService给用户自动领取
                activities.forEach(ap->{
                    UserCouponsParam param=new UserCouponsParam();
                    param.setUserId(event.getUserId());
                    param.setActivityId(ap.getId());
                    param.setShopId(shopId);
                    param.setCouponId(ap.getRewardCouponId());
                    //给用户领取优惠券
                    try {
                        couponService.receiveCoupon(param);
                    } catch (BusinessException e) {
                        log.error("用户领取优惠券失败",e);
                    }
                });
            }
        }catch (Exception e){
            log.error("消费定位店铺消息异常",e);
        }
    }
}
