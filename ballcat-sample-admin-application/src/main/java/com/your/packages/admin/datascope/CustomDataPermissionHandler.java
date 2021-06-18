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
		return false;
	}

}
