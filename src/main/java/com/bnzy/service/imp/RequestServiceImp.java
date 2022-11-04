package com.bnzy.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bnzy.domian.Request;
import com.bnzy.mapper.RequestMapper;
import com.bnzy.service.RequestService;
import org.springframework.stereotype.Service;

@Service
public class RequestServiceImp extends ServiceImpl<RequestMapper, Request> implements RequestService {
}
