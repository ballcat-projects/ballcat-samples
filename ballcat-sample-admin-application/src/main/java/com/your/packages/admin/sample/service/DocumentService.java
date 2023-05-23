package com.your.packages.admin.sample.service;

import com.your.packages.admin.sample.model.entity.Document;
import com.your.packages.admin.sample.model.vo.DocumentPageVO;
import com.your.packages.admin.sample.model.qo.DocumentQO;
import com.hccake.ballcat.common.model.domain.PageParam;
import com.hccake.ballcat.common.model.domain.PageResult;
import com.hccake.extend.mybatis.plus.service.ExtendService;

/**
 * 文档表，用于演示数据权限
 *
 * @author hccake 2021-09-22 19:22:44
 */
public interface DocumentService extends ExtendService<Document> {

	/**
	 * 根据QueryObeject查询分页数据
	 * @param pageParam 分页参数
	 * @param qo 查询参数对象
	 * @return PageResult&lt;DocumentPageVO&gt; 分页数据
	 */
	PageResult<DocumentPageVO> queryPage(PageParam pageParam, DocumentQO qo);

	/**
	 * 更新用户的组织id
	 * @param userId 用户id
	 * @param originOrganizationId 原组织id
	 * @param currentOrganizationId 现组织id
	 */
	void updateUserOrganizationId(Long userId, Long originOrganizationId, Long currentOrganizationId);

}