package com.student.work.grade.model;

import lombok.Data;

/**
 * @ClassName: StatisticVO
 * @Description: 成绩统计 展示对象
 * @author: LeiYongQiang
 * @date: 2024-04-01
 */
@Data
public class StatisticVO {

    /**
     * 最高分数
     */
    private String maxGradeNumber;

    /**
     * 最低分数
     */
    private String minGradeNumber;

    /**
     * 平均分数
     */
    private String avGradeNumber;


    /**
     * 优秀人数
     */
    private Integer excellentCount;
    /**
     * 优秀率
     */
    private String excellentRate;



    /**
     * 良好率
     */
    private String goodRate;

    /**
     * 良好人数
     */
    private Integer goodCount;


    /**
     * 及格率
     */
    private String passRate;

    /**
     * 及格人数
     */
    private Integer passCount;

    /**
     * 未及格率
     */
    private String failureRate;

    /**
     * 未及人数
     */
    private Integer failureCount;

//
//    最高分、最低分、平均分、优秀率、良好率、及格率、未及格率

}
