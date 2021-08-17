package com.your.packages.admin.excel;

import com.hccake.common.excel.head.HeadGenerator;
import com.hccake.common.excel.head.HeadMeta;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * 自定义头生成器
 *
 * @author hccake
 */
@Component
public class SimpleDataHeadGenerator implements HeadGenerator {

	@Override
	public HeadMeta head(Class<?> clazz) {
		HeadMeta headMeta = new HeadMeta();
		headMeta.setHead(simpleDataHead());
		// 排除 number 属性
		headMeta.setIgnoreHeadFields(new HashSet<>(Collections.singletonList("number")));
		return headMeta;
	}

	private List<List<String>> simpleDataHead() {
		List<List<String>> list = new ArrayList<>();
		List<String> head0 = new ArrayList<>();
		head0.add("自定义字符串标题" + System.currentTimeMillis());
		List<String> head1 = new ArrayList<>();
		head1.add("自定义日期标题" + System.currentTimeMillis());
		list.add(head0);
		list.add(head1);
		return list;
	}

}