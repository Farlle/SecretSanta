package org.example.secretsanta.controller;

import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.model.entity.UserInfoEntity;
import org.example.secretsanta.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class UserInfoController {

    private final UserInfoService userInfoService;

    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping("/create")
    public String showAddUserForm(Model model) {
        model.addAttribute("userInfoDto", new UserInfoDTO());
        return "add-user-info";
    }

    @PostMapping("/create")
    public String createUserInfo(@ModelAttribute UserInfoDTO dto, Model model) {
        UserInfoEntity userInfo = userInfoService.create(dto);
        model.addAttribute("userInfoEntity", userInfo);
        return "redirect:/show";
    }

    @GetMapping("/update/{id}")
    public String updateUserInfo(@PathVariable int id,Model model) {
        UserInfoEntity userInfo = userInfoService.getUserInfoEntityById(id);
        model.addAttribute("userInfo", userInfo);
        return "user-info-update";
    }

    @PostMapping ("/update/{id}")
    public String updateUserInfo(@PathVariable int id, @ModelAttribute("userInfo") UserInfoDTO dto, Model model) {
        userInfoService.update(id, dto);
        model.addAttribute("userInfo", dto);
        return "redirect:/show";
    }

    @GetMapping("/show")
    public String getAllUsersIndo(Model model){
        model.addAttribute("userInfoEntity", userInfoService.readAll());
        return "user-info-list";
    }

    @PostMapping("/delete/{id}")
    public String deleteUserInfo(@PathVariable int id){
        userInfoService.delete(id);
        return "redirect:/show";

    }

    @GetMapping("/test")
    public String test() {
        return "Test endpoint is working!";
    }


}