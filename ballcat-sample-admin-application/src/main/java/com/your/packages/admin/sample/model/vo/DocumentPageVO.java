package com.your.packages.admin.sample.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 文档表，用于演示数据权限分页视图对象
 *
 * @author hccake 2021-09-22 19:22:44
 */
@Data
@ApiModel(value = "文档表，用于演示数据权限分页视图对象")
public class DocumentPageVO {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@ApiModelProperty(value = "ID")
	private Integer id;

	/**
	 * 文档名称
	 */
	@ApiModelProperty(value = "文档名称")
	private String name;

	/**
	 * 所属用户ID
	 */
	@ApiModelProperty(value = "所属用户ID")
	private Integer userId;

	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "用户名")
	private String username;

	/**
	 * 所属组织ID
	 */
	@ApiModelProperty(value = "所属组织ID")
	private Integer organizationId;

	/**
	 * 组织名
	 */
	@ApiModelProperty(value = "组织名")
	private String organizationName;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	private LocalDateTime updateTime;

}