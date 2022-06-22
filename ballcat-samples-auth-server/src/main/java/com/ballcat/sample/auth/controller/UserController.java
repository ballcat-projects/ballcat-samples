package com.ballcat.sample.auth.controller;

import com.ballcat.sample.auth.model.UserInfo;
import com.hccake.ballcat.common.security.userdetails.User;
import com.hccake.ballcat.common.security.util.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hccake
 */
@RequestMapping("/")
@RestController
public class UserController {

	@GetMapping("user-info")
	public UserInfo userInfo() {
		User user = SecurityUtils.getUser();
		UserInfo sysUserInfo = new UserInfo();
		sysUserInfo.setUserId(user.getUserId());
		sysUserInfo.setUsername(user.getUsername());
		sysUserInfo.setNickname(user.getNickname());
		return sysUserInfo;
	}

}
