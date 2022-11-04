package com.bnzy.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bnzy.domian.Grades;
import com.bnzy.mapper.GradesMapper;
import com.bnzy.service.GradesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradesServiceImp extends ServiceImpl<GradesMapper, Grades> implements GradesService {

    @Autowired
    private GradesMapper gradesMapper;

    @Override
    public Integer removeGrades(List<String> id) {
        int num = 0;
        for (String s : id) {
            gradesMapper.removeGrades(s);
            num += 1;
        }
        int i = 1 / 0;

        return num;
    }
}
