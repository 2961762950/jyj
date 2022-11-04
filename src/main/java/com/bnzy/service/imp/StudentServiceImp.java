package com.bnzy.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bnzy.domian.Grades;
import com.bnzy.domian.School;
import com.bnzy.domian.SchoolStudent;
import com.bnzy.domian.Student;
import com.bnzy.dto.StudentDto;
import com.bnzy.mapper.StudentMapper;
import com.bnzy.service.GradesService;
import com.bnzy.service.SchoolService;
import com.bnzy.service.SchoolStudentService;
import com.bnzy.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class StudentServiceImp extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private GradesService gradesService;
    @Lazy//bean的加载进行懒加载（解决循坏依赖）
    @Autowired
    private SchoolStudentService schoolStudentService;
    @Lazy//bean的加载进行懒加载（解决循坏依赖）
    @Autowired
    private StudentService studentService;


    /**
     * //     * 学校/教育局分页查询
     * //     *
     *
     * @param page       分页查询基本对象
     * @param student    学生类（可以进行学号查询，姓名查询或俩者一起）
     * @param school     毕业学校（学校查看本校毕业生）
     * @param schoolName 录取学校（查看本校录取情况）
     * @return 分页查询的结果
     * @return
     */
    @Override
    public IPage<StudentDto> page1(Page<StudentDto> page, Student student, String school, String schoolName) {

        return studentMapper.getStudent(page, student, school, schoolName);
    }

    @Transactional(rollbackFor = Exception.class)//出现异常进行回滚
    @Override
    public void enrollmentStudent() {
        System.out.println(666);
        //begin=当前操作的学校（学校排名）,len=数据库中总的学校数目,current当前录取的的学生下角标,studentSize学生人数
        int begin = 1, len = 0, current = 0, studentSize = 0;
        len = schoolService.list().size();

        //对成绩进行查询并且按降序排列
        List<Grades> list = new ArrayList<>();
        LambdaQueryWrapper<Grades> gradesWrapper = new LambdaQueryWrapper<>();
        gradesWrapper.orderByDesc(Grades::getScore);
        list = gradesService.list(gradesWrapper);//全部成绩
        studentSize = list.size();

        List<Grades> gradesList = new ArrayList<>();//当前学校所录取学生的成绩
        List<SchoolStudent> schoolStudentsList = new ArrayList<>();//生成的学校与学生中间表


        for (int j = 0; j < len; j++) {

            //1、根据排名读取学校录取人数
            LambdaQueryWrapper<School> schoolWrapper = new LambdaQueryWrapper<>();
            schoolWrapper.eq(School::getRanking, begin);
            begin += 1;

            School school = schoolService.getOne(schoolWrapper);
            //当对象为空时终止循环，进入下一次
            if (school == null) {
                continue;
            }


            //2读取学校需要录取名额
            Integer enrollment = school.getEnrollment();


            gradesList.clear();
            for (int i = 0; i < enrollment; i++) {
                if (current > studentSize) {
                    break;
                }
                gradesList.add(list.get(current));
                current += 1;
            }

            //3.生成学校和学生中间表
            schoolStudentsList.clear();
            for (int i = 0; i < enrollment; i++) {
                System.out.println(gradesList.get(i).getStudentId());
                SchoolStudent schoolStudent = new SchoolStudent();
                schoolStudent.setStudent_id(gradesList.get(i).getStudentId());
                schoolStudent.setSchool_id(school.getId());
                schoolStudent.setSchoolName(school.getName());
                schoolStudent.setRevise(LocalDateTime.now());
                schoolStudentsList.add(schoolStudent);

            }
            //生成学生与学校的中间表
            schoolStudentService.saveBatch(schoolStudentsList);


            //5、结束
        }
    }

    /**
     * 修改密码后进行缓存更新
     *
     * @param student
     */
    @Override
    public void upDataStudent(Student student) {
        log.error("开启缓存");


        HashOperations hash = redisTemplate.opsForHash();
        //多表查询结果返回
        StudentDto dto = studentMapper.selectStudent(student.getId());
        //加入缓存
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

//---------------------------------------------------------------------------------------------------




}
