package com.njtech.server.service;

import com.njtech.server.pojo.MenuRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njtech.server.vo.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenxin
 * @since 2021-09-12
 */
public interface IMenuRoleService extends IService<MenuRole> {

    /**
     * 根据角色id查询菜单id
     * @param rid
     * @return
     */
    Result getMenuIdByRoleId(Integer rid);

    /**
     * 更新角色菜单
     * @return
     */
    Result updateRoleMenu(Integer rid, Integer[] mids);
}
