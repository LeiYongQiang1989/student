package com.student.work.subject.service;

import com.student.work.subject.model.SubjectDO;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: SubjectService
 * @Description: 学科业务层接口
 * @author: LeiYongQiang
 * @date: 2024-04-03
 */
public interface SubjectService {

    List<SubjectDO> getList(SubjectDO subjectDO);

    Map<String, String> add(SubjectDO subjectDO);

    Map<String, String> update(SubjectDO subjectDO);

    int deleteById(Integer id);
}
