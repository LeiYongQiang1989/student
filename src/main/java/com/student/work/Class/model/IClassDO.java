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
package com.student.work.Class.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import javax.validation.constraints.NotNull;


/**
 * @ClassName: IClassDO
 * @Description: 班级表 数据对象
 * @author: LeiYongQiang
 * @date: 2024-04-01
 */
@Data
@TableName("tb_class")
public class IClassDO  {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableField("id")
    private Integer id;


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
    @NotNull(message = "班级名称不能为空")
    private String className;


    /**
     * 院系名称
     */
    private String departmentName;

}
