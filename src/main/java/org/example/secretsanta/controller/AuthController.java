package org.example.secretsanta.controller;

import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.service.impl.UserInfoServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserInfoServiceImpl userInfoServiceImpl;

    public AuthController(UserInfoServiceImpl userInfoServiceImpl) {
        this.userInfoServiceImpl = userInfoServiceImpl;
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
        userInfoServiceImpl.registerNewUserInfoAccount(dto);
        return "redirect:/login?success";
    }



}
