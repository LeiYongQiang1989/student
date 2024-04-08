package com.student.work.grade.service.impl;

import cn.hutool.core.date.DateTime;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.utils.ExportUtils;
import com.student.work.grade.mapper.GradeMapper;
import com.student.work.grade.model.*;
import com.student.work.grade.service.GradeService;
import com.student.work.user.model.UserDO;
import com.student.work.user.model.UserDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: IClassServiceImpl
 * @Description: 成绩业务层接口实现类
 * @author: LeiYongQiang
 * @date: 2024-04-02
 */
@Service("gradeService")
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
        GradeDTO gradeDTO = new GradeDTO();
        gradeDTO.setStudentId(gradeDO.getStudentId());
        gradeDTO.setSubjectId(gradeDO.getSubjectId());
        GradeDO grade = gradeMapper.getOne(gradeDTO);
        if ( grade != null ) {
            resMap.put("resultCount","0");
            resMap.put("resultMsg","该学生此科目成绩已存在");
            return resMap;
        }
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
        Map<String, String> resMap = new HashMap<>(2);
        gradeDO.setUpdateTime(new DateTime());
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

    @Override
    public StatisticVO getStatistics(GradeDTO gradeDTO) {
        // 优秀率、良好率、及格率、未及格率
        StatisticVO statisticVO = gradeMapper.getStatistics(gradeDTO);

        // 总数
        int totalCount = gradeMapper.getCount(gradeDTO);

        // 优秀人数
        gradeDTO.setStartNumber("90");
        int excellentCount = gradeMapper.getCount(gradeDTO);
        statisticVO.setExcellentCount(excellentCount);
        statisticVO.setExcellentRate(getPercentage(totalCount,excellentCount));

        // 良好数量
        gradeDTO.setStartNumber("80");
        gradeDTO.setEndNumber("90");
        int goodCount = gradeMapper.getCount(gradeDTO);
        statisticVO.setGoodCount(goodCount);
        statisticVO.setGoodRate(getPercentage(totalCount,goodCount));

        // 及格数量
        gradeDTO.setStartNumber("60");
        gradeDTO.setEndNumber("80");
        int passCount = gradeMapper.getCount(gradeDTO);
        statisticVO.setPassCount(passCount);
        statisticVO.setPassRate(getPercentage(totalCount,passCount));

        // 未及格数量
        int failureCount = totalCount -(excellentCount + goodCount + passCount ) ;
        statisticVO.setFailureCount(failureCount);
        statisticVO.setFailureRate(getPercentage(totalCount,failureCount));

        return statisticVO;

    }

    @Override
    public void export( HttpServletResponse response,GradeDTO gradeDTO) throws Exception {
        List<GradeEx> list = gradeMapper.getList(gradeDTO);
        Map<String,String> map = new HashMap<>();
        map.put("createName","创建人");
        map.put("className","班级");
        map.put("studentName","学生");
        map.put("subjectName","课程");
        map.put("gradeNumber","成绩");
        map.put("departmentName","院系");
        map.put("createTime","时间");
        map.put("updateTime","更新时间");
        ExportUtils.exportExcel("学生成绩表.xlsx", JSONObject.toJSONString(list), GradeEx.class, response);

    }

    private String getPercentage(int totalCount, int part) {
        // 使用BigDecimal进行精确计算
        BigDecimal percentage = new BigDecimal(part)
                .multiply(new BigDecimal(100))
                .divide(new BigDecimal(totalCount), 2, BigDecimal.ROUND_HALF_UP);
        // 格式化输出
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMinimumFractionDigits(2);
        String formattedPercentage = numberFormat.format(percentage.doubleValue() / 100);
        return formattedPercentage;
    }

//    public static void main(String[] args) {
//        // 示例：计算100和200的百分比，并保留2位小数
//        int part = 100;
//        int total = 200;
//
//        // 使用BigDecimal进行精确计算
//        BigDecimal percentage = new BigDecimal(part)
//                .multiply(new BigDecimal(100))
//                .divide(new BigDecimal(total), 2, BigDecimal.ROUND_HALF_UP);
//
//        // 格式化输出
//        NumberFormat numberFormat = NumberFormat.getPercentInstance();
//        numberFormat.setMinimumFractionDigits(2);
//        String formattedPercentage = numberFormat.format(percentage.doubleValue() / 100);
//
//        System.out.println("百分比: " + formattedPercentage);
//    }

}
