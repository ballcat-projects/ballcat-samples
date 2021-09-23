package com.your.packages.admin.datascope;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.hccake.ballcat.system.model.entity.SysOrganization;
import com.hccake.ballcat.system.model.entity.SysRole;
import com.hccake.ballcat.system.model.entity.SysUser;
import com.hccake.ballcat.system.service.SysOrganizationService;
import com.hccake.ballcat.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author hccake
 */
@Component
@RequiredArgsConstructor
public class SampleDataScopeProcessor implements DataScopeProcessor {

	private final SysOrganizationService sysOrganizationService;

	private final SysUserService sysUserService;

	/**
	 * 合并角色的数据权限类型，排除相同的权限后，大的权限覆盖小的
	 * @param user 用户
	 * @param roles 角色列表
	 * @return List<Integer> 合并后的权限
	 */
	@Override
	public UserDataScope mergeScopeType(SysUser user, Collection<SysRole> roles) {
		UserDataScope userDataScope = new UserDataScope();
		Set<Integer> scopeUserIds = userDataScope.getScopeUserIds();
		Set<Integer> scopeOrganizationId = userDataScope.getScopeDeptIds();

		// 任何用户都应该可以看到自己的数据
		Integer userId = user.getUserId();
		scopeUserIds.add(userId);

		if (CollectionUtil.isEmpty(roles)) {
			return userDataScope;
		}

		// 根据角色的权限返回进行分组
		Map<Integer, List<SysRole>> map = roles.stream().collect(Collectors.groupingBy(SysRole::getScopeType));

		// 如果有全部权限，直接返回
		if (map.containsKey(DataScopeTypeEnum.ALL.getType())) {
			userDataScope.setAllScope(true);
			return userDataScope;
		}

		// 如果有本级及子级，删除其包含的几类数据权限
		boolean hasLevelChildLevel = map.containsKey(DataScopeTypeEnum.LEVEL_CHILD_LEVEL.getType());
		if (hasLevelChildLevel) {
			map.remove(DataScopeTypeEnum.SELF.getType());
			map.remove(DataScopeTypeEnum.SELF_CHILD_LEVEL.getType());
			map.remove(DataScopeTypeEnum.LEVEL.getType());
		}

		// 是否有本人及子级权限
		boolean hasSelfChildLevel = map.containsKey(DataScopeTypeEnum.SELF_CHILD_LEVEL.getType());
		// 是否有本级权限
		boolean hasLevel = map.containsKey(DataScopeTypeEnum.LEVEL.getType());
		if (hasSelfChildLevel || hasLevel) {
			// 如果有本人及子级或者本级，都删除本人的数据权限
			map.remove(DataScopeTypeEnum.SELF.getType());
			// 如果同时拥有，则等于本级及子级权限
			if (hasSelfChildLevel && hasLevel) {
				map.remove(DataScopeTypeEnum.SELF_CHILD_LEVEL.getType());
				map.remove(DataScopeTypeEnum.LEVEL.getType());
				map.put(DataScopeTypeEnum.LEVEL_CHILD_LEVEL.getType(), new ArrayList<>());
			}
		}

		// 这时如果仅仅只能看个人的，直接返回
		if (map.size() == 1 && map.containsKey(DataScopeTypeEnum.SELF.getType())) {
			userDataScope.setOnlySelf(true);
			return userDataScope;
		}

		// 如果有 本级及子级 或者 本级，都把自己的 organizationId 加进去
		Integer organizationId = user.getOrganizationId();
		if (hasLevelChildLevel || hasLevel) {
			scopeOrganizationId.add(organizationId);
		}
		// 如果有 本级及子级 或者 本人及子级，都把下级组织的 organizationId 加进去
		if (hasLevelChildLevel || hasSelfChildLevel) {
			List<SysOrganization> childOrganizations = sysOrganizationService.listChildOrganization(organizationId);
			if (CollectionUtil.isNotEmpty(childOrganizations)) {
				List<Integer> organizationIds = childOrganizations.stream().map(SysOrganization::getId)
						.collect(Collectors.toList());
				scopeOrganizationId.addAll(organizationIds);
			}
		}
		// 自定义部门
		List<SysRole> sysRoles = map.get(DataScopeTypeEnum.CUSTOM.getType());
		if (CollectionUtil.isNotEmpty(sysRoles)) {
			Set<Integer> customDeptIds = sysRoles.stream().map(SysRole::getScopeResources).filter(Objects::nonNull)
					.flatMap(x -> Arrays.stream(x.split(StrUtil.COMMA))).map(Integer::parseInt)
					.collect(Collectors.toSet());
			scopeOrganizationId.addAll(customDeptIds);
		}

		// 把部门对应的用户id都放入集合中
		if (CollectionUtil.isNotEmpty(scopeOrganizationId)) {
			List<SysUser> sysUserList = sysUserService.listByOrganizationIds(scopeOrganizationId);
			if (CollectionUtil.isNotEmpty(sysUserList)) {
				List<Integer> userIds = sysUserList.stream().map(SysUser::getUserId).collect(Collectors.toList());
				scopeUserIds.addAll(userIds);
			}
		}

		return userDataScope;
	}

}
