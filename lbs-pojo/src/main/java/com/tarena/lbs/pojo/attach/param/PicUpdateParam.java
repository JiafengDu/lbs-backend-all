package com.tarena.lbs.pojo.attach.param;

import java.io.Serializable;
import lombok.Data;

@Data
public class PicUpdateParam implements Serializable {
    private Integer id;//图片id
    private String fileUuid;//图片uuid
    private Integer businessType;//业务类型
    private Integer businessId;//业务id
}
