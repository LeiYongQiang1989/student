package com.student.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class InterceptorConfiguration extends WebMvcConfigurationSupport {

    /**
     * 重写addInterceptors()实现拦截器
     * 配置：要拦截的路径以及不拦截的路径
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册Interceptor拦截器(Interceptor这个类是我们自己写的拦截器类)
        InterceptorRegistration registration = registry.addInterceptor(new Interceptor());
//        //addPathPatterns()方法添加需要拦截的路径
//        //所有路径都被拦截
//        registration.addPathPatterns("/**");
//        //excludePathPatterns()方法添加不拦截的路径
//        //添加不拦截路径
//        registration.excludePathPatterns(
//                //登录
//                "/login",
//                // 获取秘钥
//                "/secretKey"
//        );
    }

}
