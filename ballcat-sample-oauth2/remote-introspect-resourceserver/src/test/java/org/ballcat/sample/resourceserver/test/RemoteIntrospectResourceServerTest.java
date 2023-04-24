package org.ballcat.sample.resourceserver.test;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.ballcat.sample.resourceserver.RemoteIntrospectResourceServerApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author hccake
 */
@Slf4j
@AutoConfigureMockMvc
@SpringBootTest(classes = RemoteIntrospectResourceServerApplication.class)
class RemoteIntrospectResourceServerTest {

	private final static String PRINCIPAL_INFO_PATH = "/principal";

	@Autowired
	private MockMvc mockMvc;

	private final RestTemplate restTemplate = new RestTemplate();

	@Test
	void userInfoTest() throws Exception {
		// 不登陆的时候查询用户信息, 会返回 401
		mockMvc.perform(MockMvcRequestBuilders.get(PRINCIPAL_INFO_PATH))
			.andExpect(MockMvcResultMatchers.status().is4xxClientError())
			.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(401))
			.andDo(MockMvcResultHandlers.print());

		// 请求授权服务器，获取 access_token
		String accessToken = getAccessToken();

		// 携带争取的 access_token,可以获取到信息
		mockMvc
			.perform(MockMvcRequestBuilders.get(PRINCIPAL_INFO_PATH)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value("超级管理员"))
			.andDo(MockMvcResultHandlers.print());
	}

	private String getAccessToken() {
		// 请求授权服务器，获取 access_token
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setBasicAuth("test", "test");
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("grant_type", "password");
		map.add("username", "admin");
		map.add("password", "a123456");
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		ResponseEntity<String> response = restTemplate.postForEntity("http://127.0.0.1:8110/oauth2/token", request,
				String.class);
		String responseBody = response.getBody();

		// 获取 access_token
		return JsonPath.read(responseBody, "$.access_token");
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

		// 请求授权服务器，获取 access_token
		String accessToken = getAccessToken();

		// 携带正确的 token 进行访问
		mockMvc.perform(
						MockMvcRequestBuilders.get(helloUri)
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("hello: admin"));
		// @formatter:on
	}

}
