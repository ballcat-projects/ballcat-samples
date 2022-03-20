package com.ballcat.sample.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * 握手拦截器
 *
 * @author hccake
 */
@Component
public class MyHandshakInteceptor implements HandshakeInterceptor {

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
			// 从 websocket 连接中获取 name 参数，以便做为 sessionKey
			// websocket 连接示例：ws://localhost:8080/ws?name=zhangsan
			String name = serverRequest.getServletRequest().getParameter("name");
			if (name == null) {
				name = "no-name";
			}
			attributes.put("name", name);
		}
		// 在这里可以进行鉴权，如果不通过返回 false
		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {

	}

}
