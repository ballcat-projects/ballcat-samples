package com.your.packages.admin.config;

import com.hccake.ballcat.auth.OAuth2AuthorizationServerProperties;
import com.hccake.ballcat.auth.web.OAuth2LoginUrlAuthenticationEntryPoint;
import org.ballcat.springsecurity.oauth2.server.resource.configurer.OAuth2ResourceServerExtensionConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

/**
 * Spring OAuth2 授权码模式需要登录后进行流程，处理登录错误，并重定向回登录页
 *
 * @author hccake
 */
@Component
public final class SpringOAuth2LoginRedirectConfigurer extends OAuth2ResourceServerExtensionConfigurer<HttpSecurity> {

	private static final String DEFAULT_LOGIN_URL = "/login";

	@Autowired
	private OAuth2AuthorizationServerProperties oAuth2AuthorizationServerProperties;

	@Override
	public void init(HttpSecurity http) {
		// 表单登录支持
		String formLoginPage = oAuth2AuthorizationServerProperties.getFormLoginPage();
		if (formLoginPage == null) {
			formLoginPage = DEFAULT_LOGIN_URL;
		}

		ExceptionHandlingConfigurer<HttpSecurity> exceptionHandling = (ExceptionHandlingConfigurer) http
				.getConfigurer(ExceptionHandlingConfigurer.class);
		exceptionHandling.defaultAuthenticationEntryPointFor(new OAuth2LoginUrlAuthenticationEntryPoint(formLoginPage),
				new AntPathRequestMatcher("/oauth/authorize", HttpMethod.GET.name()));
	}

}
