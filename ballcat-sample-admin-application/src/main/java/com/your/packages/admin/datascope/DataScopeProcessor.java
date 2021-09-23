package com.your.packages.admin.datascope;

import com.hccake.ballcat.system.model.entity.SysRole;
import com.hccake.ballcat.system.model.entity.SysUser;

import java.util.Collection;

/**
 * @author hccake
 */
public interface DataScopeProcessor {

	/**
	 * 根据用户和角色信息，合并用户最终的数据权限
	 * @param user 用户
	 * @param roles 角色列表
	 * @return UserDataScope
	 */
	UserDataScope mergeScopeType(SysUser user, Collection<SysRole> roles);

}
