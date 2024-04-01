/**   
 * Copyright © 2019 国网信通亿力科技有限责任公司. All rights reserved.
 * 
 * @Title: SystemConfigConstant.java 
 * @Prject: com.ylkj.cloud.core
 * @Package: com.ylkj.cloud.core.constant 
 * @author: HuangYi   
 * @date: 2019年8月5日 上午9:07:50 
 * @version: V1.0   
 */
package com.student.core;

/**
 * @ClassName: SystemConfigConstant
 * @Description: 系统参数静态变量类
 * @author: HuangYi
 * @date: 2019年8月5日 上午9:07:50
 */
public class SystemConfigConstant {
	/**
	 * 系统参数在缓存中的key：configCacheMap
	 */
	public static final String CONFIG_CACHE_MAP_KEY = "configCacheMap";
	
	/**
	 * 系统参数更新_所有_redis通知channel key
	 */
	public static final String CONFIG_CACHE_UPDATE_ALL_REDIS_CHANNEL_NAME = "channel_configCache_all_update";
	
	/**
	 * 系统参数更新_redis通知channel key
	 */
	public static final String CONFIG_CACHE_UPDATE_REDIS_CHANNEL_NAME = "channel_configCache_update";
	/**
	 * 连接符：+
	 */
	public static final String CONNECTOR = "+";
	/**
	 * 系统相关配置
	 */
	public static final String SYSTEM_PROPERTIES = "system.properties";
	/**
	 * 安全相关配置
	 */
	public static final String SECURITY_PROPERTIES = "security.properties";

	/**
	 * 文件缓存目录
	 */
	public static final String FILE_TEMP_DIR = "FILE.TEMP.DIR";
	/**
	 * excel每个sheet最大条数
	 */
	public static final String EXCEL_SHEET_NUM = "EXCEL.SHEET.NUM";
	/**
	 * 文件最大大小限制（M）
	 */
	public static final String FILE_MAX_SIZE = "FILE.MAX.SIZE";
	/**
	 * token失效时间
	 */
	public static final String TOKEN_TIME_SECONDS = "TOKEN.TIME.SECONDS";

	/**
	 * SMS4 加密KEY
	 */
	public static final String SMS4 = "SMS4";
	
	/**
	 * SMS4 登陆注册加密KEY
	 */
	public static final String SMS4_LOGIN = "SMS4.LOGIN";
	
	/**
	 * SM2公钥
	 */
	public static final String SMS2_PUK = "SMS2.PUK";
	/**
	 * SM2私钥
	 */
	public static final String SMS2_PRK = "SMS2.PRK";
	
	/**
	 * aes加密KEY
	 */
	public static final String AES = "AES";
	
	/**
	 * 用户信息缓存key
	 */
	public static final String PREFIX_USER_INFO = "PREFIX_USER_INFO_";

	/**
	 * 登录用户令牌缓存key
	 */
	public static final String PREFIX_USER_TOKEN = "PREFIX_USER_TOKEN_";

	/**
	 * 登录用户拥有角色缓存KEY前缀
	 * 
	 */
	public static final String LOGIN_USER_CACHERULES_ROLE = "loginUser_cacheRules::Roles_";
	
	/** 
	 * 登录用户拥有权限缓存KEY前缀
	 */
	public static final String LOGIN_USER_CACHERULES_PERMISSION = "loginUser_cacheRules::Permissions_";
	
	/** 
	 * 登录用户令牌缓存时间
	  */
	public static final int TOKEN_EXPIRE_TIME = 1800; // 1800秒即是半小时

	/**
	 * redis缓存失效默认30分钟
	 */
	public static final Long REDIS_TIME = 1800l;

	/**
	 * 文件大小默认50M
	 */
	public static final String DEFAULT_FILE_MAX_SIZE = "50";

	/**
	 * 登录帐号key
	 */
	public static final String LOGINID = "loginId";

