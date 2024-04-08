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

import com.student.core.BaseDTO;
import lombok.Data;

import java.util.List;


/**
 * @ClassName: GradeDO
 * @Description: 成绩表 传输对象
 * @author: LeiYongQiang
 * @date: 2024-04-01
 */
@Data
public class GradeDTO extends BaseDTO {

    private static final long serialVersionUID=1L;


    /**
     * 创建人
     */
    private Integer createId;

    /**
     * 班级id
     */
    private Integer classId;


    /**
     * 院系id
     */
    private Integer departmentId;


    /**
     * 学生id
     */
    private Integer studentId;

    /**
     * 学科id
     */
    private Integer subjectId;


    /**
     * 学科成绩
     */
    private String gradeNumber;


    private List<Integer> ids;




    private String endNumber;

    private String startNumber;

}
