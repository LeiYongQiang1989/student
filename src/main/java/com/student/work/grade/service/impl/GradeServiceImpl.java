package com.student.work.grade.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.work.grade.mapper.GradeMapper;
import com.student.work.grade.model.GradeDO;
import com.student.work.grade.model.GradeDTO;
import com.student.work.grade.model.GradeVO;
import com.student.work.grade.model.StatisticVO;
import com.student.work.grade.service.GradeService;

import javax.annotation.Resource;
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
