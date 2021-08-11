package com.your.packages.admin.datascope;

import com.hccake.ballcat.common.datascope.DataScope;
import com.hccake.ballcat.common.datascope.handler.AbstractDataPermissionHandler;

import java.util.List;

public class CustomDataPermissionHandler extends AbstractDataPermissionHandler {

	public CustomDataPermissionHandler(List<DataScope> dataScopes) {
		super(dataScopes);
	}

	@Override
	public boolean ignorePermissionControl(String mappedStatementId) {
		// 可以在这里做规则控制，跳过指定的方法，当然也可以使用 @DataPermission 注解控制
		return false;
	}

}
