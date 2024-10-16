package com.tarena.lbs.pojo.basic.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;


@Data
@Document(indexName = "lbs_store")
@Setting(shards = 1,replicas = 0)
public class StoreSearchEntity {
    @Id
    private Integer id;
    @GeoPointField
    private String location;
    @Field(type = FieldType.Text,analyzer="ik_max_word")
    private String storeName;
    //添加一个属性,关联商家的id
    @Field(type = FieldType.Integer)
    private Integer businessId;
}

