package com.njtech.server.controller;

import com.njtech.server.pojo.Admin;
import com.njtech.server.service.IAdminService;
import com.njtech.server.vo.Result;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author chenxin
 * @date 2021/9/29 15:15
 */
@RestController
@RequestMapping("/admin")
public class AdminInfoController {

    @Autowired
    private IAdminService adminService;

    @ApiModelProperty(value = "更新当前用户信息")
    @PutMapping("/info")
    public Result updateAdminInfo(@RequestBody Admin admin, Authentication authentication){
        if(adminService.updateById(admin)){
            // 更新上下文用户对象
            UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(admin,null,authentication.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            return Result.success("更新成功！");
        }
        return Result.fail("更新失败！");
    }

    @ApiModelProperty(value = "修改密码")
    @PutMapping("/password")
    public Result updatePassword(@RequestBody Map<String,Object> info){
        String newPassword = (String) info.get("newPassword");
        String oldPassword = (String) info.get("oldPassword");
        Integer adminId = (Integer) info.get("adminId");
        return adminService.updatePassword(oldPassword, newPassword, adminId);
    }
}
