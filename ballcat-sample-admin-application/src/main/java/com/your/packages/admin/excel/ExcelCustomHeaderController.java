package com.your.packages.admin.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.hccake.common.excel.annotation.ResponseExcel;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author hccake
 */
@Controller
@RequestMapping("public/excel")
public class ExcelCustomHeaderController {

	@ResponseExcel(name = "customHead", headGenerator = SimpleDataHeadGenerator.class)
	@GetMapping("customHead")
	public List<SimpleData> multi() {
		List<SimpleData> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			SimpleData simpleData = new SimpleData();
			simpleData.setString("str" + i);
			simpleData.setNumber(i);
			simpleData.setDate(new Date());
			simpleData.setIgnore("Ignore" + i);
			list.add(simpleData);
		}
		return list;
	}

	@Data
	public static class SimpleData {

		@ExcelProperty("字符串标题")
		private String string;

		@ExcelProperty("日期标题")
		private Date date;

		@ExcelProperty("数字标题")
		private Integer number;

		// 忽略此字段
		@ExcelIgnore
		private String ignore;

	}

}
