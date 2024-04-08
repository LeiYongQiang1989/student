package com.student.interceptor;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.student.core.RestConstant;
import com.student.utils.RedisUtil;
import com.student.work.user.model.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.buf.MessageBytes;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.*;

import static com.alibaba.druid.util.DruidDataSourceUtils.getUrl;

/**
 * @author LeiYongQiang
 */
@Slf4j
public class Interceptor extends HandlerInterceptorAdapter {


    public static String whiteIpList = null;

    /**
     * preHandle方法是在请求处理之前进行调用
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("startTime",System.currentTimeMillis());
        System.out.println("请求URL：" + request.getRequestURI());
//        String url = getRequestURI(request);
        if ((request.getRequestURI().contains("/login")) || (request.getRequestURI().contains("/secretKey"))) {
            return true;
        }


////        if ((request.getRequestURI().contains("/login")) || (request.getRequestURI().contains("/secretKey"))
////                || (request.getRequestURI().contains("/getPassWord"))) {
////            return true;
////        }
//        String clientIp = "";
//        boolean contains = isInWhitelist(request,clientIp);
//        if (!contains) {
//            returnJson(response, "{\n" + "\"code\": 4000,\n" + "\"success\": false,\n" +
//                    "\"resultCode\": \"BASE_4000\",\n" + "\"msg\": \"暂无访问权限！\"\n" + "}");
//            return false;
//        }
//
//        try {
//            String token = request.getHeader(RestConstant.X_ACCESS_TOKEN);
//            if (StringUtils.isBlank(token)) {
//                returnJson(response, "{\n" + "\"code\": 4001,\n" + "\"success\": false,\n" +
//                        "\"resultCode\": \"BASE_4001\",\n" + "\"msg\": \"请求头中未携带token!！\"\n" + "}");
//                return false;
//            }
//            //判断是否登录
//            String operatorPersonnel = getOperatorPersonnel(request,token);
//            if (StringUtils.isNotBlank(operatorPersonnel)) {
//                String method = request.getMethod();
//                String requestParam = null;
//                //若是请求是POST获取body字符串，不然GET的话用request.getQueryString()获取参数值
//                if("POST".equals(method)){
//                    if (request instanceof HttpServletRequestFilter.RequestWrapper ) {
//                        HttpServletRequestFilter.RequestWrapper repeatedRequest = (HttpServletRequestFilter.RequestWrapper) request;
//                        requestParam = HttpContextUtils.getBodyString(repeatedRequest);
//                        //客户端总体请求信息
//                        StringBuilder clientInfo = new StringBuilder();
//                        clientInfo.append("客户端信息:[").append("ip:").append(clientIp)
//                                .append(", 请求方式:").append(method)
//                                .append(", URI:").append(request.getRequestURI())
//                                .append(", 操作人是:").append(operatorPersonnel)
//                                .append(", 请求参数值:").append(requestParam.replaceAll("\\s*", ""))
//                                .append("]");
//                        log.info(clientInfo.toString());
//                    }
//                }else if ("GET".equals(method)){
//                    //客户端总体请求信息
//                    StringBuilder clientInfo = new StringBuilder();
//                    clientInfo.append("客户端信息:[").append("ip:").append(clientIp)
//                            .append(", 请求方式:").append(method)
//                            .append(", URI:").append(request.getRequestURI())
//                            .append(", 操作人是:").append(operatorPersonnel)
//                            .append("]");
//                    log.info(clientInfo.toString());
//                }
//                return true;
//            }
//            returnJson(response, "{\n" + "\"code\": 4002,\n" + "\"success\": false,\n" +
//                    "\"resultCode\": \"BASE_4002\",\n" + "\"msg\": \"无效token！\"\n" + "}");
//            //如果设置为false时，被请求时，拦截器执行到此处将不会继续操作;如果设置为true时，请求将会继续执行后面的操作
//            return false;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }

        return true;
    }

    private boolean isInWhitelist(HttpServletRequest request , String clientIp) {
        clientIp= request.getHeader("x-forwarded-for");
        if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("Proxy-Client-IP");
        }
        if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("WL-Proxy-Client-IP");
        }
        if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getRemoteAddr();
        }
        log.info("访问登录接口Ip是：{}", clientIp);
//        if (clientIp.equals("127.0.0.1, ")) {
//            clientIp = "26.74.79.249";
//        }
        if (whiteIpList == null) {
            whiteIpList = (String)RedisUtil.redis.getMapValueFromCache("management-system", "whiteIpList");
        }
        List<String> list = Arrays.asList(whiteIpList.split(","));
        Set<String> whiteIpSet = new HashSet<>(list);
        boolean contains = whiteIpSet.contains(clientIp);
        return contains;
    }

    /**
     * 设置返回响应体数据
     * @param response
     * @param json
     * @throws Exception
     */
    private void returnJson(HttpServletResponse response, String json) throws Exception{
        PrintWriter writer  = response.getWriter();;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        writer.write(json);
        writer.close();
    }


