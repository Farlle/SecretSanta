package org.example.secretsanta.controller;

import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.service.security.CustomUserDetailsService;
import org.example.secretsanta.service.serviceinterface.UserInfoService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Profile("dev")
@RequestMapping("/userInfo")
public class UserInfoController {

    private final UserInfoService userInfoService;

    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping("/create")
    public String showAddUserPage(Model model) {
        model.addAttribute("userInfoDto", new UserInfoDTO());
        return "add-user-info";
    }

    @PostMapping("/create")
    public String createUserInfo(@ModelAttribute UserInfoDTO dto, Model model) {
        UserInfoDTO userInfo = userInfoService.create(dto);
        model.addAttribute("userInfo", userInfo);
        return "redirect:/userInfo/show";
    }

    @GetMapping("/update/{id}")
    public String updateUserInfo(@PathVariable int id, Model model) {
        UserInfoDTO userInfo = userInfoService.getUserInfoById(id);
        model.addAttribute("userInfo", userInfo);
        return "user-info-update";
    }

    @PostMapping("/update/{id}")
    public String updateUserInfo(@PathVariable int id, @ModelAttribute("userInfo") UserInfoDTO dto, Model model) {
        userInfoService.update(id, dto);
        model.addAttribute("userInfo", dto);
        return "redirect:/userInfo/show";
    }

    @GetMapping("/show")
    public String getAllUsersInfo(Model model) {
        model.addAttribute("userInfo", userInfoService.readAll());
        return "user-info-list";
    }

    @PostMapping("/delete/{id}")
    public String deleteUserInfo(@PathVariable int id) {
        userInfoService.delete(id);
        return "redirect:/userInfo/show";

    }

}
