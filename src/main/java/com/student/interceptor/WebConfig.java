package com.student.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Bean
//    public FilterRegistrationBean httpServletRequestReplacedFilter(){
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.setFilter((Filter) new AccessFilter());
//        registrationBean.addUrlPatterns("/*");
//        registrationBean.setName("accessRequestFilter");
//        registrationBean.setOrder(1);
//        return registrationBean;
//    }

    @Bean
    public Interceptor interceptor() {
        return new Interceptor();
    }
    /**
     * 配置拦截器
     * @author yuqingquan
     * @param registry
     */
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor());
    }


}
