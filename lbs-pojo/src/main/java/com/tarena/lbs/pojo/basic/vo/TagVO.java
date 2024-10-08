package com.tarena.lbs.pojo.basic.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TagVO implements Serializable {

    @ApiModelProperty(value = "标签分类",example = "旅行")
    private String tagCategoryName;

    private TagLibraryVO tagLibraryBO;

}
