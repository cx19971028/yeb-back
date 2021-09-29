package com.njtech.server.controller;


import com.njtech.server.pojo.Joblevel;
import com.njtech.server.service.IJoblevelService;
import com.njtech.server.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenxin
 * @since 2021-09-12
 */
@RestController
@RequestMapping("/system/basic/joblevel")
public class JoblevelController {

    @Autowired
    private IJoblevelService joblevelService;

    @ApiOperation(value = "查询所有职称")
    @GetMapping("/")
    public Result getAllJobLevel(){
        List<Joblevel> joblevelList = joblevelService.list();
        return Result.success(joblevelList);
    }

    @ApiOperation(value = "增加职称")
    @PostMapping("/")
    public Result addJobLevel(@RequestBody Joblevel joblevel){
        if(joblevelService.save(joblevel)){
            return Result.success("添加成功");
        }
        return Result.fail("添加失败");
    }

    @ApiOperation(value = "更新职称")
    @PutMapping("/")
    public Result updateJoblevel(@RequestBody Joblevel joblevel){
        if(joblevelService.updateById(joblevel)){
            return Result.success("更新成功");
        }
        return Result.fail("更新失败");
    }

    @ApiOperation(value = "删除职称")
    @DeleteMapping("/{id}")
    public Result deleteJoblevel(@PathVariable Integer id){
        if(joblevelService.removeById(id)){
            return Result.success("删除成功");
        }
        return Result.fail("删除失败");
    }

    @ApiOperation(value = "批量喊出职称")
    @DeleteMapping("/")
    public Result deleteByIds(Integer[] ids){
        if(joblevelService.removeByIds(Arrays.asList(ids))){
            return Result.success("批量删除成功");
        }
        return Result.fail("批量删除失败");
    }
}
