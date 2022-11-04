package com.bnzy.service.imp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bnzy.domian.SchoolStudent;
import com.bnzy.domian.Student;
import com.bnzy.dto.StudentDto;
import com.bnzy.mapper.SchoolStudentMapper;
import com.bnzy.mapper.StudentMapper;
import com.bnzy.service.GradesService;
import com.bnzy.service.SchoolStudentService;
import com.bnzy.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class SchoolStudentServiceImp extends ServiceImpl<SchoolStudentMapper, SchoolStudent> implements SchoolStudentService {
    @Autowired
    private GradesService gradesService;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StudentService studentService;

    @Autowired
    private SchoolStudentMapper schoolStudentMapper;

    /**
     * @param page       分页查询基本对象
     * @param student    学生类（可以进行学号查询，姓名查询或俩者一起）
     * @param school     毕业学校（学校查看本校毕业生）
     * @param schoolName 录取学校（查看本校录取情况）
     * @return 分页查询的结果
     */
    @Override
    public IPage<StudentDto> page(Page<StudentDto> page, Student student, String school, String schoolName) {

        return studentMapper.getStudent(page, student, school, schoolName);
    }

    /**
     * 教育局-修改学生信息
     *
     * @param studentDto 被修改的对象
     * @return 被操作的条数（正常情况应该为3）
     */

    @Override
    public Integer upData(StudentDto studentDto) {

        return studentMapper.upDataStudent(studentDto);

    }

    /**
     * 删除
     *
     * @param ids 别删除的id
     */
    @Transactional(rollbackFor = Exception.class)//出现异常进行回滚
    @Override
    public void removeGrades(List<String> ids) {
        //删除学生表
        studentService.removeByIds(ids);
        //删除成绩表
        gradesService.removeGrades(ids);
        //删除学生与学校中间表
        for (String id : ids) {
            schoolStudentMapper.removeSchoolStudent(id);
        }



    }

}
