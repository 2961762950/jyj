package com.bnzy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bnzy.domian.SchoolStudent;
import com.bnzy.domian.Student;
import com.bnzy.dto.StudentDto;

import java.util.List;

public interface SchoolStudentService extends IService<SchoolStudent> {

    //教育局-学生分页查询
    IPage<StudentDto> page(Page<StudentDto> page, Student student, String school, String schoolName);
    //教育局-修改学生信息
    Integer upData(StudentDto studentDto);
    //删除（单个或者批量）
    void removeGrades(List<String> ids);
}
