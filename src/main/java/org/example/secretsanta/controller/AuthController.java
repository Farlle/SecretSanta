package org.example.secretsanta.controller;

import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.exception.UserAlreadyExistsException;
import org.example.secretsanta.service.impl.UserInfoServiceImpl;
import org.example.secretsanta.service.serviceinterface.UserInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserInfoService userInfoService;

    public AuthController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userInfo", new UserInfoDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registrationUserInfo(@ModelAttribute("userInfo") UserInfoDTO dto,
                                       RedirectAttributes redirectAttributes) throws Exception {
        try{
            userInfoService.registerNewUserInfoAccount(dto);
        } catch (UserAlreadyExistsException e){
            redirectAttributes.addFlashAttribute("errorMessage","Имя пользователя уже занято!");
            return  "redirect:/login";
        }
        return "register-telegram";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

}
