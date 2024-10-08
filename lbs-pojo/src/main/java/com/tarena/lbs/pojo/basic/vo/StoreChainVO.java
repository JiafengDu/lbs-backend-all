package com.tarena.lbs.pojo.basic.vo;

import java.io.Serializable;
import lombok.Data;

@Data
public class StoreChainVO implements Serializable {

    private Integer id;

    private String storeName;

    private String storeAddress;

    private String businessName;

    private String link;

    private String linkImg;

    private Integer linkStatus;


}
