package com.tarena.lbs.pojo.content.entity;

import java.util.Date;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Data
@ToString
@Setting(shards = 1, replicas = 0)
@Document(indexName = "lbs_action") // 假设索引名为"actions"
public class ActionSearchEntity {
    @Id
    @Field(type = FieldType.Keyword)
    private String id;

    @Field(type = FieldType.Integer)
    private Integer articleUserId;

    @Field(type = FieldType.Auto)
    private String articleTitle;

    @Field(type = FieldType.Integer)
    private Integer behaviorUserId;

    @Field(type = FieldType.Date, format = {DateFormat.date_hour_minute_second})
    private Date createTime;

    @Field(type = FieldType.Integer)
    private Integer articleId;

    @Field(type = FieldType.Keyword)
    private String articleType;

    @Field(type = FieldType.Keyword)
    private String articleLabel;

    @Field(type = FieldType.Text)
    private String comment;

    @Field(type = FieldType.Integer)
    private Integer behavior;


}
