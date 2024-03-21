package org.example.secretsanta.controller;

import org.example.secretsanta.dto.InviteDTO;
import org.example.secretsanta.dto.RoomDTO;
import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.service.security.CustomUserDetailsService;
import org.example.secretsanta.service.serviceinterface.InviteService;
import org.example.secretsanta.service.serviceinterface.RoomService;
import org.example.secretsanta.service.serviceinterface.UserInfoService;
import org.example.secretsanta.wrapper.InviteTelegramWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

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
        inviteDTO.setTelegram(inviteTelegramWrapper.getParticipantTelegram().replaceAll("@", ""));
        inviteService .sendInvite(idRoom, inviteDTO);
        return "redirect:/room/show/" + inviteTelegramWrapper.getRoom().getIdRoom();
    }

}
