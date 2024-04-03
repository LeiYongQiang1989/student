/**
 * Copyright © 2019 国网信通亿力科技有限责任公司. All rights reserved.
 *
 * @Title: RedisTemplateUtil.java
 * @Prject: com.ylkj.cloud.core
 * @Package: com.ylkj.cloud.core.utils
 * @author: huang
 * @date: 2019年7月31日 下午7:20:31
 * @version: V1.0
 */
package com.student.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: RedisTemplateUtil
 * @Description: redis工具类
 * @author: huang
 * @date: 2019年7月31日 下午7:20:31  
 */
@Component
@Slf4j
public class RedisTemplateUtil {
	
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     *
     * @Title: putObjectValueIntoCache
     * @Description: 存字符串数据
     * @param key
     * @param value
     */
    public void putObjectValueIntoCache(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     *
     * @Title: putObjectValueIntoCache
     * @Description: 存字符串数据并设置缓存时间
     * @param key
     * @param value
     * @param time
     * @param unit
     */
    public void putObjectValueIntoCache(String key, String value, long time, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, time, unit);
    }

    /**
     *
     * @Title: putObjectValueIntoCache
     * @Description: 存数值数据
     * @param key
     * @param value
     */
    public void putObjectValueIntoCache(String key, int value) {
        redisTemplate.opsForValue().set(key, String.valueOf(value));
    }

