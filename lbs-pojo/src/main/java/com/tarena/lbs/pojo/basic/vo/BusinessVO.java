package com.tarena.lbs.pojo.basic.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class BusinessVO implements Serializable {

    @ApiModelProperty(value = "id",example = "1")
    private Integer id;

    @ApiModelProperty(value = "商家名称",example = "美食第一店")
    private String businessName;

    @ApiModelProperty(value = "商家行业类型（字典数据id）",example = "89")
    private Integer industryType;


    @ApiModelProperty(value = "负责人姓名",example = "张莉莎")
    private String businessHeadName;

    @ApiModelProperty(value = "负责人电话",example = "18654236854")
    private String businessHeadPhone;

    @ApiModelProperty(value = "公司地址",example = "荔枝大厦")
    private String companyAddress;

    @ApiModelProperty(value = "营业执照",example = "string")
    private String businessLicense;

    @ApiModelProperty(value = "商家简介",example = "开创最好吃的美食")
    private String businessIntroduction;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "入驻时间",example = "2023-01-01 01:01:01")
    private Date entryTime;

    @ApiModelProperty(value = "商家状态",example = "1")
    private Integer businessStatus;

    @ApiModelProperty(value = "审核备注",example = "同意")
    private String auditRemarks;

    @ApiModelProperty(value = "行业名称",example = "美食")
    private String industryName;

    @ApiModelProperty(value = "商家头像")
    private String businessLogo;


}
