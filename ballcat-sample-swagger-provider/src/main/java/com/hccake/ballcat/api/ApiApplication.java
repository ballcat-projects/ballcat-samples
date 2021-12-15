package com.hccake.ballcat.api;

import com.hccake.ballcat.common.security.annotation.EnableOauth2ResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @EnableXxlJob
@SpringBootApplication
@EnableOauth2ResourceServer
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}
