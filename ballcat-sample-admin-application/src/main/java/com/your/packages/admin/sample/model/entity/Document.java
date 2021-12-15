package com.your.packages.admin.sample.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hccake.extend.mybatis.plus.alias.TableAlias;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文档表，用于演示数据权限
 *
 * @author hccake 2021-09-22 19:22:44
 */
@TableAlias("t")
@Data
@TableName("sample_document")
@Schema(title = "用户文档")
public class Document {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
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
	 * 所属组织ID
	 */
	@Schema(title = "所属组织ID")
	private Integer organizationId;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	@Schema(title = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@Schema(title = "更新时间")
	private LocalDateTime updateTime;

}
