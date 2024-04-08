package com.student.work.Class.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.work.Class.model.IClassDO;
import com.student.work.Class.model.IClassDTO;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: IClassService
 * @Description: 班级业务层接口
 * @author: LeiYongQiang
 * @date: 2024-04-01
 */
public interface IClassService {

    /**
     * 分页查询班级
     * @param page
     * @param iClassDTO
     * @return_type: Page<UserVO>
     */
    Page<IClassDO> selectPage(Page<IClassDTO> page, IClassDTO iClassDTO);


    /**
     * 添加
     * @param iClassDO
     * @return_type: Map
     */
    Map<String, String> addClass(IClassDO iClassDO);


    Map<String, String> update(IClassDO iClassDO);

    int deleteBatchIds(List<Integer> ids);
}
