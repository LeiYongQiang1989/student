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
import com.baomidou.mybatisplus.annotation.TableName;
import com.student.work.user.model.BaseDO;
import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * @ClassName: GradeDO
 * @Description: 成绩表 数据对象
 * @author: LeiYongQiang   1.添加   2. 修改   3. 统计分析  4. 查询  5.导出
 * @date: 2024-04-01
 */


@Data
@TableName("tb_grade")
public class GradeDO extends BaseDO {

    private static final long serialVersionUID=1L;


    /**
     * 创建人id
     */
    @TableField("create_id")
    @NotNull(message = "创建人id不能为空")
    private Integer createId;

    /**
     * 创建人
     */
    @TableField("create_name")
    private String createName;



    /**
     * 学生id
     */
    @TableField("student_id")
    @NotNull(message = "学生id不能为空")
    private Integer studentId;


    /**
     * 学生姓名
     */
    @TableField("student_name")
    private String studentName;




    /**
     * 学科成绩
     */
    @TableField("grade_number")
    @NotNull(message = "学科成绩不能为空")
    private String gradeNumber;


    /**
     * 院系id
     */
    @TableField("department_id")
    @NotNull(message = "院系id不能为空")
    private Integer departmentId;

    /**
     * 院系名称
     */
    @TableField("department_name")
    private String departmentName;


    /**
     * 学科id
     */
    @TableField("subject_id")
    @NotNull(message = "学科id不能为空")
    private Integer subjectId;


    /**
     * 班级id
     */
    @TableField("class_id")
    @NotNull(message = "班级id不能为空")
    private Integer classId;


}
