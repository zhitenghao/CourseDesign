package com.swjt.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swjt.community.entity.SysMenu;
import com.swjt.community.entity.SysRole;
import com.swjt.community.entity.User;
import com.swjt.community.mapper.UserMapper;
import com.swjt.community.service.SysMenuService;
import com.swjt.community.service.SysRoleService;
import com.swjt.community.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swjt.community.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DaiRui
 * @since 2021-10-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    SysMenuService sysMenuService;

    @Override
    public User getUserByAccount(String userAccount) {
        return getOne(new QueryWrapper<User>().eq("user_account", userAccount));
    }

    @Override
    public String getUserAuthorityInfo(String userId) {
        User User = userMapper.selectById(userId);

        //  ROLE_admin,ROLE_normal,sys:user:list,....
        String authority = "";

        if (redisUtil.hasKey("GrantedAuthority:" + User.getUserAccount())) {
            authority = (String) redisUtil.get("GrantedAuthority:" + User.getUserAccount());

        } else {
            // 获取角色编码
            List<SysRole> roles = sysRoleService.list(new QueryWrapper<SysRole>()
                    .inSql("id", "select role_id from sys_user_role where user_id = " + userId));

            if (roles.size() > 0) {
                String roleCodes = roles.stream().map(r -> "ROLE_" + r.getCode()).collect(Collectors.joining(","));
                //把逗号加到末尾
                authority = roleCodes.concat(",");
            }

            // 获取菜单操作编码
            List<String> menuIds = userMapper.getNavMenuIds(userId);
            if (menuIds.size() > 0) {

                List<SysMenu> menus = sysMenuService.listByIds(menuIds);
                String menuPerms = menus.stream().map(m -> m.getPerms()).collect(Collectors.joining(","));

                authority = authority.concat(menuPerms);
            }

            redisUtil.set("GrantedAuthority:" + User.getUserAccount(), authority, 60 * 60);
        }

        return authority;
    }

    @Override
    public void clearUserAuthorityInfo(String userAccount) {
        redisUtil.del("GrantedAuthority:" + userAccount);
    }

    @Override
    public void clearUserAuthorityInfoByRoleId(String roleId) {

        List<User> sysUsers = this.list(new QueryWrapper<User>()
                .inSql("id", "select user_id from sys_user_role where role_id = " + roleId));

        sysUsers.forEach(u -> {
            this.clearUserAuthorityInfo(u.getUserAccount());
        });

    }

    @Override
    public void clearUserAuthorityInfoByMenuId(String menuId) {
        List<User> sysUsers = userMapper.listByMenuId(menuId);

        sysUsers.forEach(u -> {
            this.clearUserAuthorityInfo(u.getUserAccount());
        });
    }

    @Override
    public List<User> userList() {
        return list(new QueryWrapper<User>().eq("user_status","1"));
    }
}
