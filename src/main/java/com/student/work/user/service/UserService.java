/**   
 * Copyright © 2019 国网信通亿力科技有限责任公司. All rights reserved.
 * 
 * @Title:IUserService.java 
 * @Prject: com.ylkj.cloud.upms.user.service
 * @Package: com.ylkj.cloud.upms.user.service
 * @author: HuangZhenPeng   
 * @date: 2019-08-20
 * @version: V1.0   
 */
package com.student.work.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.work.user.model.UserDO;
import com.student.work.user.model.UserDTO;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: IUserService
 * @Description: 用户表 服务接口
 * @author: HuangZhenPeng
 * @date: 2019-08-20
 */
public interface UserService {
	

	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	UserDO selectById(Long id);

	/**
	 *根据条件查询
	 * @param userDto
	 * @return
	 */
	UserDO selectOne(UserDTO userDto);

	/**
	 * 分页查询用户信息
	 *
	 * @Description: 分页查询
	 * @param userDto
	 * @param page
	 * @return UserDO
	 */
    Page<UserDO> selectPage(Page<UserDTO> page, UserDTO userDto);


	/**
	 * 根据id删除用户信息
	 * @param ids
	 * @return int
	 */
	int deleteBatchIds(List<Long> ids);

	/**
	 * 新增用户
	 * @param userDo
	 * @return Map
	 */
	Map<String, String> insertUser(UserDO userDo);

	/**
	 * 根据id更新用户信息
	 * @param userDo
	 * @return Map
	 */
	Map<String, String> update(UserDO userDo);

//	/**
//	 * @Title: selectUserInfo
//	 * @Description: 查询用户信息
//	 * @param userDto
//	 * @return
//	 */
//	List<UserDO> selectUserList(UserDTO userDto);


}
