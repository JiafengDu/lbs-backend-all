package com.tarena.lbs.pojo.attach.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class AttachQrDTO implements Serializable {
    private Integer id;
    private String fileUuid;
    private String url;
}
