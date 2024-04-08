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
package com.student.work.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.core.RestConstant;
import com.student.core.Result;
import com.student.core.ResultGenerator;
import com.student.shiro.controller.CheckUserController;
import com.student.work.user.model.UserDO;
import com.student.work.user.model.UserDTO;
import com.student.work.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**+
 * @ClassName: UserController
 * @Description: 用户表 控制层
 * @author: LeiYongQiang
 * @date: 2024-03-26
 */
@RestController
@RequestMapping(UserController.PATH)
public class UserController extends CheckUserController {

	public final static String PATH = RestConstant.VERSION_V1 + "/user";
	// 日志审计列表中“菜单路径”
	private static final String MENU_NAME = "用户管理";
	// 日志审计列表中“日志内容”显示信息
	private static final String CONTENT = "用户管理";

	@Autowired
	private UserService userService;

	/**
	 * @Title: getUserPage
	 * @Description: 分页查询
	 * @param userDto
	 * @return
	 */
	@PostMapping(value = "/getUserPage")
	public Result getUserPage(@RequestBody @Validated UserDTO userDto) throws Exception {
		Page<UserDTO> page = new Page<>(userDto.getPageNo(),userDto.getPageSize());
		Page<UserDO> result = userService.selectPage(page, userDto);
		return ResultGenerator.genOkResult(result);
	}
	/**
	 * @Title: selectOne
	 * @Description:
	 */
	@PostMapping("/selectOne")
	public Result selectOne(@RequestBody UserDTO userDto) {
		if (userDto.getId() == null) {
			return ResultGenerator.genFailedResult("id不能为空！");
		}
		UserDO userDo = userService.selectOne(userDto);
		return ResultGenerator.genOkResult(userDo);
	}

	/**
	 * @Title: insert
	 * @Description: 新增记录
	 * @return
	 */
	@PostMapping("/add")
	public Result insert(@RequestBody @Validated UserDO userDo) {
		Map<String,String> result = userService.insertUser(userDo);
		if (Integer.parseInt(result.get("resultCount")) > 0) {
			return ResultGenerator.genOkResult("添加用户成功",result.get("resultCount"));
		}
		return ResultGenerator.genFailedResult(result.get("resultMsg"));
	}
	/**
	 * @Title: update
	 * @Description: 修改记录
	 * @return
	 */
	@PostMapping("/updateById")
	public Result update(@RequestBody  UserDO userDo) {
		if (userDo.getId() == null) {
			return ResultGenerator.genFailedResult("主键id不能为空！");
		}
		Map<String,String> result = userService.update(userDo);
		if (Integer.parseInt(result.get("resultCount")) > 0) {
			return ResultGenerator.genOkResult("修改成功");
		}
		return ResultGenerator.genFailedResult(result.get("resultMsg"));
	}


	/**
	 * @Title: deleteBatchIds
	 * @Description: 批量删除
	 * @param
	 * @return
	 */
	@PostMapping(value = "/deleteBatchIds")
	public Result deleteBatchIds(@RequestBody UserDTO userDto) {
		if (userDto.getIds().size() < 1) {
			return ResultGenerator.genFailedResult("id不能为空！");
		}
		int i = userService.deleteBatchIds(userDto.getIds());
		if (i < 0) {
			return ResultGenerator.genFailedResult("删除失败");
		}
		return ResultGenerator.genOkResult(i);
	}
}
