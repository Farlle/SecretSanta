package org.example.secretsanta.controller;

import org.example.secretsanta.dto.MessageDTO;
import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.mapper.UserInfoMapper;
import org.example.secretsanta.model.entity.MessageEntity;
import org.example.secretsanta.model.entity.UserInfoEntity;
import org.example.secretsanta.service.MessageService;
import org.example.secretsanta.service.UserInfoService;
import org.example.secretsanta.service.security.CustomUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Controller
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;
    private final UserInfoService userInfoService;
    private final CustomUserDetailsService userDetailsService;

    public MessageController(MessageService messageService, UserInfoService userInfoService, CustomUserDetailsService userDetailsService) {
        this.messageService = messageService;
        this.userInfoService = userInfoService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/inbox/{idRecipient}")
    public String getInbox(@PathVariable("idRecipient") int idRecipient,Model model, Principal principal) {
        UserInfoDTO sender = UserInfoMapper.toUserInfoDTO(userDetailsService.findUserByName(principal.getName()));
        UserInfoDTO recipient = UserInfoMapper.toUserInfoDTO(userInfoService.getUserInfoEntityById(idRecipient));
        int currentUserId = sender.getIdUserInfo();

        List<MessageEntity> messages = messageService.getConversation(currentUserId, idRecipient);

        model.addAttribute("messages", messages);
        model.addAttribute("sender", sender);
        model.addAttribute("recipient", recipient);

        model.addAttribute("message", new MessageEntity());

        return "message-inbox";
    }

    @PostMapping("/send")
    @Transactional
    public String sendMessage(@ModelAttribute("message") MessageDTO message, Principal principal) {
        UserInfoDTO userInfoDTO = UserInfoMapper.toUserInfoDTO(userDetailsService.findUserByName(principal.getName()));

        message.setSender(UserInfoMapper.toUserInfoEntity(userInfoDTO));

        LocalDateTime now = LocalDateTime.now();
        java.util.Date currentDate = java.util.Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        Date sqlDate = new Date(currentDate.getTime());
        message.setDepartureDate(sqlDate);
        messageService.create(message);

        return "redirect:/message/inbox/" + message.getIdRecipient();
    }

 /*   @GetMapping("/postbox")
    public String getPostBox(Principal principal, Model model){
        UserInfoDTO userInfoDTO = UserInfoMapper.toUserInfoDTO(userDetailsService.findUserByName(principal.getName()));
        List<Integer> idRecipient = messageService.getAllUserDialog(userInfoDTO.getIdUserInfo());
        List<MessageEntity> postBox;
        for (int i = 0; i < idRecipient.size(); i++) {
            postBox.add(messageService.getConversation(userInfoDTO.getIdUserInfo(), idRecipient.get(i)));
        }
        model.addAttribute("postBox", postBox);
        return "post-box";
    }*/

}
