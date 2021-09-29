package com.njtech.server.utils;

import com.njtech.server.pojo.Admin;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author chenxin
 * @date 2021/9/22 11:00
 */
public class SecurityUtil {

    public static Admin getCurrentAdmin(){
        return (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
