package org.ballcat.sample.resourceserver.controller;

import com.hccake.ballcat.common.security.util.SecurityUtils;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author hccake
 */
@RequestMapping("/")
@RestController
public class PrincipalController {

	@GetMapping("/principal")
	public Object principal() {
		Authentication authentication = SecurityUtils.getAuthentication();
		return Optional.ofNullable(authentication).map(Authentication::getPrincipal).orElse(null);
	}

}
