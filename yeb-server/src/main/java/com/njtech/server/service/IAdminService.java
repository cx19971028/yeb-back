package com.njtech.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.njtech.server.pojo.Admin;
import com.njtech.server.pojo.Role;
import com.njtech.server.vo.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenxin
 * @since 2021-09-14
 */
public interface IAdminService extends IService<Admin> {

    /**
     * 登录认证
     * @param username
     * @param password
     * @return
     */
    Result login(String username, String password, String code, HttpServletRequest request);

    /**
     * 根据username查询用户
     * @param username
     * @return
     */
    Admin getAdminByUsername(String username);

    /**
     * 根据用户id查询用户角色
     * @param adminId
     * @return
     */
    List<Role> getRolesByAdminId(Integer adminId);

    /**
     * 获取所有操作员
     * @param keywords
     * @return
     */
    Result getAllAdmin(String keywords);


    /**
     * 修改密码
     * @param oldPassword
     * @param newPassword
     * @param adminId
     * @return
     */
    Result updatePassword(String oldPassword, String newPassword, Integer adminId);
}
