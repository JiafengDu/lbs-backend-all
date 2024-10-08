package com.tarena.lbs.pojo.content.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

@Data
@ApiModel("分类结果返回")
public class ArticleCategoryVO {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("分类名称")
    private String categoryName;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("分类状态")
    private Integer categoryStatus;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;
}
