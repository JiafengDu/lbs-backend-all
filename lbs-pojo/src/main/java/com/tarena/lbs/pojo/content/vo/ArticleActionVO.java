package com.tarena.lbs.pojo.content.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("文章行为返回")
@Data
public class ArticleActionVO {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("文章id")
    private Integer articleId;

    @ApiModelProperty("文章标题")
    private String articleTitle;

    @ApiModelProperty("文章封面")
    private String articleCover;

    @ApiModelProperty("用户id")
    private String likePerson;

    @ApiModelProperty("用户头像")
    private String likePersonImg;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("评论")
    private String comment;

}
