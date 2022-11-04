package com.bnzy.utils;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
@Slf4j
@Component

public class RemoveRedis {

    @Autowired
    private RedisTemplate redisTemplate;

    @CacheEvict(allEntries = true,value ="202025020032" )
    public  void Remove(){
//        Set keys = redisTemplate.keys("*");
//
//        if (ObjectUtils.isNotEmpty(keys)) {
//            Long delete = redisTemplate.delete(keys);
//            log.error("影响的行数=》{}",delete);
//        }


    }
}
