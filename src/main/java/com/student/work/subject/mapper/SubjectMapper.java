package com.student.work.subject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.student.work.subject.model.SubjectDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName: IClassService
 * @Description: 班级数据持久层接口
 * @author: LeiYongQiang
 * @date: 2024-04-01
 */
public interface SubjectMapper extends BaseMapper<SubjectDO> {

//    /**
//     * 分页查询用户
//     * @param page
//     * @param iClassDTO
//     * @return_type: Page<UserVO>
//     */
//    Page<IClassDO> selectPage(Page<UserDTO> page, @Param("param") IClassDTO iClassDTO);

    List<SubjectDO> getList( @Param("param")SubjectDO subjectDO);

    SubjectDO getOne( @Param("param")SubjectDO subjectDO);
}
