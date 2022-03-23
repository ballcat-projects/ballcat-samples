package com.ballcat.sample.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

/**
 * WebSocket 的使用示例类
 *
 * @author hccake
 */
@Slf4j
@SpringBootApplication
public class WebsocketApplication {

	public static void main(String[] args) throws UnknownHostException {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(WebsocketApplication.class, args);
		Environment env = applicationContext.getEnvironment();
		String ip = InetAddress.getLocalHost().getHostAddress();
		String port = env.getProperty("server.port");
		String path = env.getProperty("server.servlet.context-path");
		if (!StringUtils.hasText(path)) {
			path = "";
		}

		String home = "http://localhost:" + port + path;

		log.info("\n----------------------------------------------------------\n" + "项目启动成功，websocket 测试页面地址:\n\t"
				+ "Local访问网址: \t\t" + home + "\n\t" + "External访问网址: \thttp://" + ip + ":" + port + path + "\n"
				+ "----------------------------------------------------------");

		// 打开浏览器
		browse(home);
	}

	public static void browse(String url) {
		if (Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();
			try {
				desktop.browse(new URI(url));
			}
			catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}
		else {
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
