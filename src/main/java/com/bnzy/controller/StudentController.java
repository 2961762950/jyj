package com.bnzy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bnzy.common.R;
import com.bnzy.domian.Student;
import com.bnzy.dto.StudentDto;
import com.bnzy.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/student")
@CrossOrigin //允许跨域访问
public class StudentController {
    //学生能否进行查询true为可以查询,false为不可以查询
    private boolean select = true;

    @Autowired
    private StudentService studentService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 学生端查询
     * 在缓存中读取信息
     *
     * @param student
     * @return
     */
    @GetMapping()
    public R<StudentDto> select(Student student) {
        if (!select) {
            return R.error("窗口暂未开放");
        }

        HashOperations hash = redisTemplate.opsForHash();
        StudentDto dto = new StudentDto();
        //查询缓存id
        String id = (String) hash.get(student.getId(), "学号");
        //查询缓存密码
        String password = (String) hash.get(student.getId(), "密码");
        //把参数密码加密
        String studentPassword = DigestUtils.md5DigestAsHex(student.getPassword().getBytes());


        //判断参数id和缓存id是否一样
        //判断参数密码和缓存密码是否一样（md5加密的）
        if (student.getId().equals(id) && studentPassword.equals(password)) {


            dto.setId(student.getId());
            dto.setStudentName((String) hash.get(student.getId(), "姓名"));
            dto.setAge((Integer) hash.get(student.getId(), "年龄"));
            dto.setGraduateSchool((String) hash.get(student.getId(), "毕业学校"));
            dto.setGender((String) hash.get(student.getId(), "性别"));
            dto.setLevel((String) hash.get(student.getId(), "当前层次"));
//            dto.setPassword((String)hash.get(student.getId(),"密码" ));
            dto.setMathematics((Integer) hash.get(student.getId(), "数学"));
            dto.setEnglish((Integer) hash.get(student.getId(), "英语"));
            dto.setChinese((Integer) hash.get(student.getId(), "语文"));
            dto.setScore((Integer) hash.get(student.getId(), "总分"));
            dto.setSchoolName((String) hash.get(student.getId(), "录取学校"));


            return R.success(dto);


        }
        return R.error("未查询到信息，请确认");
    }

    /**
     * 学生修改密码
     *
     * @param student 携带 学号和更新的密码
     */
    @PostMapping
    public R<String> upDataStudent(@RequestBody Student student) {

        if (!select) {
            return R.error("窗口暂未开放");
        }


        //密码加密
        student.setPassword(DigestUtils.md5DigestAsHex(student.getPassword().getBytes()));
        //查询学生是否存在
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(student.getId() != null, Student::getId, student.getId());
        wrapper.eq(student.getPassword() != null, Student::getPassword, student.getPassword());

        Student one = studentService.getOne(wrapper);
        if (one == null) {
            return R.error("学号有误，请确认。");
        }
        //修改的密码放在studentanme属性上。将其取出填到password上
        student.setPassword(DigestUtils.md5DigestAsHex(student.getStudentName().getBytes()));
        //将修改的密码去除
        student.setStudentName(null);

        boolean b = studentService.updateById(student);

        if (b) {
            //修改成功后跟新缓存
            studentService.upDataStudent(student);
            return R.success("修改成功");
        } else {
            return R.error("修改失败请稍后再试");
        }
    }

    /**
     * 3
     *
     * @param selects 需改为什么（真或者假）
     * @return 修改后当前结果
     */
    @PutMapping("/revise")
    public R<Boolean> selectStudent(Boolean selects) {

        select = selects;

        return R.success(select);
    }

    /**
     * 录取学生
     *
     * @return 操作结果
     */
    @PostMapping("/state")
    public R<String> enrollmentStudent() {

        studentService.enrollmentStudent();

        return R.success("录取成功");
    }


}
