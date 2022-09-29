package com.ballcat.sample.auth.component;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ReflectUtil;
import org.ballcat.springsecurity.oauth2.server.resource.ResourceServerWebSecurityConfigurerAdapter;
import org.ballcat.springsecurity.oauth2.server.resource.properties.OAuth2ResourceServerProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 添加 {@link AnonymousAuthenticationProvider} 到 provider 的最后，对于忽略的访问权限控制的接口支持匿名访问
 *
 * @author hccake
 */
@Component
public class SupportAnonymousBeanPostProcessor implements BeanPostProcessor {

	private final Field providersField = ReflectUtil.getField(ResourceServerWebSecurityConfigurerAdapter.class,
			"authenticationProviders");

	private final OAuth2ResourceServerProperties resourceServerProperties;

	public SupportAnonymousBeanPostProcessor(OAuth2ResourceServerProperties resourceServerProperties) {
		this.resourceServerProperties = resourceServerProperties;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof ResourceServerWebSecurityConfigurerAdapter) {
			Object fieldValue = ReflectUtil.getFieldValue(bean, providersField);
			AnonymousAuthenticationProvider anonymousAuthenticationProvider = new AnonymousAuthenticationProvider(
					resourceServerProperties);
			if (fieldValue != null) {
				List<AuthenticationProvider> list = (List<AuthenticationProvider>) fieldValue;
				list.add(anonymousAuthenticationProvider);
			}
			else {
				ReflectUtil.setFieldValue(bean, providersField, ListUtil.toList(anonymousAuthenticationProvider));
			}
		}
		return bean;
	}

}
