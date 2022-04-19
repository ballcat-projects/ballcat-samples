package com.ballcat.smaple.i18n;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
public class DemoData1 {

	@NotNull
	private String username;

	@Range(min = 0, max = 150)
	private Integer age;

}