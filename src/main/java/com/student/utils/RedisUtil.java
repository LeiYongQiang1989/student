package com.student.utils;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 *
 * @author LeiYongQiang
 */
@Component
public class RedisUtil {

    @Resource
    private RedisTemplateUtil redisTemplateUtil;


    /**
     * 赋值一个静态的redis
     */
    public static RedisTemplateUtil redis;

    @PostConstruct
    public void setRedisTemplateUtil(){
        redis = this.redisTemplateUtil;
    }

}
