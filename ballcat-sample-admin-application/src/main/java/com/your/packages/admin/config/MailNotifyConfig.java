package com.your.packages.admin.config;

import com.hccake.ballcat.common.mail.sender.MailSender;
import com.hccake.ballcat.notify.push.MailNotifyPusher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hccake
 */
@Configuration(proxyBeanMethods = false)
public class MailNotifyConfig {

	@Bean
	@ConditionalOnBean(MailSender.class)
	public MailNotifyPusher mailNotifyPusher(MailSender mailSender) {
		return new MailNotifyPusher(mailSender);
	}

}
