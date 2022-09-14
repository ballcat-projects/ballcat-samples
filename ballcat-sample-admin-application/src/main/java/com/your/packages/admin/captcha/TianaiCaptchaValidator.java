package com.your.packages.admin.captcha;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.hccake.ballcat.auth.filter.captcha.CaptchaValidator;
import com.hccake.ballcat.auth.filter.captcha.domain.CaptchaResponse;

import cloud.tianai.captcha.spring.application.ImageCaptchaApplication;
import cloud.tianai.captcha.spring.plugins.secondary.SecondaryVerificationApplication;
import lombok.RequiredArgsConstructor;

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