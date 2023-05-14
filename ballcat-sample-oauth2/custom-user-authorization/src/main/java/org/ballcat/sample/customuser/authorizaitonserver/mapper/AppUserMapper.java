package org.ballcat.sample.customuser.authorizaitonserver.mapper;

import org.ballcat.sample.customuser.authorizaitonserver.user.AppUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hccake
 */
@Component
public class AppUserMapper {

	private final Map<String, AppUser> appUserStore = new HashMap<>();

	public AppUserMapper(PasswordEncoder passwordEncoder) {
		// 初始化一个用户来测试
		AppUser appUser1 = new AppUser().setUserId(1L)
			.setUsername("appUser1")
			.setPassword(passwordEncoder.encode("a123456"))
			.setNickname("用户1");
		appUserStore.put(appUser1.getUsername(), appUser1);
	}

	public AppUser selectByUsername(String username) {
		return appUserStore.get(username);
	}

}
