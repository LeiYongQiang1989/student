/**   
 * Copyright © 2019 国网信通亿力科技有限责任公司. All rights reserved.
 * 
 * @Title: Application.java 
 * @Prject: com.ylkj.cloud.example
 * @Package: com.ylkj.cloud
 * @author: Jianbin   
 * @date: 2019年7月31日 下午2:27:58
 * @version: V1.0   
 */
package com.student;

import cn.hutool.core.util.StrUtil;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.DispatcherServlet;


/**
 * 
 * @ClassName: Application 
 * @Description: 启动入口
 * @author: LeiYongQiang
 * @date: 2024年3月14日 11:36:58
 */                                                                 
@SpringBootApplication
//@ImportResource(locations = { "classpath:configs/druid-bean.xml","classpath:configs/spring-core.xml" })
@MapperScan(basePackages = { "com.student.**.mapper" })
@EnableAsync
//@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60*30)
public class Application {
	@Autowired
	private Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.println("    ____                                                _____   _                    _\n"+
				"  /_____|                                              / ____| | |                  | |\n" +
				" | (___   _ __   _ __  _  _ __    __ _  ______  ______ | |     | |  ___   _   _   __| |\n" +
				"  \\___ \\ | '_ \\ | '__|| || '_ \\  / _` ||______||______|| |     | | / _ \\ | | | | / _` |\n" +
				"  ____) || |_) || |   | || | | || (_| |                | |____ | || (_) || |_| || (_| |\n" +
				" |_____/ | .__/ |_|   |_||_| |_| \\__, |                 \\_____||_| \\___/  \\__,_| \\__,_|\n" +
				"         | |                      __/ |                                                \n" +
				"         |_|                     |___/    ");
		System.out.println("-------------------------------------学生成绩管理启动成功 ----------------------------------------");
	}

	@Bean
	public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
		ServletRegistrationBean reg = new ServletRegistrationBean(dispatcherServlet);
		reg.getUrlMappings().clear();
		reg.addUrlMappings("/api/*");
		String appname = environment.getProperty("spring.application.name");
		reg.addUrlMappings(StrUtil.format("/{}/api/*", appname));
		reg.addUrlMappings("/*");
		return reg;
	}
	
	@Bean
	public ConfigurableServletWebServerFactory configurableServletWebServerFactory() {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();

		factory.addContextCustomizers(context -> {
			SecurityConstraint securityConstraint = new SecurityConstraint();
			securityConstraint.setUserConstraint("CONFIDENTIAL");
			SecurityCollection collection = new SecurityCollection();
			collection.addPattern("/*");
			//collection.addMethod("HEAD");
			collection.addMethod("PUT");
			collection.addMethod("DELETE");
			// collection.addMethod("OPTIONS");
			// collection.addMethod("TRACE");
			collection.addMethod("COPY");
			collection.addMethod("SEARCH");
			collection.addMethod("PROPFIND");
			securityConstraint.addCollection(collection);
			securityConstraint.setAuthConstraint(true);
			context.addConstraint(securityConstraint);
			context.setUseHttpOnly(true);
		});
		// 如果需要禁用TRACE请求，需添加以下代码：
		factory.addConnectorCustomizers(connector -> {
			connector.setAllowTrace(true);
		});
		return factory;
	}
}
