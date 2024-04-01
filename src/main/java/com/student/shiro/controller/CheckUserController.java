package com.student.shiro.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.student.core.RestConstant;
import com.student.core.SystemConfigConstant;
import com.student.utils.RedisTemplateUtil;
import com.student.utils.RequestContextUtil;
import com.student.work.user.model.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author wenhui
 */
@Slf4j
public class CheckUserController  {


    @Autowired
    private RedisTemplateUtil redisTemplateUtil;



    public CheckUserController() {
    }

    protected HttpServletRequest getRequest() {
        return RequestContextUtil.getRequest();
    }

    protected HttpSession getSession() {
        HttpServletRequest request = this.getRequest();
        return request.getSession(true);
    }

    public HttpServletResponse getResponse() {
        return RequestContextUtil.getResponse();
    }


    /**
     * @param
     * @return
     * @Title:
     * @Description: 校验用户权限
     */
    public boolean checkUser() {
        String token = this.getRequest().getHeader(RestConstant.X_ACCESS_TOKEN);
        // 获取当前登录用户信息
        String temp = (String) redisTemplateUtil.getMapValueFromCache(SystemConfigConstant.PREFIX_USER_INFO + token, SystemConfigConstant.USERINFO);
        UserDO userVo = JSONUtil.toBean(temp, UserDO.class);
        if (ObjectUtil.isNull(userVo) || userVo.getId() != null ) {
            return false;
        }
        return true;

    }


    /**
     * @param
     * @return
     * @Description: 获取用户
     */
    public UserDO getUser() {
        String token = this.getRequest().getHeader(RestConstant.X_ACCESS_TOKEN);
        // 获取当前登录用户信息
        String temp = (String) redisTemplateUtil.getMapValueFromCache(SystemConfigConstant.PREFIX_USER_INFO + token, SystemConfigConstant.USERINFO);
        UserDO userVo = JSONUtil.toBean(temp, UserDO.class);
        if (ObjectUtil.isNull(userVo)) {
            return null;
        }
        return userVo;
    }

    /**
     * @param
     * @return
     * @Description: 获取用户id
     */
    public Integer getUserNo() {
        //获取用户信息
        UserDO userVo  = getUser();
        return userVo.getId();
    }


}
