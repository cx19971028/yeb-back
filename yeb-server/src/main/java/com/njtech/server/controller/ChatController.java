package com.njtech.server.controller;

import com.njtech.server.service.IAdminService;
import com.njtech.server.vo.Result;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenxin
 * @date 2021/9/29 11:49
 */
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private IAdminService adminService;

    @ApiModelProperty(value = "获取所有操作员")
    @GetMapping("/admin")
    public Result getAllAdmins(String keywords){
        return adminService.getAllAdmin(keywords);
    }
}
