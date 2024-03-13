package org.example.secretsanta.controller;

import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.service.impl.UserInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String registrationUserInfo(@ModelAttribute("userInfo") UserInfoDTO dto) throws Exception {
        userInfoService.registerNewUserInfoAccount(dto);
        return "redirect:/login?success";
    }



}
