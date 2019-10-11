package com.peter.auth.facades;

import com.peter.auth.service.RecaptchaService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecaptchaFacade {

    private final RecaptchaService captchaService;

    public void validateCaptcha(HttpServletRequest request, String recaptchaResponse, BindingResult bindingResult) {

        String ip = request.getRemoteAddr();
        String captchaVerifyMessage =
                captchaService.verifyRecaptcha(ip, recaptchaResponse);

        if (StringUtils.isNotEmpty(captchaVerifyMessage)) {
            bindingResult.addError(new ObjectError("recaptcha", captchaVerifyMessage));
        }

    }

}
