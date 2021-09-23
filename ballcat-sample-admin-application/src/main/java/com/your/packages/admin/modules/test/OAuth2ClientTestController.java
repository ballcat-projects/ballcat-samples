package com.your.packages.admin.modules.test;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hccake
 */
@RequestMapping("/client")
@RestController
@RequiredArgsConstructor
public class OAuth2ClientTestController {

	@GetMapping("hello")
	public String hello() {
		return "hello, oauth2 client";
	}

	@GetMapping("user")
	public String user() {
		return SecurityContextHolder.getContext().toString();
	}

}
