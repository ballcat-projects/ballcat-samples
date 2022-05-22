package com.ballcat.sample.auth.grant.mobile;

import com.ballcat.sample.auth.model.AppUser;
import com.ballcat.sample.auth.service.AppUserService;
import com.hccake.ballcat.common.security.userdetails.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * 手机号登录
 *
 * @author hccake
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MobileAuthenticationProvider implements AuthenticationProvider {

	private final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

	private final AppUserService appUserService;

	@Override
	public Authentication authenticate(Authentication authentication) {
		MobileAuthenticationToken mobileAuthenticationToken = (MobileAuthenticationToken) authentication;

		// mobile 登录只做了 app 用户的支持,所以这里就不判断客户端类型了
		String phoneNumber = mobileAuthenticationToken.getPrincipal().toString();
		AppUser appUser = appUserService.getByPhoneNumber(phoneNumber);
		if (appUser == null) {
			log.debug("Authentication failed: no credentials provided");

			throw new BadCredentialsException(messages
					.getMessage("AbstractUserDetailsAuthenticationProvider.noopBindAccount", "Noop Bind Account"));
		}

		// 转换成 UserDetails
		UserDetails userDetails = User.builder().userId(appUser.getUserId()).username(appUser.getUsername())
				.password(appUser.getPassword()).nickname(appUser.getNickname()).status(1).attributes(new HashMap<>())
				.build();

		// 检查账号状态
		// detailsChecker.check(userDetails);

		MobileAuthenticationToken authenticationToken = new MobileAuthenticationToken(userDetails,
				userDetails.getAuthorities());
		authenticationToken.setDetails(mobileAuthenticationToken.getDetails());
		return authenticationToken;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return MobileAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
