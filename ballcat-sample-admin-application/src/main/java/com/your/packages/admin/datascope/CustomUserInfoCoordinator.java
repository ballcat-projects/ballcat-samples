package com.your.packages.admin.datascope;

import com.hccake.ballcat.common.security.constant.UserAttributeNameConstants;
import com.hccake.ballcat.system.authentication.UserInfoCoordinator;
import com.hccake.ballcat.system.model.dto.UserInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author hccake
 */
@Component
@RequiredArgsConstructor
public class CustomUserInfoCoordinator implements UserInfoCoordinator {

	private final DataScopeProcessor dataScopeProcessor;

	@Override
	public Map<String, Object> coordinateAttribute(UserInfoDTO userInfoDTO, Map<String, Object> attribute) {
		// 数据权限填充
		UserDataScope userDataScope = dataScopeProcessor.mergeScopeType(userInfoDTO.getSysUser(),
				userInfoDTO.getRoles());
		attribute.put(UserAttributeNameConstants.USER_DATA_SCOPE, userDataScope);
		return attribute;
	}

}