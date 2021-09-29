package com.njtech.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njtech.server.mapper.AdminRoleMapper;
import com.njtech.server.pojo.AdminRole;
import com.njtech.server.service.IAdminRoleService;
import com.njtech.server.service.IAdminService;
import com.njtech.server.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenxin
 * @since 2021-09-14
 */
@Service
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole> implements IAdminRoleService {

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    /**
     * 更新操作员角色
     * @return
     */
    @Transactional
    @Override
    public Result updateAdminRole(Integer adminId, Integer[] rids) {
        LambdaQueryWrapper<AdminRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AdminRole::getAdminId,adminId);
        adminRoleMapper.delete(queryWrapper);
        if(rids==null || rids.length==0){
            return Result.success("更新成功！");
        }

        Integer result = adminRoleMapper.addAdminRole(adminId, rids);
        if(rids.length == result){
            return Result.success("更新成功！");
        }
        return  Result.success("更新失败！");
    }
}
