
package org.ballcat.sample.authorizationserver.userdetails;

import com.hccake.ballcat.common.security.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUserDetailsService implements UserDetailsService {

	private final Map<String, User> users;

	public InMemoryUserDetailsService(User... userList) {
		this.users = new HashMap<>();
		for (User user : userList) {
			this.users.put(user.getUsername(), user);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.users.get(username.toLowerCase());
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return user;
	}

}
