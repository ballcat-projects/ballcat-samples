package org.ballcat.sample.customuser.authorizaitonserver.user;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hccake
 */
@Data
@Builder
public class AppUserDetails implements UserDetails, OAuth2User, CredentialsContainer {

	private final AppUserInfo appUserInfo;

	private final Collection<? extends GrantedAuthority> authorities;

	/**
	 * 用于密码登陆时校验
	 */
	private String password;

	@Override
	public String getName() {
		return appUserInfo.getUsername();
	}

	@Override
	public Map<String, Object> getAttributes() {
		return new HashMap<>();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return appUserInfo.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public void eraseCredentials() {
		this.password = null;
	}

}
