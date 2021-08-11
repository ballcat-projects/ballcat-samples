package com.your.packages.admin.modules.test;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author hccake
 */
@Data
public class DemoData {

	@NotNull(message = "{validation.username}：{}")
	private String username;

	@Range(min = 0, max = 150, message = "{validation.age}：{}")
	private Integer age;

}
