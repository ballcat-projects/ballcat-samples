package com.hccake.ballcat.api.modules.api.controller;

import com.hccake.ballcat.common.core.exception.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Hccake
 * @version 1.0
 * @date 2019/10/16 11:46
 */
@RequestMapping("/public")
@RestController
public class TestController {

	@Operation(summary = "测试地址")
	@PostMapping("/test")
	public String test() {
		return "Hello word!";
	}

	@GetMapping("/test/{test}")
	public String test(@PathVariable String test) {
		return "Hello " + test;
	}

	@PostMapping("/formdata")
	public String test1(@RequestParam("formdata") String formdata) {
		return formdata;
	}

	@PostMapping("/xwww")
	public String test2(@RequestParam("xwww") String xwww) {
		return xwww;
	}

	@PostMapping("/raw")
	public TestObj test3(@RequestBody TestObj testObj) {

		System.out.println(testObj);

		TestObj test = new TestObj();
		test.setLocalDateTime(LocalDateTime.now());
		test.setLocalDate(LocalDate.now());
		test.setStr("test");

		return test;
	}

	@PostMapping("/errortest")
	public String error() {
		throw new BusinessException(9999, "Error Test！");
	}

}
