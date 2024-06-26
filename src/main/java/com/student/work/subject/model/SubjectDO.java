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
package com.student.work.subject.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * @ClassName: SubjectDO
 * @Description: 学科表 数据对象
 * @author: LeiYongQiang
 * @date: 2024-04-01
 */
@Data
@TableName("tb_subject")
public class SubjectDO  {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableField("id")
    private Integer id;



    /**
     * 学科名称
     */
    @TableField("subject_name")
    @NotNull(message = "学科名称不能为空")
    private String subjectName;




}
