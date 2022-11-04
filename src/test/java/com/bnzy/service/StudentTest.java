package com.bnzy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bnzy.domian.Student;
import com.bnzy.dto.StudentDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StudentTest {
    @Autowired
    private StudentService studentService;
    @Autowired
    private GradesService gradesService;

//    @Autowired
//    private ReadRedis readRedis;
//    @Test
//    public void setReadRedis(){
//        readRedis.Read();
//    }

    @Test
    public void state() {
        Page<StudentDto> page = new Page<>(0, 1);
        Student student = new Student();
//        student.setId("202025020032");
        IPage<StudentDto> page1 = studentService.page1(page, student, null, null);
        System.out.println(page1.getRecords().size());
    }

    @Test
    public void enrollmentStudent() {
        System.out.println("开始。。。");
        studentService.enrollmentStudent();


        System.out.println("结束。。。");
    }

}