	/**
	 * 登录用户信息key
	 */
	public static final String USERINFO = "userInfo";

	/**
	 * 登录用户帐号key
	 */
	public static final String USERNAME = "username";

	/**
	 * 登录用户真名key
	 */
	public static final String CURUSERNAME = "curUserName";
	
	/**
	 * 登录用户id
	 */
	public static final String ID = "id";

	/**
	 * 登录用户角色key
	 */
	public static final String ROLES = "roles";
	
	/**
	 * 登录用户角色id key
	 */
	public static final String ROLE_IDS = "roleIds";

	/**
	 * 登录用户部门key
	 */
	public static final String DEPARTS = "departs";

	/**
	 * 请求referer头
	 */
	public static final String HTTP_REFERER = "Referer";
	
	/**
	 * 请求host
	 */
	public static final String HTTP_HOST = "Host";
	
	/**
	 * 登出用户名
	 */
	public static final String LOGOUT_USERNAME = "logout_username";
	/**
	 * 登出用户真名
	 */
	public static final String LOGOUT_CURUSERNAME = "logout_curusername";
	
	/**
	 * 登出用户Ip
	 */
	public static final String LOGOUT_LOGINIP = "logout_loginIp";

	/**
	 * 是否记录日志 （0：否；1 ：是）
	 */
	public static final String SYSTEM_LOG_ENABLE_FLAG="SYSTEM.LOG.ENABLE.FLAG";
	
	
	/**
	 * 系统运行模式（0:生产;1:调试）
	 */
	public static final String SYSTEM_RUM_MODE="SYSTEM.RUN.MODE";
	
	
	/**
	 * 系统运行模式_生产
	 */
	public static final String SYSTEM_RUM_MODE_PRO="0";
	
	/**
	 * 系统运行模式_调试
	 */
	public static final String SYSTEM_RUM_MODE_DEBUG="1";
	
	
	/**
	 * 系统前端异常处理模式（0:旧模式;1:新模式）
	 */
	public static final String SYSTEM_WEB_EXCEPTION_MODE="SYSTEM.WEB.EXCEPTION.MODE";
	
	
	/**
	 * 系统前端异常处理模式_旧模式
	 */
	public static final int SYSTEM_WEB_EXCEPTION_MODE_OLD = 0;
	
	/**
	 * 系统前端异常处理模式_新模式
	 */
	public static final int SYSTEM_WEB_EXCEPTION_MODE_NEW = 1;
	
	
	/**
	 * 密码盐
	 */
	public static final String MMPD_SALT = "MMPD_SALT";
	
	/**
	 * SESSIONID
	 */
	public static final String SSID = "ssid";
	
	/**
	 * 登录ip
	 */
	public static final String LOGIN_IP = "loginIp";
	
	/**
	 * 登录时间
	 */
	public static final String LOGIN_TIME = "loginTime";
	
	/**
	 * sm2私钥
	 */
	public static final String PRIKEY = "priKey";
	
	/**
	 * sm2公钥
	 */
	public static final String PUBKEY = "pubKey";
	
	/**
	 * SMS2后传前参数加密私钥
	 */
	public static final String SMS2_BFF_PRK = "SMS2.BFF.PRK";
	
	/**
	 * SMS2后传前参数加密公钥
	 */
	public static final String SMS2_BFF_PUK = "SMS2.BFF.PUK";

	/**
	 * 检验用户密码有效期（单位:天）
	 */
	public static final String TIME_OVERDUE_THRESHOLD = "TIME.OVERDUE.THRESHOLD";
	/**
	 * 历史密码修改近几次参数
	 */
	public static final String MMPD_RECENTLY_REPETITIONS = "MMPD.RECENTLY.REPETITIONS";

	/**
	 * 历史密码修改重复次数参数
	 */
	public static final String MMPD_HISTORICAL_REPETITIONS = "MMPD.HISTORICAL.REPETITIONS";
}
