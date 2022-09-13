package com.your.packages.admin.captcha;

import java.io.InputStream;
import java.io.ObjectInputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.hccake.ballcat.auth.filter.captcha.CaptchaValidator;
import com.hccake.ballcat.auth.filter.captcha.domain.CaptchaResponse;

import cloud.tianai.captcha.spring.application.ImageCaptchaApplication;
import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Primary
@RequiredArgsConstructor
public class TianaiCaptchaValidator implements CaptchaValidator {

  private final ImageCaptchaApplication application;

  @Override
  public CaptchaResponse validate(HttpServletRequest request) {
    String id = request.getParameter("id");
    ImageCaptchaTrack imageCaptchaTrack = null;
    try(InputStream is = request.getInputStream()){
      ObjectInputStream ois = new ObjectInputStream(is);
      imageCaptchaTrack = (ImageCaptchaTrack)ois.readObject();
      ois.close();
    }catch(Exception ex){
      log.error("read captcha validation body failed");
    }
    boolean match = application.matching(id, imageCaptchaTrack);
    CaptchaResponse captchaResponse = new CaptchaResponse();
    captchaResponse.setSuccess(match);
    return captchaResponse;
  }
}