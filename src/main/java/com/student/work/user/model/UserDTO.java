/**   
 * Copyright © 2019 国网信通亿力科技有限责任公司. All rights reserved.
 * 
 * @Title:UserDTO.java 
 * @Prject: com.ylkj.cloud.upms.user.model
 * @Package: com.ylkj.cloud.upms.user.model
 * @author: HuangZhenPeng   
 * @date: 2019-08-20
 * @version: V1.0   
 */
package com.student.work.user.model;

import com.student.core.BaseDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: UserDTO
 * @Description: 用户表 传输对象
 * @author: LeiYongQiang
 * @date: 2024-03-25
 */
@Data
public class UserDTO extends BaseDTO {

    private static final long serialVersionUID=1L;

    private Long id;

    private List<Long> ids;
    //更新时间
    private String userNo;

    //更新时间
    private String realName;

    //密码
    private String passWord;


    //性别（1：男 2：女
    private Integer sex;


    //电话
    private String phone;

    //状态(1：正常  2：冻结
    private Integer status;


    /**
     * 删除状态（1：正常，-1：已删除）
     */
    private String delFlag;

    //更新时间
    private Date updateTime;

    //角色编码
    private String roleCode;

    // md5密码盐
    private String salt;

    /**
     * 传参所需解密参数
     */
    private String decryptKey;

    private Integer departmentId;

    private Integer classId;
}
