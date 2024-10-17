package com.tarena.basic.test.es.practice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String name;
    private String studentNo;
    private LocalDate birthday;
    private String location;
    private String gender;
}

