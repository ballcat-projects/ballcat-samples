package org.ballcat.sample.customuser.authorizaitonserver.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ballcat.sample.customuser.authorizaitonserver.jackson.AppUserDetailsMixin;
import org.ballcat.sample.customuser.authorizaitonserver.jackson.AppUserInfoMixin;
import org.ballcat.sample.customuser.authorizaitonserver.user.AppUserDetails;
import org.ballcat.sample.customuser.authorizaitonserver.user.AppUserInfo;
import org.ballcat.springsecurity.oauth2.server.authorization.OAuth2AuthorizationObjectMapperCustomizer;
import org.springframework.stereotype.Component;

/**
 * 授权服务器的 ObjectMapper 增强器
 *
 * @author hccake
 */
@Component
public class CustomOAuth2AuthorizationObjectMapperCustomizer implements OAuth2AuthorizationObjectMapperCustomizer {

	@Override
	public void customize(ObjectMapper objectMapper) {
		// 添加自定义 mixin 用于序列化
		objectMapper.addMixIn(AppUserInfo.class, AppUserInfoMixin.class);
		objectMapper.addMixIn(AppUserDetails.class, AppUserDetailsMixin.class);
	}

}
