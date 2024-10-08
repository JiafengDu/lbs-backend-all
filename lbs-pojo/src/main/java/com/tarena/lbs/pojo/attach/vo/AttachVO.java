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
package com.tarena.lbs.pojo.attach.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AttachVO {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("文件uuid")
    private String fileUuid;

    @ApiModelProperty("文件名")
    private String clientFileName;

    @ApiModelProperty("下载次数")
    private Integer downloadTimes;

    @ApiModelProperty("文件大小")
    private Integer contentLength;

    @ApiModelProperty("类型")
    private String contentType;

    @ApiModelProperty("是否封面")
    private Integer isCover;

    @ApiModelProperty("宽度")
    private Integer width;

    @ApiModelProperty("高度")
    private Integer height;

    @ApiModelProperty("系统类型")
    private Integer businessType;

    @ApiModelProperty("系统id")
    private Integer businessId;

    @ApiModelProperty("状态")
    private Byte status;

    @ApiModelProperty("创建人id")
    private Long createUserId;

    @ApiModelProperty("创建时间")
    private Long gmtCreate;

    @ApiModelProperty("修改人id")
    private Long modifiedUserId;

    @ApiModelProperty("修改时间")
    private Long gmtModified;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建人姓名")
    private String createUserName;

    @ApiModelProperty("修改人姓名")
    private String modifiedUserName;

}
