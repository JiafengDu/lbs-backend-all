package com.tarena.lbs.pojo.basic.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tarena.lbs.base.protocol.pager.BasePageQuery;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class TagLibraryQuery extends BasePageQuery implements Serializable {

    @ApiModelProperty(value = "id",example = "1")
    private Integer id;

    @ApiModelProperty(value = "标签类型(字典数据id)",example = "92==用户" +
            "93==内容" ,required = true)
    private Integer tagType;

    @ApiModelProperty(value = "标签名称",example = "测试")
    private String tagName;

    @ApiModelProperty(value = "标签类别(字典数据id)",example = "96")
    private Integer tagCategory;

    @ApiModelProperty(value = "标签状态",example = "1")
    private Integer status;

    @ApiModelProperty(value = "标签状态list",example = "1")
    private List<Integer> statusList;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始时间",example = "2023-01-01 01:01:01")
    private Date startingTime;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束时间",example ="2023-01-01 01:01:01")
    private Date endTime;

    //yes==包含自己  no==不包含自己
    @ApiModelProperty(value = "是否包含自己",example ="yes,no")
    private String self;


}