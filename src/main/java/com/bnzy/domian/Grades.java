package com.bnzy.domian;

import lombok.Data;

/**
 * 学生成绩
 */
@Data
public class Grades {

    private Long studentId;//学号

    private Integer mathematics;//数学

    private Integer english;//英语

    private Integer chinese;//语文

    private Integer score;//总分



}
