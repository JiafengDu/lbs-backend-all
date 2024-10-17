package com.tarena.basic.test.es;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocEntity {
    private Integer id;
    private String location;
    private String name;
}
