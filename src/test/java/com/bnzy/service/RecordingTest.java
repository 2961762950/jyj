package com.bnzy.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bnzy.domian.Administrator;
import com.bnzy.domian.Recording;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
@Slf4j
@SpringBootTest
public class RecordingTest {

    @Autowired
    private RecordingService recordingService;

    @Test
    public void Recording(){
        Recording recording=new Recording(1,"老大","老二", LocalDateTime.now());

        boolean save = recordingService.save(recording);
        log.error("保存结果=》{}",save);


        LambdaQueryWrapper<Recording> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(Recording::getId,1);

        Recording one = recordingService.getOne(wrapper);

        log.error(one.toString());
    }

}
