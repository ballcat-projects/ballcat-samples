package com.ballcat.sample.auth.service;

import cn.hutool.core.collection.ListUtil;
import com.ballcat.sample.auth.model.AppUser;
import com.hccake.ballcat.common.security.util.PasswordUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hccake
 */
@Service
public class AppUserService {

	// @formatter:off
	private final List<AppUser> appUserStore = ListUtil.toList(
			new AppUser(1, "appUser1", PasswordUtils.encode("a123456"), "app用户1", "15800000000"),
			new AppUser(2, "appUser2", PasswordUtils.encode("b123456"), "app用户2", "15800000001"),
			new AppUser(3, "appUser3", PasswordUtils.encode("c123456"), "app用户3", "15800000002")
	);

	/**
	 * 查询用户，实际项目中应该去查询数据库
	 * @param username 用户名
	 * @return SysUser
	 */
	public AppUser getByUsername(String username) {
		return appUserStore.stream()
				.filter(appUser -> appUser.getUsername().equals(username))
				.findFirst()
				.orElse(null);
	}


	/**
	 * 查询用户，实际项目中应该去查询数据库
	 * @param phoneNumber 手机号
	 * @return SysUser
	 */
	public AppUser getByPhoneNumber(String phoneNumber) {
		return appUserStore.stream()
				.filter(appUser -> appUser.getPhoneNumber().equals(phoneNumber))
				.findFirst()
				.orElse(null);
	}
	// @formatter:on

}
