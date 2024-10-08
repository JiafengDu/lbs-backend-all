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
package com.tarena.lbs.pojo.content.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ArticleActionParam {
    @ApiModelProperty(value = "文章id")
    //文章id
    private Integer id;
    @ApiModelProperty(value = "行为")
    //点赞=1 收藏=2 评论=3
    private Integer action;
    @ApiModelProperty(value = "行为")
    //点赞=1 收藏=2 评论=3
    private Integer behavior;
    @ApiModelProperty(value = "评论内容")
    //评论
    private String comment;
}