    /**
     * 验证是否登录
     *
     * @param request
     * @return
     */
    public String getOperatorPersonnel(HttpServletRequest request, String token) {
        // 获取当前登录用户信息
        String temp = (String) RedisUtil.redis.getMapValueFromCache("PREFIX_USER_INFO_" + token, "userInfo");
        if (StringUtils.isBlank(temp)) {
            log.info("接口 " + request.getRequestURI() + " 携带token无效!");
            return null;
        }

        UserDO userVo = JSONUtil.toBean(temp, UserDO.class);
        if (ObjectUtil.isNull(userVo) || userVo.getId() == null ) {
            log.info("接口" + request.getRequestURI() + "的token未获取到用户信息！");
            return null;
        }
        String operatorPersonnel = userVo.getRealName();
        return operatorPersonnel;

    }

    /**
     * 控制器方法不抛异常时会被调用
     * @param request
     * @param response
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception{

        Long startTime = (Long)request.getAttribute("startTime");
//        String url = getRequestURI(request);
        String url = getRequestURI(request);
        log.info("接口[" + url + "]当次请求耗时：" + (System.currentTimeMillis() - startTime) + "ms");
    }

    private String getRequestURI(HttpServletRequest request) throws Exception {
        Object a = findCoyoteRequest(request);
        Field coyoteRequest = a.getClass().getDeclaredField("coyoteRequest");
        coyoteRequest.setAccessible(true);
        Object b = coyoteRequest.get(a);

        Field uriMB = b.getClass().getDeclaredField("uriMB");
        uriMB.setAccessible(true);
        MessageBytes c = (MessageBytes)uriMB.get(b);
        return c.getString();
    }


    //根据Field获得对应的Class
    private Class getClassByName(Class classObject, String name){
        Map<Class,List<Field>> fieldMap = new HashMap<>();
        Class returnClass = null;
        Class tempClass = classObject;
        while (tempClass != null) {
            fieldMap.put(tempClass,Arrays.asList(tempClass .getDeclaredFields()));
            tempClass = tempClass.getSuperclass();
        }

        for(Map.Entry<Class,List<Field>> entry: fieldMap.entrySet()){
            for (Field f : entry.getValue()) {
                if(f.getName().equals(name)){
                    returnClass = entry.getKey();
                    break;
                }
            }
        }
        return returnClass;
    }

    //递归遍历父类寻找coyoteRequest Field
    private Object findCoyoteRequest(Object request)  throws Exception {
        Class a = getClassByName(request.getClass(),"request");
        Field request1 = a.getDeclaredField("request");
        request1.setAccessible(true);
        Object b = request1.get(request);
        if(getClassByName(b.getClass(),"coyoteRequest") == null){
            return findCoyoteRequest(b);
        }else{
            return b;
        }
    }

}
