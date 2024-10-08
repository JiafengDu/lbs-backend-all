package com.tarena.lbs.pojo.basic.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tarena.lbs.base.protocol.pager.BasePageQuery;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class BusinessQuery extends BasePageQuery implements Serializable {

    @ApiModelProperty(value = "商家名称",example = "美食第一店")
    private String businessName;

    @ApiModelProperty(value = "负责人电话",example = "18654236854")
    private String businessHeadPhone;

    @ApiModelProperty(value = "商家状态")
    private Integer businessStatus;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始时间",example = "2023-01-01 01:01:01")
    private Date startingTime;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束时间",example = "2023-01-01 01:01:01")
    private Date endTime;
    @Ignore
    private Integer businessId;
}
