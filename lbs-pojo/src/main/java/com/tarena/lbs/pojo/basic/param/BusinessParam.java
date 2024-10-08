package com.tarena.lbs.pojo.basic.param;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class BusinessParam implements Serializable {

    @ApiModelProperty(value = "id",example = "1", required = true)
    private Integer id;

    @ApiModelProperty(value = "商家名称",example = "美食第一店", required = false)
    private String businessName;

//   industryType =(字典) dictItemId;
    @ApiModelProperty(value = "商家行业类型（字典数据id）",example = "34", required = false)
    private Integer industryType;

    @ApiModelProperty(value = "负责人姓名",example = "张莉莎", required = false)
    private String businessHeadName;

    @ApiModelProperty(value = "负责人电话",example = "18654236854", required = false)
    private String businessHeadPhone;

    @ApiModelProperty(value = "公司地址",example = "荔枝大厦", required = false)
    private String companyAddress;

    @ApiModelProperty(value = "营业执照",example = "string", required = false)
    private String businessLicense;

    @ApiModelProperty(value = "商家简介",example = "开创最好吃的美食", required = false)
    private String businessIntroduction;

    @ApiModelProperty(value = "商家状态 1 2 3 4 5", required = false)
    private Integer businessStatus;

    @ApiModelProperty(value = "审核备注",example = "同意", required = false)
    private String auditRemarks;

    @ApiModelProperty(value = "商家头像")
    private String businessLogo;


}
