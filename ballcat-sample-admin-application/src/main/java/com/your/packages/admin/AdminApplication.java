package com.your.packages.admin;

import com.hccake.ballcat.autoconfigure.log.annotation.EnableAccessLog;
import com.hccake.ballcat.autoconfigure.log.annotation.EnableOperationLog;
import com.hccake.ballcat.common.job.annotation.EnableXxlJob;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Hccake
 */
@EnableXxlJob
@EnableAccessLog
@EnableOperationLog
@MapperScan({ "com.your.packages.**.mapper" })
@SpringBootApplication(scanBasePackages = { "com.your.packages.admin" })
public class AdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

}
