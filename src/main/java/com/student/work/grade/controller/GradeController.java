package com.student.work.grade.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.core.RestConstant;
import com.student.core.Result;
import com.student.core.ResultGenerator;
import com.student.shiro.controller.CheckUserController;
import com.student.work.grade.model.GradeDO;
import com.student.work.grade.model.GradeDTO;
import com.student.work.grade.model.GradeVO;
import com.student.work.grade.model.StatisticVO;
import com.student.work.grade.service.GradeService;
import com.student.work.user.model.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName: GradeController
 * @Description: 班级控制层
 * @author: LeiYongQiang
 * @date: 2024-04-02
 */
@RestController
@RequestMapping(GradeController.PATH)
@Slf4j
public class GradeController extends CheckUserController {

    public final static String PATH = RestConstant.VERSION_V1 + "/grade";

    @Resource
    private GradeService gradeService;

    /**
     * @Title: getPage
     * @Description: 分页查询
     * @param gradeDTO
     * @return
     */
    @PostMapping(value = "/getPage")
    public Result getPage(@RequestBody GradeDTO gradeDTO)  {
        UserDO  userDO = getUser();
        Page<GradeVO> page = new Page<>(gradeDTO.getPageNo(),gradeDTO.getPageSize());
        if (userDO.getRoleCode() == "3") {
            gradeDTO.setStudentId(userDO.getId());
        } else if (userDO.getRoleCode() == "2") {
            gradeDTO.setCreateId(userDO.getId());
        }
        Page<GradeVO> result = gradeService.getPage(page, gradeDTO);
        return ResultGenerator.genOkResult(result);
    }


    /**
     * @Title: add
     * @Description: 添加
     * @param gradeDO
     * @return
     */
    @PostMapping(value = "/add")
    public Result add(@RequestBody @Validated GradeDO gradeDO) throws Exception {
        // 角色为3的学生无添加、修改、删除权限
        UserDO  userDO = getUser();
        if (userDO.getRoleCode() == "3") {
            return ResultGenerator.genFailedResult("该用户无权限");
        }
        Map<String,String> result = gradeService.addGrade(gradeDO);
        if (Integer.parseInt(result.get("resultCount")) > 0) {
            return ResultGenerator.genOkResult("添加成功");
        }
        return ResultGenerator.genFailedResult(result.get("resultMsg"));
    }

    /**
     * @Title: update
     * @Description: 修改记录
     * @return
     */
    @PostMapping("/updateById")
    public Result update(@RequestBody  GradeDO gradeDO) {
        // 角色为3的学生无添加、修改、删除权限
        UserDO  userDO = getUser();
        if (userDO.getRoleCode() == "3") {
            return ResultGenerator.genFailedResult("该用户无权限");
        }
        if (gradeDO.getId() == null) {
            return ResultGenerator.genFailedResult("主键id不能为空！");
        }
        Map<String,String> result = gradeService.update(gradeDO);
        if (Integer.parseInt(result.get("resultCount")) > 0) {
            return ResultGenerator.genOkResult("修改成功");
        }
        return ResultGenerator.genFailedResult(result.get("resultMsg"));
    }

    /**
     * @Title: deleteBatchIds
     * @Description: 批量删除
     * @param
     * @return
     */
    @PostMapping(value = "/deleteBatchIds")
    public Result deleteBatchIds(@RequestBody GradeDTO gradeDTO) {
        // 角色为3的学生无添加、修改、删除权限
        UserDO  userDO = getUser();
        if (userDO.getRoleCode() == "3") {
            return ResultGenerator.genFailedResult("该用户无权限");
        }
        if (gradeDTO.getIds().size() == 0) {
            return ResultGenerator.genFailedResult("id不能为空！");
        }
        int i = gradeService.deleteBatchIds(gradeDTO.getIds());
        if (i < 0) {
            return ResultGenerator.genFailedResult("删除失败");
        }
        return ResultGenerator.genOkResult(i);
    }


    /**
     *
     * 3. 统计分析 ：最高分、最低分、平均分、优秀率、良好率、及格率、未及格率
     *  5.导出
     */
    /**
     * @Title: getStatistics
     * @Description: 统计
     * @param gradeDTO
     * @return
     */
    @PostMapping(value = "/getStatistics")
    public Result getStatistics(@RequestBody GradeDTO gradeDTO)  {
        // 角色为3的学生无添加、修改、删除权限
        UserDO  userDO = getUser();
        if (userDO.getRoleCode() == "3") {
            return ResultGenerator.genFailedResult("该用户无权限");
        }
        if (gradeDTO.getSubjectId() == null ) {
            return ResultGenerator.genFailedResult("统计时学科id不能为空！");
        }
        StatisticVO result = gradeService.getStatistics(gradeDTO);
        return ResultGenerator.genOkResult(result);
    }


}
