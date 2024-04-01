/**   
 * Copyright © 2019 国网信通亿力科技有限责任公司. All rights reserved.
 * 
 * @Title: SpringContextUtil.java 
 * @Prject: com.ylkj.cloud.example
 * @Package: com.ylkj.cloud.core.utils 
 * @author: Jianbin   
 * @date: 2019年7月26日 下午10:52:23 
 * @version: V1.0   
 */
package com.student.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @ClassName: SpringContextUtil
 * @Description: 获取bean方式
 * @author: Jianbin
 * @date: 2019年7月26日 下午10:52:23
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {
	// Spring应用上下文环境
	private static ApplicationContext applicationContext;

	/**
	 * 实现ApplicationContextAware接口的回调方法，设置上下文环境
	 * @param applicationContext
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextUtil.applicationContext = applicationContext;
	}
	/**
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	/**
	 * 获取对象
	 * @param name
	 * @return Object
	 * @throws BeansException
	 */
	public static Object getBean(String name) throws BeansException {
		if (applicationContext != null) {
			return applicationContext.getBean(name);
		} else {
			return null;
		}
	}
	public static Object getEnvProper(String name) throws BeansException {
		if (applicationContext != null) {
			return applicationContext.getEnvironment().getProperty(name);
		} else {
			return null;
		}
	}
	
	/** 
	 * @Title: publish 
	 * @Description: 发布消息
	 * @param object
	 * @throws BeansException
	 */
	public static void publish(Object object) throws BeansException {
		if(applicationContext != null){
			applicationContext.publishEvent(object);
		}
	}
}
