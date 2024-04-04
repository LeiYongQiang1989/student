package com.student.work.Class.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.work.Class.mapper.IClassMapper;
import com.student.work.Class.model.IClassDO;
import com.student.work.Class.model.IClassDTO;
import com.student.work.Class.service.IClassService;
import com.student.work.subject.model.SubjectDO;
import com.student.work.user.model.UserDO;
import com.student.work.user.model.UserDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @ClassName: IClassServiceImpl
 * @Description: 班级业务层接口实现
 * @author: LeiYongQiang
 * @date: 2024-04-01
 */
@Service("IClassService")
public class IClassServiceImpl implements IClassService {

    @Resource
    private IClassMapper iClassMapper;

    @Override
    public Page<IClassDO> selectPage(Page<UserDTO> page, IClassDTO iClassDTO) {
        return iClassMapper.selectPage(page,iClassDTO);
    }

    @Override
    public Map<String, String> addClass(IClassDO iClassDO) {
        Map<String, String> resMap = new HashMap<>(2);
        IClassDO iClassSource  = iClassMapper.getOne(iClassDO);
        if (iClassSource != null) {
            resMap.put("resultCount", "0");
            resMap.put("resultMsg","该班级已存在！");
            return resMap;
        }

        int result = iClassMapper.insert(iClassDO);
        resMap.put("resultCount",String.valueOf(result));
        if (result > 0) {
            resMap.put("resultMsg","添加班级成功");
            return resMap;
        }
        resMap.put("resultMsg","添加班级失败");
        return resMap;
    }

    @Override
    public Map<String, String> update(IClassDO iClassDO) {
        Map<String, String> resMap = new HashMap<>();
        int result = iClassMapper.updateById(iClassDO);
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
        return  iClassMapper.deleteBatchIds(ids);
    }


}
