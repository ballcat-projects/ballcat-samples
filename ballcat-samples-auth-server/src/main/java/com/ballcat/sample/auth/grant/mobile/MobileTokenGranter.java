package com.ballcat.sample.auth.grant.mobile;

import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Hccake
 * @version 1.0
 * @date 2020/1/20 18:18
 */
public class MobileTokenGranter extends AbstractTokenGranter {

	private static final String GRANT_TYPE = "mobile";

	private final AuthenticationManager authenticationManager;

	public MobileTokenGranter(AuthenticationManager authenticationManager,
			AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
			OAuth2RequestFactory requestFactory) {
		this(authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);

	}

	protected MobileTokenGranter(AuthenticationManager authenticationManager,
			AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
			OAuth2RequestFactory requestFactory, String grantType) {
		super(tokenServices, clientDetailsService, requestFactory, grantType);
		this.authenticationManager = authenticationManager;
	}

	@Override
	protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
		Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
		String phoneNumber = parameters.get("phone_number");
		if (phoneNumber == null) {
			phoneNumber = "";
		}
		phoneNumber = phoneNumber.trim();

		// TODO 这里需要获取验证码参数进行校验, demo 项目中就跳过这一环节了
		String verificationCode = parameters.get("verification_code");

		MobileAuthenticationToken mobileAuthenticationToken = new MobileAuthenticationToken(phoneNumber);

		Authentication authentication;
		try {
			authentication = this.authenticationManager.authenticate(mobileAuthenticationToken);

			logger.debug("Authentication success: " + authentication);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		catch (AccountStatusException ase) {
			//covers expired, locked, disabled cases (mentioned in section 5.2, draft 31)
			throw new InvalidGrantException(ase.getMessage());
		}
		catch (BadCredentialsException e) {
			// If the username/password are wrong the spec says we should send 400/invalid grant
			throw new InvalidGrantException(e.getMessage());
		}

		if (authentication == null || !authentication.isAuthenticated()) {
			throw new InvalidGrantException("Could not authenticate user by phoneNumber: " + phoneNumber);
		}

		OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
		return new OAuth2Authentication(storedOAuth2Request, authentication);
	}

}
