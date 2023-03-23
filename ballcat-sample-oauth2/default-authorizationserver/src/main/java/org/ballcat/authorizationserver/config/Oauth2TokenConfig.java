package org.ballcat.authorizationserver.config;

import com.hccake.ballcat.common.security.constant.TokenAttributeNameConstants;
import com.hccake.ballcat.common.security.constant.UserInfoFiledNameConstants;
import com.hccake.ballcat.common.security.userdetails.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsSet;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hccake
 */
@Configuration(proxyBeanMethods = false)
public class Oauth2TokenConfig {

	@Bean
	public OAuth2TokenCustomizer<OAuth2TokenClaimsContext> oAuth2TokenCustomizer() {
		return context -> {
			OAuth2TokenClaimsSet.Builder claims = context.getClaims();
			Authentication authentication = context.getPrincipal();
			Object principal = authentication.getPrincipal();
			if (principal instanceof User) {
				User user = (User) principal;
				Map<String, Object> attributes = user.getAttributes();
				claims.claim(TokenAttributeNameConstants.ATTRIBUTES, attributes);
				HashMap<String, Object> userInfoMap = getUserInfoMap(user);
				claims.claim(TokenAttributeNameConstants.INFO, userInfoMap);
			}
		};
	}

	private static HashMap<String, Object> getUserInfoMap(User user) {
		HashMap<String, Object> userInfo = new HashMap<>(6);
		userInfo.put(UserInfoFiledNameConstants.USER_ID, user.getUserId());
		userInfo.put(UserInfoFiledNameConstants.TYPE, user.getType());
		userInfo.put(UserInfoFiledNameConstants.ORGANIZATION_ID, user.getOrganizationId());
		userInfo.put(UserInfoFiledNameConstants.USERNAME, user.getUsername());
		userInfo.put(UserInfoFiledNameConstants.NICKNAME, user.getNickname());
		userInfo.put(UserInfoFiledNameConstants.AVATAR, user.getAvatar());
		return userInfo;
	}

}
