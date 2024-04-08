package com.student.work.subject.controller;

import com.student.core.RestConstant;
import com.student.core.Result;
import com.student.core.ResultGenerator;
import com.student.shiro.controller.CheckUserController;
import com.student.work.subject.model.SubjectDO;
import com.student.work.subject.service.SubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: SubjectController
 * @Description: 学科（课程）控制层
 * @author: LeiYongQiang
 * @date: 2024-04-03
 */
@RestController
@RequestMapping(SubjectController.PATH)
@Slf4j
public class SubjectController extends CheckUserController {

    public final static String PATH = RestConstant.VERSION_V1 + "/subject";

    @Resource
    private SubjectService subjectService;

    /**
     * @Title: getList
     * @Description: 查询所有的
     * @return
     */
    @PostMapping(value = "/getList")
    public Result getList(@RequestBody SubjectDO subjectDO) throws Exception {
        List<SubjectDO> result = subjectService.getList(subjectDO);
        return ResultGenerator.genOkResult(result);
    }


    /**
     * @Title: add
     * @Description: 添加
     * @param subjectDO
     * @return
     */
    @PostMapping(value = "/add")
    public Result getClassPage(@RequestBody @Validated  SubjectDO subjectDO) throws Exception {
        Map<String,String> result = subjectService.add(subjectDO);
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
    public Result update(@RequestBody  SubjectDO subjectDO) {
        if (subjectDO.getId() == null) {
            return ResultGenerator.genFailedResult("主键id不能为空！");
        }
        Map<String,String> result = subjectService.update(subjectDO);
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
    @PostMapping(value = "/deleteById")
    public Result deleteBatchIds(@RequestBody SubjectDO subjectDO) {
        if (subjectDO.getId() == null) {
            return ResultGenerator.genFailedResult("id不能为空！");
        }
        int i = subjectService.deleteById(subjectDO.getId());
        if (i < 1) {
            return ResultGenerator.genFailedResult("删除失败");
        }
        return ResultGenerator.genOkResult("删除成功",i);
    }

}
