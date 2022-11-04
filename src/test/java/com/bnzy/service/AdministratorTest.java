package com.bnzy.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bnzy.domian.Administrator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class AdministratorTest {
    
    @Autowired
    private AdministratorService administratorService;


    /**
     * 管理员测试
     */
    @Test
    public void Administrator(){
        Administrator administrator=new Administrator("肖泽斌","123456","123456","教育局");

        boolean save = administratorService.save(administrator);

        System.out.println("保存=》"+save);

        LambdaQueryWrapper<Administrator>  wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(Administrator::getName,"肖泽斌");

        Administrator one = administratorService.getOne(wrapper);
        System.out.println("查询到结果");
        System.out.println(one.toString() );

    }



}
