package org.ballcat.sample.customuser.authorizaitonserver.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.hccake.ballcat.common.security.userdetails.User;
import org.ballcat.sample.customuser.authorizaitonserver.user.AppUser;
import org.ballcat.sample.customuser.authorizaitonserver.user.AppUserDetails;
import org.ballcat.sample.customuser.authorizaitonserver.user.AppUserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.Collection;

/**
 * 自定义的 AppUserDetails jackson 反序列化器
 * <p>
 * 参考 {@link org.springframework.security.jackson2.UserDeserializer}
 *
 * @author hccake
 */
public class AppUserDetailsDeserializer extends JsonDeserializer<AppUserDetails> {

	private static final TypeReference<Collection<SimpleGrantedAuthority>> SIMPLE_GRANTED_AUTHORITY_SET = new TypeReference<Collection<SimpleGrantedAuthority>>() {
	};

	/**
	 * This method will create {@link org.springframework.security.core.userdetails.User}
	 * object. It will ensure successful object creation even if password key is null in
	 * serialized json, because credentials may be removed from the
	 * {@link org.springframework.security.core.userdetails.User} by invoking
	 * {@link org.springframework.security.core.userdetails.User#eraseCredentials()}. In
	 * that case there won't be any password key in serialized json.
	 * @param jp the JsonParser
	 * @param ctxt the DeserializationContext
	 * @return the user
	 * @throws IOException if a exception during IO occurs
	 * @throws JsonProcessingException if an error during JSON processing occurs
	 */
	@Override
	public AppUserDetails deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
		ObjectMapper mapper = (ObjectMapper) jp.getCodec();
		JsonNode jsonNode = mapper.readTree(jp);

		AppUserInfo appUserInfo = mapper.treeToValue(jsonNode.get("appUserInfo"), AppUserInfo.class);

		JsonNode passwordNode = readJsonNode(jsonNode, "password");
		String password = passwordNode.asText("");
		if (passwordNode.asText(null) == null) {
			password = null;
		}

		Collection<? extends GrantedAuthority> authorities = mapper.convertValue(jsonNode.get("authorities"),
				SIMPLE_GRANTED_AUTHORITY_SET);

		return AppUserDetails.builder().appUserInfo(appUserInfo).authorities(authorities).password(password).build();
	}

	private JsonNode readJsonNode(JsonNode jsonNode, String field) {
		return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
	}

}
