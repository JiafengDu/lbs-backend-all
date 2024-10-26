package com.tarena.lbs.basic.web.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface BasicOutputSource {
    //定位店铺的output通道
    @Output("store-location-output")
    MessageChannel getStoreLocationOutput();
}
