package com.ballcat.smaple.i18n;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
public class DemoData3 {

	// 可以多个占位符传参
	@NotNull(message = "{validation.username}：{}")
	private String username;

	// 可以使用注解上的值
	@Range(min = 0, max = 150, message = "{validation.age}：{}")
	private Integer age;

}