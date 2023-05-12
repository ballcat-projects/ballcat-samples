package org.ballcat.sample.authorizationserver.userdetails;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

/**
 * @author hccake
 */
@RequiredArgsConstructor
public class SystemUserService {

	private final List<SystemUser> systemUsers;

	public SystemUserService(SystemUser... systemUsers) {
		this.systemUsers = Arrays.asList(systemUsers);
	}

	public SystemUser loadUserByUsername(String username) {
		return systemUsers.stream()
			.filter(systemUser -> systemUser.getUsername().equals(username))
			.findFirst()
			.orElse(null);
	}

	public SystemUser loadUserByPhoneNumber(String phoneNumber) {
		return systemUsers.stream()
			.filter(systemUser -> systemUser.getPhoneNumber().equals(phoneNumber))
			.findFirst()
			.orElse(null);
	}

}
