package org.example.secretsanta.controller;

import org.example.secretsanta.utils.DateUtils;
import org.example.secretsanta.dto.MessageDTO;
import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.mapper.UserInfoMapper;
import org.example.secretsanta.service.impl.MessageServiceImpl;
import org.example.secretsanta.service.impl.UserInfoServiceImpl;
import org.example.secretsanta.service.security.CustomUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/message")
public class MessageController {

    private final MessageServiceImpl messageServiceImpl;
    private final UserInfoServiceImpl userInfoServiceImpl;
    private final CustomUserDetailsService userDetailsService;

    public MessageController(MessageServiceImpl messageServiceImpl, UserInfoServiceImpl userInfoServiceImpl, CustomUserDetailsService userDetailsService) {
        this.messageServiceImpl = messageServiceImpl;
        this.userInfoServiceImpl = userInfoServiceImpl;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/inbox/{idRecipient}")
    public String getInbox(@PathVariable("idRecipient") int idRecipient,Model model, Principal principal) {
        UserInfoDTO sender = (userDetailsService.findUserByName(principal.getName()));
        UserInfoDTO recipient = userInfoServiceImpl.getUserInfoById(idRecipient);
        int currentUserId = sender.getIdUserInfo();

        List<MessageDTO> messages = messageServiceImpl.getConversation(currentUserId, idRecipient);

        model.addAttribute("messages", messages);
        model.addAttribute("sender", sender);
        model.addAttribute("recipient", recipient);

        model.addAttribute("message", new MessageDTO());

        return "message-inbox";
    }

    @PostMapping("/send")
    @Transactional
    public String sendMessage(@ModelAttribute("message") MessageDTO message, Principal principal) {
        UserInfoDTO userInfoDTO = userDetailsService.findUserByName(principal.getName());

        message.setSender(UserInfoMapper.toUserInfoEntity(userInfoDTO));

        message.setDepartureDate(DateUtils.convertDateToSqlDate(LocalDateTime.now()));
        messageServiceImpl.create(message);

        return "redirect:/message/inbox/" + message.getIdRecipient();
    }

}
