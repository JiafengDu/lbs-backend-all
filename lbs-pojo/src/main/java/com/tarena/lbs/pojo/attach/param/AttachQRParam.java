package com.tarena.lbs.pojo.attach.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachQRParam implements Serializable {
    //你想要你的二维码里面扫完了 访问哪个地址的字符串
    private String content;
    private Integer businessType;
    private Integer businessId;
}
