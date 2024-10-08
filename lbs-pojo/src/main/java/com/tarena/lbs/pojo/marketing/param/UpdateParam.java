package com.tarena.lbs.pojo.marketing.param;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateParam implements Serializable {

    @NotNull(message = "status不能为空")
    @ApiModelProperty("状态1启用0禁用2删除")
    private Integer status;

    @NotNull(message = "id不能为空")
    @ApiModelProperty("ID")
    private Integer Id;

}
