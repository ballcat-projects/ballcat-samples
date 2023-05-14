package org.ballcat.sample.customuser.authorizaitonserver;

import org.ballcat.springsecurity.oauth2.server.authorization.annotation.EnableOauth2AuthorizationServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hccake
 */
@EnableOauth2AuthorizationServer
@SpringBootApplication
public class CustomUserAuthorizationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomUserAuthorizationServerApplication.class, args);
	}

}
