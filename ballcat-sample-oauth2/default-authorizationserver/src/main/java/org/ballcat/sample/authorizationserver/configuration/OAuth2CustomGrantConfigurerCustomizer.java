package org.ballcat.sample.authorizationserver.configuration;

import lombok.RequiredArgsConstructor;
import org.ballcat.sample.authorizationserver.grant.mobile.MobileVerificationCodeService;
import org.ballcat.sample.authorizationserver.grant.mobile.OAuth2MobileAuthenticationConverter;
import org.ballcat.sample.authorizationserver.grant.mobile.OAuth2MobileAuthenticationProvider;
import org.ballcat.sample.authorizationserver.userdetails.DemoUserDetailsService;
import org.ballcat.sample.authorizationserver.userdetails.SystemUserService;
import org.ballcat.springsecurity.oauth2.server.authorization.config.configurer.OAuth2ConfigurerUtils;
import org.ballcat.springsecurity.oauth2.server.authorization.config.customizer.OAuth2AuthorizationServerConfigurerCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.stereotype.Component;

/**
 * 扩展授权登陆支持
 *
 * @author hccake
 */
@Component
@RequiredArgsConstructor
public class OAuth2CustomGrantConfigurerCustomizer implements OAuth2AuthorizationServerConfigurerCustomizer {

	private final ApplicationContext context;

	@Override
	public void customize(OAuth2AuthorizationServerConfigurer oAuth2AuthorizationServerConfigurer,
			HttpSecurity httpSecurity) {
		// 添加 手机号登陆 模式支持
		oAuth2AuthorizationServerConfigurer.tokenEndpoint(tokenEndpoint -> {

			DemoUserDetailsService userDetailsService = getBeanOrNull(DemoUserDetailsService.class);
			OAuth2AuthorizationService authorizationService = getBeanOrNull(OAuth2AuthorizationService.class);
			OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator = OAuth2ConfigurerUtils
				.getTokenGenerator(httpSecurity);

			OAuth2MobileAuthenticationProvider authenticationProvider = new OAuth2MobileAuthenticationProvider(
					authorizationService, tokenGenerator, userDetailsService, new MobileVerificationCodeService());

			tokenEndpoint.authenticationProvider(authenticationProvider);
			tokenEndpoint.accessTokenRequestConverter(new OAuth2MobileAuthenticationConverter());
		});
	}

	/**
	 * @return a bean of the requested class if there's just a single registered
	 * component, null otherwise.
	 */
	private <T> T getBeanOrNull(Class<T> type) {
		String[] beanNames = this.context.getBeanNamesForType(type);
		if (beanNames.length != 1) {
			return null;
		}
		return this.context.getBean(beanNames[0], type);
	}

}
