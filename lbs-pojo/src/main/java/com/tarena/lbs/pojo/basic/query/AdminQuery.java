package com.tarena.lbs.pojo.basic.query;

import com.tarena.lbs.base.protocol.pager.BasePageQuery;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class AdminQuery extends BasePageQuery implements Serializable {

    @ApiModelProperty(value = "手机号")
    private String accountPhone;

    @ApiModelProperty(value = "用户名")
    private String nickname;
}