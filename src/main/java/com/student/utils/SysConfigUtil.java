package com.student.utils;

import com.student.core.SystemConfigConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class SysConfigUtil {
    /**
     * management-system系统参数在缓存中的key：configCacheMap
     */
    public static final String CONFIG_CACHE_MAP_KEY = "management-system";

    /**
     * 国密2私钥
     */
    @Value("${SMS2.PRK}")
    private String prk;

    /**
     * 国密4加密常量
     */
    @Value("${SMS4.LOGIN}")
    private String login;

    @Value("${authority.whiteIpList}")
    private String whiteIpList;


    @Autowired
    private RedisTemplateUtil redisUtil;

    @PostConstruct
    public void init() {
        Map<String,String> map = new HashMap<>();
        map.put(SystemConfigConstant.SECURITY_PROPERTIES + SystemConfigConstant.CONNECTOR + SystemConfigConstant.SMS2_PRK,prk);
        map.put(SystemConfigConstant.SECURITY_PROPERTIES + SystemConfigConstant.CONNECTOR + SystemConfigConstant.SMS4_LOGIN,login);
        map.put("whiteIpList",whiteIpList);
        map.put("system.properties+TOKEN.timeOut","3600");
        System.err.println("系统启动时，存入的management-system是：" + map.toString());
        // 项目启动时，给redis中存储 国密2私钥 和 常量sm4Key
        redisUtil.putObjectValueIntoCache(CONFIG_CACHE_MAP_KEY, map);

    }



}
