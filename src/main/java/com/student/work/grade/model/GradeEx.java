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

import lombok.Data;

import java.util.Date;


/**
 * @ClassName: GradeEx
 * @Description: 成绩导出对象
 * @author: LeiYongQiang
 * @date: 2024-04-08
 */
@Data
public class GradeEx {

    /**
     * 班级名称
     */
    private String className;


    /**
     * 学生名称
     */
    private String studentName;



    /**
     * 科目名称
     */
    private String subjectName;


    /**
     * 学科成绩
     */
    private String gradeNumber;


    /**
     * 院系
     */
    private String departmentName;


    private String createTime;



    /**
     * 创建人
     */
    private String createName;


    /**
     * 更新时间
     */
    private String updateTime;
}
