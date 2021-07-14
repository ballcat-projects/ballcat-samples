package com.hccake.ballcat.api.modules.api.controller;

import com.hccake.ballcat.common.security.userdetails.User;
import com.hccake.ballcat.common.security.util.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试 ResourceServer 是否起效
 *
 * @author hccake
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@GetMapping
	public User user() {
		return SecurityUtils.getUser();
	}

}
