package org.ballcat.sample.resourceserver.controller;

import com.hccake.ballcat.common.security.util.SecurityUtils;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 公共的，可匿名访问的接口
 *
 * @author hccake
 */
@RestController
@RequestMapping("/public")
public class PublicController {

	@GetMapping("hello")
	public String hello() {
		Authentication authentication = SecurityUtils.getAuthentication();
		return "hello: " + authentication.getName();
	}

}
