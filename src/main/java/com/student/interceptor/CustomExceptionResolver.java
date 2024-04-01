package com.student.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Slf4j
@Component
public class CustomExceptionResolver implements HandlerExceptionResolver, Ordered {

    @Override
    public int getOrder() {
        return 2;
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object o, Exception e) {
        log.info("接口" +request.getRequestURI() + "出现异常");
        e.printStackTrace();
        try {
            PrintWriter writer  = response.getWriter();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            String returnJson = "{\n" +
                    "    \"code\": 500,\n" +
                    "    \"success\": false,\n" +
                    "    \"msg\": \"操作异常，请联系管理员\"\n" +
                    "}";
            writer.write(returnJson);
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ModelAndView();
    }



}
