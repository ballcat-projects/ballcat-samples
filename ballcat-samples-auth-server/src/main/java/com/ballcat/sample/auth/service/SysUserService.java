package com.ballcat.sample.auth.service;

import cn.hutool.core.collection.ListUtil;
import com.ballcat.sample.auth.model.SysUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hccake
 */
@Service
public class SysUserService {

	private final List<SysUser> sysUserStore;

	// @formatter:off
	public SysUserService(PasswordEncoder passwordEncoder) {
		sysUserStore = ListUtil.toList(
				new SysUser(1, "admin", passwordEncoder.encode("a123456"), "超级管理员"),
				new SysUser(2, "test1", passwordEncoder.encode("b123456"), "测试用户1"),
				new SysUser(3, "test2", passwordEncoder.encode("c123456"), "测试用户2")
		);
	}


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
