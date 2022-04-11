package com.ballcat.sample.websocket.message;

import com.hccake.ballcat.common.websocket.message.JsonWebSocketMessage;

/**
 * @author hccake
 */
public class HelloJsonWebsocketMessage extends JsonWebSocketMessage {

	private String text;

	public HelloJsonWebsocketMessage() {
		super("hello");
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
