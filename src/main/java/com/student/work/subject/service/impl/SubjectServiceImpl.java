package com.student.work.subject.service.impl;

import com.student.work.subject.mapper.SubjectMapper;
import com.student.work.subject.model.SubjectDO;
import com.student.work.subject.service.SubjectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: SubjectService
 * @Description: 学科业务层接口实现类
 * @author: LeiYongQiang
 * @date: 2024-04-03
 */
@Service("subjectService")
public class SubjectServiceImpl implements SubjectService {

    @Resource
    private SubjectMapper subjectMapper;

    @Override
    public List<SubjectDO> getList(SubjectDO subjectDO) {
        return subjectMapper.getList(subjectDO);
    }

    @Override
    public Map<String, String> add(SubjectDO subjectDO) {
        Map<String, String> resMap = new HashMap<>(2);
        SubjectDO subjectSource  = subjectMapper.getOne(subjectDO);
        if (subjectSource != null) {
            resMap.put("resultCount", "0");
            resMap.put("resultMsg","该学科已存在！");
            return resMap;
        }

        int result = subjectMapper.insert(subjectDO);
        resMap.put("resultCount", String.valueOf(result));
        if (result > 0) {
            resMap.put("resultMsg","添加成功");
            return resMap;
        }
        resMap.put("resultMsg","添加失败");
        return resMap;
    }

    @Override
    public Map<String, String> update(SubjectDO subjectDO) {
        Map<String, String> resMap = new HashMap<>();
        int result = subjectMapper.updateById(subjectDO);
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
        return subjectMapper.deleteById(id);
    }
}
