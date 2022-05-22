package com.ballcat.sample.auth.config;

import com.hccake.ballcat.auth.configurer.OAuth2ClientConfigurer;
import com.hccake.ballcat.common.security.util.PasswordUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

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
	public OAuth2ClientConfigurer oAuth2ClientConfigurer() {
		// 内存中注册两个客户端 ui 和 app
		// @formatter:off
		return (configurer) -> {
			configurer.inMemory()
					.withClient("admin").secret(PasswordUtils.encode("admin")).scopes("all")
					.and()
					.withClient("app").secret(PasswordUtils.encode("app")).scopes("all");
		};
		// @formatter:on
	}

}
