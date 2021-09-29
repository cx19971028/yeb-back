package com.njtech.server.service.impl;

import com.njtech.server.pojo.Admin;
import com.njtech.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author chenxin
 * @date 2021/9/15 9:21
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IAdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 获取登录用户信息
        Admin admin = adminService.getAdminByUsername(username);
        if(admin == null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        admin.setRoles(adminService.getRolesByAdminId(admin.getId()));
        return admin;
    }
}
