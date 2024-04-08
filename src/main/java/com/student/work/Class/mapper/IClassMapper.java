package com.student.work.Class.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.student.work.Class.model.IClassDO;
import com.student.work.Class.model.IClassDTO;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
/**
 * @ClassName: IClassService
 * @Description: 班级数据持久层接口
 * @author: LeiYongQiang
 * @date: 2024-04-01
 */
public interface IClassMapper extends BaseMapper<IClassDO> {

    /**
     * 分页查询用户
     * @param page
     * @param iClassDTO
     * @return_type: Page<UserVO>
     */
    Page<IClassDO> getPage(Page<IClassDTO> page, @Param("param") IClassDTO iClassDTO);

    IClassDO getOne(@Param("param") IClassDO iClassDO);
}
