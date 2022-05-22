package com.ballcat.sample.auth.component;

import com.ballcat.sample.auth.model.AppUser;
import com.ballcat.sample.auth.model.SysUser;
import com.ballcat.sample.auth.service.AppUserService;
import com.ballcat.sample.auth.service.SysUserService;
import com.hccake.ballcat.common.security.userdetails.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author hccake
 */
@Component
@RequiredArgsConstructor
public class SysUserDetailsService implements UserDetailsService {

	private final SysUserService sysUserService;

	private final AppUserService appUserService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		org.springframework.security.core.userdetails.User client = (org.springframework.security.core.userdetails.User) authentication
				.getPrincipal();

		// 这里简单的使用客户端的用户名进行判断,实际可以根据客户端的其他信息,比如 scope 进行切换登录表
		if ("app".equals(client.getUsername())) {
			AppUser appUser = appUserService.getByUsername(username);
			if (appUser == null) {
				throw new UsernameNotFoundException("username error!");
			}
			// 转换成 UserDetails, 这里可以返回不同的 UserDetails 实现
			return User.builder().userId(appUser.getUserId()).username(appUser.getUsername())
					.password(appUser.getPassword()).nickname(appUser.getNickname()).status(1)
					.attributes(new HashMap<>()).build();
		}

		SysUser sysUser = sysUserService.getByUsername(username);
		if (sysUser == null) {
			throw new UsernameNotFoundException("username error!");
		}
		// 转换成 UserDetails, 这里可以返回不同的 UserDetails 实现
		return User.builder().userId(sysUser.getUserId()).username(sysUser.getUsername())
				.password(sysUser.getPassword()).nickname(sysUser.getNickname()).status(1).attributes(new HashMap<>())
				.build();
	}

}
