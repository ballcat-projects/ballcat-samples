package com.ballcat.sample.i18n;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

/**
 * @author hccake
 */
@RequestMapping("/i18n")
@RestController
@RequiredArgsConstructor
@Validated
public class I18nTestController {

	private final MessageSource messageSource;

	/**
	 * 从 messageSource 中获取对应的国际化信息 注意切换请求头中的 Accept-Language 对应的语言：en_US | zh_CN
	 * @see org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
	 * @param code 国家化 code
	 * @return 国际化文本
	 */
	@GetMapping("hello")
	public String hello(@RequestParam("code") String code) {
		return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
	}

	/**
	 * 参数校验错误信息的国际化
	 *
	 * 当传递小于 10 的年龄时，查看返回的错误信息（请求头 Accept-Language 切换语言）
	 * @param age 年龄，不能小于 10
	 * @return Integer 年龄
	 */
	@GetMapping("paramValidate")
	public Integer paramValidate(@Min(value = 10, message = "{validation.ageWrong}") @RequestParam("age") Integer age) {
		return age;
	}

	/**
	 * 实体类的属性校验错误信息的国际化
	 * @param demoData 测试入参
	 * @return DemoData
	 */
	@GetMapping("bodyValidate")
	public DemoData bodyValidate(@Validated @RequestBody DemoData demoData) {
		return demoData;
	}

}
