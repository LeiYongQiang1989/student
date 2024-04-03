/**   
 * Copyright © 2019 国网信通亿力科技有限责任公司. All rights reserved.
 * 
 * @Title: UserController.java 
 * @Prject: com.ylkj.cloud.upms.user.controller
 * @Package: com.ylkj.cloud.upms.user.controller
 * @author: HuangZhenPeng   
 * @date: 2019-08-20
 * @version: V1.0   
 */
package com.student.shiro.controller;

import ch.qos.logback.core.joran.spi.ActionException;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.student.core.RestConstant;
import com.student.core.Result;
import com.student.core.ResultGenerator;
import com.student.core.SystemConfigConstant;
import com.student.shiro.model.DO.Secret;
import com.student.shiro.model.VO.UpmsErrorCodeEnum;
import com.student.utils.JwtUtil;
import com.student.utils.RedisTemplateUtil;
import com.student.utils.Sm2Util;
import com.student.utils.Sm4Util;
import com.student.work.user.model.TokenClaim;
import com.student.work.user.model.UserDO;
import com.student.work.user.model.UserDTO;
import com.student.work.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: ManageLoginController
 * @Description: 用户会话权限 控制层
 * @author: LeiYongQiang
 * @date: 2024-03-26
 */
@RestController
@RequestMapping(LoginController.PATH)
@Slf4j
public class LoginController extends CheckUserController {

	public final static String PATH = RestConstant.VERSION_V1 + "/system";
	// 日志审计列表中“菜单路径”
	private static final String MENU_NAME = "系统管理->登录";
	// 日志审计列表中“日志内容”显示信息
	private static final String CONTENT = "登录";

	@Autowired
	private UserService userService;

	@Autowired
	private RedisTemplateUtil redisUtil;



	//国密2私钥
	@Value("${SMS2.pubKey}")
	private String pubKey;

	//国密4加密常量
	@Value("${SMS4.LOGIN}")
	private String login;

	@Value("${authority.whiteIpList}")
	private String whiteIpList;

	@Value("${authority.loginUserToken}")
	private String loginUserToken;

	// 密码盐
	@Value("${authority.salt}")
	private String salt;



	/**
	 *
	 * @Title: secretKey
	 * @Description: 获取登录秘钥
	 * @return
	 */
	@PostMapping("/secretKey")
	public Result secretKey() {
		//	读取配置文件，将秘钥返回给前端
		Secret secret = new Secret();
		secret.setPubKey(pubKey);
		secret.setEntKey(login);
		return ResultGenerator.genOkResult(secret);
	}
	/**
	 * @param
	 * @return
	 * @Title: login
	 * @Description: 登录
	 */
	@PostMapping(value = "/login")
	public Result login(@RequestBody @Validated UserDTO userDTO) {
		log.info("登录接口参数是：{}" ,JSONObject.toJSONString(userDTO));
		// 1. 校验用户是否有效
		if (ObjectUtil.isEmpty(userDTO)) {
			return ResultGenerator.genFailedResult(UpmsErrorCodeEnum.OPERATOR_EMPTY.getValue(),
					UpmsErrorCodeEnum.OPERATOR_EMPTY.getErrorReason());
		}
		// 2.校验用户名
		if (ObjectUtil.isNotEmpty(userDTO) && StrUtil.isEmpty(userDTO.getUserNo())) {
			return ResultGenerator.genFailedResult(UpmsErrorCodeEnum.OPERATOR_USERNAME_EMPTY.getValue(),
					UpmsErrorCodeEnum.OPERATOR_USERNAME_EMPTY.getErrorReason());
		}
		// 3.校验密码
		if (ObjectUtil.isNotEmpty(userDTO) && StrUtil.isEmpty(userDTO.getPassWord())) {
			return ResultGenerator.genFailedResult(UpmsErrorCodeEnum.OPERATOR_MMPD_EMPTY.getValue(),
					UpmsErrorCodeEnum.OPERATOR_MMPD_EMPTY.getErrorReason());
		}

		String clientIp = this.getRequest().getHeader("x-forwarded-for");
		if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
			clientIp = this.getRequest().getHeader("Proxy-Client-IP");
		}
		if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
			clientIp = this.getRequest().getHeader("WL-Proxy-Client-IP");
		}
		if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
			clientIp = this.getRequest().getRemoteAddr();
		}
		log.info("访问登录接口Ip是：{}",clientIp);
		if (clientIp.equals("127.0.0.1, 26.74.79.249")) {
			clientIp = "26.74.79.249";
		}
		List<String> list = Arrays.asList(whiteIpList.split(","));
		Set<String> whiteIpSet = new HashSet<>(list);
		boolean contains = whiteIpSet.contains(clientIp);
		if (!contains) {
			return ResultGenerator.genFailedResult("暂无访问权限！");
		}

		/**
		 * 采用sm2私钥解密出sm4加密密钥后解密出密码明文，在使用sm4后台密钥加密，与数据库比对，兼容旧数据 eidt by hzp 20210722
		 */
		// 获取系统的 私钥priKey 和 常量sm4Key
		String priKey = (String)redisUtil.getMapValueFromCache("management-system",
				"security.properties"+ "+" + SystemConfigConstant.SMS2_PRK);
		String sm4Key = login;
		// 采用sm2私钥解密出sm4加密密钥 frontKey。【decryptKey：传参所需解密参数】
		String frontKey = Sm2Util.decodeByPriKey(priKey, userDTO.getDecryptKey());
