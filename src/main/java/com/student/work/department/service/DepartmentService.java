package com.student.work.department.service;

import com.student.work.Class.model.IClassDO;
import com.student.work.department.model.DepartmentDO;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: DepartmentService
 * @Description: 院系业务层接口
 * @author: LeiYongQiang
 * @date: 2024-04-02
 */
public interface DepartmentService {

    List<DepartmentDO> getList();

    Map<String, String> add(DepartmentDO departmentDO);

    Map<String, String> update(DepartmentDO departmentDO);

    int deleteById(Integer id);
}
