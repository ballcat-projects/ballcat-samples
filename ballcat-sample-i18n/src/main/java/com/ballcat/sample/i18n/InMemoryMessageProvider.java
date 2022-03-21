package com.ballcat.sample.i18n;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.hccake.ballcat.common.i18n.I18nMessage;
import com.hccake.ballcat.common.i18n.I18nMessageProvider;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 动态的 I18n 配置信息提供者
 *
 * spring 的国际化信息默认读取的 resource 文件中提供的，有时业务需要可以动态修改，通过提供 I18nMessageProvider，可以解决这个问题
 *
 * @author hccake
 */
@Component
public class InMemoryMessageProvider implements I18nMessageProvider {

	/**
	 * 模拟动态存储，实际开发中可以将信息存储在任意地方，如 mysql、或者 mongo、redis 等
	 */
	private static final Map<String, Map<String, String>> MAP = new HashMap<>();
	static {

		Map<String, String> zhMap = new HashMap<>();
		Map<String, String> enMap = new HashMap<>();
		zhMap.put("i18n.people", "人类");
		enMap.put("i18n.people", "People");

		zhMap.put("i18n.dog", "小狗");
		enMap.put("i18n.dog", "Dog");

		zhMap.put("i18n.cat", "猫");
		enMap.put("i18n.cat", "Cat");

		MAP.put("zh-CN", zhMap);
		MAP.put("en-US", enMap);
	}

	@Override
	public I18nMessage getI18nMessage(String code, Locale locale) {
		// 注意，这里使用的是 languageTag，请求头中 Accept-Language 的值中是下划线 zh_CN,
		// languageTag 是中划线 zh-CN
		String languageTag = locale.toLanguageTag();
		Map<String, String> map = MAP.get(languageTag);
		if (MapUtil.isEmpty(map)) {
			return null;
		}
		String message = map.get(code);
		if (StrUtil.isEmpty(message)) {
			return null;
		}

		I18nMessage i18nMessage = new I18nMessage();
		i18nMessage.setCode(code);
		i18nMessage.setLanguageTag(languageTag);
		i18nMessage.setMessage(message);
		return i18nMessage;
	}

}
