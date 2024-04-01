package com.student.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.student.core.SystemConfigConstant;
import com.student.work.user.model.TokenClaim;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @ClassName: JwtUtil
 * @Description: JWT工具类
 * @author: huang
 * @date: 2019年8月22日 下午1:44:38
 */
@Slf4j
public class JwtUtil {

    // 过期时间30分钟
    public static final long EXPIRE_TIME = 30 * 60 * 1000;

    private static final String USERNAME_SM4_KEY = "12HDESAAHIHQWERT";

    //设置withClaim的key
    public static final String KEY_NAME = "uid";

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, TokenClaim tokenClaim, String secret) {
        try {
            // 根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(secret);
            //JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();
            JWTVerifier verifier = JWT.require(algorithm).withClaim(KEY_NAME, Sm4Util.encodeByHex(tokenClaim.objToJson(), USERNAME_SM4_KEY)).build();
            // 效验TOKEN
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名，username
     */
    public static String getUsername(String token) {
        if (StrUtil.isNotBlank(token)) {
            try {
                DecodedJWT jwt = JWT.decode(token);
                String username;

                //兼容旧的jwt获取username:"xxx"
                username = jwt.getClaim("username").asString();
                if (username != null) {
                    return username;
                }
                TokenClaim userInfo = getUserInfo(token);
                if (userInfo != null) {
                    return userInfo.getUsername();
                }
            } catch (JWTDecodeException e) {
                log.error("获取用户名失败", e);
                return null;
            }
        }
        return null;
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户id，id
     */
    public static String getUserId(String token) {
        if (StrUtil.isNotBlank(token)) {
            try {
                TokenClaim userInfo = getUserInfo(token);
                if (userInfo != null) {
                    return userInfo.getId();
                }
            } catch (JWTDecodeException e) {
                log.error("获取用户名失败", e);
                return null;
            }
        }
        return null;
    }


    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户信息，username
     */
    public static TokenClaim getUserInfo(String token) {
		if (!StrUtil.isEmptyOrUndefined(token)) {
            try {
                DecodedJWT jwt = JWT.decode(token);
                String json = Sm4Util.decodeByHex(jwt.getClaim(KEY_NAME).asString(), USERNAME_SM4_KEY);
                if(JSONUtil.isJson(json)) {
                	// uid:sm4("id":"xxx","username":"xxx");
                	 return TokenClaim.jsonToObj(json);
                }else {
                	//uid: sm4(username);
                	return new TokenClaim("",json);
                }
               
            } catch (JWTDecodeException e) {
                log.error("获取用户名失败", e);
                return null;
            }
        }
        return null;
    }

    /**
     * 生成签名,30min后过期
     *
     * @param tokenClaim 用户信息
     * @param secret   用户的密码
     * @return 加密的token
     */
    public static String sign(TokenClaim tokenClaim, String secret) {
        String time = getSystemCacheUtil().getCfgValue(SystemConfigConstant.SYSTEM_PROPERTIES, SystemConfigConstant.TOKEN_TIME_SECONDS);
        long expireTime = 0;
        if (StrUtil.isNotEmpty(time)) {
            expireTime = Long.valueOf(time) * 1000;
        } else {
            expireTime = EXPIRE_TIME;
        }
        Date date = new Date(System.currentTimeMillis() + expireTime);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息
        //return JWT.create().withClaim("username", username).withExpiresAt(date).sign(algorithm);
        return JWT.create().withClaim(KEY_NAME, Sm4Util.encodeByHex(tokenClaim.objToJson(), USERNAME_SM4_KEY)).withExpiresAt(date).sign(algorithm);
    }

    /**
     * isc模式登陆时 生成签名,30min后过期
     * @param tokenClaim 用户信息
     * @param secret   用户的密码
     * @param time isc侧会话有效时间
     * @return 加密的token
     */
    public static String sign(TokenClaim tokenClaim, String secret,Long time) {
        long expireTime = time * 1000;
        Date date = new Date(System.currentTimeMillis() + expireTime);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create().withClaim(KEY_NAME, Sm4Util.encodeByHex(tokenClaim.objToJson(), USERNAME_SM4_KEY)).withExpiresAt(date).sign(algorithm);
    }

    /**
     * 根据request中的token获取用户账号
     *
     * @param request
     * @return
     */
    public static String getUserNameByToken(HttpServletRequest request) {
        String accessToken = request.getHeader("X-Access-Token");
        String username = getUsername(accessToken);
        if (StrUtil.isEmpty(username)) {
            log.error("未获取到用户");
        }
        return username;
    }

    /**
     * 从session中获取变量
     *
     * @param key
     * @return
     */
    public static String getSessionData(String key) {
        //${myVar}%
        //得到${} 后面的值
        String moshi = "";
        if (key.indexOf("}") != -1) {
            moshi = key.substring(key.indexOf("}") + 1);
        }
        String returnValue = null;
        if (key.contains("#{")) {
            key = key.substring(2, key.indexOf("}"));
        }
        if (StrUtil.isNotEmpty(key)) {
            HttpServletRequest request = RequestContextUtil.getRequest();
            HttpSession session = request.getSession();
            returnValue = (String) session.getAttribute(key);
        }
        //结果加上${} 后面的值
        if (returnValue != null) {
            returnValue = returnValue + moshi;
        }
        return returnValue;
    }

    private static SystemCacheUtil getSystemCacheUtil() {
		return (SystemCacheUtil)SpringContextUtil.getBean("systemCacheUtil");
	}
}
