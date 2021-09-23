package com.your.packages.admin.sample.model.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 文档表，用于演示数据权限 查询对象
 *
 * @author hccake 2021-09-22 19:22:44
 */
@Data
@ApiModel(value = "文档表，用于演示数据权限查询对象")
public class DocumentQO {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@ApiModelProperty(value = "ID")
	private Integer id;

}