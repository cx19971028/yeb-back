package com.njtech.server.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njtech.server.mapper.MenuMapper;
import com.njtech.server.pojo.Admin;
import com.njtech.server.pojo.Menu;
import com.njtech.server.service.IMenuService;
import com.njtech.server.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.jws.Oneway;
import java.util.Collections;
import java.util.List;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenxin
 * @since 2021-09-12
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 根据用户id查询菜单列表
     * @return
     */
    @Override
    public Result getMenusByAdminId() {
        Admin admin = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 先从缓存中取数据
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        List<Menu> menuList = (List<Menu>)valueOperations.get("menu_" + admin.getId());
        if(CollectionUtils.isEmpty(menuList)){
            menuList = menuMapper.getMenusByAdminId(admin.getId());
            valueOperations.set("menu_" + admin.getId(),menuList);
        }
        return Result.success(menuList);
    }

    @Override
    public List<Menu> getAllMenusByRole() {
        return menuMapper.getAllMenusByRole();
    }

    /**
     * 查询所有菜单及其子菜单
     * @return
     */
    @Override
    public Result getAllMenus() {
        List<Menu> list = menuMapper.getAllMenus();
        return Result.success(list);
    }
}
