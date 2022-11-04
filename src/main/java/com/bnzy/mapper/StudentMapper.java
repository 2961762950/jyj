package com.bnzy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bnzy.domian.Student;
import com.bnzy.dto.StudentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 有关学生的增删改查
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {



    //修改数据后缓存更新
    @Select("select\n" +
            "       s.id,s.student_name,s.age,s.graduate_school,s.gender,s.level,s.password,\n" +
            "       g.mathematics,g.english,g.chinese,g.score,ss.school_name\n" +
            "\n" +
            "from student s,school_student ss,grades g\n" +
            "\n" +
            " where s.id=ss.student_id and s.id=g.student_id and s.id=#{id};"
    )
    StudentDto selectStudent(String id);

    /**
     * 学校教育局分页查询
     *
     * @param page  分页查询基本对象
     * @param student 学生类（可以进行学号查询，姓名查询或俩者一起）
     * @param school 毕业学校（学校查看本校毕业生）
     * @param schoolName 录取学校（查看本校录取情况）
     * @return 分页查询的结果
     */
    @Select("<script>" +
            "select\n" +
            "       s.id,s.student_name,s.age,s.graduate_school,s.gender,s.level,s.password,\n" +
            "       g.mathematics,g.english,g.chinese,g.score,ss.school_name\n" +
            "\n" +
            "from student s,school_student ss,grades g\n" +
            "\n" +
            " where s.id=ss.student_id and s.id=g.student_id" +

            "<if test='student.studentName  !=  null'>" +
            " and  s.student_name=#{student.studentName} " +
            "</if> " +

            "<if test='student.id != null'>" +
            "and s.id=#{student.id}" +
            "</if>" +

            "<if test='school !=  null'>" +
            "and s.graduate_school=#{school}" +
            "</if>" +

            "<if test='schoolName !=  null'>" +
            "and ss.school_name=#{schoolName}" +
            "</if>" +

            "</script>")
    IPage<StudentDto> getStudent(@Param("page") Page<StudentDto> page, @Param("student") Student student, @Param("school") String school, @Param("schoolName") String schoolName);

    /**
     * 根据学号进行修改
     * @param studentDto
     * @return
     */

    @Update("update student s,school_student ss,grades g " +
            "set s.student_name=#{studentDto.studentName},s.graduate_school=#{studentDto.graduateSchool},s.age=#{studentDto.age},s.gender=#{studentDto.gender}," +
            "s.level=#{studentDto.level},g.mathematics=#{studentDto.mathematics},g.english=#{studentDto.english}," +
            "g.chinese=#{studentDto.chinese},g.score=#{studentDto.score},ss.school_name=#{studentDto.schoolName}" +
            "where s.id=#{studentDto.id} and ss.student_id=#{studentDto.id} and g.student_id=#{studentDto.id}")
    int upDataStudent(@Param("studentDto") StudentDto studentDto);

}
