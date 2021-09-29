package com.njtech.server.controller;


import com.njtech.server.pojo.Admin;
import com.njtech.server.pojo.Role;
import com.njtech.server.service.IAdminRoleService;
import com.njtech.server.service.IAdminService;
import com.njtech.server.service.IRoleService;
import com.njtech.server.vo.Result;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenxin
 * @since 2021-09-14
 */
@RestController
@RequestMapping("/system/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IAdminRoleService adminRoleService;

    @ApiModelProperty(value = "获取所有操作员")
    @GetMapping("/")
    public Result getAllAdmin(String keywords){
        return adminService.getAllAdmin(keywords);
    }

    @ApiModelProperty(value = "更新操作员")
    @PutMapping("/")
    public Result updateAdmin(@RequestBody Admin admin){
        if(adminService.updateById(admin)){
            return Result.success("更新成功！");
        }
        return Result.fail("更新失败！");
    }

    @ApiModelProperty(value = "删除操作员")
    @DeleteMapping("/{id}")
    public Result deleteAdmin(@PathVariable("id")Integer id){
        if(adminService.removeById(id)){
            return Result.success("删除成功！");
        }
        return Result.fail("删除失败！");
    }

    @ApiModelProperty(value = "获取所有角色")
    @GetMapping("/roles")
    public Result getAllRole(){
        List<Role> roleList = roleService.list();
        return Result.success(roleList);
    }

    @ApiModelProperty(value = "更新操作员角色")
    @PutMapping("/role")
    public Result updateAdminRole(Integer adminId, Integer[] rids){
        return adminRoleService.updateAdminRole(adminId, rids);
    }

}
