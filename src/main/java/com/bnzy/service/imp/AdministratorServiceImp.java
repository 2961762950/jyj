package com.bnzy.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bnzy.mapper.AdministratorMapper;
import com.bnzy.domian.Administrator;
import com.bnzy.service.AdministratorService;
import org.springframework.stereotype.Service;

@Service
public class AdministratorServiceImp extends ServiceImpl<AdministratorMapper, Administrator> implements AdministratorService {
}
