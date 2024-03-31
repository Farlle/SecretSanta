package org.example.secretsanta.controller;

import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.service.UserInfoService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер отвечающйи за действия над пользователем. Использовался для разработки программы
 */
@Controller
@Profile("dev")
@RequestMapping("/userInfo")
public class UserInfoController {

    private final UserInfoService userInfoService;

    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    /**
     * Метод возвращающий страницу для добавления нового пользователя
     *
     * @param model Моедль для передачи данных на страницу
     * @return Страница для добавления пользователя
     */
    @GetMapping("/create")
    public String showAddUserPage(Model model) {
        model.addAttribute("userInfoDto", new UserInfoDTO());
        return "add-user-info";
    }

    /**
     * Метод создания пользователя
     *
     * @param dto Объект пользователя, который необходимо создать
     * @param model Модель для передачи данных на страницу
     * @return Перенаправляет на страницу с информацией обо всех пользователях
     */
    @PostMapping("/create")
    public String createUserInfo(@ModelAttribute UserInfoDTO dto, Model model) {
        UserInfoDTO userInfo = userInfoService.create(dto);
        model.addAttribute("userInfo", userInfo);
        return "redirect:/userInfo/show";
    }

    /**
     * Метод для получения страницы с информацией о пользователе
     *
     * @param id Идентификатор пользователя
     * @param model Модель для передачи данных на страницу
     * @return Страница для обновления пользователя
     */
    @GetMapping("/update/{id}")
    public String updateUserInfo(@PathVariable int id, Model model) {
        UserInfoDTO userInfo = userInfoService.getUserInfoById(id);
        model.addAttribute("userInfo", userInfo);
        return "user-info-update";
    }

    /**
     * Метод для обновления пользователя
     *
     * @param id Идентификатор пользователя
     * @param dto Объект пользователя который надо обновить
     * @param model Модель для передачи данных на страницу
     * @return Пренаправляет на страницу с информацией о всех пользователях
     */
    @PostMapping("/update/{id}")
    public String updateUserInfo(@PathVariable int id, @ModelAttribute("userInfo") UserInfoDTO dto, Model model) {
        userInfoService.update(id, dto);
        model.addAttribute("userInfo", dto);
        return "redirect:/userInfo/show";
    }

    /**
     * Метод для получения страницы с информацией о всех пользователях
     *
     * @param model Модель для передачи данных на страницу
     * @return Страница с информацией о всех пользователях
     */
    @GetMapping("/show")
    public String getAllUsersInfo(Model model) {
        model.addAttribute("userInfo", userInfoService.readAll());
        return "user-info-list";
    }

    /**
     * Метод для удаления пользователя
     *
     * @param id Идентификатор пальзователя
     * @return Перенаправляет на страницу со всеми пользователями
     */
    @PostMapping("/delete/{id}")
    public String deleteUserInfo(@PathVariable int id) {
        userInfoService.delete(id);
        return "redirect:/userInfo/show";

    }

}
