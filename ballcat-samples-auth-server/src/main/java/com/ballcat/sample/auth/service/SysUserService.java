package com.ballcat.sample.auth.service;

import cn.hutool.core.collection.ListUtil;
import com.ballcat.sample.auth.model.SysUser;
import com.hccake.ballcat.common.security.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hccake
 */
@Service
@RequiredArgsConstructor
public class SysUserService {

	// @formatter:off
	private final List<SysUser> sysUserStore = ListUtil.toList(
			new SysUser(1, "admin", PasswordUtils.encode("a123456"), "超级管理员"),
			new SysUser(2, "test1", PasswordUtils.encode("b123456"), "测试用户1"),
			new SysUser(3, "test2", PasswordUtils.encode("c123456"), "测试用户2")
	);

	/**
	 * 查询用户，实际项目中应该去查询数据库
	 * @param username 用户名
	 * @return SysUser
	 */
	public SysUser getByUsername(String username) {
		return sysUserStore.stream()
				.filter(sysUser -> sysUser.getUsername().equals(username))
				.findFirst()
				.orElse(null);
	}
	// @formatter:on

}
