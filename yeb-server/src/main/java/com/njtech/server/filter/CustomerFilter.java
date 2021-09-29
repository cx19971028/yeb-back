package com.njtech.server.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.SecurityConfig;
import com.njtech.server.pojo.Menu;
import com.njtech.server.pojo.Role;
import com.njtech.server.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

/**
 * 权限控制
 * 根据请求的url分析出请求所需要的角色
 *
 * @author chenxin
 * @date 2021/9/18 20:00
 */
@Component
@Slf4j
public class CustomerFilter implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private IMenuService menuService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 获取请求的url
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        // 获取权限资源列表
        List<Menu> menus = menuService.getAllMenusByRole();
        for (Menu menu : menus) {
            // 判断请求url与权限资源列表是否匹配
            AntPathMatcher antPathMatcher = new AntPathMatcher();
            if(antPathMatcher.match(menu.getUrl(),requestUrl)){
                // 取资源所对应的角色列表
                String[] str = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
                log.info("访问：{},需要角色为：{}",requestUrl, str);
                return SecurityConfig.createList(str);
            }
        }
        // 没匹配到的url默认登录即可访问
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
