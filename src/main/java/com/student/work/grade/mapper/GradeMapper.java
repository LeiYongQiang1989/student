package com.student.work.grade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.work.grade.model.GradeDO;
import com.student.work.grade.model.GradeDTO;
import com.student.work.grade.model.GradeVO;
import org.apache.ibatis.annotations.Param;


/**
 * @ClassName: UserMapper
 * @Description: 用户部门关联关系 Mapper 接口
 * @author: HuangZhenPeng
 * @date: 2019-08-20
 */
public interface GradeMapper extends BaseMapper<GradeDO> {


    Page<GradeVO> getPage(Page<GradeVO> page, @Param("param") GradeDTO gradeDTO);


}
