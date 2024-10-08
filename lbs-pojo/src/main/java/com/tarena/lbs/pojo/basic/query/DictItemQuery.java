package com.tarena.lbs.pojo.basic.query;

import com.tarena.lbs.base.protocol.pager.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@ApiModel(value = "DictItemQuery", description = "字典数据查询参数")
@Data
public class DictItemQuery extends BasePageQuery implements Serializable {

    @ApiModelProperty(value = "显示标签")
    private String label;

    @ApiModelProperty(value = "状态 0可用  1不可用")
    private Integer status;

    private List<Integer> statusList;

    @ApiModelProperty(value = "字典id",required = true)
    private Integer dictId;

    private String dictValue;

    private String self;

    private Integer id;
}
