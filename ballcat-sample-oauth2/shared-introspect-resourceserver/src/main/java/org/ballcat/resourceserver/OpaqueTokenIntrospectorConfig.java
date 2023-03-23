package org.ballcat.resourceserver;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hccake.ballcat.common.security.jackson2.UserMixin;
import com.hccake.ballcat.common.security.userdetails.User;
import org.ballcat.springsecurity.oauth2.server.resource.introspection.SpringAuthorizationServerSharedStoredOpaqueTokenIntrospector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

import java.util.List;

/**
 * @author hccake
 */
@Configuration(proxyBeanMethods = false)
public class OpaqueTokenIntrospectorConfig {

	/**
	 * OAuth2 授权服务器中注册的 client 仓库
	 */
	@Bean
	public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
		return new JdbcRegisteredClientRepository(jdbcTemplate);
	}

	/**
	 * OAuth2 授权管理 Service
	 */
	@Bean
	public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate,
			RegisteredClientRepository registeredClientRepository) {
		JdbcOAuth2AuthorizationService oAuth2AuthorizationService = new JdbcOAuth2AuthorizationService(jdbcTemplate,
				registeredClientRepository);

		// 需要注册自己的 mixin 来处理类型转换
		// link
		// https://github.com/spring-projects/spring-authorization-server/issues/397#issuecomment-900148920
		JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper rowMapper = new JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper(
				registeredClientRepository);

		ObjectMapper objectMapper = new ObjectMapper();
		ClassLoader classLoader = JdbcOAuth2AuthorizationService.class.getClassLoader();
		List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
		objectMapper.registerModules(securityModules);
		objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());

		// You will need to write the Mixin for your class so Jackson can marshall it.
		objectMapper.addMixIn(User.class, UserMixin.class);
		rowMapper.setObjectMapper(objectMapper);

		oAuth2AuthorizationService.setAuthorizationRowMapper(rowMapper);

		return oAuth2AuthorizationService;
	}

	/**
	 * sas 共享存储的 token 自省器
	 */
	@Bean
	public OpaqueTokenIntrospector sharedStoredOpaqueTokenIntrospector(
			OAuth2AuthorizationService authorizationService) {
		return new SpringAuthorizationServerSharedStoredOpaqueTokenIntrospector(authorizationService);
	}

}
