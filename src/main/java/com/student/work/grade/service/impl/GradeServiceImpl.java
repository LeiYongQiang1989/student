package com.student.work.grade.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.work.grade.mapper.GradeMapper;
import com.student.work.grade.model.GradeDO;
import com.student.work.grade.model.GradeDTO;
import com.student.work.grade.model.GradeVO;
import com.student.work.grade.service.GradeService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: IClassServiceImpl
 * @Description: 成绩业务层接口实现类
 * @author: LeiYongQiang
 * @date: 2024-04-02
 */
public class GradeServiceImpl implements GradeService {

    @Resource
    private GradeMapper gradeMapper;

    @Override
    public Page<GradeVO> getPage(Page<GradeVO> page,GradeDTO gradeDTO) {
        return gradeMapper.getPage(page,gradeDTO);
    }

    @Override
    public Map<String, String> addGrade(GradeDO gradeDO) {
        Map<String, String> resMap = new HashMap<>(2);
        int result = gradeMapper.insert(gradeDO);
        resMap.put("resultCount",String.valueOf(result));
        if (result > 0) {
            resMap.put("resultMsg","添加成绩成功");
            return resMap;
        }
        resMap.put("resultMsg","添加成绩失败");
        return resMap;
    }

    @Override
    public Map<String, String> update(GradeDO gradeDO) {
        Map<String, String> resMap = new HashMap<>();
        int result = gradeMapper.updateById(gradeDO);
        resMap.put("resultCount",String.valueOf(result));
        if (result > 0) {
            resMap.put("resultMsg","更新成功");
            return resMap;
        }
        resMap.put("resultMsg","更新失败");
        return resMap;
    }

    @Override
    public int deleteBatchIds(List<Integer> ids) {
        return  gradeMapper.deleteBatchIds(ids);
    }
}
