package com.your.packages.admin.modules.test;

import com.hccake.common.excel.annotation.ResponseExcel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

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

	@ResponseExcel(name = "demo", i18nHeader = true)
	@GetMapping("excelExport")
	public List<DemoData> excelExport() {
		List<DemoData> list = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			DemoData demoData = new DemoData();
			demoData.setUsername("username:" + i);
			demoData.setAge(i);
			list.add(demoData);
		}

		return list;
	}

}
