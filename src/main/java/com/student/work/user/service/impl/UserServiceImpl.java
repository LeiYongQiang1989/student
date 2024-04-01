/**   
 * Copyright © 2019 国网信通亿力科技有限责任公司. All rights reserved.
 * 
 * @Title:UserServiceImpl.java 
 * @Prject: com.ylkj.cloud.upms.user.service.impl
 * @Package: com.ylkj.cloud.upms.user.service.impl
 * @author: HuangZhenPeng   
 * @date: 2019-08-20
 * @version: V1.0   
 */
package com.student.work.user.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.utils.Sm4Util;
import com.student.work.user.mapper.UserMapper;
import com.student.work.user.model.UserDO;
import com.student.work.user.model.UserDTO;
import com.student.work.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: UserServiceImpl
 * @Description: 用户表 服务接口实现
 * @author: HuangZhenPeng
 * @date: 2019-08-20
 */
@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;

	/**
	 * 国密4加密常量
	 */
	@Value("${SMS4.LOGIN}")
	private String sm4Key;


	@Override
	public UserDO selectById(Long id) {
		UserDO user = userMapper.selectById(id);
		return user;
	}
	
	@Override
	public UserDO selectOne(UserDTO userDto) {
		UserDO userDo = userMapper.selectOne(userDto);
		return userDo;
	}

	@Override
	public Page<UserDO> selectPage(Page<UserDTO> page, UserDTO userDto) {
		return userMapper.selectPage(page,userDto);
	}

	@Override
	public int deleteBatchIds(List<Long> ids) {
		return  userMapper.deleteBatchIds(ids);
	}

	@Override
	public Map<String, String> insertUser(UserDO userDo) {
		Map<String, String> resMap = new HashMap<>(2);
		UserDTO userDto = new UserDTO();
		userDto.setUserNo(userDo.getUserNo());
		UserDO user = userMapper.selectOne(userDto);
		if ( user != null ) {
			resMap.put("resultCount","0");
			resMap.put("resultMsg","用户已存在");
			return resMap;
		}
		userDo.setDelFlag("1");
		userDo.setStatus("1");
		//将明文密码进行加密
		String passWord  = Sm4Util.encodeByHex(userDo.getPassWord(), sm4Key);
		userDo.setPassWord(passWord);
		int result = userMapper.insert(userDo);
		resMap.put("resultCount",String.valueOf(result));
		if (result > 0) {
			resMap.put("resultMsg","添加用户成功");
			return resMap;
		}
		resMap.put("resultMsg","添加用户失败");
		return resMap;
	}

	@Override
	public Map<String, String> update(UserDO userDo) {
		Map<String, String> resMap = new HashMap<>();
		int result = userMapper.updateById(userDo);
		resMap.put("resultCount",String.valueOf(result));
		if (result > 0) {
			resMap.put("resultMsg","更新成功");
			return resMap;
		}
		resMap.put("resultMsg","更新失败");
		return resMap;
	}


//	@Override
//	public List<UserDO> selectUserList(UserDTO userDto) {
//		return userMapper.selectUserList(userDto);
//	}


}
