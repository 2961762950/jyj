package com.bnzy.domian;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 学生信息
 */
@Data
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.INPUT)//数据库id自行增长
    private String id;//学号

    private String studentName;//学生名字

    private Integer age;

    private String graduateSchool;//毕业学校

    private String gender;//性别

    private String level;//当前层次

    private String password;

//    private Integer state;//0表示已经被录取1表示未被录取


}
