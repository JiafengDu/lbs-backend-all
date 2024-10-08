package com.tarena.lbs.pojo.basic.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("lbs_business") // 指定表名
@NoArgsConstructor
@AllArgsConstructor
public class BusinessPO implements Serializable {

    @TableId(value = "id", type = IdType.AUTO) // 主键ID，类型为自动增长
    private Integer id;

    @TableField("business_name") // 映射字段business_name
    private String businessName;

    @TableField("industry_type") // 映射字段industry_type
    private Integer industryType;

    @TableField("business_head_name") // 映射字段business_head_name
    private String businessHeadName;

    @TableField("business_head_phone") // 映射字段business_head_phone
    private String businessHeadPhone;

    @TableField("company_address") // 映射字段company_address
    private String companyAddress;

    @TableField("business_license") // 映射字段business_license
    private String businessLicense;

    @TableField("business_introduction") // 映射字段business_introduction
    private String businessIntroduction;

    @TableField("entry_time") // 映射字段entry_time
    private Date entryTime;

    @TableField("business_status") // 映射字段business_status
    private Integer businessStatus;

    @TableField("audit_remarks") // 映射字段audit_remarks
    private String auditRemarks;

    @TableField("business_logo") // 映射字段business_logo
    private String businessLogo;
}
