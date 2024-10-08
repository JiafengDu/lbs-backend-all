package com.tarena.lbs.pojo.content.entity;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Data
@ToString
@Document(indexName = "lbs_article")
@Setting(shards = 1, replicas = 0)
public class ArticleSearchEntity {

    @Id
    @Field(type = FieldType.Integer)
    private Integer id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String articleTitle;

    @Field(type = FieldType.Integer)
    private Integer articleCategoryId;

    @Field(type = FieldType.Keyword)
    private String articleLabel;

    @Field(type = FieldType.Text)
    private String articleDigest;
    @Field(type = FieldType.Keyword)
    private String articleUsername;
    @Field(type = FieldType.Keyword)
    private String articleCover;

    @Field(type = FieldType.Keyword)
    private String articleMainPic;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String articleDetail;

    @Field(type = FieldType.Integer)
    private Integer shopId;
    //活动标语  吐血大甩卖 冬季打折 全场8折 es内置有一些分词器 standard simple
    @Field(type = FieldType.Text)
    private String activityWord;

    @Field(type = FieldType.Integer)
    private Integer activityId;

    @Field(type = FieldType.Text)
    private String latitude;

    @GeoPointField
    private String location; // 注意：这里假设location是GeoPoint类型

    @Field(type = FieldType.Text)
    private String longitude;

    @Field(type = FieldType.Integer)
    private Integer articleStatus;

    @Field(type = FieldType.Integer)
    private Integer accessCount;

    @Field(type = FieldType.Integer)
    private Integer likeCount;

    @Field(type = FieldType.Integer)
    private Integer favoriteCount;

    @Field(type = FieldType.Integer)
    private Integer userId;

    @Field(type = FieldType.Integer)
    private Integer source;

    @Field(type = FieldType.Date, format = {DateFormat.date_hour_minute_second})
    private Date createTime;

    @Field(type = FieldType.Date, format = {DateFormat.date_hour_minute_second})
    private Date updateTime;
}
