package com.student.work.department.mapper;///**

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.student.work.Class.model.IClassDO;
import com.student.work.department.model.DepartmentDO;

import java.util.List;


/**
 * @ClassName: UserMapper
 * @Description: 用户部门关联关系 Mapper 接口
 * @author: HuangZhenPeng
 * @date: 2019-08-20
 */
public interface DepartmentMapper extends BaseMapper<DepartmentDO> {

    List<DepartmentDO> selectL(String orgCode);

    List<IClassDO> selectList();
}
