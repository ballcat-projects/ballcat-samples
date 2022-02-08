package com.your.packages.admin.config;

import com.hccake.ballcat.common.idempotent.IdempotentAspect;
import com.hccake.ballcat.common.idempotent.key.IdempotentKeyStore;
import com.hccake.ballcat.common.idempotent.key.RedisIdempotentKeyStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 幂等组件配置类
 *
 * @author hccake
 */
@Configuration(proxyBeanMethods = false)
public class IdempotentConfiguration {

	@Bean
	public IdempotentKeyStore idempotentKeyStore(StringRedisTemplate stringRedisTemplate) {
		return new RedisIdempotentKeyStore(stringRedisTemplate);
	}

	@Bean
	public IdempotentAspect idempotentAspect(IdempotentKeyStore idempotentKeyStore) {
		return new IdempotentAspect(idempotentKeyStore);
	}

}
