package com.tarena.lbs.pojo.content.param;
import lombok.Data;
@Data
public class ArticleContentParam {
    private Integer id;
    private String articleTitle;
    private Integer articleCategoryId;
    private String articleCategory;
    private String articleLabel;
    private String articleDigest;
    private String articleCover;
    private String articleMainPic;
    private String articleDetail;
    private Integer shopId;
    private String activityWord;
    private Integer activityId;
    private String latitude;
    private String longitude;
    //1===（待发布） 2===（已发布） 3===下线
    private Integer articleStatus;
    private Integer accessCount;
    private Integer likeCount;
    private Integer favoriteCount;
    //1==代表前台添加   2==代表后台添加
    private Integer source;
}