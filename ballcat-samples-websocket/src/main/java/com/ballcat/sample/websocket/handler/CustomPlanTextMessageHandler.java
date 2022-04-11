package com.ballcat.sample.websocket.handler;

import com.hccake.ballcat.common.websocket.handler.PlanTextMessageHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * @author hccake
 */
@Component
public class CustomPlanTextMessageHandler implements PlanTextMessageHandler {

	@Override
	public void handle(WebSocketSession session, String message) {
		try {
			TextMessage textMessage = new TextMessage("接收到文本消息：" + message);
			session.sendMessage(textMessage);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
