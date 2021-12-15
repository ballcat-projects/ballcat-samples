package com.your.packages.admin.sample.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文档表，用于演示数据权限分页视图对象
 *
 * @author hccake 2021-09-22 19:22:44
 */
@Data
@Schema(title = "用户文档分页视图对象")
public class DocumentPageVO {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@Schema(title = "ID")
	private Integer id;

	/**
	 * 文档名称
	 */
	@Schema(title = "文档名称")
	private String name;

	/**
	 * 所属用户ID
	 */
	@Schema(title = "所属用户ID")
	private Integer userId;

	/**
	 * 用户名
	 */
	@Schema(title = "用户名")
	private String username;

	/**
	 * 所属组织ID
	 */
	@Schema(title = "所属组织ID")
	private Integer organizationId;

	/**
	 * 组织名
	 */
	@Schema(title = "组织名")
	private String organizationName;

	/**
	 * 创建时间
	 */
	@Schema(title = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@Schema(title = "更新时间")
	private LocalDateTime updateTime;

}