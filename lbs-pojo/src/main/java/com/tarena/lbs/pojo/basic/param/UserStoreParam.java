package com.tarena.lbs.pojo.basic.param;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStoreParam implements Serializable {

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("店铺id")
    private Integer storeId;

}