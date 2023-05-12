package org.ballcat.sample.authorizationserver.grant.mobile;

import com.hccake.ballcat.common.security.authentication.OAuth2UserAuthenticationToken;
import com.hccake.ballcat.common.security.userdetails.User;
import lombok.extern.slf4j.Slf4j;
import org.ballcat.sample.authorizationserver.grant.CustomOAuth2ParameterNames;
import org.ballcat.sample.authorizationserver.userdetails.SystemUser;
import org.ballcat.sample.authorizationserver.userdetails.SystemUserService;
import org.ballcat.sample.authorizationserver.userdetails.UserDetailsConverter;
import org.ballcat.springsecurity.oauth2.server.authorization.authentication.AbstractOAuth2ResourceOwnerAuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Map;

/**
 * @author Hccake
 */
@Slf4j
public class OAuth2MobileAuthenticationProvider
		extends AbstractOAuth2ResourceOwnerAuthenticationProvider<OAuth2MobileAuthenticationToken> {

	private final SystemUserService systemUserService;

	private final MobileVerificationCodeService mobileVerificationCodeService;

	/**
	 * Constructs an {@code OAuth2MobileAuthenticationProvider} using the provided
	 * parameters.
	 * @param systemUserService the systemUser service
	 * @param authorizationService the authorization service
	 * @param tokenGenerator the token generator
	 * @since 1.0.0
	 */
	public OAuth2MobileAuthenticationProvider(OAuth2AuthorizationService authorizationService,
			OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator, SystemUserService systemUserService,
			MobileVerificationCodeService mobileVerificationCodeService) {
		super(authorizationService, tokenGenerator);
		Assert.notNull(systemUserService, "systemUserService cannot be null");
		this.systemUserService = systemUserService;
		this.mobileVerificationCodeService = mobileVerificationCodeService;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		boolean supports = OAuth2MobileAuthenticationToken.class.isAssignableFrom(authentication);
		log.debug("supports authentication={}} returning {}", authentication, supports);
		return supports;
	}

	@Override
	protected Authentication getAuthenticatedAuthentication(OAuth2MobileAuthenticationToken authentication)
			throws AuthenticationException {

		Map<String, Object> additionalParameters = authentication.getAdditionalParameters();

		String phoneNumber = (String) additionalParameters.get(CustomOAuth2ParameterNames.PHONE_NUMBER);
		String verificationCode = (String) additionalParameters.get(CustomOAuth2ParameterNames.VERIFICATION_CODE);

		if (!mobileVerificationCodeService.checkVerificationCode(phoneNumber, verificationCode)) {
			throw new BadCredentialsException("手机验证码错误");
		}

		SystemUser systemUser = systemUserService.loadUserByPhoneNumber(phoneNumber);
		if (systemUser == null) {
			throw new BadCredentialsException("用户不存在");
		}

		User user = UserDetailsConverter.convert(systemUser);
		OAuth2UserAuthenticationToken oAuth2UserAuthenticationToken = new OAuth2UserAuthenticationToken(user,
				Collections.emptyList());
		log.debug("got oAuth2UserInfoAuthenticationToken={}", oAuth2UserAuthenticationToken);

		return oAuth2UserAuthenticationToken;
	}

}
