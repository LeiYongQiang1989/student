package com.student.work.department.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.student.work.Class.model.IClassDO;
import com.student.work.department.mapper.DepartmentMapper;
import com.student.work.department.model.DepartmentDO;
import com.student.work.department.service.DepartmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: DepartmentServiceImpl
 * @Description: 院系业务层接口实现类
 * @author: LeiYongQiang
 * @date: 2024-04-02
 */
@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {


    @Resource
    private DepartmentMapper departmentMapper;

    @Override
    public List<DepartmentDO> getList() {
        return departmentMapper.selectList();
    }

    @Override
    public Map<String, String> add(DepartmentDO departmentDO) {
        Map<String, String> resMap = new HashMap<>(2);
        DepartmentDO departmentSource  = departmentMapper.selectOne(new LambdaQueryWrapper<DepartmentDO>()
                .eq(DepartmentDO::getDepartmentName,departmentDO.getDepartmentName()));
        if (departmentSource != null) {
            resMap.put("resultCount", "0");
            resMap.put("resultMsg","该班级已存在！");
            return resMap;
        }
        int result = departmentMapper.insert(departmentDO);
        resMap.put("resultCount",String.valueOf(result));
        if (result > 0) {
            resMap.put("resultMsg","添加成功");
            return resMap;
        }
        resMap.put("resultMsg","添加失败");
        return resMap;
    }

    @Override
    public Map<String, String> update(DepartmentDO departmentDO) {
        Map<String, String> resMap = new HashMap<>();
        int result = departmentMapper.updateById(departmentDO);
        resMap.put("resultCount",String.valueOf(result));
        if (result > 0) {
            resMap.put("resultMsg","更新成功");
            return resMap;
        }
        resMap.put("resultMsg","更新失败");
        return resMap;
    }

    @Override
    public int deleteById(Integer id) {
        return departmentMapper.deleteById(id);
    }


}
