package com.tarena.lbs.pojo.basic.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@ApiModel("字典数据基本信息响应结果")
@Data
public class DictItemVO implements Serializable {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty(value = "字典id")
    private Integer dictId;

    @ApiModelProperty(value = "显示标签")
    private String label;

    @ApiModelProperty(value = "字典值")
    private String dictValue;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "状态 0可用  1不可用")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
}
