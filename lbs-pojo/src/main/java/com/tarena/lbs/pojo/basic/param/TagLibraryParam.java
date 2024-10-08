package com.tarena.lbs.pojo.basic.param;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class TagLibraryParam implements Serializable {
    @ApiModelProperty(value = "id",example = "1", required = true)
    private Integer id;
    //   tagType =(字典) dictItemId;
    @ApiModelProperty(value = "标签类型(字典数据id)",example = "92", required = true)
    private Integer tagType;

    @ApiModelProperty(value = "标签名称",example = "测试", required = true)
    private String tagName;

    @ApiModelProperty(value = "标签类别(字典数据id)",example = "96", required = true)
    private Integer tagCategory;

//    @ApiModelProperty(value = "标签编码",example = "0cccd1a7-f68e-46b2-9e56-c543b4a8e02d", required = false)
//    private String coding;

    @ApiModelProperty(value = "标签状态",example = "1", required = false)
    private Integer status;

    @ApiModelProperty(value = "使用次数",example = "11", required = false)
    private Integer usageCount;

    @ApiModelProperty(value = "标签描述",example = "好看的", required = false)
    private String tagDesc;

    @ApiModelProperty(value = "前端可见性",example = "0:public、1:private", required = false)
    private Integer visibisity;

//    @ApiModelProperty(value = "标签创建时间",example = "yyyy-MM-dd HH:mm:ss", required = false)
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date createAt;
//
//    @ApiModelProperty(value = "标签修改时间",example = "yyyy-MM-dd HH:mm:ss", required = false)
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date updateAt;
}