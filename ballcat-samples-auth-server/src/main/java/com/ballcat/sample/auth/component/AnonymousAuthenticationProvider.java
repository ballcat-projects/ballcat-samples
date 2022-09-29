package com.ballcat.sample.auth.component;

import cn.hutool.core.collection.CollectionUtil;
import com.hccake.ballcat.common.core.util.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.ballcat.springsecurity.oauth2.server.resource.properties.OAuth2ResourceServerProperties;
import org.springframework.http.server.PathContainer;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
public class AnonymousAuthenticationProvider implements AuthenticationProvider {

	private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource;

	private final String key;

	private final Object principal;

	private final List<GrantedAuthority> authorities;

	private final List<PathPattern> ignorePathPatterns;

	public AnonymousAuthenticationProvider(OAuth2ResourceServerProperties resourceServerProperties) {
		List<String> ignoreUrls = resourceServerProperties.getIgnoreUrls();
		if (CollectionUtil.isNotEmpty(ignoreUrls)) {
			ignorePathPatterns = ignoreUrls.stream().map(PathPatternParser.defaultInstance::parse)
					.collect(Collectors.toList());
		}
		else {
			ignorePathPatterns = new ArrayList<>();
		}

		this.authenticationDetailsSource = new WebAuthenticationDetailsSource();
		this.key = UUID.randomUUID().toString();
		this.principal = "anonymousUser";
		this.authorities = AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS");
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		HttpServletRequest request = WebUtils.getRequest();
		String requestURI = request.getRequestURI();
		PathContainer pathContainer = PathContainer.parsePath(requestURI);

		boolean anyMatch = ignorePathPatterns.stream().anyMatch(x -> x.matches(pathContainer));
		if (anyMatch) {
			Authentication anonymousAuthentication = createAuthentication(request);
			log.debug("Set SecurityContextHolder to anonymous SecurityContext");
			return anonymousAuthentication;
		}

		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

	protected Authentication createAuthentication(HttpServletRequest request) {
		AnonymousAuthenticationToken token = new AnonymousAuthenticationToken(this.key, this.principal,
				this.authorities);
		token.setDetails(this.authenticationDetailsSource.buildDetails(request));
		return token;
	}

}
