package org.example.secretsanta.controller;

import org.example.secretsanta.dto.InviteDTO;
import org.example.secretsanta.dto.RoomDTO;
import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.mapper.UserInfoMapper;
import org.example.secretsanta.model.enums.Status;
import org.example.secretsanta.service.impl.InviteServiceImpl;
import org.example.secretsanta.service.impl.RoomServiceImpl;
import org.example.secretsanta.service.impl.UserInfoServiceImpl;
import org.example.secretsanta.service.security.CustomUserDetailsService;
import org.example.secretsanta.wrapper.InviteTelegramWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/invite")
public class InviteController {

    private final InviteServiceImpl inviteServiceImpl;
    private final UserInfoServiceImpl userInfoServiceImpl;
    private final CustomUserDetailsService userDetailsService;
    private final RoomServiceImpl roomServiceImpl;

    public InviteController(InviteServiceImpl inviteServiceImpl, UserInfoServiceImpl userInfoServiceImpl, CustomUserDetailsService userDetailsService, RoomServiceImpl roomServiceImpl) {
        this.inviteServiceImpl = inviteServiceImpl;
        this.userInfoServiceImpl = userInfoServiceImpl;
        this.userDetailsService = userDetailsService;
        this.roomServiceImpl = roomServiceImpl;
    }

    @GetMapping("/send/{idRoom}")
    public String inviteTelegramForm(@PathVariable("idRoom") int idRoom, Model model, Principal principal,
                                     RedirectAttributes redirectAttributes) {
        UserInfoDTO currentUser = userDetailsService.findUserByName(principal.getName());
        RoomDTO roomDTO = roomServiceImpl.getRoomById(idRoom);

        if (roomServiceImpl.getRoomOrganizer(roomDTO).getIdUserInfo() != currentUser.getIdUserInfo()) {
            redirectAttributes.addFlashAttribute("error", "Вы не можете приглашать участников");
            return "redirect:/room/show/" + idRoom;
        }

        InviteTelegramWrapper inviteTelegramWrapper = new InviteTelegramWrapper();
        inviteTelegramWrapper.setSanta(userDetailsService.findUserByName(principal.getName()));
        inviteTelegramWrapper.setRoom(roomServiceImpl.getRoomById(idRoom));

        model.addAttribute("inviteTelegramWrapper", inviteTelegramWrapper);
        return "invite-page";
    }

    @PostMapping("/send")
    public String inviteTelegramUser(@ModelAttribute InviteTelegramWrapper inviteTelegramWrapper,
                                     RedirectAttributes redirectAttributes) {
        UserInfoDTO participantUser = userInfoServiceImpl
                .getUsersInfoByTelegram(inviteTelegramWrapper.getParticipantTelegram());
        if (participantUser == null) {
            redirectAttributes.addFlashAttribute("error", "Этот пользователь не регестрировал телеграм!");
            return "redirect:/room/show/" + inviteTelegramWrapper.getRoom().getIdRoom();
        }
        int idRoom = inviteTelegramWrapper.getRoom().getIdRoom();
        InviteDTO inviteDTO = new InviteDTO();
        inviteDTO.setUserInfoEntity(UserInfoMapper.toUserInfoEntity(participantUser));
        inviteDTO.setTelegram(inviteTelegramWrapper.getParticipantTelegram().replaceAll("@", ""));
        inviteServiceImpl.sendInvite(idRoom, inviteDTO);
        return "redirect:/room/show/" + inviteTelegramWrapper.getRoom().getIdRoom();
    }


}
