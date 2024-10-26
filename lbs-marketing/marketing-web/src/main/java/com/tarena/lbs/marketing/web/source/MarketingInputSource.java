package com.tarena.lbs.marketing.web.source;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MarketingInputSource {
    @Input("store-location-input")
    SubscribableChannel getStoreLocationInput();
}
