package org.ballcat.sample.authorizationserver.env;

import lombok.RequiredArgsConstructor;
import org.ballcat.sample.authorizationserver.grant.CustomAuthorizationGrantType;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.UUID;

/**
 * @author hccake
 */
@Component
@RequiredArgsConstructor
public class RegisteredClientInitializer implements ApplicationRunner {

	private final RegisteredClientRepository registeredClientRepository;

	@Override
	public void run(ApplicationArguments args) {
		RegisteredClient testClient = RegisteredClient.withId(UUID.randomUUID().toString())
			.clientId("test")
			.clientSecret("{noop}test")
			.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
			.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
			.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
			.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
			.authorizationGrantType(AuthorizationGrantType.PASSWORD)
			.authorizationGrantType(CustomAuthorizationGrantType.MOBILE)
			.redirectUri("http://127.0.0.1:8111/authorized")
			.scope("skip_captcha") // 跳过验证码
			.scope("skip_password_decode") // 跳过 AES 密码解密
			.tokenSettings(TokenSettings.builder()
				// 使用不透明令牌
				.accessTokenFormat(OAuth2TokenFormat.REFERENCE)
				.accessTokenTimeToLive(Duration.ofDays(1))
				.refreshTokenTimeToLive(Duration.ofDays(3))
				.build())
			.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
			.build();
		registeredClientRepository.save(testClient);

		RegisteredClient appClient = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("app")
				.clientSecret("{noop}app")
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				.authorizationGrantType(AuthorizationGrantType.PASSWORD)
				.authorizationGrantType(CustomAuthorizationGrantType.MOBILE)
				.redirectUri("http://127.0.0.1:8111/authorized")
				.scope("skip_captcha") // 跳过验证码
				.scope("skip_password_decode") // 跳过 AES 密码解密
				.tokenSettings(TokenSettings.builder()
						// 使用不透明令牌
						.accessTokenFormat(OAuth2TokenFormat.REFERENCE)
						.accessTokenTimeToLive(Duration.ofDays(1))
						.refreshTokenTimeToLive(Duration.ofDays(3))
						.build())
				.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
				.build();
		registeredClientRepository.save(appClient);
	}

}
