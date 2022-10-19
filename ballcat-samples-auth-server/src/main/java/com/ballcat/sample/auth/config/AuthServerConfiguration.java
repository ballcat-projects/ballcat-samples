package com.ballcat.sample.auth.config;

import com.ballcat.sample.auth.grant.CustomTokenGrantBuilder;
import com.hccake.ballcat.auth.authentication.TokenGrantBuilder;
import com.hccake.ballcat.auth.configurer.OAuth2ClientConfigurer;
import org.ballcat.springsecurity.oauth2.server.resource.introspection.SpingOAuth2SharedStoredOpaqueTokenIntrospector;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

/**
 * @author hccake
 */
@Configuration(proxyBeanMethods = false)
public class AuthServerConfiguration {

	/**
	 * token 存储，ballcat-admin 项目中默认使用的 redis 存储，这里示例项目没有连接 redis, 就用内存存储
	 * @return TokenStore
	 */
	@Bean
	public TokenStore tokenStore() {
		return new InMemoryTokenStore();
	}

	/**
	 * client 配置也是，ballcat-admin 是使用数据库读取，这里使用 内存
	 * @return OAuth2ClientConfigurer
	 */
	@Bean
	@ConditionalOnMissingBean
	public OAuth2ClientConfigurer oAuth2ClientConfigurer(PasswordEncoder passwordEncoder) {
		// 内存中注册两个客户端 ui 和 app
		// @formatter:off
		return (configurer) -> configurer.inMemory()
				.withClient("admin").secret(passwordEncoder.encode("admin")).scopes("all")
				.and()
				.withClient("app").secret(passwordEncoder.encode("app")).scopes("all")
				.and()
				.withClient("test").secret(passwordEncoder.encode("test")).scopes("all");
		// @formatter:on
	}

	@Bean
	public TokenGrantBuilder tokenGrantBuilder(AuthenticationManager authenticationManager) {
		return new CustomTokenGrantBuilder(authenticationManager);
	}

	/**
	 * 当资源服务器和授权服务器的 token 共享存储时，配合 Spring-security-oauth2 的 TokenStore，用于解析其生成的不透明令牌
	 * @see org.springframework.security.oauth2.provider.token.TokenStore
	 * @return SharedStoredOpaqueTokenIntrospector
	 */
	@Bean
	@ConditionalOnMissingBean
	public OpaqueTokenIntrospector sharedStoredOpaqueTokenIntrospector(TokenStore tokenStore) {
		return new SpingOAuth2SharedStoredOpaqueTokenIntrospector(tokenStore);
	}

}
