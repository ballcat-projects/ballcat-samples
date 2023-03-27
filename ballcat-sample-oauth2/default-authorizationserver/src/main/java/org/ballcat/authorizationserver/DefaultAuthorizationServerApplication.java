package org.ballcat.authorizationserver;

import org.ballcat.springsecurity.oauth2.server.authorization.annotation.EnableOauth2AuthorizationServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hccake
 */
@EnableOauth2AuthorizationServer
@SpringBootApplication
public class DefaultAuthorizationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DefaultAuthorizationServerApplication.class, args);
	}

}
