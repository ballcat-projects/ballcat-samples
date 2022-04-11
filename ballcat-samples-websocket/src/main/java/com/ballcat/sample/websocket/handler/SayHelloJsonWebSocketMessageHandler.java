package com.ballcat.sample.websocket.handler;

import com.ballcat.sample.websocket.message.HelloJsonWebsocketMessage;
import com.ballcat.sample.websocket.message.SayHelloJsonWebsocketMessage;
import com.hccake.ballcat.common.websocket.WebSocketMessageSender;
import com.hccake.ballcat.common.websocket.handler.JsonMessageHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

/**
 * 当收到 say-hello 类型的消息时，返回一个 HelloJsonWebsocketMessage
 *
 * @author hccake
 */
@Component
public class SayHelloJsonWebSocketMessageHandler implements JsonMessageHandler<SayHelloJsonWebsocketMessage> {

	@Override
	public void handle(WebSocketSession session, SayHelloJsonWebsocketMessage message) {
		String language = message.getLanguage();
		HelloJsonWebsocketMessage helloJsonWebsocketMessage = new HelloJsonWebsocketMessage();
		if ("中文".equals(language)) {
			helloJsonWebsocketMessage.setText("你好");
		}
		else {
			helloJsonWebsocketMessage.setText("Hello");
		}
		WebSocketMessageSender.send(session, helloJsonWebsocketMessage);
	}

	@Override
	public String type() {
		return "say-hello";
	}

	@Override
	public Class<SayHelloJsonWebsocketMessage> getMessageClass() {
		return SayHelloJsonWebsocketMessage.class;
	}

}
