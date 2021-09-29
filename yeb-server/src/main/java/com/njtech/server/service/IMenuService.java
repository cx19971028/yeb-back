package com.njtech.server.service;

import com.njtech.server.pojo.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njtech.server.vo.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenxin
 * @since 2021-09-12
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 根据用户id查询菜单列表
     * @return
     */
    Result getMenusByAdminId();

    /**
     * 根据角色查询所有权限资源
     */
    List<Menu> getAllMenusByRole();

    /**
     * 查询所有菜单及其子菜单
     * @return
     */
    Result getAllMenus();
}
