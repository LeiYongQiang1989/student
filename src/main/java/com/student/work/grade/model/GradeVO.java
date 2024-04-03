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
package com.student.work.grade.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.student.work.user.model.BaseDO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * @ClassName: GradeDO
 * @Description: 成绩表 展示对象
 * @author: LeiYongQiang
 * @date: 2024-04-01
 */
@Data
public class GradeVO {

    private static final long serialVersionUID=1L;

    private Integer id;
    /**
     * 创建人id
     */
    private Integer createId;

    /**
     * 创建人
     */
    private String createName;

    /**
     * 班级id
     */
    private Integer classId;

    /**
     * 班级名称
     */
    private String className;


    /**
     * 学生id
     */
    private Integer studentId;

    /**
     * 学生名称
     */
    private String studentName;

    /**
     * 学科id
     */
    private Integer subjectId;


    /**
     * 学生名称
     */
    private String subjectName;


    /**
     * 学科成绩
     */
    private String gradeNumber;

    /**
     * 院系id
     */
    private Integer departmentId;

    /**
     * 院系
     */
    private String departmentName;


    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;




}
