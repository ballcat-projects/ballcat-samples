
package org.ballcat.sample.authorizationserver.userdetails;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;

@RequiredArgsConstructor
public class DemoUserDetailsService implements UserDetailsService {

	private final SystemUserService systemUserService;

	private final AppUserService appUserService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// clientId 为 app 时，走 app 用户登陆
		if (authentication instanceof OAuth2ClientAuthenticationToken) {
			OAuth2ClientAuthenticationToken oAuth2ClientAuthenticationToken = (OAuth2ClientAuthenticationToken) authentication;
			String clientId = oAuth2ClientAuthenticationToken.getRegisteredClient().getClientId();
			if ("app".equals(clientId)) {
				AppUser appUser = appUserService.loadUserByUsername(username);
				if (appUser == null) {
					throw new UsernameNotFoundException("用户" + username + "不存在");
				}
				return UserDetailsConverter.convert(appUser);
			}
		}

		// 默认系统用户登陆
		SystemUser systemUser = systemUserService.loadUserByUsername(username);
		if (systemUser == null) {
			throw new UsernameNotFoundException("用户" + username + "不存在");
		}
		return UserDetailsConverter.convert(systemUser);
	}

	public UserDetails loadUserByPhoneNumber(String phoneNumber) throws UsernameNotFoundException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// clientId 为 app 时，走 app 用户登陆
		if (authentication instanceof OAuth2ClientAuthenticationToken) {
			OAuth2ClientAuthenticationToken oAuth2ClientAuthenticationToken = (OAuth2ClientAuthenticationToken) authentication;
			String clientId = oAuth2ClientAuthenticationToken.getRegisteredClient().getClientId();
			if ("app".equals(clientId)) {
				AppUser appUser = appUserService.loadUserByPhoneNumber(phoneNumber);
				if (appUser == null) {
					throw new UsernameNotFoundException("用户" + phoneNumber + "不存在");
				}
				return UserDetailsConverter.convert(appUser);
			}
		}

		// 默认系统用户登陆
		SystemUser systemUser = systemUserService.loadUserByPhoneNumber(phoneNumber);
		if (systemUser == null) {
			throw new UsernameNotFoundException("用户" + phoneNumber + "不存在");
		}
		return UserDetailsConverter.convert(systemUser);
	}

}
