package com.tarena.lbs.pojo.message.query;

import com.tarena.lbs.pojo.message.param.TriggerMessageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户注册后，发给消息模块的数据
 */
@Data
@ApiModel("查询某个用户最新消息")
public class MessageQuery extends TriggerMessageParam {

    @ApiModelProperty("用户编号")
    private Integer userId;

    @ApiModelProperty("2公告,1活动消息,0互动消息")
    private Integer type;

}
