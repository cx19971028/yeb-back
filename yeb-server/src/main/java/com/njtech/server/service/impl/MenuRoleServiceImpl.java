package com.njtech.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.njtech.server.pojo.MenuRole;
import com.njtech.server.mapper.MenuRoleMapper;
import com.njtech.server.service.IMenuRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njtech.server.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenxin
 * @since 2021-09-12
 */
@Service
@Slf4j
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements IMenuRoleService {

    @Autowired
    private MenuRoleMapper menuRoleMapper;

    @Override
    public Result getMenuIdByRoleId(Integer rid) {
        LambdaQueryWrapper<MenuRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MenuRole::getRid, rid);
        List<MenuRole> menuRoles = menuRoleMapper.selectList(queryWrapper);
        List<Integer> midList = menuRoles.stream().map(MenuRole::getMid).collect(Collectors.toList());
        log.info("根据用户id：{}，查询到菜单id为：{}",rid,midList);
        return Result.success(midList);
    }

    /**
     * 更新角色菜单
     * @return
     */
    @Override
    @Transactional
    public Result updateRoleMenu(Integer rid, Integer[] mids) {
        LambdaQueryWrapper<MenuRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MenuRole::getRid,rid);
        menuRoleMapper.delete(queryWrapper);
        if(mids == null || mids.length == 0){
            return Result.success("更新成功");
        }

        int result = menuRoleMapper.updateRoleMenu(rid,mids);
        if(result==mids.length){
            return Result.success("更新成功");
        }
        return Result.fail("更新失败");
    }
}
