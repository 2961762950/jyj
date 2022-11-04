package com.bnzy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bnzy.domian.SchoolStudent;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SchoolStudentMapper extends BaseMapper<SchoolStudent> {

    @Delete("delete from jyjv.school_student where student_id=#{id}")
    Integer removeSchoolStudent(@Param("id") String id);
}
