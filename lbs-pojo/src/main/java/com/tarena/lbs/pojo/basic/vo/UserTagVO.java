package com.tarena.lbs.pojo.basic.vo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
@Deprecated
public class UserTagVO implements Serializable {

    @ApiModelProperty(value = "标签分类",example = "旅行")
    private String tagCategoryName;

    private TagLibraryVO tagLibraryBO;

}
