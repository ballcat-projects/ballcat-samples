package com.ballcat.sample.websocket.message;

import com.hccake.ballcat.common.websocket.message.JsonWebSocketMessage;

/**
 * 当服务端收到此消息时，根据指定的语言，回复消息
 *
 * @author hccake
 */
public class SayHelloJsonWebsocketMessage extends JsonWebSocketMessage {

	/**
	 * 回复的语言，默认英文
	 */
	private String language;

	public SayHelloJsonWebsocketMessage() {
		super("say-hello");
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
