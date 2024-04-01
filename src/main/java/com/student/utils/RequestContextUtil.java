package com.student.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wenhui
 */
public class RequestContextUtil {

    public RequestContextUtil() {
    }

    public static HttpServletRequest getRequest() {
        return null != RequestContextHolder.getRequestAttributes() ? ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest() : null;
    }

    public static HttpServletResponse getResponse() {
        return null != RequestContextHolder.getRequestAttributes() ? ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse() : null;
    }
}
