/**   
 * Copyright © 2019 国网信通亿力科技有限责任公司. All rights reserved.
 * 
 * @Title:UserMapper.java 
 * @Prject: com.ylkj.cloud.upms.user.mapper
 * @Package: com.ylkj.cloud.upms.user.mapper
 * @author: HuangZhenPeng   
 * @date: 2019-08-20
 * @version: V1.0   
 */
package com.student.work.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.work.user.model.UserDO;
import com.student.work.user.model.UserDTO;
import org.apache.ibatis.annotations.Param;


/**
 * @ClassName: UserMapper
 * @Description: 用户表 Mapper 接口
 * @author: HuangZhenPeng
 * @date: 2019-08-20
 */
public interface UserMapper extends BaseMapper<UserDO> {
	/**
	 * 分页查询用户
	 * @param page
	 * @param userDto
	 * @return_type: Page<UserVO>
	 */
	Page<UserDO> selectPage(Page<UserDTO> page, @Param("param") UserDTO userDto);
	

	/**
	 * 根据传入的用户条件查询单个用户
	 * @param userDto
	 * @return
	 */
	UserDO selectOne(@Param("param") UserDTO userDto);


	/**
	 * @Description: 根据id更新单个用户
	 * @param userDO
	 * @return
	 */
	int updateUser(@Param("param") UserDO userDO);


	/**
	 * @Description: 根据id更新单个用户
	 * @param id
	 * @return
	 */
	int deleteUser(Long id);


//
//	List<UserDO> selectUserList(@Param("param") UserDTO userDto);
}
