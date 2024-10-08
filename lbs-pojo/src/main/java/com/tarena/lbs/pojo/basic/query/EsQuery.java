package com.tarena.lbs.pojo.basic.query;

import com.tarena.lbs.base.protocol.pager.BasePageQuery;
import java.io.Serializable;
import lombok.Data;

@Data
public class EsQuery extends BasePageQuery implements Serializable {

    //索引名
    private String indexName;

    //查询字段field
    private String field;

    //查询值
    private String word;

    //分页参数
    private Integer from;

    //每页大小
    private Integer size;

    public Integer getSize() {
        return size==0?30:size;
    }
}


