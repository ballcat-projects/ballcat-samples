package com.your.packages.admin.captcha.tianai;

import cloud.tianai.captcha.spring.application.ImageCaptchaApplication;
import cloud.tianai.captcha.spring.plugins.secondary.SecondaryVerificationApplication;
import cn.hutool.core.util.StrUtil;
import com.hccake.ballcat.common.core.captcah.CaptchaResponse;
import com.hccake.ballcat.common.core.captcah.CaptchaValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * tianai 验证码的校验器
 * @author whace
 */
@Component
@Primary
@RequiredArgsConstructor
public class TianaiCaptchaValidator implements CaptchaValidator {

	private final ImageCaptchaApplication sca;

	@Override
	public CaptchaResponse validate(HttpServletRequest request) {
		String captchaId = request.getParameter("captchaId");
		if (StrUtil.isBlank(captchaId)) {
			CaptchaResponse captchaResponse = new CaptchaResponse();
			captchaResponse.setSuccess(false);
			captchaResponse.setErrMsg("captcha id can not be null");
			return captchaResponse;
		}
		boolean match = false;
		if (sca instanceof SecondaryVerificationApplication) {
			match = ((SecondaryVerificationApplication) sca).secondaryVerification(captchaId);
		}
		CaptchaResponse captchaResponse = new CaptchaResponse();
		captchaResponse.setSuccess(match);
		return captchaResponse;
	}

}