package com.tarena.lbs.pojo.message.param;

import com.tarena.lbs.base.protocol.pager.BasePageQuery;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import lombok.Data;

@Data
@ApiModel(description = "行为查询数据")
public class TriggerMessageQueryParam extends BasePageQuery implements Serializable {

    /**
     * 用户动作 如：注册，关注
     */
    private String userAction;

}
