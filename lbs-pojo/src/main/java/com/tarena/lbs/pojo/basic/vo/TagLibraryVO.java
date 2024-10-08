package com.tarena.lbs.pojo.basic.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class TagLibraryVO implements Serializable {

    @ApiModelProperty(value = "id",example = "1")
    private Integer id;

    @ApiModelProperty(value = "标签类型(字典数据id)",example = "92")
    private Integer tagType;

    @ApiModelProperty(value = "标签名称",example = "美食")
    private String tagName;

    @ApiModelProperty(value = "标签编码",example = "0cccd1a7-f68e-46b2-9e56-c543b4a8e02d")
    private String coding;

    @ApiModelProperty(value = "标签状态",example = "1")
    private Integer status;


    @ApiModelProperty(value = "使用次数",example = "11")
    private Integer usageCount;

    @ApiModelProperty(value = "标签描述",example = "好看的")
    private String tagDesc;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "标签创建时间",example = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "标签修改时间",example = "yyyy-MM-dd HH:mm:ss")
    private Date updateAt;

}