package com.ballcat.sample.auth.grant;

import com.ballcat.sample.auth.grant.mobile.MobileTokenGranter;
import com.hccake.ballcat.auth.authentication.TokenGrantBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.List;

/**
 * 自定义的 tokenGrant 扩展，添加手机号登录支持
 *
 * @author hccake
 */
public class CustomTokenGrantBuilder extends TokenGrantBuilder {

	public CustomTokenGrantBuilder(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	public TokenGranter build(final AuthorizationServerEndpointsConfigurer endpoints) {
		List<TokenGranter> tokenGranters = defaultTokenGranters(endpoints);
		MobileTokenGranter mobileTokenGranter = getMobileTokenGranter(endpoints);
		tokenGranters.add(mobileTokenGranter);
		return new CompositeTokenGranter(tokenGranters);
	}

	private MobileTokenGranter getMobileTokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
		ClientDetailsService clientDetailsService = endpoints.getClientDetailsService();
		AuthorizationServerTokenServices tokenServices = endpoints.getTokenServices();
		OAuth2RequestFactory requestFactory = endpoints.getOAuth2RequestFactory();
		return new MobileTokenGranter(getAuthenticationManager(), tokenServices, clientDetailsService, requestFactory);
	}

}
