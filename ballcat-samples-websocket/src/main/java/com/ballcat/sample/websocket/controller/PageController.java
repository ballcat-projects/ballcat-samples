package com.ballcat.sample.websocket.controller;

import com.hccake.ballcat.autoconfigure.websocket.WebSocketProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author hccake
 */
@Controller
@RequiredArgsConstructor
public class PageController {

	private final WebSocketProperties webSocketProperties;

	/**
	 * 首页转到 websocket 测试页面
	 * @return ModelAndView
	 */
	@RequestMapping
	public ModelAndView home() {
		boolean withSockjs = webSocketProperties.isWithSockjs();
		ModelAndView modelAndView = new ModelAndView("/index");
		modelAndView.addObject("withSockjs", withSockjs);
		return modelAndView;
	}

}
