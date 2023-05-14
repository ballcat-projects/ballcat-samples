package org.ballcat.sample.customuser.authorizaitonserver.token;

import com.hccake.ballcat.common.security.constant.TokenAttributeNameConstants;
import org.ballcat.sample.customuser.authorizaitonserver.user.AppUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsSet;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 自定义 OAuth2TokenCustomizer，以便在自省时返回对应信息
 *
 * @see AppUserDetails
 * @author hccake
 */
@Component
public class AppUserOAuth2TokenCustomizer implements OAuth2TokenCustomizer<OAuth2TokenClaimsContext> {

	@Override
	public void customize(OAuth2TokenClaimsContext context) {
		OAuth2TokenClaimsSet.Builder claims = context.getClaims();
		Authentication authentication = context.getPrincipal();

		// client token
		if (authentication instanceof OAuth2ClientAuthenticationToken) {
			claims.claim(TokenAttributeNameConstants.IS_CLIENT, true);
			return;
		}

		Object principal = authentication.getPrincipal();
		if (principal instanceof AppUserDetails) {
			AppUserDetails userDetails = (AppUserDetails) principal;
			Map<String, Object> attributes = userDetails.getAttributes();
			claims.claim(TokenAttributeNameConstants.ATTRIBUTES, attributes);
			claims.claim(TokenAttributeNameConstants.INFO, userDetails.getAppUserInfo());
			claims.claim(TokenAttributeNameConstants.IS_CLIENT, false);
		}
	}

}
