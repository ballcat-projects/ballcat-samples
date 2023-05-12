package org.ballcat.sample.excel;

import com.hccake.common.excel.annotation.ResponseExcel;
import com.hccake.common.excel.annotation.Sheet;
import com.hccake.common.excel.head.EmptyHeadGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hccake
 */
@RestController
public class TestController {

	@ResponseExcel(name = "多sheet测试1")
	@GetMapping("/e1")
	public List<List<DemoData>> e1() {
		List<List<DemoData>> lists = new ArrayList<>();
		lists.add(list());
		lists.add(list());
		return lists;
	}

	@ResponseExcel(name = "多sheet测试2", sheets = { @Sheet(sheetName = "第一个Sheet"), @Sheet(sheetName = "第二个sheet") })
	@GetMapping("/e2")
	public List<List<DemoData>> e2() {
		List<List<DemoData>> lists = new ArrayList<>();
		lists.add(list());
		lists.add(list());
		return lists;
	}

	@ResponseExcel(name = "多sheet测试3",
			sheets = { @Sheet(sheetName = "第一个Sheet"), @Sheet(sheetName = "第二个sheet"), @Sheet(sheetName = "第三个sheet") })
	@GetMapping("/e3")
	public List<List<DemoData>> e3() {
		List<List<DemoData>> lists = new ArrayList<>();
		lists.add(list());
		lists.add(list());
		return lists;
	}

	@ResponseExcel(name = "中文导出 测试")
	@GetMapping("/e4")
	public List<DemoData> e4() {
		return list();
	}

	@ResponseExcel(name = "模板导出测试", template = "template.xlsx", headGenerator = EmptyHeadGenerator.class)
	@GetMapping("/e5")
	public List<DemoData> e5() {
		return list();
	}

	@ResponseExcel(name = "模板填充测试", template = "fill-template.xlsx", fill = true)
	@GetMapping("/e6")
	public List<DemoData> e6() {
		return list();
	}

	private List<DemoData> list() {
		List<DemoData> dataList = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			DemoData data = new DemoData();
			data.setUsername("tr1" + i);
			data.setPassword("tr2" + i);
			dataList.add(data);
		}
		return dataList;
	}

}
