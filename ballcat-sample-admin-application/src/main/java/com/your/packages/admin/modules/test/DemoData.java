package com.your.packages.admin.modules.test;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author hccake
 */
@Data
public class DemoData {

	@ExcelProperty(value = "{DemoData.username}（{loginName}）", index = 0)
	@NotNull(message = "{DemoData.username}：{}")
	private String username;

	@ExcelProperty(value = "年龄", index = 1)
	@Range(min = 0, max = 150, message = "{DemoData.age}：{}")
	private Integer age;

}
