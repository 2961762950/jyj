package com.bnzy.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;


@SpringBootTest
public class Redis {

    @Autowired
    private RemoveRedis removeRedis;

    @Autowired
    private ReadRedis readRedis;
    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    public void remove(){
    removeRedis.Remove();

    }
    @Test
    public void read(){

        readRedis.Read();
        HashOperations hashOperations = redisTemplate.opsForHash();
        String name = (String) hashOperations.get("202025020032", "姓名");
        Integer nl = (Integer) hashOperations.get("202025020032", "年龄");
        String by = (String) hashOperations.get("202025020032", "毕业学校");
        String xb = (String) hashOperations.get("202025020032", "性别");
        String dq = (String) hashOperations.get("202025020032", "当前层次");
        String mm = (String) hashOperations.get("202025020032", "密码");
        Integer sx = (Integer) hashOperations.get("202025020032", "数学");
        Integer yy = (Integer) hashOperations.get("202025020032", "英语");
        Integer yw = (Integer) hashOperations.get("202025020032", "语文");
        Integer zf = (Integer) hashOperations.get("202025020032", "总分");
        String lv = (String) hashOperations.get("202025020032", "录取学校");

        System.out.println(name);
        System.out.println(nl);
        System.out.println(by);
        System.out.println(xb);
        System.out.println(dq);
        System.out.println(mm);
        System.out.println(sx);
        System.out.println(yy);
        System.out.println(yw);
        System.out.println(zf);
        System.out.println(lv);


    }

}
