package com.njtech.server.controller;

import com.njtech.server.pojo.Admin;
import com.njtech.server.service.IAdminService;
import com.njtech.server.vo.Result;
import com.njtech.server.vo.param.AdminLoginParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @author chenxin
 * @date 2021/9/14 19:04
 */
@Api(tags = "LoginController")
@RestController
public class LoginController {

    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "登录之后返回token")
    @PostMapping("/login")
    public Result login(@RequestBody AdminLoginParam loginParam, HttpServletRequest request) {
        return adminService.login(loginParam.getUsername(),loginParam.getPassword(),loginParam.getCode(),request);
    }

    @ApiOperation(value = "获取用户信息")
    @GetMapping("/admin/info")
    public Result getAdminInfo(Principal principal){
        if(principal==null){
            return null;
        }
        String username = principal.getName();
        Admin admin = adminService.getAdminByUsername(username);
        if(admin==null){
            return null;
        }
        admin.setPassword(null);
        admin.setRoles(admin.getRoles());
        return Result.success(admin);
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public Result logout(){
        SecurityContextHolder.clearContext();
        return Result.success("注销成功");
    }
}
