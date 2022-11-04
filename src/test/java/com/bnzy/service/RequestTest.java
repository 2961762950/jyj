package com.bnzy.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bnzy.domian.Request;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class RequestTest {

    @Autowired
    private RequestService requestService;
    /**
     * 招生条件
     */
    @Test
    public void Request(){
        Request condition=new Request(3,"女","18岁","没有男朋友");
//
//        Request condition=new Request(1);
        boolean save = requestService.save(condition);
        log.info("结果=》{}",save);
        LambdaQueryWrapper<Request> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(Request::getSchoolId,3);

        Request one =requestService.getOne(wrapper);

        log.info(one.toString());

////

    }

}
