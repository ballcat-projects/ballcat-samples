package org.ballcat.sample.authorizationserver.userdetails;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

/**
 * @author hccake
 */
@RequiredArgsConstructor
public class AppUserService {

	private final List<AppUser> appUsers;

	public AppUserService(AppUser... appUsers) {
		this.appUsers = Arrays.asList(appUsers);
	}

	public AppUser loadUserByUsername(String username) {
		return appUsers.stream().filter(appUser -> appUser.getUsername().equals(username)).findFirst().orElse(null);
	}

	public AppUser loadUserByPhoneNumber(String phoneNumber) {
		return appUsers.stream()
			.filter(appUser -> appUser.getPhoneNumber().equals(phoneNumber))
			.findFirst()
			.orElse(null);
	}

}
