package com.tarena.lbs.pojo.basic.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreVO implements Serializable {

    private Integer id;

    private Integer businessId;

    private String storeName;

    private String storePhone;

    private String storeHeadName;

    private String storeHeadPhone;

    private Integer provinceId;

    private Integer areaId;

    private Integer cityId;

    private String storeLocation;

    private String storeLongitude;

    private String storeLatitude;

    private List<PictureVO> pictureVOS;

    private Integer userId;

    private String storeLogo;

    private String storeLogoPic;

    private String storeIntroduction;

    private Integer sort;

    private String tradeTime;

    private Integer storeStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}