package com.your.packages.admin.datascope;

import com.hccake.ballcat.auth.userdetails.UserInfoCoordinator;
import com.hccake.ballcat.system.model.entity.SysUser;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class CustomUserInfoCoordinator extends UserInfoCoordinator {

	@Override
	public Map<String, Object> coordinateAttribute(SysUser sysUser, Map<String, Object> attribute) {
		// 用户资源，角色和权限
		List<String> classList;
		// 这里仅仅是示例，实际使用时一定是根据当前用户名去查询出其所拥有的资源列表
		if ("A".equals(sysUser.getUsername())) {
			classList = Collections.singletonList("一班");
		}
		else {
			classList = Arrays.asList("一班", "二班");
		}
		attribute.put("classList", classList);

		return attribute;
	}

}