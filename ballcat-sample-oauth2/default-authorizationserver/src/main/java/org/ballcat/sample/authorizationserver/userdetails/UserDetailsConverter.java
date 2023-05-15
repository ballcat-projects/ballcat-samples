package org.ballcat.sample.authorizationserver.userdetails;

import com.hccake.ballcat.common.security.userdetails.User;

import java.util.HashMap;

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
			.phoneNumber(systemUser.getPhoneNumber())
			.type(systemUser.getType())
			.attributes(new HashMap<>())
			.status(1)
			.build();
	}

	public static User convert(AppUser appUser) {
		return User.builder()
			.userId(appUser.getUserId())
			.username(appUser.getUsername())
			.nickname(appUser.getNickname())
			.password(appUser.getPassword())
			.phoneNumber(appUser.getPhoneNumber())
			.type(appUser.getType())
			.attributes(new HashMap<>())
			.status(1)
			.build();
	}

}
