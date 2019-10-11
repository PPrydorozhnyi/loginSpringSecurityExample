//package com.peter.auth.filters;
//
//import com.peter.auth.service.RecaptchaService;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class RecaptchaFilter extends AbstractAuthenticationProcessingFilter {
//
//    private static final String URL = "/login";
//    private static final String CAPTCHA_PARAMETER = "g-recaptcha-response";
//
//    private final RecaptchaService captchaService;
//
//    @Autowired
//    public RecaptchaFilter(RecaptchaService captchaService) {
//        super(URL);
//        this.captchaService = captchaService;
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
//        String parameter = httpServletRequest.getParameter(CAPTCHA_PARAMETER);
//        String ip = httpServletRequest.getRemoteAddr();
//        String captchaVerifyMessage =
//                captchaService.verifyRecaptcha(ip, parameter);
//
//        if (StringUtils.isNotEmpty(captchaVerifyMessage)) {
//            throw new BadCredentialsException("Enter captcha");
//        }
//        return new UsernamePasswordAuthenticationFilter().attemptAuthentication(httpServletRequest, httpServletResponse);
//    }
//
//    @Override
//    @Autowired
//    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
//        super.setAuthenticationManager(authenticationManager);
//    }
//
//}
