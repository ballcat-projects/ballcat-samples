package com.your.packages.admin.sample.converter;

import com.your.packages.admin.sample.model.entity.Document;
import com.your.packages.admin.sample.model.vo.DocumentPageVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 文档表，用于演示数据权限模型转换器
 *
 * @author hccake 2021-09-22 19:22:44
 */
@Mapper
public interface DocumentConverter {

	DocumentConverter INSTANCE = Mappers.getMapper(DocumentConverter.class);

	/**
	 * PO 转 PageVO
	 * @param document 文档表，用于演示数据权限
	 * @return DocumentPageVO 文档表，用于演示数据权限PageVO
	 */
	DocumentPageVO poToPageVo(Document document);

}
