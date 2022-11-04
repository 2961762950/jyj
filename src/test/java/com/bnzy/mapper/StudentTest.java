package com.bnzy.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bnzy.domian.Student;
import com.bnzy.dto.StudentDto;
import com.bnzy.service.GradesService;
import com.bnzy.service.SchoolStudentService;
import com.bnzy.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
public class StudentTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private GradesMapper gradesMapper;

    @Autowired
    private GradesService gradesService;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private SchoolStudentService schoolStudentService;

    @Test
    public void a() {
        Page<StudentDto> page = new Page<>(0, 1);
        Student student = new Student();
//        student.setId("202025020032");
        student.setStudentName("肖泽斌");
        IPage<StudentDto> page1 = studentMapper.getStudent(page, student, null, "清华大学");
        System.out.println(page1.getPages());
        List<StudentDto> records = page1.getRecords();
        StudentDto studentDto = records.get(0);
        System.out.println(studentDto.toString());

        studentDto.setMathematics(100);

        int i = studentMapper.upDataStudent(studentDto);
        System.out.println(i);

    }

    @Test
    public void b() {
        List<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("2");
        strings.add("3");

        schoolStudentService.removeGrades(strings);


    }


}
