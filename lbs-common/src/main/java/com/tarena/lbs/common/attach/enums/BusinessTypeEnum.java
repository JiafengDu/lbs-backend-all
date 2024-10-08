/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tarena.lbs.common.attach.enums;

import lombok.Getter;

@Getter
public enum BusinessTypeEnum {

    SHOP_QR(100, "店铺二维码"),
    SHOP_LOGO(200,"店铺LOGO"),
    STORE(300, "门店图片"),
    ACTIVITY(400, "活动图片"),
    COUPON_CODE(500, "券码图片"),
    USER(600, "用户头像");

    BusinessTypeEnum(Integer type, String message) {
        this.type = type;
        this.message = message;
    }

    private Integer type;

    private String message;
}
