package com.your.packages.admin.sample.controller;

import com.hccake.ballcat.common.log.operation.annotation.CreateOperationLogging;
import com.hccake.ballcat.common.log.operation.annotation.DeleteOperationLogging;
import com.hccake.ballcat.common.model.domain.PageParam;
import com.hccake.ballcat.common.model.domain.PageResult;
import com.hccake.ballcat.common.model.result.BaseResultCode;
import com.hccake.ballcat.common.model.result.R;
import com.your.packages.admin.sample.model.entity.Document;
import com.your.packages.admin.sample.model.qo.DocumentQO;
import com.your.packages.admin.sample.model.vo.DocumentPageVO;
import com.your.packages.admin.sample.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 文档表，用于演示数据权限
 *
 * @author hccake 2021-09-22 19:22:44
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/sample/document")
@Tag(name = "用户文档", description = "用于演示数据权限管理")
public class DocumentController {

	private final DocumentService documentService;

	/**
	 * 分页查询
	 * @param pageParam 分页参数
	 * @param documentQO 文档表，用于演示数据权限查询对象
	 * @return R 通用返回体
	 */
	@Operation(summary = "分页查询")
	@GetMapping("/page")
	public R<PageResult<DocumentPageVO>> getDocumentPage(@Valid PageParam pageParam, DocumentQO documentQO) {
		return R.ok(documentService.queryPage(pageParam, documentQO));
	}

	/**
	 * 新增文档表，用于演示数据权限
	 * @param document 文档表，用于演示数据权限
	 * @return R 通用返回体
	 */
	@Operation(summary = "新增用户文档")
	@CreateOperationLogging(msg = "新增文档表，用于演示数据权限")
	@PostMapping
	public R<Void> save(@RequestBody Document document) {
		return documentService.save(document) ? R.ok()
				: R.failed(BaseResultCode.UPDATE_DATABASE_ERROR, "新增文档表，用于演示数据权限失败");
	}

	/**
	 * 通过id删除文档表，用于演示数据权限
	 * @param id id
	 * @return R 通用返回体
	 */
	@Operation(summary = "通过id删除用户文档")
	@DeleteOperationLogging(msg = "通过id删除文档表，用于演示数据权限")
	@DeleteMapping("/{id}")
	public R<Void> removeById(@PathVariable("id") Integer id) {
		return documentService.removeById(id) ? R.ok()
				: R.failed(BaseResultCode.UPDATE_DATABASE_ERROR, "通过id删除文档表，用于演示数据权限失败");
	}

}