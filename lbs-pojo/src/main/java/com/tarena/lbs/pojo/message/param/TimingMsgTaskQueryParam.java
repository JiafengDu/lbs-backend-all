package com.tarena.lbs.pojo.message.param;

import com.tarena.lbs.base.protocol.pager.BasePageQuery;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
@ApiModel(description = "定时消息任务查询数据")
public class TimingMsgTaskQueryParam extends BasePageQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer triggerStatus;

    private String jobDesc;

    private Date startDate;

    private Date endDate;
}