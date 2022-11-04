package com.bnzy.domian;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 学生，学校中间表（学校录取的学生）
 */
@Data
public class SchoolStudent implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.INPUT)//数据库id自行增长

    private Integer id;

    private Long student_id;//学生id（学号）

    private Integer school_id;//学校id

    private String schoolName;//学校名称

    private LocalDateTime revise;//时间

}
