package com.student.work.grade.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.work.Class.model.IClassDO;
import com.student.work.grade.model.GradeDO;
import com.student.work.grade.model.GradeDTO;
import com.student.work.grade.model.GradeVO;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: IClassService
 * @Description: 成绩业务层接口
 * @author: LeiYongQiang
 * @date: 2024-04-02
 */
public interface GradeService {

    Page<GradeVO> getPage(Page<GradeVO> page, GradeDTO gradeDTO);

    Map<String, String> addGrade(GradeDO gradeDO);

    Map<String, String> update(GradeDO gradeDO);

    int deleteBatchIds(List<Integer> ids);
}
