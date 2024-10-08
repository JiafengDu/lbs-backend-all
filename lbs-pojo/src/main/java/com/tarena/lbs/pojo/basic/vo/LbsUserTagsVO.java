package com.tarena.lbs.pojo.basic.vo;

import java.io.Serializable;
import lombok.Data;

@Data
public class LbsUserTagsVO implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer tagId;
}