package com.your.packages.admin.modules.test;

import com.hccake.ballcat.common.idempotent.annotation.Idempotent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 幂等测试
 *
 * @author hccake
 */
@RestController
@RequestMapping("/public/idempotent")
public class IdempotentTestController {

	@GetMapping
	@Idempotent(uniqueExpression = "#key")
	public String test(@RequestParam("key") String key) {
		return key;
	}

}
