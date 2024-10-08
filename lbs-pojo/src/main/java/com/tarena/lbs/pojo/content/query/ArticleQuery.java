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
package com.tarena.lbs.pojo.content.query;

import com.tarena.lbs.base.protocol.pager.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@ApiModel(value = "ArticleQuery", description = "文章查询参数")
public class ArticleQuery extends BasePageQuery implements Serializable {

    @ApiModelProperty(value = "文章标题")
    private String articleTitle;

    @ApiModelProperty(value = "文章分类关联ID")
    private Integer articleCategoryId;

    @ApiModelProperty(value = "文章标签")
    private String articleLabel;

    @ApiModelProperty(value = "文章状态", dataType = "Integer[]")
    private List<Integer> articleStatus;

    @ApiModelProperty(value = "文章来源")
    private Integer source;

    @ApiModelProperty(value = "活动关联ID")
    private Integer activityId;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始时间")
    private Date updateStartTime;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束时间")
    private Date updateEndTime;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

}
