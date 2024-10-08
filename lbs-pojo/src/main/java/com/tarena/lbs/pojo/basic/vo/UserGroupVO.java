package com.tarena.lbs.pojo.basic.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UserGroupVO implements Serializable {
    private Integer id;
    private String groupName;
    private String tagIds;
    private Integer userNumber;
    private Integer businessId;
    private LocalDateTime createAt;
}
