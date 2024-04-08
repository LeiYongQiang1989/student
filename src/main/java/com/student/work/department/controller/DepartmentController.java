package com.student.work.department.controller;

import com.student.core.RestConstant;
import com.student.core.Result;
import com.student.core.ResultGenerator;
import com.student.shiro.controller.CheckUserController;
import com.student.work.Class.model.IClassDO;
import com.student.work.department.model.DepartmentDO;
import com.student.work.department.service.DepartmentService;
import com.student.work.user.model.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: DepartmentController
 * @Description: 院系控制层
 * @author: LeiYongQiang
 * @date: 2024-04-02
 */
@RestController
@RequestMapping(DepartmentController.PATH)
@Slf4j
public class DepartmentController extends CheckUserController {

    public final static String PATH = RestConstant.VERSION_V1 + "/department";

    @Resource
    private DepartmentService departmentService;

    /**
     * @Title: getList
     * @Description: 查询所有的
     * @return
     */
    @GetMapping(value = "/getList")
    public Result getList() throws Exception {
       List<DepartmentDO> result = departmentService.getList();
        return ResultGenerator.genOkResult(result);
    }


    /**
     * @Title: add
     * @Description: 添加
     * @param departmentDO
     * @returnd
     */
    @PostMapping(value = "/add")
    public Result add(@RequestBody @Validated  DepartmentDO departmentDO) throws Exception {
        // 只有角色为1的系统管理员才有添加、修改、删除权限
//        UserDO userDO = getUser();
//        if (!"1".equals(userDO.getRoleCode()) ) {
//            return ResultGenerator.genFailedResult("该用户无权限");
//        }
        Map<String,String> result = departmentService.add(departmentDO);
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
    public Result update(@RequestBody  DepartmentDO departmentDO) {
//        // 只有角色为1的系统管理员才有添加、修改、删除权限
//        UserDO userDO = getUser();
//        if (!"1".equals(userDO.getRoleCode()) ) {
//            return ResultGenerator.genFailedResult("该用户无权限");
//        }
        if (departmentDO.getId() == null) {
            return ResultGenerator.genFailedResult("主键id不能为空！");
        }
        Map<String,String> result = departmentService.update(departmentDO);
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
    public Result deleteById(@RequestBody DepartmentDO departmentDO) {
//        // 只有角色为1的系统管理员才有添加、修改、删除权限
//        UserDO userDO = getUser();
//        if (!"1".equals(userDO.getRoleCode()) ) {
//            return ResultGenerator.genFailedResult("该用户无权限");
//        }
        if (departmentDO.getId() == null) {
            return ResultGenerator.genFailedResult("id不能为空！");
        }
        int i = departmentService.deleteById(departmentDO.getId());
        if (i < 0) {
            return ResultGenerator.genFailedResult("删除失败");
        }
        return ResultGenerator.genOkResult("删除成功",i);
    }

}
