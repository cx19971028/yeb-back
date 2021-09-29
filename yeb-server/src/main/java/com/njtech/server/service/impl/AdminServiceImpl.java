package com.njtech.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njtech.server.utils.JwtTokenUtil;
import com.njtech.server.mapper.AdminMapper;
import com.njtech.server.mapper.RoleMapper;
import com.njtech.server.pojo.Admin;
import com.njtech.server.pojo.Role;
import com.njtech.server.service.IAdminService;
import com.njtech.server.utils.SecurityUtil;
import com.njtech.server.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenxin
 * @since 2021-09-14
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 登录认证，返回token
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public Result login(String username, String password, String code, HttpServletRequest request) {
        // 校验验证码
        String captcha = (String) request.getSession().getAttribute("captcha");
        if(StringUtils.isEmpty(code) || !captcha.equalsIgnoreCase(code)){
            return Result.fail("验证码输入错误，请重新输入");
        }
        // 根据username查询用户信息
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(userDetails == null || !passwordEncoder.matches(password,userDetails.getPassword())){
            return Result.fail("用户名或密码不正确!");
        }
        if(!userDetails.isEnabled()){
            return Result.fail("账号被禁用，请联系管理员");
        }
        // 账户密码认证信息（已认证）
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails,password, userDetails.getAuthorities());
        // 将账户密码认证信息放入全局作用域,方便同线程的其他方法获取认证信息
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 创建token，并返回
        String token = jwtTokenUtil.createToken(userDetails);
        Map<String,Object> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);
        return Result.success("登录成功",tokenMap);
    }

    @Override
    public Admin getAdminByUsername(String username) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername, username);
        queryWrapper.eq(Admin::isEnabled,true);
        return adminMapper.selectOne(queryWrapper);
    }

    /**
     * 根据用户id查询用户角色
     * @param adminId
     * @return
     */
    @Override
    public List<Role> getRolesByAdminId(Integer adminId) {
        List<Role> roles = roleMapper.getRolesByAdminId(adminId);
        return roles;
    }

    @Override
    public Result getAllAdmin(String keywords) {
        Integer id = SecurityUtil.getCurrentAdmin().getId();
        List<Admin> adminList = adminMapper.getAllAdmin(id,keywords);
        return Result.success(adminList);
    }

    /**
     * 修改密码
     * @param oldPassword
     * @param newPassword
     * @param adminId
     * @return
     */
    @Override
    public Result updatePassword(String oldPassword, String newPassword, Integer adminId) {
        Admin admin = adminMapper.selectById(adminId);
        // 判断旧密码是否正确
        if (passwordEncoder.matches(admin.getPassword(), oldPassword)) {
            admin.setPassword(passwordEncoder.encode(newPassword));
            int reuslt = adminMapper.updateById(admin);
            if (reuslt == 1) {
                return Result.success("更新成功!");
            }
        }
        return Result.fail("更新失败！");
    }
}
