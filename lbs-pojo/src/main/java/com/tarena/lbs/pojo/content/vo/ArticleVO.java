package com.tarena.lbs.pojo.content.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

@ApiModel("文章结果返回")
@Data
public class ArticleVO {

    @ApiModelProperty("文章id")
    private Integer id;

    @ApiModelProperty("文章标题")
    private String articleTitle;

    @ApiModelProperty("文章分类id")
    private Integer articleCategoryId;

    @ApiModelProperty("文章分类名称")
    private String articleCategory;

    @ApiModelProperty("文章标签")
    private String articleLabel;

    @ApiModelProperty("文章摘要")
    private String articleDigest;

    @ApiModelProperty("文章封面")
    private String articleCover;

    @ApiModelProperty("文章主图")
    private String articleMainPic;

    @ApiModelProperty("文章详情")
    private String articleDetail;

    @ApiModelProperty("店铺Id")
    private Integer shopId;

    @ApiModelProperty("活动引流标语")
    private String activityWord;

    @ApiModelProperty("活动ID")
    private Integer activityId;

    @ApiModelProperty("维度")
    private String latitude;

    @ApiModelProperty("经度、维度，逗号隔开")
    private String location;

    @ApiModelProperty("经度")
    private String longitude;

    @ApiModelProperty("文章状态")
    private Integer articleStatus;

    @ApiModelProperty("访问数量")
    private Integer accessCount;

    @ApiModelProperty("点赞数量")
    private Integer likeCount;

    @ApiModelProperty("收藏数量")
    private Integer favoriteCount;

    @ApiModelProperty("文章的发布者")
    private Integer userId;

    @ApiModelProperty("文章的发布者头像")
    private String userPic;

    @ApiModelProperty("文章的发布者名称")
    private String userName;

    @ApiModelProperty("文章涞源")
    private Integer source;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
