package com.student.work.department.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.student.work.Class.model.IClassDO;
import com.student.work.department.model.DepartmentDO;
import java.util.List;


/**
 * @ClassName: DepartmentMapper
 * @Description: 院系数据持久层接口
 * @author: LeiYongQiang
 * @date: 2024-04-03
 */
public interface DepartmentMapper extends BaseMapper<DepartmentDO> {


    List<IClassDO> selectList();


}
