package com.your.packages.admin.sample.service.impl;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.hccake.ballcat.common.model.domain.PageParam;
import com.hccake.ballcat.common.model.domain.PageResult;
import com.hccake.ballcat.system.model.entity.SysUser;
import com.hccake.ballcat.system.service.SysUserService;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import com.your.packages.admin.sample.mapper.DocumentMapper;
import com.your.packages.admin.sample.model.entity.Document;
import com.your.packages.admin.sample.model.qo.DocumentQO;
import com.your.packages.admin.sample.model.vo.DocumentPageVO;
import com.your.packages.admin.sample.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 文档表，用于演示数据权限
 *
 * @author hccake 2021-09-22 19:22:44
 */
@Service
@RequiredArgsConstructor
public class DocumentServiceImpl extends ExtendServiceImpl<DocumentMapper, Document> implements DocumentService {

	private final SysUserService sysUserService;

	/**
	 * 根据QueryObeject查询分页数据
	 * @param pageParam 分页参数
	 * @param qo 查询参数对象
	 * @return PageResult<DocumentPageVO> 分页数据
	 */
	@Override
	public PageResult<DocumentPageVO> queryPage(PageParam pageParam, DocumentQO qo) {
		return baseMapper.queryPage(pageParam, qo);
	}

	@Override
	public void updateUserOrganizationId(Long userId, Long originOrganizationId, Long currentOrganizationId) {
		baseMapper.updateUserOrganizationId(userId, originOrganizationId, currentOrganizationId);
	}

	/**
	 * 插入一条记录（选择字段，策略插入）
	 * @param document 实体对象
	 */
	@Override
	public boolean save(Document document) {
		SysUser sysUser = sysUserService.getById(document.getUserId());
		document.setOrganizationId(sysUser.getOrganizationId());
		return SqlHelper.retBool(baseMapper.insert(document));
	}

}
