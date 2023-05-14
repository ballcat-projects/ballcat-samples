package org.ballcat.sample.customuser.authorizaitonserver.user;

import lombok.RequiredArgsConstructor;
import org.ballcat.sample.customuser.authorizaitonserver.mapper.AppUserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;

/**
 * @author hccake
 */
@Component
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

	private final AppUserMapper appUserMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser appUser = appUserMapper.selectByUsername(username);
		if (appUser == null) {
			throw new UsernameNotFoundException("用户不存在");
		}

		return AppUserDetails.builder()
			.appUserInfo(AppUserConverter.INSTANCE.toAppUserInfo(appUser))
			.password(appUser.getPassword())
			.authorities(new HashSet<>())
			.build();
	}

}
