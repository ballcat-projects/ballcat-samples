package org.ballcat.resourceserver;

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

	@GetMapping("/user")
	public User user() {
		return SecurityUtils.getUser();
	}

}
