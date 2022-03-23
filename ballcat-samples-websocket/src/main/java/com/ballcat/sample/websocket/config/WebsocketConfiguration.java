package com.ballcat.sample.websocket.config;

import com.ballcat.sample.websocket.component.MyHandshakInteceptor;
import com.hccake.ballcat.autoconfigure.websocket.SockJsServiceConfigurer;
import com.hccake.ballcat.autoconfigure.websocket.WebSocketProperties;
import com.hccake.ballcat.common.websocket.session.SessionKeyGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hccake
 */
@Configuration(proxyBeanMethods = false)
public class WebsocketConfiguration {

	/**
	 * SessionKey 生成器，从 webSocketSession 的 attribute 中，获取 name 属性做为 key
	 * @see MyHandshakInteceptor name 属性是在拦截器中放入的
	 * @return SessionKeyGenerator
	 */
	@Bean
	public SessionKeyGenerator sessionKeyGenerator() {
		return webSocketSession -> webSocketSession.getAttributes().get("name");
	}

	/**
	 * 当开启 sockjs 的时候，把心跳设置为 5s 一次，默认时 25s
	 * @return SockJsServiceConfigurer
	 */
	@Bean
	@ConditionalOnProperty(prefix = WebSocketProperties.PREFIX, name = "with-sockjs", havingValue = "true")
	public SockJsServiceConfigurer sockJsServiceConfigurer() {
		return sockJsServiceRegistration -> sockJsServiceRegistration.setHeartbeatTime(5000);
	}

}
