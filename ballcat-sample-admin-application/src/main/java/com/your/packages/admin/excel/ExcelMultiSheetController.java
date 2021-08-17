package com.your.packages.admin.excel;

import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.hccake.common.excel.annotation.ResponseExcel;
import com.hccake.common.excel.annotation.Sheet;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hccake
 */
@Controller
@RequestMapping("public/excel")
public class ExcelMultiSheetController {

	@ResponseExcel(name = "不同Sheet的导出",
			sheets = { @Sheet(sheetName = "demoData", includes = { "username" }),
					@Sheet(sheetName = "testData", excludes = { "number" }) })
	@GetMapping("/different-sheet")
	public List<List> multiDifferent() {
		List<List> lists = new ArrayList<>();
		lists.add(demoDatalist());
		lists.add(testDatalist());
		return lists;
	}

	private List<DemoData> demoDatalist() {
		List<DemoData> dataList = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			DemoData data = new DemoData();
			data.setUsername("tr1" + i);
			data.setPassword("tr2" + i);
			dataList.add(data);
		}
		return dataList;
	}

	private List<TestData> testDatalist() {
		List<TestData> dataList = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			TestData data = new TestData();
			data.setStr("str" + i);
			data.setNumber(i);
			data.setLocalDateTime(LocalDateTime.now());
			dataList.add(data);
		}
		return dataList;
	}

	// 实体对象
	@Data
	public static class DemoData {

		private String username;

		private String password;

	}

	@Data
	public static class TestData {

		private String str;

		private Integer number;

		@ColumnWidth(50) // 定义宽度
		private LocalDateTime localDateTime;

	}

}
