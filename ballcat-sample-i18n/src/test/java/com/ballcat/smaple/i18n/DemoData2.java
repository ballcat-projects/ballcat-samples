package com.ballcat.smaple.i18n;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
public class DemoData2 {

	// 可以多个占位符传参
	@NotNull(message = "{validation.username}：{javax.validation.constraints.NotNull.message}")
	private String username;

	// 可以使用注解上的值
	@Range(min = 0, max = 150, message = "{validation.age}：{org.hibernate.validator.constraints.Range.message}")
	private Integer age;

}