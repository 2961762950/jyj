package com.bnzy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bnzy.domian.Grades;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GradesMapper extends BaseMapper<Grades> {

    @Delete("delete from jyjv.grades where student_id in (#{id})")
    Integer removeGrades(@Param("id") String id);
}
