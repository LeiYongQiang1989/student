/**   
 * Copyright © 2019 国网信通亿力科技有限责任公司. All rights reserved.
 * 
 * @Title:UserDO.java 
 * @Prject: com.ylkj.cloud.upms.user.model
 * @Package: com.ylkj.cloud.upms.user.model
 * @author: HuangZhenPeng   
 * @date: 2019-08-20
 * @version: V1.0   
 */
package com.student.work.user.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;


/**
 * @ClassName: UserDO
 * @Description: 用户表 数据对象
 * @author: LeiYongQiang
 * @date: 2024-03-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tb_user")
public class UserDO extends BaseDO {

    private static final long serialVersionUID=1L;

    /**
     * 登录账号
     */
    @TableField("user_no")
    @NotNull(message = "账号不能为空")
    private String userNo;

    /**
     * 真实姓名
     */
    @TableField("real_name")
    @NotNull(message = "姓名不能为空")
    private String realName;

    /**
     * 密码
     */
    @TableField("pass_word")
    @NotNull(message = "密码不能为空")
    private String passWord;


    /**
     * 性别（1：男 2：女）
     */
    @TableField("sex")
    @NotNull(message = "性别不能为空")
    private Integer sex;

    /**
     * 电话
     */
    @TableField("phone")
    @NotNull(message = "电话不能为空")
    private String phone;


    /**
     * 状态(1：正常  2：冻结 ）
     */
    @TableField("status")
    private String status;

    /**
     * 删除状态（1：正常，-1：已删除）
     */
    @TableField("del_flag")
    private String delFlag;


    /**
     * 角色编码
     */
    @TableField("role_code")
    @NotNull(message = "角色编码不能为空")
    private String roleCode;

    /**
     * 院系
     */
    @TableField("department")
    private String  department;

    /**
     * 院系id
     */
    @TableField("department_id")
    @NotNull(message = "院系id不能为空")
    private Integer departmentId;

    /**
     * 班级名称
     */
    @TableField("class_name")
    private String className;

    /**
     * 班级id
     */
    @TableField("class_id")
    @NotNull(message = "班级id不能为空")
    private Integer classId;


}
