package com.tarena.lbs.pojo.basic.event;

import lombok.Data;

import java.util.List;

//定位上报 功能 所需传递的消息数据
@Data
public class LocationStoreEvent {
    private Integer userId;//消费者根据userId发放优惠券
    private List<Integer> storeIds;//消费者根据店铺id 查找活动信息
}
