package com.bnzy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bnzy.domian.Grades;

import java.util.List;

public interface GradesService extends IService<Grades> {
    Integer removeGrades(List<String> id);
}
