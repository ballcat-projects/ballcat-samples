package org.ballcat.sample.authorizationserver;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

/**
 * @author hccake
 */
@Slf4j
@AutoConfigureMockMvc
@SpringBootTest(classes = DefaultAuthorizationServerApplication.class)
class DefaultAuthorizationServerTest {

	private final static MockHttpServletRequestBuilder ADMIN_TOKEN_REQUEST = MockMvcRequestBuilders
		.post("/oauth2/token")
		.queryParam("grant_type", "password")
		.queryParam("username", "admin")
		.queryParam("password", "a123456")
		.with(httpBasic("test", "test"));

	private final static MockHttpServletRequestBuilder APP_TOKEN_REQUEST = MockMvcRequestBuilders.post("/oauth2/token")
		.queryParam("grant_type", "password")
		.queryParam("username", "user1")
		.queryParam("password", "a123456")
		.with(httpBasic("app", "app"));

	@Autowired
	private MockMvc mockMvc;

	@Test
	void adminLoginTest() throws Exception {
		// 管理后台用户登录
		mockMvc.perform(ADMIN_TOKEN_REQUEST)
			// 添加断言
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.access_token").isNotEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value("超级管理员"))
			// 添加返回结果 一般在测试时候用
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void appLoginTest() throws Exception {
		// app用户登录
		mockMvc.perform(APP_TOKEN_REQUEST)
			// 添加断言
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.access_token").isNotEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value("C端用户1"))
			// 添加返回结果 一般在测试时候用
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void adminIntrospectTest() throws Exception {
		// 管理后台用户登录
		String result = mockMvc.perform(ADMIN_TOKEN_REQUEST)
			// 添加断言
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.access_token").isNotEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value("超级管理员"))
			// 添加返回结果 一般在测试时候用
			.andDo(MockMvcResultHandlers.print())
			.andReturn()
			.getResponse()
			.getContentAsString();

		// 获取 access_token
		String accessToken = JsonPath.read(result, "$.access_token");

		// 自省请求
		MockHttpServletRequestBuilder introspectRequest = MockMvcRequestBuilders.post("/oauth2/introspect")
			.queryParam("token", accessToken)
			.with(httpBasic("test", "test"));

		mockMvc.perform(introspectRequest)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.active").value("true"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.sub").value("admin"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.attributes").isMap())
			.andExpect(MockMvcResultMatchers.jsonPath("$.info.nickname").value("超级管理员"))
			// 添加返回结果 一般在测试时候用
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void appIntrospectTest() throws Exception {
		// 管理后台用户登录
		String result = mockMvc.perform(APP_TOKEN_REQUEST)
			// 添加断言
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.access_token").isNotEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value("C端用户1"))
			// 添加返回结果 一般在测试时候用
			.andDo(MockMvcResultHandlers.print())
			.andReturn()
			.getResponse()
			.getContentAsString();

		// 获取 access_token
		String accessToken = JsonPath.read(result, "$.access_token");

		// 自省请求
		MockHttpServletRequestBuilder introspectRequest = MockMvcRequestBuilders.post("/oauth2/introspect")
			.queryParam("token", accessToken)
			.with(httpBasic("app", "app"));

		mockMvc.perform(introspectRequest)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.active").value("true"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.sub").value("user1"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.attributes").isMap())
			.andExpect(MockMvcResultMatchers.jsonPath("$.info.nickname").value("C端用户1"))
			// 添加返回结果 一般在测试时候用
			.andDo(MockMvcResultHandlers.print());
	}

}
