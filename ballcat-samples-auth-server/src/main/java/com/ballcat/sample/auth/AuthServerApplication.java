package com.ballcat.sample.auth;

import com.hccake.ballcat.auth.annotation.EnableOauth2AuthorizationServer;
import org.ballcat.springsecurity.oauth2.server.resource.annotation.EnableOauth2ResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hccake
 */
@EnableOauth2AuthorizationServer
@EnableOauth2ResourceServer
@SpringBootApplication
public class AuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}

}
