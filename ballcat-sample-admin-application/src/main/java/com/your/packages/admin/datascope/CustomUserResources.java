package com.your.packages.admin.datascope;

import com.hccake.ballcat.oauth.domain.DefaultUserResources;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomUserResources extends DefaultUserResources {
	/**
	 * 班级列表
	 */
	private List<String> classList;

}