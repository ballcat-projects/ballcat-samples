package org.ballcat.sample.authorizationserver.authentication;

import com.hccake.ballcat.common.security.userdetails.User;
import org.ballcat.springsecurity.oauth2.server.authorization.web.authentication.OAuth2TokenResponseEnhancer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * token 响应增强
 *
 * @author Hccake
 */
@Component
public class DemoOAuth2TokenResponseEnhancer implements OAuth2TokenResponseEnhancer {

	@Override
	public Map<String, Object> enhance(OAuth2AccessTokenAuthenticationToken accessTokenAuthentication) {
		Object principal = Optional.ofNullable(SecurityContextHolder.getContext())
			.map(SecurityContext::getAuthentication)
			.map(Authentication::getPrincipal)
			.orElse(null);

		// token 附属信息
		Map<String, Object> additionalParameters = accessTokenAuthentication.getAdditionalParameters();
		if (additionalParameters == null) {
			additionalParameters = new HashMap<>(8);
		}

		// 在 token 响应中添加额外的数据，这里添加了 nickname
		if (principal instanceof User) {
			User user = (User) principal;
			additionalParameters.put("nickname", user.getNickname());
		}

		return additionalParameters;
	}

}
