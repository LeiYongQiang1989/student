/**   
 * Copyright © 2021 国网信通亿力科技有限责任公司. All rights reserved.
 * 
 * @Title: SystemConfigUtil.java 
 * @Prject: ycloud-tools
 * @Package: com.ylkj.cloud.utils 
 * @author: wujianbin   
 * @date: 2021年11月5日 下午2:10:04 
 * @version: V1.0   
 */
package com.student.utils;

import com.student.core.SystemConfigConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/** 
 * @ClassName: SystemConfigUtil 
 * @Description: 获取系统参数值
 * @author: wujianbin
 * @date: 2021年11月5日 下午2:10:04  
 */
@Component
public class SystemCacheUtil {
	
	@Autowired
	private RedisTemplateUtil redisTemplateUtil;
	/**
	 * 
	 * @Title: getCfgValue 
	 * @Description: 根据key获取系统参数值
	 * @param name
	 * @param key
	 * @return
	 */
	public String getCfgValue(String name, String key) {
		String result = (String) redisTemplateUtil.getMapValueFromCache(SystemConfigConstant.CONFIG_CACHE_MAP_KEY,
				name + SystemConfigConstant.CONNECTOR + key);
		return result;
	}
	
	/**
	 * 
	 * @Title: putCfgValue 
	 * @Description: 设置系统参数缓存数据
	 * @param name
	 * @param key
	 * @param value
	 */
	public void setCfgValue(String name, String key, String value) {
		redisTemplateUtil.putMapValueToCache(SystemConfigConstant.CONFIG_CACHE_MAP_KEY,
				name + SystemConfigConstant.CONNECTOR + key, value);
	}
	
	/**
	 * 
	 * @Title: getCacheValue 
	 * @Description: 获取Token中数据
	 * @param name
	 * @param key
	 * @return
	 */
	public String getTokenValue(String token, String key) {
		String result = (String) redisTemplateUtil.getMapValueFromCache(SystemConfigConstant.PREFIX_USER_INFO + token, key);
		return result;
	}
}
