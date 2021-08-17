package com.your.packages.admin.modules.test;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author hccake
 */
@RequestMapping("/public/i18n")
@RestController
@RequiredArgsConstructor
@Validated
public class I18nTestController {

	private final MessageSource messageSource;

	@GetMapping("hello")
	public String hello(@RequestParam("code") String code) {
		return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
	}

	@GetMapping("paramValidate")
	public Integer paramValidate(@Min(value = 10, message = "{validation.ageWrong}") @RequestParam("age") Integer age) {
		return age;
	}

	@GetMapping("bodyValidate")
	public DemoData bodyValidate(@Validated @RequestBody DemoData demoData) {
		return demoData;
	}

	@Data
	public static class DemoData {

		@NotNull(message = "{DemoData.username}：{}")
		private String username;

		@Range(min = 0, max = 150, message = "{DemoData.age}：{}")
		private Integer age;

	}

}
