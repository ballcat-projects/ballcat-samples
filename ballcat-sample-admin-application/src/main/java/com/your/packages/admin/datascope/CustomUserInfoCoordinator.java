package com.your.packages.admin.datascope;

import com.hccake.ballcat.oauth.UserInfoCoordinator;
import com.hccake.ballcat.oauth.domain.UserResources;
import com.hccake.ballcat.system.model.entity.SysUser;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

@Component
public class CustomUserInfoCoordinator extends UserInfoCoordinator {

	@Override
	public UserResources coordinateResource(SysUser user, Set<String> roles, Set<String> permissions) {
		// 用户资源，角色和权限
		CustomUserResources userResources = new CustomUserResources();
		userResources.setRoles(roles);
		userResources.setPermissions(permissions);

		// 这里仅仅是示例，实际使用时一定是根据当前用户名去查询出其所拥有的资源列表
		if ("A".equals(user.getUsername())) {
			userResources.setClassList(Collections.singletonList("一班"));
		}
		else {
			userResources.setClassList(Arrays.asList("一班", "二班"));
		}

		return userResources;
	}

}