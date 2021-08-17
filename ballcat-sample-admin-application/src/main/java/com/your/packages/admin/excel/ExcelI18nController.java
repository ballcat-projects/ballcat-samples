package com.your.packages.admin.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.hccake.common.excel.annotation.RequestExcel;
import com.hccake.common.excel.annotation.ResponseExcel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hccake
 */
@Slf4j
@Controller
@RequestMapping("public/excel")
public class ExcelI18nController {

	@ResponseExcel(name = "国际化导出", i18nHeader = true)
	@GetMapping("i18n")
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

	/**
	 * 国际化导入测试，由于头信息是占位符，所以导入时需要使用 index 进行
	 * @param list list
	 * @return 导出的数据
	 */
	@PostMapping("i18n")
	@ResponseBody
	public List<DemoData> importExcel(@RequestExcel List<DemoData> list) {
		return list;
	}

	@Data
	public static class DemoData {

		@ExcelProperty(value = "{DemoData.username}", index = 0)
		@NotNull(message = "{DemoData.username}：{}")
		private String username;

		@ExcelProperty(value = "{DemoData.age}", index = 1)
		@Range(min = 0, max = 150, message = "{DemoData.age}：{}")
		private Integer age;

	}

}