    /**
     *
     * @Title: putObjectValueIntoCache
     * @Description: 存数值数据并设置缓存时间
     * @param key
     * @param value
     * @param time
     * @param unit
     */
    public void putObjectValueIntoCache(String key, int value, long time, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, String.valueOf(value), time, unit);
    }

    /**
     *
     * @Title: putObjectValueIntoCache
     * @Description: 存List集合数据
     * @param key
     * @param values
     */
    public void putObjectValueIntoCache(String key, List<String> values) {
        redisTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     *
     * @Title: putObjectValueIntoCache
     * @Description: 存List集合数据, 并设置缓存时间
     * @param key
     * @param values
     * @param time
     * @param unit
     */
    public void putObjectValueIntoCache(String key, List<String> values, long time, TimeUnit unit) {
        redisTemplate.opsForList().leftPushAll(key, values, time, unit);
    }

    /**
     *
     * @Title: putObjectValueIntoCache
     * @Description: 存放set集合数据
     * @param key
     * @param values
     * @return
     * @return
     */
    public void putObjectValueIntoCache(String key, Set<String> values) {
        redisTemplate.opsForSet().add(key, values);
    }

    /**
     *
     * @Title: putObjectValueIntoCache
     * @Description: 存放set集合数据, 并设置缓存时间
     * @param key
     * @param values
     * @param time
     * @param unit
     * @return
     * @return
     */
    public void putObjectValueIntoCache(String key, Set<String> values, long time, TimeUnit unit) {
        redisTemplate.opsForSet().add(key, values, time, unit);
    }

    /**
     *
     * @Title: putObjectValueIntoCache
     * @Description: 存各类型数据
     * @param key
     * @param object
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void putObjectValueIntoCache(String key, Object object) {
        if (object instanceof HashMap) {
            Map map = (Map) object;
            redisTemplate.opsForHash().putAll(key, map);
        } else if (object instanceof List) {
            List list = (List) object;
            redisTemplate.opsForList().leftPushAll(key, list);
        } else if (object instanceof String) {
            String value = (String) object;
            redisTemplate.opsForValue().set(key, value);
        } else if (object instanceof Set) {
            Set set = (Set) object;
            redisTemplate.opsForSet().add(key, set);
        } else {
            redisTemplate.opsForValue().set(key, object);
        }
    }

    /**
     *
     * @Title: putObjectValueIntoCache
     * @Description: 存各类型数据, 并设置缓存时间
     * @param key
     * @param object
     * @param time
     * @param unit
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void putObjectValueIntoCache(String key, Object object, long time, TimeUnit unit) {
        if (object instanceof HashMap) {
            Map map = (Map) object;
            redisTemplate.opsForHash().putAll(key, map);
            redisTemplate.expire(key, time, unit);
        } else if (object instanceof List) {
            List list = (List) object;
            redisTemplate.opsForList().leftPushAll(key, list, time, unit);
        } else if (object instanceof String) {
            String value = (String) object;
            redisTemplate.opsForValue().set(key, value, time, unit);
        } else if (object instanceof Set) {
            Set set = (Set) object;
            redisTemplate.opsForSet().add(key, set, time, unit);
        } else {
            redisTemplate.opsForValue().set(key, object, time, unit);
        }
    }

    /**
     *
     * @Title: batchSet
     * @Description: 批量添加 key-value (重复的键会覆盖)
     * @param keyAndValue
     */
    public void batchSet(Map<String, Object> keyAndValue) {
        redisTemplate.opsForValue().multiSet(keyAndValue);
    }


    /**
     *
     * @Title: getObjectValueFromCache
     * @Description: 获取key指定的缓存值中的field对应的值
     * @param key
     * @param field
     * @return
     */
    public Object getMapValueFromCache(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    /**
     *
     * @Title: putMapValueToCache
     * @Description: map中放入数据
     * @param key
     * @param field
     * @param vlaue
     */
    public void putMapValueToCache(String key, String field, Object vlaue) {
        redisTemplate.opsForHash().put(key, field, vlaue);
    }

    /**
     *
     * @Title: getListFromCache
     * @Description: 根据key从集合中取数据
     * @param key
     * @return
     */
    public List<String> getListFromCache(String key) {
        List<String> values = new ArrayList<String>();
        List<Object> objects = redisTemplate.opsForList().range(key, 0, -1);
        for (Object obj : objects) {
            values.add(obj.toString());
        }
        return values;
    }


    /**
     *
     * @Title: getStringFromCache
     * @Description: 根据key取字符串
     * @param key
     * @return
     */
    public String getStringFromCache(String key) {
        return String.valueOf(redisTemplate.opsForValue().get(key));
    }

    /**
     *
     * @Title: getObjectFromCache
     * @Description: 根据key取数据
     * @param key
     * @return
     */
    public Object getObjectFromCache(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     *
     * @Title: getObjectFromCache
     * @Description: 根据key取数据并强转类型
     * @param <T>
     * @param key
     * @param t
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getObjectFromCache(String key, Class<T> t) {
        return (T) redisTemplate.opsForValue().get(key);
    }


//    /**
//     *
//     * @Title: getMapFromCache
//     * @Description: 根据key获取map数据
//     * @param key
//     * @return
//     */
//    public Object getMapFromCache(String key) {
//        key = InputInjectFilter.encodeInputString(key);
//        return redisTemplate.opsForHash().entries(key);
//    }


    /**
     *
     * @Title: getSetFromCache
     * @Description: 返回值为redis中键值为key的value的Set集合
     * @param key
     * @return
     */
    public Set<Object> getSetFromCache(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     *
     * @Title: expireKey
     * @Description: 设置某个键的过期时间, 时间单位自定
     * @param key
     * @param time
     * @param unit
     * @return
     */
    public boolean expireKey(String key, Long time, TimeUnit unit) {
        return redisTemplate.expire(key, time, unit);
    }

    /**
     *
     * @Title: removeKeyExpire
     * @Description: 移除key的缓存时间
     * @param key
     * @return
     */
    public boolean removeKeyExpire(String key) {
        return redisTemplate.boundValueOps(key).persist();
    }

    /**
     *
     * @Title: getKeyExpire
     * @Description: 获取key的缓存时间
     * @param key
     * @return
     */
    public Long getKeyExpire(String key) {
        return redisTemplate.boundValueOps(key).getExpire();
    }

    /**
     *
     * @Title: hasKey
     * @Description: 判断某个键是否存在
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     *
     * @Title: hashKey
     * @Description: 验证指定 key下 有没有指定的 hashkey
     * @param key
     * @param hashKey
     * @return
     */
    public boolean hashKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     *
     * @Title: rename
     * @Description: 重命名key
     * @param key
     * @param newKey
     */
    public void rename(String key, String newKey) {
        redisTemplate.boundValueOps(key).rename(newKey);
    }

    /**
     *
     * @Title: removeValueFromCache
     * @Description: 移除指定key的缓存
     * @param key
     */
    public void removeValueFromCache(String key) {
        redisTemplate.delete(key);
    }


    /**
     *
     * @Title: delete
     * @Description: 删除指定 hash 的 HashKey
     * @param key
     * @param hashKeys
     * @return
     */
    public Long delete(String key, Object... hashKeys) {
        return redisTemplate.opsForHash().delete(key, hashKeys);
    }

    /**
     *
     * @Title: delete
     * @Description: 删除多个键
     * @param keys
     */
    public void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

//    /**
//     *
//     * @Title: removeAllValueFromCache
//     * @Description: 移除所有缓存
//     */
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    public void removeAllValueFromCache() {
//        redisTemplate.execute(new RedisCallback() {
//            public String doInRedis(RedisConnection connection) throws DataAccessException {
//                connection.flushDb();
//                return "ok";
//            }
//        });
//
//    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }


    public boolean setIfAbsent(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            return operations.setIfAbsent(key, value);
        } catch (Exception e) {
            log.error("分布锁机制异常", e);
        }
        return result;
    }
    
    
    public void publish(String channel,Object message) {
		redisTemplate.convertAndSend(channel, message);
	}
}
