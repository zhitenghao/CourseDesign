package com.swjt.community.security;

import com.swjt.community.entity.User;
import com.swjt.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
    UserService UserService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User User = UserService.getUserByAccount(username);
		if (User == null) {
			throw new UsernameNotFoundException("用户名或密码不正确");
		}
		return new AccountUser(User.getId(), User.getUserAccount(), User.getUserPwd(), getUserAuthority(User.getId()));
	}

	/**
	 * 获取用户权限信息（角色、菜单权限）
	 * @param userId
	 * @return
	 */
	public List<GrantedAuthority> getUserAuthority(String userId){

		// 角色(ROLE_admin)、菜单操作权限 sys:user:list（sys：系统资源，user：用户管理，list：列表）
		String authority = UserService.getUserAuthorityInfo(userId);  // ROLE_admin,ROLE_normal,sys:user:list,....

		//自动转成ROLE的形式
		return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
	}
}
