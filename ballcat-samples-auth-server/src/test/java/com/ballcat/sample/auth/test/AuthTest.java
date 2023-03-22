package com.ballcat.sample.auth.test;

import com.ballcat.sample.auth.AuthServerApplication;
import com.hccake.ballcat.common.util.JsonUtils;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

/**
 * @author hccake
 */
@Slf4j
@AutoConfigureMockMvc
@SpringBootTest(classes = AuthServerApplication.class)
public class AuthTest {

	private final static String LOGIN_PATH = "/oauth/token";

	private final static String USER_INFO_PATH = "/user-info";

	@Autowired
	private MockMvc mockMvc;

	@Test
	void adminLoginTest() throws Exception {
		// 管理后台用户登录
		MockHttpServletRequestBuilder requestBuilder = loginRequestBuilder("admin", "a123456", "admin", "admin");
		mockMvc.perform(requestBuilder)
			// 添加断言
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.access_token").isNotEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.info.nickname").value("超级管理员"))
			// 添加返回结果 一般在测试时候用
			.andDo(MockMvcResultHandlers.print());

	}

	@Test
	void appLoginTest() throws Exception {
		// 客户端用户登录
		MockHttpServletRequestBuilder requestBuilder = loginRequestBuilder("appUser1", "a123456", "app", "app");
		mockMvc.perform(requestBuilder)
			// 添加断言
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.access_token").isNotEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.info.nickname").value("app用户1"))
			// 添加返回结果 一般在测试时候用
			.andDo(MockMvcResultHandlers.print());

	}

	@Test
	void userInfoTest() throws Exception {
		// 不登陆的时候查询用户信息, 会返回 401
		mockMvc.perform(MockMvcRequestBuilders.get(USER_INFO_PATH))
			.andExpect(MockMvcResultMatchers.status().is4xxClientError())
			.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(401))
			.andDo(MockMvcResultHandlers.print());

		MockHttpServletRequestBuilder requestBuilder = loginRequestBuilder("appUser1", "a123456", "app", "app");

		// 登录
		String loginResult = mockMvc.perform(requestBuilder)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.access_token").isNotEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.info.nickname").value("app用户1"))
			.andDo(MockMvcResultHandlers.print())
			.andReturn()
			.getResponse()
			.getContentAsString();

		HashMap map = JsonUtils.toObj(loginResult, HashMap.class);
		String accessToken = (String) map.get("access_token");

		// 登录后再查询,可以获取到信息
		mockMvc
			.perform(MockMvcRequestBuilders.get(USER_INFO_PATH)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value("app用户1"))
			.andDo(MockMvcResultHandlers.print());
	}

	private MockHttpServletRequestBuilder loginRequestBuilder(String username, String password, String clientId,
			String clientSecret) {
		return MockMvcRequestBuilders.post(LOGIN_PATH)
			.queryParam("grant_type", "password")
			.queryParam("username", username)
			.queryParam("password", password)
			.with(httpBasic(clientId, clientSecret));
	}

	@Test
	void anonymousTest() throws Exception {

		String helloUri = "/public/hello";

		// @formatter:off
		// 匿名访问
		mockMvc.perform(MockMvcRequestBuilders.get(helloUri))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("hello: anonymousUser"));

		// 携带错误的 token 进行访问
		mockMvc.perform(
						MockMvcRequestBuilders.get(helloUri)
								.header(HttpHeaders.AUTHORIZATION, "Bearer errorToken"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("hello: anonymousUser"));


		// 登陆后进行访问
		MockHttpServletRequestBuilder requestBuilder = loginRequestBuilder("admin", "a123456", "admin", "admin");
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

		String contentAsString = mvcResult.getResponse().getContentAsString();
		String accessToken = JsonPath.read(contentAsString, "$.access_token");

		mockMvc.perform(
				MockMvcRequestBuilders.get(helloUri)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("hello: admin"));
		// @formatter:on
	}

}
