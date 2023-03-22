package com.your.packages.admin.sample.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hccake.ballcat.common.model.domain.PageParam;
import com.hccake.ballcat.common.model.domain.PageResult;
import com.hccake.extend.mybatis.plus.conditions.query.LambdaQueryWrapperX;
import com.hccake.extend.mybatis.plus.mapper.ExtendMapper;
import com.hccake.extend.mybatis.plus.toolkit.WrappersX;
import com.your.packages.admin.sample.model.entity.Document;
import com.your.packages.admin.sample.model.qo.DocumentQO;
import com.your.packages.admin.sample.model.vo.DocumentPageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文档表，用于演示数据权限
 *
 * @author hccake 2021-09-22 19:22:44
 */
public interface DocumentMapper extends ExtendMapper<Document> {

	/**
	 * 分页查询
	 * @param pageParam 分页参数
	 * @param qo 查询参数
	 * @return PageResult<DocumentPageVO> VO分页数据
	 */
	default PageResult<DocumentPageVO> queryPage(PageParam pageParam, DocumentQO qo) {
		IPage<Document> page = this.prodPage(pageParam);
		LambdaQueryWrapperX<Document> wrapper = WrappersX.lambdaQueryX(Document.class);
		List<DocumentPageVO> records = this.selectPageVO(page, wrapper);
		return new PageResult<>(records, page.getTotal());
	}

	/**
	 * 分页查询
	 * @param page 分页数据
	 * @param wrapper 条件构造器
	 * @return List<DocumentPageVO>
	 */
	List<DocumentPageVO> selectPageVO(IPage<Document> page, @Param(Constants.WRAPPER) Wrapper wrapper);

	/**
	 * 更新用户的组织id
	 * @param userId 用户id
	 * @param originOrganizationId 原组织id
	 * @param currentOrganizationId 现组织id
	 */
	default void updateUserOrganizationId(Integer userId, Integer originOrganizationId, Integer currentOrganizationId) {
		LambdaUpdateWrapper<Document> wrapper = Wrappers.lambdaUpdate(Document.class)
			.set(Document::getOrganizationId, currentOrganizationId)
			.eq(Document::getUserId, userId)
			.eq(Document::getOrganizationId, originOrganizationId);
		this.update(null, wrapper);
	}

}