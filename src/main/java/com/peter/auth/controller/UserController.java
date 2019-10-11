package com.peter.auth.controller;

import com.peter.auth.facades.RecaptchaFacade;
import com.peter.auth.model.User;
import com.peter.auth.service.SecurityService;
import com.peter.auth.service.UserService;
import com.peter.auth.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;

    private final SecurityService securityService;

    private final UserValidator userValidator;

    private final RecaptchaFacade recaptchaFacade;

    private static final String CAPTCHA_PARAMETER = "g-recaptcha-response";

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult,
                               HttpServletRequest request, Model model) {

        String parameter = request.getParameter(CAPTCHA_PARAMETER);
        recaptchaFacade.validateCaptcha(request, parameter, bindingResult);
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("captcha", "Please complete captcha");
            return "registration";
        }

        userService.save(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @GetMapping("/adminPage")
    public String admin(Model model) {
        return "admin";
    }

    @GetMapping({"/", "/welcome"})
    public String welcome(Model model) {
        return "welcome";
    }
}
