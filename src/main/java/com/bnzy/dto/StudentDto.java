package com.bnzy.dto;

import com.bnzy.domian.Student;
import lombok.Data;

@Data
public class StudentDto extends Student {

    private Integer mathematics;//数学

    private Integer english;//英语

    private Integer chinese;//语文

    private Integer score;//总分

    private String schoolName;//学校名称
}
