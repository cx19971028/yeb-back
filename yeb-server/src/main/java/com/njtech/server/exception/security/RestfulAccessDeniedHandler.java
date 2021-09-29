package com.njtech.server.exception.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.njtech.server.vo.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 当访问资源接口没有权限时候，统一处理
 * 用来解决认证过的用户访问无权限资源时的异常
 * @author chenxin
 * @date 2021/9/15 16:53
 */
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter out = httpServletResponse.getWriter();
        out.write(new ObjectMapper().writeValueAsString(Result.fail("权限不足，请联系管理员")));
        out.flush();
        out.close();
    }
}
