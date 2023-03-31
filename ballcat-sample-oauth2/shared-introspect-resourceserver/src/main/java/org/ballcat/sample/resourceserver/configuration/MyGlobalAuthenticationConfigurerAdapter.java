package org.ballcat.sample.resourceserver.configuration;

import lombok.RequiredArgsConstructor;
import org.ballcat.springsecurity.authentication.AnonymousForeverAuthenticationProvider;
import org.ballcat.springsecurity.oauth2.server.resource.properties.OAuth2ResourceServerProperties;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.stereotype.Component;

/**
 * 开启匿名支持
 *
 * @author hccake
 */
@Component
@RequiredArgsConstructor
public class MyGlobalAuthenticationConfigurerAdapter extends GlobalAuthenticationConfigurerAdapter {

	private final OAuth2ResourceServerProperties resourceServerProperties;

	@Override
	public void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(
				new AnonymousForeverAuthenticationProvider(resourceServerProperties.getIgnoreUrls()));
	}

}
