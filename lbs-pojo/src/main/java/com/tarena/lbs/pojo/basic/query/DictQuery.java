package com.tarena.lbs.pojo.basic.query;

import com.tarena.lbs.base.protocol.pager.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@ApiModel(value = "DictQuery", description = "字典查询参数")
@Data
public class DictQuery extends BasePageQuery implements Serializable {

    @ApiModelProperty(value = "字典名称")
    private String dictName;

    @ApiModelProperty(value = "字典类型")
    private String dictType;

    @ApiModelProperty(value = "状态 0可用  1不可用")
    private Integer status;

    private List<Integer> statusList;

    //yes==包含自己  no==不包含自己
    private String self;

    private Integer id;
}
