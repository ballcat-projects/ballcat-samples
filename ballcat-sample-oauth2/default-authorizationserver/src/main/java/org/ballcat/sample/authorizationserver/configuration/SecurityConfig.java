package org.ballcat.sample.authorizationserver.configuration;

import org.ballcat.sample.authorizationserver.userdetails.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author hccake
 */
@Configuration(proxyBeanMethods = false)
public class SecurityConfig {

	@Bean
	public SystemUserService systemUserService(PasswordEncoder passwordEncoder) {
		SystemUser user = new SystemUser().setUserId(1L)
			.setUsername("admin")
			.setPassword(passwordEncoder.encode("a123456"))
			.setNickname("超级管理员")
			.setPhoneNumber("15800008888")
			.setType(1);
		return new SystemUserService(user);
	}

	@Bean
	public AppUserService appUserService(PasswordEncoder passwordEncoder) {
		AppUser user = new AppUser().setUserId(1L)
			.setUsername("user1")
			.setPassword(passwordEncoder.encode("a123456"))
			.setNickname("C端用户1")
			.setPhoneNumber("15800009999")
			.setType(2);
		return new AppUserService(user);
	}

	@Bean
	public UserDetailsService users(SystemUserService systemUserService, AppUserService appUserService) {
		return new DemoUserDetailsService(systemUserService, appUserService);
	}

}
