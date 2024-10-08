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

import com.tarena.lbs.base.protocol.enums.ErrorCodeSupport;
import lombok.Getter;

@Getter
public enum AttachResultEnum implements ErrorCodeSupport {

    OK("0", "success"),
    BIND_ERROR("1", "非法入参"),
    SYSTEM_ERROR("-1", "system error"),

    CONTENT_NOT_EXISTS("4001", "内容不存在"),
    FILE_FAIL("4002", "文件上传失败"),
    TYPE_ERROR("4003", "内容类型错误:1食谱2视频3资讯"),
    ;

    AttachResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;

    private String message;
}
