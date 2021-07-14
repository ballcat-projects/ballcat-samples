package com.hccake.ballcat.api;

import com.hccake.ballcat.common.security.annotation.EnableOauth2ResourceServer;
import com.hccake.ballcat.common.swagger.annotation.EnableSwagger2Provider;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @EnableXxlJob
@EnableSwagger2Provider
@SpringBootApplication
@MapperScan(basePackages = { "com.hccake.ballcat.**.mapper" })
@EnableOauth2ResourceServer
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}
