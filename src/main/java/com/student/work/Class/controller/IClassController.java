package com.student.work.Class.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.core.RestConstant;
import com.student.core.Result;
import com.student.core.ResultGenerator;
import com.student.shiro.controller.CheckUserController;
import com.student.work.Class.model.IClassDO;
import com.student.work.Class.model.IClassDTO;
import com.student.work.Class.service.IClassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName: IClassController
 * @Description: 班级控制层
 * @author: LeiYongQiang
 * @date: 2024-04-02
 */
@RestController
@RequestMapping(IClassController.PATH)
@Slf4j
public class IClassController extends CheckUserController {

    public final static String PATH = RestConstant.VERSION_V1 + "/Iclass";

    @Resource
    private IClassService iClassService;

    /**
     * @Title: getClassPage
     * @Description: 分页查询
     * @param iClassDTO
     * @return
     */
    @PostMapping(value = "/getClassPage")
    public Result getClassPage(@RequestBody IClassDTO iClassDTO) throws Exception {
        Page<IClassDTO> page = new Page<>(iClassDTO.getPageNo(),iClassDTO.getPageSize());
        Page<IClassDO> result = iClassService.selectPage(page, iClassDTO);
        return ResultGenerator.genOkResult(result);
    }


    /**
     * @Title: addClass
     * @Description: 添加班级
     * @param iClassDO
     * @return
     */
    @PostMapping(value = "/addClass")
    public Result addClass(@RequestBody @Validated  IClassDO iClassDO) throws Exception {
        Map<String,String> result = iClassService.addClass(iClassDO);
        if (Integer.parseInt(result.get("resultCount")) > 0) {
            return ResultGenerator.genOkResult("添加成功",1);
        }
        return ResultGenerator.genFailedResult(result.get("resultMsg"));
    }

    /**
     * @Title: update
     * @Description: 修改记录
     * @return
     */
    @PostMapping("/updateById")
    public Result update(@RequestBody  IClassDO iClassDO) {
        if (iClassDO.getId() == null) {
            return ResultGenerator.genFailedResult("主键id不能为空！");
        }
        Map<String,String> result = iClassService.update(iClassDO);
        if (Integer.parseInt(result.get("resultCount")) > 0) {
            return ResultGenerator.genOkResult("修改成功",1);
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
    public Result deleteBatchIds(@RequestBody IClassDTO iClassDTO) {
        if (iClassDTO.getIds().size() == 0) {
            return ResultGenerator.genFailedResult("id不能为空！");
        }
        int i = iClassService.deleteBatchIds(iClassDTO.getIds());
        if (i < 0) {
            return ResultGenerator.genFailedResult("删除失败");
        }
        return ResultGenerator.genOkResult("删除成功",i);
    }

}
