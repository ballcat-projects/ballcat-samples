package com.your.packages.admin.oauth2.jackson2;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.your.packages.admin.datascope.UserDataScope;

import java.io.IOException;
import java.util.Set;

/**
 * 自定义的 UserDataScope jackson 反序列化器
 *
 * @author hccake
 */
public class UserDataScopeDeserializer extends JsonDeserializer<UserDataScope> {

	private static final TypeReference<Set<Integer>> INTEGER_SET = new TypeReference<Set<Integer>>() {
	};

	@Override
	public UserDataScope deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
		ObjectMapper mapper = (ObjectMapper) jp.getCodec();
		JsonNode jsonNode = mapper.readTree(jp);

		boolean allScope = readJsonNode(jsonNode, "allScope").asBoolean();
		boolean onlySelf = readJsonNode(jsonNode, "onlySelf").asBoolean();

		Set<Integer> scopeUserIds = mapper.convertValue(jsonNode.get("scopeUserIds"), INTEGER_SET);
		Set<Integer> scopeDeptIds = mapper.convertValue(jsonNode.get("scopeDeptIds"), INTEGER_SET);

		UserDataScope userDataScope = new UserDataScope();
		userDataScope.setAllScope(allScope);
		userDataScope.setOnlySelf(onlySelf);
		userDataScope.setScopeUserIds(scopeUserIds);
		userDataScope.setScopeDeptIds(scopeDeptIds);

		return userDataScope;
	}

	private JsonNode readJsonNode(JsonNode jsonNode, String field) {
		return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
	}

}
