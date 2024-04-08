package com.student.work.Class.model;

import com.student.core.BaseDTO;
import lombok.Data;

import java.util.List;


/**
 * @ClassName: IClassDTO
 * @Description: 班级参数对象
 * @author: LeiYongQiang
 * @date: 2024-04-02
 */
@Data
public class IClassDTO extends BaseDTO {
    /**
     * 主键id
     */
    private Integer id;


    /**
     * 院系id
     */
    private Integer departmentId;


    /**
     * 班级名称
     */
    private String className;


    private List<Integer> ids;


}
