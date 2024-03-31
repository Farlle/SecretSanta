package org.example.secretsanta.controller;

import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.exception.UserAlreadyExistsException;
import org.example.secretsanta.service.UserInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Контроллер аутентификации
 */
@Controller
public class AuthController {

    private final UserInfoService userInfoService;

    public AuthController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    /**
     * Метод для отображения страницы входа
     @return страница входа.
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }


    /**
     * @param model
     * @return страницу с регистрацией
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userInfo", new UserInfoDTO());
        return "register";
    }

    /**
     Обрабатывает POST-запрос на регистрацию нового пользователя.
     @param dto Данные о пользователе, полученные из формы.
     @param redirectAttributes Атрибуты для передачи сообщений между запросами.
     @return страницу для подключения телеграм.
     @throws UserAlreadyExistsException Если имя пользователя занято.
     */
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


    /**
     * Метод для отображения домашней страницы
     * @return домашняя страница
     */
    @GetMapping("/home")
    public String home() {
        return "home";
    }

}
