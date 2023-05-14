package org.ballcat.sample.customuser.authorizaitonserver;

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
@SpringBootTest(classes = CustomUserAuthorizationServerApplication.class)
class CustomUserAuthorizationServerTest {

	private final static MockHttpServletRequestBuilder TOKEN_REQUEST = MockMvcRequestBuilders.post("/oauth2/token")
		.queryParam("grant_type", "password")
		.queryParam("username", "appUser1")
		.queryParam("password", "a123456")
		.with(httpBasic("test", "test"));

	@Autowired
	private MockMvc mockMvc;

	@Test
	void adminLoginTest() throws Exception {
		// 用户登录
		mockMvc.perform(TOKEN_REQUEST)
			// 添加断言
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.access_token").isNotEmpty())
			// 添加返回结果 一般在测试时候用
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void introspectTest() throws Exception {
		// 用户登录
		String result = mockMvc.perform(TOKEN_REQUEST)
			// 添加断言
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.access_token").isNotEmpty())
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
			.andExpect(MockMvcResultMatchers.jsonPath("$.sub").value("appUser1"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.attributes").isMap())
			// 添加返回结果 一般在测试时候用
			.andDo(MockMvcResultHandlers.print());
	}

}
