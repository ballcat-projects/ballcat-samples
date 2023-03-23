package org.ballcat.resourceserver;

import org.ballcat.springsecurity.oauth2.server.resource.annotation.EnableOauth2ResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hccake
 */
@EnableOauth2ResourceServer
@SpringBootApplication
public class SharedIntrospectResourceServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SharedIntrospectResourceServerApplication.class, args);
	}

}
