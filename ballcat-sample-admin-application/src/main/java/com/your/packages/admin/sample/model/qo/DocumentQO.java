package com.your.packages.admin.sample.model.qo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springdoc.api.annotations.ParameterObject;

/**
 * 文档表，用于演示数据权限 查询对象
 *
 * @author hccake 2021-09-22 19:22:44
 */
@Data
@Schema(title = "用户文档查询对象")
@ParameterObject
public class DocumentQO {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@Schema(title = "ID")
	private Integer id;

}