package com.student.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author wenhui
 */
public class RedisTemplateUtil {

    private static final Logger log = LoggerFactory.getLogger(RedisTemplateUtil.class);

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public RedisTemplateUtil() {
    }

    public void putObjectValueIntoCache(String key, String value) {
        this.redisTemplate.opsForValue().set(key, value);
    }

    public void putObjectValueIntoCache(String key, String value, long time, TimeUnit unit) {
        this.redisTemplate.opsForValue().set(key, value, time, unit);
    }

    public void putObjectValueIntoCache(String key, int value) {
        this.redisTemplate.opsForValue().set(key, String.valueOf(value));
    }

    public void putObjectValueIntoCache(String key, int value, long time, TimeUnit unit) {
        this.redisTemplate.opsForValue().set(key, String.valueOf(value), time, unit);
    }

    public void putObjectValueIntoCache(String key, List<String> values) {
        this.redisTemplate.opsForList().leftPushAll(key, new Object[]{values});
    }

    public void putObjectValueIntoCache(String key, List<String> values, long time, TimeUnit unit) {
        this.redisTemplate.opsForList().leftPushAll(key, new Object[]{values, time, unit});
    }

    public void putObjectValueIntoCache(String key, Set<String> values) {
        this.redisTemplate.opsForSet().add(key, new Object[]{values});
    }

    public void putObjectValueIntoCache(String key, Set<String> values, long time, TimeUnit unit) {
        this.redisTemplate.opsForSet().add(key, new Object[]{values, time, unit});
    }

    public void putObjectValueIntoCache(String key, Object object) {
        if (object instanceof HashMap) {
            Map map = (Map)object;
            this.redisTemplate.opsForHash().putAll(key, map);
        } else if (object instanceof List) {
            List list = (List)object;
            this.redisTemplate.opsForList().leftPushAll(key, list);
        } else if (object instanceof String) {
            String value = (String)object;
            this.redisTemplate.opsForValue().set(key, value);
        } else if (object instanceof Set) {
            Set set = (Set)object;
            this.redisTemplate.opsForSet().add(key, new Object[]{set});
        } else {
            this.redisTemplate.opsForValue().set(key, object);
        }

    }

    public void putObjectValueIntoCache(String key, Object object, long time, TimeUnit unit) {
        if (object instanceof HashMap) {
            Map map = (Map)object;
            this.redisTemplate.opsForHash().putAll(key, map);
            this.redisTemplate.expire(key, time, unit);
        } else if (object instanceof List) {
            List list = (List)object;
            this.redisTemplate.opsForList().leftPushAll(key, new Object[]{list, time, unit});
        } else if (object instanceof String) {
            String value = (String)object;
            this.redisTemplate.opsForValue().set(key, value, time, unit);
        } else if (object instanceof Set) {
            Set set = (Set)object;
            this.redisTemplate.opsForSet().add(key, new Object[]{set, time, unit});
        } else {
            this.redisTemplate.opsForValue().set(key, object, time, unit);
        }

    }

    public Object getMapValueFromCache(String key, String field) {
        return this.redisTemplate.opsForHash().get(key, field);
    }

    public void removeValueFromCache(String key) {
        this.redisTemplate.delete(key);
    }

    public String getStringFromCache(String key) {
        return String.valueOf(this.redisTemplate.opsForValue().get(key));
    }

    public void putMapValueToCache(String key, String field, Object vlaue) {
        this.redisTemplate.opsForHash().put(key, field, vlaue);
    }

}
