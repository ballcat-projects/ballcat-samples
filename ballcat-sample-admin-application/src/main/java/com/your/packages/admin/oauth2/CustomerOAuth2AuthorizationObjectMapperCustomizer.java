package com.your.packages.admin.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.your.packages.admin.datascope.UserDataScope;
import com.your.packages.admin.oauth2.jackson2.UserDataScopeMixin;
import org.ballcat.springsecurity.oauth2.server.authorization.OAuth2AuthorizationObjectMapperCustomizer;
import org.springframework.stereotype.Component;

/**
 * 对于 User attributes 中存放的数据，必须要有对应的 Mixin 类，否则会报错
 *
 * @link <a href="https://github.com/spring-projects/spring-security/issues/4370">...</a>
 * @author hccake
 */
@Component
public class CustomerOAuth2AuthorizationObjectMapperCustomizer implements OAuth2AuthorizationObjectMapperCustomizer {

	@Override
	public void customize(ObjectMapper objectMapper) {
		objectMapper.addMixIn(UserDataScope.class, UserDataScopeMixin.class);
	}

}
