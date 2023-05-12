package org.ballcat.sample.authorizationserver.grant.mobile;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.ballcat.sample.authorizationserver.grant.CustomAuthorizationGrantType;
import org.ballcat.springsecurity.oauth2.server.authorization.authentication.AbstractOAuth2ResourceOwnerAuthenticationToken;
import org.ballcat.springsecurity.oauth2.server.authorization.authentication.OAuth2ResourceOwnerPasswordAuthenticationProvider;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Set;

/**
 * An {@link Authentication} implementation used for the OAuth 2.0 Resource Owner Mobile
 * Grant.
 *
 * @author Hccake
 * @since 1.0.0
 * @see OAuth2AuthorizationGrantAuthenticationToken
 * @see OAuth2ResourceOwnerPasswordAuthenticationProvider
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OAuth2MobileAuthenticationToken extends AbstractOAuth2ResourceOwnerAuthenticationToken {

	private final String phoneNumber;

	/**
	 * Constructs an {@code OAuth2ClientCredentialsAuthenticationToken} using the provided
	 * parameters.
	 * @param clientPrincipal the authenticated client principal
	 */
	public OAuth2MobileAuthenticationToken(String phoneNumber, Authentication clientPrincipal,
			@Nullable Map<String, Object> additionalParameters, @Nullable Set<String> scopes) {
		super(CustomAuthorizationGrantType.MOBILE, clientPrincipal, additionalParameters, scopes);
		Assert.hasText(phoneNumber, "phone number cannot be empty");
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String getName() {
		return phoneNumber;
	}

}
