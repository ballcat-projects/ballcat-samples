package com.your.packages.admin.datascope;

import com.hccake.ballcat.common.datascope.DataScope;
import com.hccake.ballcat.common.security.userdetails.User;
import com.hccake.ballcat.common.security.util.SecurityUtils;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.schema.Column;

import java.util.*;
import java.util.stream.Collectors;

public class CustomDataScope implements DataScope {

	// 列名
	private static final String CLASS = "class";

	@Override
	public String getResource() {
		return CLASS;
	}

	@Override
	public Collection<String> getTableNames() {
		Set<String> tableNames = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
		tableNames.addAll(Collections.singletonList("tbl_student"));
		return tableNames;
	}

	@Override
	public Expression getExpression(String tableName, Alias tableAlias) {
		// 获取当前登录用户
		User user = SecurityUtils.getUser();
		if (user == null) {
			return null;
		}
		// 获取用户拥有的班级列表
		Map<String, Object> attributes = user.getAttributes();
		@SuppressWarnings("unchecked")
		List<String> classList = (List<String>) attributes.get("classList");
		List<Expression> list = classList.stream().map(x -> new StringValue(String.valueOf(x)))
				.collect(Collectors.toList());

		// 列对象
		Column column = new Column(tableAlias == null ? CLASS : tableAlias.getName() + "." + CLASS);
		// 数据权限规则，where class in ("一班"，"二班")
		ExpressionList expressionList = new ExpressionList();
		expressionList.setExpressions(list);
		return new InExpression(column, expressionList);
	}

}