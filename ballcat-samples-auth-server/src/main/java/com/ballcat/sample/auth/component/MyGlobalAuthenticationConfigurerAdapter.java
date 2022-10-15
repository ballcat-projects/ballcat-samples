package com.ballcat.sample.auth.component;

import lombok.RequiredArgsConstructor;
import org.ballcat.springsecurity.oauth2.server.resource.properties.OAuth2ResourceServerProperties;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.stereotype.Component;

/**
 * @author hccake
 */
@Component
@RequiredArgsConstructor
public class MyGlobalAuthenticationConfigurerAdapter extends GlobalAuthenticationConfigurerAdapter {

	private final OAuth2ResourceServerProperties resourceServerProperties;

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(new AnonymousAuthenticationProvider(resourceServerProperties));
	}

}
