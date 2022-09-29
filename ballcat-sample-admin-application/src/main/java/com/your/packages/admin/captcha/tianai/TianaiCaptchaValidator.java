package com.your.packages.admin.captcha.tianai;

import cloud.tianai.captcha.spring.application.ImageCaptchaApplication;
import cloud.tianai.captcha.spring.plugins.secondary.SecondaryVerificationApplication;
import com.hccake.ballcat.common.core.captcah.CaptchaResponse;
import com.hccake.ballcat.common.core.captcah.CaptchaValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Primary
@RequiredArgsConstructor
public class TianaiCaptchaValidator implements CaptchaValidator {

  private final ImageCaptchaApplication sca;

  @Override
  public CaptchaResponse validate(HttpServletRequest request) {
    String id = request.getParameter("captchaId");
    boolean match = false;
    if(sca instanceof SecondaryVerificationApplication){
       match = ((SecondaryVerificationApplication) sca).secondaryVerification(id);
    }
    CaptchaResponse captchaResponse = new CaptchaResponse();
    captchaResponse.setSuccess(match);
    return captchaResponse;
  }
}