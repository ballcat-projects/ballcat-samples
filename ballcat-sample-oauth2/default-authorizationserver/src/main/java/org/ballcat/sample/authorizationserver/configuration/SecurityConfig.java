package org.ballcat.sample.authorizationserver.configuration;

import org.ballcat.sample.authorizationserver.userdetails.InMemoryUserDetailsService;
import org.ballcat.sample.authorizationserver.userdetails.SystemUser;
import org.ballcat.sample.authorizationserver.userdetails.SystemUserService;
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
		SystemUser user = new SystemUser().setUserId(1)
			.setUsername("admin")
			.setPassword(passwordEncoder.encode("a123456"))
			.setNickname("超级管理员")
			.setPhoneNumber("15800008888");
		return new SystemUserService(user);
	}

	@Bean
	public UserDetailsService users(SystemUserService systemUserService) {
		return new InMemoryUserDetailsService(systemUserService);
	}

}
