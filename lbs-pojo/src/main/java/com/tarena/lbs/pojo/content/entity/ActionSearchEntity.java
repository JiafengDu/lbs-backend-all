package com.tarena.lbs.pojo.content.entity;

import java.util.Date;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Data
@ToString
@Setting(shards = 1, replicas = 0)
@Document(indexName = "lbs_action") // 假设索引名为"actions"
public class ActionSearchEntity {

    @Id
    private String id;

    @Field(type = FieldType.Integer)
    private Integer articleUserId;

    @Field(type = FieldType.Auto)
    private String articleTitle;

    @Field(type = FieldType.Integer)
    private Integer behaviorUserId;

    @Field(type = FieldType.Date,format= {},pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Field(type = FieldType.Integer)
    private Integer articleId;

    @Field(type = FieldType.Keyword)
    private String articleType;

    @Field(type = FieldType.Keyword)
    private String articleLabel;

    @Field(type = FieldType.Integer)
    private Integer action;

    @Field(type = FieldType.Text)
    private String comment;

    @Field(type = FieldType.Integer)
    private Integer behavior;
}
