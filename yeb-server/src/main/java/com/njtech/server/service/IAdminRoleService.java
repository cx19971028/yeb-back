package com.njtech.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.njtech.server.pojo.AdminRole;
import com.njtech.server.vo.Result;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenxin
 * @since 2021-09-14
 */
public interface IAdminRoleService extends IService<AdminRole> {

    /**
     * 更新操作员角色
     * @return
     */
    Result updateAdminRole(Integer adminId, Integer[] rids);
}
