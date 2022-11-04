package com.bnzy.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bnzy.domian.Grades;
import com.bnzy.domian.SchoolStudent;
import com.bnzy.domian.Student;
import com.bnzy.dto.StudentDto;
import com.bnzy.service.GradesService;
import com.bnzy.service.SchoolStudentService;
import com.bnzy.service.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 读取学生信息生成studentdto
 */
@Component
public class ReadRedis {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StudentService studentService;
    @Autowired
    private GradesService gradesService;
    @Autowired
    private SchoolStudentService schoolStudentService;

    /**
     * 将学生信息写入缓存
     *
     * @return
     */

    @Scheduled(cron="${time.cron}")
    public void Read() {
        HashOperations hash = redisTemplate.opsForHash();
        //获取学生信息
        List<Student> list = studentService.list();
        List<StudentDto> studentDto = unreadStudent(list);


        for (StudentDto dto : studentDto) {
            hash.put(dto.getId(), "学号", dto.getId());
            hash.put(dto.getId(), "姓名", dto.getStudentName());
            hash.put(dto.getId(), "年龄", dto.getAge());
            hash.put(dto.getId(), "毕业学校", dto.getGraduateSchool());//毕业学校信息
            hash.put(dto.getId(), "性别", dto.getGender());
            hash.put(dto.getId(), "当前层次", dto.getLevel());
            hash.put(dto.getId(), "密码", dto.getPassword());
            hash.put(dto.getId(), "数学", dto.getMathematics());
            hash.put(dto.getId(), "英语", dto.getEnglish());
            hash.put(dto.getId(), "语文", dto.getChinese());
            hash.put(dto.getId(), "总分", dto.getScore());
            hash.put(dto.getId(), "录取学校", dto.getSchoolName());

        }


    }


    /**
     * 查询学生基本信息
     *
     * @return
     */
    public List<StudentDto> unreadStudent(List<Student> list) {


        //将Student集合转为StudentDto集合
        List<StudentDto> studentDtos = list.stream().map((item) -> {

            StudentDto studentDto = new StudentDto();
            //将对象item的属性拷贝为studentDto
            BeanUtils.copyProperties(item, studentDto);
            //根据学号查询成绩
            LambdaQueryWrapper<Grades> gradesWrapper = new LambdaQueryWrapper<>();
            gradesWrapper.eq(Grades::getStudentId, item.getId());
            //查询成绩
            Grades grades = gradesService.getOne(gradesWrapper);
            //不为空则进行数据填充
            if (grades != null) {
                studentDto.setMathematics(grades.getMathematics());
                studentDto.setEnglish(grades.getEnglish());
                studentDto.setChinese(grades.getChinese());
                studentDto.setScore(grades.getScore());
            }
            //根据学生id查询录取学校
            LambdaQueryWrapper<SchoolStudent> schoolStudentWrapper = new LambdaQueryWrapper<>();
            schoolStudentWrapper.eq(SchoolStudent::getStudent_id, item.getId());
            SchoolStudent schoolStudent = schoolStudentService.getOne(schoolStudentWrapper);
            //不为空则进行数据填充
            if (schoolStudent != null) {
                studentDto.setSchoolName(schoolStudent.getSchoolName());
            }

            return studentDto;
        }).collect(Collectors.toList());


        return studentDtos;
    }


}