//		System.out.println("frontKey是："+ frontKey);
		// 使用加密密钥frontKey 解密 sm4 前端传过来的密码
		String realPass = Sm4Util.decodeByHex(userDTO.getPassWord(), frontKey);
//		System.out.println("解密后的realPass是："+ realPass);
		// sm4 加密， 用于用户密码有效性校验：sm4Key为常量固定值
		String passWord  = Sm4Util.encodeByHex(realPass, sm4Key);
		userDTO.setPassWord(passWord);
		UserDO sysUser = userService.selectOne(userDTO);
		if (ObjectUtil.isEmpty(sysUser)) {
			return ResultGenerator.genFailedResult("用户名或密码错误！");
		}
		// 2. 用户登录信息
		String mmpdSalt = sysUser.getPassWord();
		String userNo = sysUser.getUserNo();
		String userId = String.valueOf(sysUser.getId());
		String sm4KeyParam = Sm4Util.generateSm4Key(16);
		if ( StrUtil.isNotEmpty(sm4KeyParam)) {
			mmpdSalt = Sm4Util.encodeByHex(userNo + mmpdSalt, sm4KeyParam, salt);
		}
		// 生成token
		String token = JwtUtil.sign(new TokenClaim(userId, userNo), mmpdSalt);
		// 获取失效时间
		long expireTime = SystemConfigConstant.TOKEN_EXPIRE_TIME;
		//多重登录校验，根据用户名密码从redis中取出token值，若存在，则说明用户已登录，删除上次登录信息
		String oldToken = redisUtil.getStringFromCache(loginUserToken + userNo);
		if (StrUtil.isNotEmpty(oldToken)) {
			// 清空用户信息
			redisUtil.removeValueFromCache("PREFIX_USER_INFO_" + oldToken);
		}
		//放入新生成的token值
		redisUtil.putObjectValueIntoCache(loginUserToken + userNo, token);
		// 用户信息放入缓存
		HashMap<Object, Object> userMap = CollUtil.newHashMap();
		userMap.put(SystemConfigConstant.LOGINID, userNo);
		userMap.put(SystemConfigConstant.USERINFO, JSONUtil.toJsonStr(sysUser));
		userMap.put(SystemConfigConstant.ROLE, sysUser.getRoleCode());
		userMap.put(SystemConfigConstant.CURUSERNAME, sysUser.getRealName());
		userMap.put(SystemConfigConstant.ID, sysUser.getId());
		userMap.put(SystemConfigConstant.LOGIN_IP, clientIp);
		userMap.put(SystemConfigConstant.LOGIN_TIME, DateUtil.now());
		userMap.put(SystemConfigConstant.HTTP_REFERER, this.getRequest().getHeader(HttpHeaders.REFERER));
		redisUtil.putObjectValueIntoCache("PREFIX_USER_INFO_" + token, userMap, expireTime, TimeUnit.SECONDS);
		Map<String,Object> resultMap = new HashedMap();
		resultMap.put("token",token);
		resultMap.put("userNo",userNo);
		resultMap.put("realName",sysUser.getRealName());
		return ResultGenerator.genOkResult(resultMap);
	}


	/**
	 *
	 * @Title: logout
	 * @Description: 登出
	 * @return
	 */
	@PostMapping("/logOut")
	public Result logout() {
		String token = this.getRequest().getHeader(RestConstant.X_ACCESS_TOKEN);
		if (StringUtils.isBlank(token)){
			return  ResultGenerator.genFailedResult("token不能为空！");
		}
		UserDO userVo = this.getUser();
		if (userVo == null ){
			return  ResultGenerator.genFailedResult("token已失效！");
		}
		//添加日志记录登出用户信息
		String userName = (String) redisUtil.getMapValueFromCache(SystemConfigConstant.PREFIX_USER_INFO + token,
				SystemConfigConstant.USERNAME);
		String curUserName = (String) redisUtil.getMapValueFromCache(SystemConfigConstant.PREFIX_USER_INFO + token,
				SystemConfigConstant.CURUSERNAME);
		// 清空用户信息
		redisUtil.removeValueFromCache("PREFIX_USER_INFO_" + token);
		redisUtil.removeValueFromCache( loginUserToken + userVo.getUserNo());
		return ResultGenerator.genOkResult("登出成功！");
	}





	/**
	 * @param
	 * @return
	 * @Description: 获取用户
	 */
	public UserDO getUser() {
		String token = this.getRequest().getHeader(RestConstant.X_ACCESS_TOKEN);
		// 获取当前登录用户信息
		String temp = (String) redisUtil.getMapValueFromCache("PREFIX_USER_INFO_" + token, SystemConfigConstant.USERINFO);
		UserDO userVo = JSONUtil.toBean(temp, UserDO.class);
		if (ObjectUtil.isNull(userVo)) {
			return null;
		}
		return userVo;
	}


}
