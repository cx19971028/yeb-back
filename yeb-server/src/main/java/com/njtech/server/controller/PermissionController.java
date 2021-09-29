package com.njtech.server.controller;

import com.njtech.server.pojo.Role;
import com.njtech.server.service.IMenuRoleService;
import com.njtech.server.service.IMenuService;
import com.njtech.server.service.IRoleService;
import com.njtech.server.vo.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * 角色
 *
 * @author chenxin
 * @date 2021/9/20 10:32
 */
@RestController
@RequestMapping("/system/basic")
public class PermissionController {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IMenuRoleService menuRoleService;

    @ApiOperation(value = "查询所有角色")
    @GetMapping("/")
    public Result getAllRoles(){
        List<Role> roleList = roleService.list();
        return Result.success(roleList);
    }

    @ApiOperation(value = "添加角色")
    @PostMapping("/")
    public Result addRole(@RequestBody Role role){
        if(!role.getName().startsWith("ROLE_")){
            role.setName("ROLE_"+role.getName());
        }
        if(roleService.save(role)){
            return Result.success("添加成功！");
        }
        return Result.success("添加失败！");
    }

    @ApiOperation(value = "修改角色")
    @PutMapping("/")
    public Result update(@RequestBody Role role){
        if(!role.getName().startsWith("ROLE_")){
            role.setName("ROLE_"+role.getName());
        }
        if(roleService.updateById(role)){
            return Result.success("修改成功！");
        }
        return Result.success("修改失败！");
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/role/{id}")
    public Result deleteById(Integer id){
        if(roleService.removeById(id)){
            return Result.success("删除成功！");
        }
        return Result.success("删除失败!");
    }

    @ApiOperation(value = "查询所有菜单及其子菜单")
    @GetMapping("/menus")
    public Result getAllMenus(){
        return menuService.getAllMenus();
    }

    @ApiOperation(value = "根据角色id查询菜单id")
    @GetMapping("/mid/{rid}")
    public Result getMenuIdByRoleId(@PathVariable("rid") Integer rid){
        return menuRoleService.getMenuIdByRoleId(rid);
    }

    @ApiOperation(value = "更新角色菜单")
    @PutMapping("/permiss")
    public Result updateRoleMenu(Integer rid, Integer[] mids){
        return menuRoleService.updateRoleMenu(rid, mids);
    }

}
