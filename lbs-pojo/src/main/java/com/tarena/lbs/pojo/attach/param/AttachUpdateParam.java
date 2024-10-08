package com.tarena.lbs.pojo.attach.param;

import java.io.Serializable;
import lombok.Data;

@Data
public class AttachUpdateParam implements Serializable {

    private Integer isCover;

    private Integer businessType;

    private Integer businessId;

    private Integer id;
}
