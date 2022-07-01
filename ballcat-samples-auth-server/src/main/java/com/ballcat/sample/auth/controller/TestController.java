package com.ballcat.sample.auth.controller;

import com.hccake.ballcat.common.security.util.SecurityUtils;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hccake
 */
@RestController
@RequestMapping("/test")
public class TestController {

	@GetMapping
	public String test() {
		Authentication authentication = SecurityUtils.getAuthentication();
		return "test: " + authentication.getName();
	}

}
