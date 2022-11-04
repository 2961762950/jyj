package com.bnzy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bnzy.domian.Student;
import com.bnzy.dto.StudentDto;

public interface StudentService extends IService<Student> {

    //学生信息修改后进行缓存更新
    void upDataStudent(Student student);

    //学校-学生分页查询
    IPage<StudentDto> page1(Page<StudentDto> page,Student student, String school,String schoolName);
    //学校录取学校
    void enrollmentStudent();

}
