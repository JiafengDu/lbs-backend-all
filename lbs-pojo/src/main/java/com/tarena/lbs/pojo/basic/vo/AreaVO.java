package com.tarena.lbs.pojo.basic.vo;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class AreaVO implements Serializable {

    private Long id;

    private Long parentId;

    private String name;

    private List<AreaVO> children;
}