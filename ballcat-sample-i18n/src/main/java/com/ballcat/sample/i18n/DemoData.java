package com.ballcat.sample.i18n;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
public class DemoData {

	@NotNull(message = "{DemoData.username}：{}")
	private String username;

	@Range(min = 0, max = 150, message = "{DemoData.age}：{}")
	private Integer age;

}