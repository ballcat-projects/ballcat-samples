package org.ballcat.sample.authorizationserver.grant;

import org.springframework.security.oauth2.core.AuthorizationGrantType;

/**
 * @author hccake
 */
public final class CustomAuthorizationGrantType {

	private CustomAuthorizationGrantType() {
	}

	public static final AuthorizationGrantType MOBILE = new AuthorizationGrantType("mobile");

}
