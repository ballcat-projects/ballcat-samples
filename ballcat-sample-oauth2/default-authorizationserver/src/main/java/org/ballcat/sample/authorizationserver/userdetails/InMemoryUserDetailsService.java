
package org.ballcat.sample.authorizationserver.userdetails;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class InMemoryUserDetailsService implements UserDetailsService {

	private final SystemUserService systemUserService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SystemUser systemUser = systemUserService.loadUserByUsername(username);
		if (systemUser == null) {
			throw new UsernameNotFoundException("用户" + username + "不存在");
		}
		return UserDetailsConverter.convert(systemUser);
	}

}
