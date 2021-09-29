package com.njtech.server.controller;


import com.njtech.server.pojo.Position;
import com.njtech.server.service.IPositionService;
import com.njtech.server.vo.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.catalina.LifecycleState;
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
@RequestMapping("/system/basic/pos")
public class PositionController {

    @Autowired
    private IPositionService positionService;

    @ApiOperation(value = "获取所有职位信息")
    @GetMapping("/")
    public Result getAllPosition(){
        List<Position> positionList = positionService.list();
        return Result.success(positionList);
    }

    @ApiOperation(value = "添加职位信息")
    @PostMapping("/")
    public Result addPosition(@RequestBody Position position){
        if(positionService.save(position)){
            return Result.success("添加职位成功");
        }
        return Result.fail("添加职位失败");
    }

    @ApiOperation(value = "更新职位信息")
    @PutMapping("/")
    public Result updatePosition(@RequestBody Position position){
        if(positionService.updateById(position)){
            return Result.success("更新职位成功");
        }
        return Result.fail("更新职位失败");
    }

    @ApiOperation(value = "删除职位信息")
    @DeleteMapping("/{id}")
    public Result deletePosition(@PathVariable Integer id){
        if(positionService.removeById(id)){
            return Result.success("删除成功");
        }
        return Result.fail("删除失败");
    }

    @ApiOperation(value = "批量删除职位信息")
    @DeleteMapping("/")
    public Result deletePositionByIds(Integer[] ids){
        if(positionService.removeByIds(Arrays.asList(ids))){
            return Result.success("批量删除成功");
        }
        return Result.fail("批量删除失败");
    }
}
