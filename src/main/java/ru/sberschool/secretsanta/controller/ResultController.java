package ru.sberschool.secretsanta.controller;

import ru.sberschool.secretsanta.dto.ResultDTO;
import ru.sberschool.secretsanta.dto.RoomDTO;
import ru.sberschool.secretsanta.dto.UserInfoDTO;
import ru.sberschool.secretsanta.dto.WishDTO;
import ru.sberschool.secretsanta.exception.DrawingAlreadyPerformedException;
import ru.sberschool.secretsanta.exception.NotEnoughUsersException;
import ru.sberschool.secretsanta.service.security.CustomUserDetailsService;
import ru.sberschool.secretsanta.service.ResultService;
import ru.sberschool.secretsanta.service.RoomService;
import ru.sberschool.secretsanta.service.UserInfoService;
import ru.sberschool.secretsanta.service.WishService;
import ru.sberschool.secretsanta.wrapper.ResultWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер для проведения жеребьевок и просмотра ее результата
 */
@Controller
@RequestMapping("/result")
public class ResultController {

    private final ResultService resultService;
    private final UserInfoService userInfoService;
    private final RoomService roomService;
    private final CustomUserDetailsService userDetailsService;
    private final WishService wishService;

    public ResultController(ResultService resultService, UserInfoService userInfoService,
                            RoomService roomService, CustomUserDetailsService userDetailsService,
                            WishService wishService) {
        this.resultService = resultService;
        this.userInfoService = userInfoService;
        this.roomService = roomService;
        this.userDetailsService = userDetailsService;
        this.wishService = wishService;
    }

    /**
     * Метод для просмотра результатов
     *
     * @param idRoom Идентификатор комнаты, в которой просматриваем результат
     * @param model Модель для передачи данных в представление
     * @param principal Представляет текущего пользователя
     * @return Страница с результатами
     */
    @GetMapping("/show/{idRoom}")
    public String showResultDraw(@PathVariable("idRoom") int idRoom, Model model, Principal principal) {
        List<ResultDTO> results = resultService.showDrawInRoom(idRoom);
        List<ResultWrapper> resultWrapper = new ArrayList<>();
        UserInfoDTO currentUser = userDetailsService.findUserByName(principal.getName());

        for (ResultDTO result : results) {
            UserInfoDTO santa = userInfoService.getUserInfoById(result.getIdSanta());
            UserInfoDTO ward = userInfoService.getUserInfoById(result.getIdWard());
            WishDTO wish = wishService.getUserWishInRoom(idRoom, ward.getIdUserInfo());
            resultWrapper.add(new ResultWrapper(santa, ward, wish));
        }

        ResultWrapper wardResultWrapper = resultWrapper.stream()
                .filter(wrapper -> wrapper.getSanta().getIdUserInfo() == currentUser.getIdUserInfo())
                .findFirst()
                .orElse(null);

        model.addAttribute("resultWrappers", wardResultWrapper);
        return "result-show-in-room";
    }

    /**
     * Метод для проведения жеребьевки в комнате
     *
     * @param idRoom Индентификатор в комнате
     * @param principal Представляет текущего пользователя
     * @param redirectAttributes Атрибуты для передачи данных на страницу
     * @return Возвращает страницу просмотра результат после просмотра результат, либо страницу с информацией о комнате,
     * если недостаточно пользователей в комнате или текущий пользователь не организатор
     */
    @GetMapping("/drawing/{idRoom}")
    public String drawingLots(@PathVariable("idRoom") int idRoom, Principal principal,
                              RedirectAttributes redirectAttributes) {

        UserInfoDTO currentUser = userDetailsService.findUserByName(principal.getName());
        RoomDTO roomDTO = roomService.getRoomById(idRoom);

        if ((roomService.getRoomOrganizer(roomDTO).getIdUserInfo() != currentUser.getIdUserInfo())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Вы не можете проводить жеребьевку");
            return "redirect:/room/show/" + idRoom;
        }

        RoomDTO room = roomService.getRoomById(idRoom);
        try {
            resultService.performDraw(room);
        } catch (NotEnoughUsersException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Маловато гостей");
            return "redirect:/room/show/" + idRoom;
        } catch (DrawingAlreadyPerformedException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Упс, ты не организатор");
            return "redirect:/room/show/" + idRoom;
        }
        return "redirect:/result/show/" + idRoom;
    }

}
