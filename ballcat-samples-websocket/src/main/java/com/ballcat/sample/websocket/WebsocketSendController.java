package com.ballcat.sample.websocket;

import com.hccake.ballcat.common.websocket.distribute.MessageDO;
import com.hccake.ballcat.common.websocket.distribute.MessageDistributor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hccake
 */
@RestController
public class WebsocketSendController {

	private final MessageDistributor messageDistributor;

	public WebsocketSendController(MessageDistributor messageDistributor) {
		this.messageDistributor = messageDistributor;
	}

	/**
	 * 广播, 测试并发 webSession 发送
	 *
	 * ps: 如果配置中不开启并发支持，会导致并发调用时报连接关闭的异常信息
	 * @return String
	 */
	@GetMapping("broadcast")
	public String a() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					String messageText = Thread.currentThread().getName() + ": message: " + i;
					MessageDO messageDO = new MessageDO().setNeedBroadcast(true).setMessageText(messageText);
					System.out.println(messageText);
					messageDistributor.distribute(messageDO);
				}
			}
		};

		for (int i = 0; i < 3; i++) {
			new Thread(runnable, "线程" + i).start();
		}

		return "ok";
	}

}
