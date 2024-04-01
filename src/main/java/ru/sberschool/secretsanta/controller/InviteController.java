package ru.sberschool.secretsanta.controller;

import ru.sberschool.secretsanta.dto.InviteDTO;
import ru.sberschool.secretsanta.dto.RoomDTO;
import ru.sberschool.secretsanta.dto.UserInfoDTO;
import ru.sberschool.secretsanta.service.security.CustomUserDetailsService;
import ru.sberschool.secretsanta.service.InviteService;
import ru.sberschool.secretsanta.service.RoomService;
import ru.sberschool.secretsanta.service.UserInfoService;
import ru.sberschool.secretsanta.wrapper.InviteTelegramWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

/**
 * Контроллер для обработки отправления приглашений в комнату
 */
@Controller
@RequestMapping("/invite")
public class InviteController {

    private final InviteService inviteService;
    private final UserInfoService userInfoService;
    private final CustomUserDetailsService userDetailsService;
    private final RoomService roomService;

    public InviteController(InviteService inviteService, UserInfoService userInfoService,
                            CustomUserDetailsService userDetailsService, RoomService roomService) {
        this.inviteService = inviteService;
        this.userInfoService = userInfoService;
        this.userDetailsService = userDetailsService;
        this.roomService = roomService;
    }

    /**
     * Метод для получения страницы отправки сообщения
     *
     * @param idRoom Идентификатор в которую приглашается пользователь
     * @param model Модель для передачи данных на страницу
     * @param principal Текущий пользователь
     * @param redirectAttributes Атрибуты для передачи в запрос
     * @return Страницу для создания приглашения
     */
    @GetMapping("/send/{idRoom}")
    public String inviteTelegramForm(@PathVariable("idRoom") int idRoom, Model model, Principal principal,
                                     RedirectAttributes redirectAttributes) {
        UserInfoDTO currentUser = userDetailsService.findUserByName(principal.getName());
        RoomDTO roomDTO = roomService .getRoomById(idRoom);

        if (roomService .getRoomOrganizer(roomDTO).getIdUserInfo() != currentUser.getIdUserInfo()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Вы не можете приглашать участников");
            return "redirect:/room/show/" + idRoom;
        }

        InviteTelegramWrapper inviteTelegramWrapper = new InviteTelegramWrapper();
        inviteTelegramWrapper.setSanta(userDetailsService.findUserByName(principal.getName()));
        inviteTelegramWrapper.setRoom(roomService .getRoomById(idRoom));

        model.addAttribute("inviteTelegramWrapper", inviteTelegramWrapper);
        return "invite-page";
    }

    /**
     * Метод для отправки отправки приглашения в комнату через телеграм
     *
     * @param inviteTelegramWrapper Обертка, которая содержит данные для приглашения
     * @param redirectAttributes Атрибуты для передачи в запрос
     * @return Редирект на страницу комнаты после отправки сообщения
     */
    @PostMapping("/send")
    public String inviteTelegramUser(@ModelAttribute InviteTelegramWrapper inviteTelegramWrapper,
                                     RedirectAttributes redirectAttributes) {
        UserInfoDTO participantUser = userInfoService 
                .getUsersInfoByTelegram(inviteTelegramWrapper.getParticipantTelegram());
        if (participantUser == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Этот пользователь не регестрировал телеграм!");
            return "redirect:/room/show/" + inviteTelegramWrapper.getRoom().getIdRoom();
        }
        int idRoom = inviteTelegramWrapper.getRoom().getIdRoom();
        InviteDTO inviteDTO = new InviteDTO();
        inviteDTO.setUserInfoDTO(participantUser);
        inviteDTO.setTelegram(inviteTelegramWrapper.getParticipantTelegram().replace("@", ""));
        inviteService .sendInvite(idRoom, inviteDTO);
        return "redirect:/room/show/" + inviteTelegramWrapper.getRoom().getIdRoom();
    }

}
