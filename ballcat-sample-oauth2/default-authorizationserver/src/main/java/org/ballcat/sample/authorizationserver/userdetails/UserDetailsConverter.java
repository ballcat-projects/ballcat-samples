package org.ballcat.sample.authorizationserver.userdetails;

import cn.hutool.core.map.MapUtil;
import com.hccake.ballcat.common.security.userdetails.User;

/**
 * @author hccake
 */
public final class UserDetailsConverter {

	private UserDetailsConverter() {
	}

	public static User convert(SystemUser systemUser) {
		return User.builder()
			.userId(systemUser.getUserId())
			.username(systemUser.getUsername())
			.nickname(systemUser.getNickname())
			.password(systemUser.getPassword())
			.status(1)
			.attributes(MapUtil.of("phoneNumber", systemUser.getPhoneNumber()))
			.build();
	}

}
