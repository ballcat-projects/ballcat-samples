package org.ballcat.authorizationserver.config;

import com.hccake.ballcat.common.security.constant.UserAttributeNameConstants;
import com.hccake.ballcat.common.security.userdetails.User;
import org.ballcat.authorizationserver.userdetails.InMemoryUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author hccake
 */
@Configuration(proxyBeanMethods = false)
public class SecurityConfig {

	@Bean
	public UserDetailsService users(PasswordEncoder passwordEncoder) {
		List<String> roleCodes = Collections.singletonList("ROLE_admin");
		List<String> permissions = Collections.singletonList("user:read");

		List<SimpleGrantedAuthority> authorities = Stream.of(roleCodes, permissions)
			.flatMap(Collection::stream)
			.map(SimpleGrantedAuthority::new)
			.collect(Collectors.toList());

		// 默认将角色和权限放入属性中
		HashMap<String, Object> attributes = new HashMap<>(8);
		attributes.put(UserAttributeNameConstants.ROLE_CODES, roleCodes);
		attributes.put(UserAttributeNameConstants.PERMISSIONS, permissions);

		User user = User.builder()
			.userId(1)
			.type(1)
			.username("user1")
			.password(passwordEncoder.encode("password"))
			.nickname("测试用户1")
			.status(1)
			.avatar("http://s.com")
			.organizationId(1)
			.authorities(authorities)
			.attributes(attributes)
			.build();

		return new InMemoryUserDetailsService(user);
	}

}
