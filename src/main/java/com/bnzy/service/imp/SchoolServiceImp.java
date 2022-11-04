package com.bnzy.service.imp;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bnzy.domian.School;
import com.bnzy.mapper.SchoolMapper;
import com.bnzy.service.SchoolService;
import org.springframework.stereotype.Service;

@Service
public class SchoolServiceImp extends ServiceImpl<SchoolMapper, School> implements SchoolService {


}
