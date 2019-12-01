package com.peter.auth.controller;

import com.peter.auth.facades.RecaptchaFacade;
import com.peter.auth.model.dto.UserDTO;
import com.peter.auth.model.entity.User;
import com.peter.auth.service.SecurityService;
import com.peter.auth.service.UserService;
import com.peter.auth.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IteratorUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private static final String AUTHORIZATION_REQUEST_BASE_URI
            = "oauth2/authorization";
    private static final String CAPTCHA_PARAMETER = "g-recaptcha-response";

    private final ClientRegistrationRepository clientRegistrationRepository;
    private final UserService userService;
    private final SecurityService securityService;
    private final UserValidator userValidator;
    private final RecaptchaFacade recaptchaFacade;
    private final ModelMapper mapper;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new UserDTO());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") UserDTO userForm, BindingResult bindingResult,
                               HttpServletRequest request, Model model) {

        String parameter = request.getParameter(CAPTCHA_PARAMETER);
        recaptchaFacade.validateCaptcha(request, parameter, bindingResult);
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("captcha", "Please complete captcha");
            return "registration";
        }

        userService.save(mapper.map(userForm, User.class));

        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        getLoginPageWithOAuth2(model);

        return "login";
    }

    private String getLoginPageWithOAuth2(Model model) {
        Map<String, String> oauth2AuthenticationUrls = new HashMap<>();

        Iterable<ClientRegistration> clientRegistrations = IteratorUtils::emptyIterator;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
                .as(Iterable.class);
        if (type != ResolvableType.NONE &&
                ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }

        clientRegistrations.forEach(registration ->
                oauth2AuthenticationUrls.put(registration.getClientName(),
                        AUTHORIZATION_REQUEST_BASE_URI + "/" + registration.getRegistrationId()));
        model.addAttribute("urls", oauth2AuthenticationUrls);

        return "oauth_login";
    }

    @GetMapping("/adminPage")
    public String admin(Model model) {
        return "admin";
    }

    @GetMapping({"/", "/welcome"})
    public String welcome(Model model, OAuth2AuthenticationToken authentication) {
        model.addAttribute("username", authentication.getPrincipal()
                .getAttributes().getOrDefault("name", "User"));
        return "welcome";
    }
}
