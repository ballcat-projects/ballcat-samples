package com.ballcat.sample.auth.component;

import com.ballcat.sample.auth.model.UserInfo;
import com.hccake.ballcat.common.security.constant.TokenAttributeNameConstants;
import com.hccake.ballcat.common.security.userdetails.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 扩展登陆后返回的数据信息
 *
 * @author hccake
 */
@Component
public class CustomTokenEnhancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Authentication userAuthentication = authentication.getUserAuthentication();
		if (userAuthentication == null) {
			return accessToken;
		}

		Object principal = userAuthentication.getPrincipal();
		if (principal instanceof User) {
			User user = (User) principal;
			// token 附属信息
			Map<String, Object> additionalInfo = new HashMap<>(8);

			// 用户基本信息
			UserInfo userInfo = getSysUserInfo(user);
			additionalInfo.put(TokenAttributeNameConstants.INFO, userInfo);

			((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
		}

		return accessToken;
	}

	/**
	 * 根据 User 对象获取 SysUserInfo
	 * @param user User
	 * @return SysUserInfo
	 */
	public UserInfo getSysUserInfo(User user) {
		UserInfo sysUserInfo = new UserInfo();
		sysUserInfo.setUserId(user.getUserId());
		sysUserInfo.setUsername(user.getUsername());
		sysUserInfo.setNickname(user.getNickname());
		return sysUserInfo;
	}

}
