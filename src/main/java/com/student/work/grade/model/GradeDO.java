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

/**
 * 1.添加   2. 修改
 * 3. 统计分析 ：最高分、最低分、平均分、优秀率、良好率、及格率、未及格率
 * 4. 查询  5.导出
 */
@Data
@TableName("tb_grade")
public class GradeDO extends BaseDO {

    private static final long serialVersionUID=1L;


    /**
     * 创建人
     */
    @TableField("create_user")
    @NotNull(message = "创建人不能为空")
    private Integer createUser;

    /**
     * 班级id
     */
    @TableField("class_id")
    @NotNull(message = "班级id不能为空")
    private Integer classId;


    /**
     * 学生id
     */
    @TableField("student_id")
    @NotNull(message = "学生id不能为空")
    private Integer studentId;

    /**
     * 学科id
     */
    @TableField("subject_id")
    @NotNull(message = "学科id不能为空")
    private Integer subjectId;


    /**
     * 学科成绩
     */
    @TableField("grade_number")
    @NotNull(message = "学科成绩不能为空")
    private String gradeNumber;




}
