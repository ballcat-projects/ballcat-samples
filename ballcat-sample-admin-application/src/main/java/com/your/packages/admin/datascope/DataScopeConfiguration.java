package com.your.packages.admin.datascope;

import com.hccake.ballcat.common.datascope.DataScope;
import com.hccake.ballcat.common.datascope.interceptor.DataPermissionInterceptor;
import com.hccake.ballcat.common.datascope.processor.DataScopeSqlProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration(proxyBeanMethods = false)
public class DataScopeConfiguration {

	@Bean
	public DataPermissionInterceptor dataPermissionInterceptor() {
		CustomDataScope customDataScope = new CustomDataScope();
		List<DataScope> list = new ArrayList<>();
		list.add(customDataScope);
		CustomDataPermissionHandler dataPermissionHandler = new CustomDataPermissionHandler(list);
		return new DataPermissionInterceptor(new DataScopeSqlProcessor(), dataPermissionHandler);
	}
}
