package org.example.secretsanta.controller;

import org.example.secretsanta.dto.MessageDTO;
import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.service.impl.MessageServiceImpl;
import org.example.secretsanta.service.impl.TelegramServiceImpl;
import org.example.secretsanta.service.impl.UserInfoServiceImpl;
import org.example.secretsanta.service.security.CustomUserDetailsService;
import org.example.secretsanta.utils.DateUtils;
import org.example.secretsanta.wrapper.DialogWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

@Controller
@RequestMapping("/message")
public class MessageController {

    private final MessageServiceImpl messageServiceImpl;
    private final UserInfoServiceImpl userInfoServiceImpl;
    private final CustomUserDetailsService userDetailsService;

    public MessageController(MessageServiceImpl messageServiceImpl, UserInfoServiceImpl userInfoServiceImpl,
                             CustomUserDetailsService userDetailsService) {
        this.messageServiceImpl = messageServiceImpl;
        this.userInfoServiceImpl = userInfoServiceImpl;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/dialogs")
    public String getDialogs(Model model, Principal principal) {
        UserInfoDTO currentUser = userDetailsService.findUserByName(principal.getName());
        List<MessageDTO> messageDTOS = messageServiceImpl.getDistinctDialog(currentUser.getIdUserInfo());
        List<DialogWrapper> dialogs = new ArrayList<>();

        for (int i = 0; i < messageDTOS.size(); i++) {
            dialogs.add(new DialogWrapper(userInfoServiceImpl.getUserInfoById(messageDTOS.get(i).getSender()
                    .getIdUserInfo()),
                    userInfoServiceImpl.getUserInfoById(messageDTOS.get(i).getIdRecipient()),
                    messageDTOS.get(i).getMessage().toString()));
        }
        model.addAttribute("dialogs", dialogs);
        return "dialogs";
    }

    @GetMapping("/conversation/{idUserInfo}")
    public String receiveMessages(@PathVariable("idUserInfo") int idUserInfo, Model model, Principal principal) {
        UserInfoDTO currentUser = userDetailsService.findUserByName(principal.getName());
        List<MessageDTO> messagesTo = messageServiceImpl.getConversation(currentUser.getIdUserInfo(), idUserInfo);
        List<MessageDTO> messagesFrom = messageServiceImpl.getConversation(idUserInfo, currentUser.getIdUserInfo());
        List<MessageDTO> allMessages = new ArrayList<>();
        allMessages.addAll(messagesFrom);
        allMessages.addAll(messagesTo);
        Collections.sort(allMessages);
        LinkedHashSet<MessageDTO> allMessageSet = new LinkedHashSet<>(allMessages);


        model.addAttribute("idRecipient", idUserInfo);
        model.addAttribute("sender", currentUser);
        model.addAttribute("messages", allMessageSet);
        model.addAttribute("messageDto", new MessageDTO());
        return "conversation";
    }

    @PostMapping("/sendMessage")
    @Transactional
    public String sendMessage(@ModelAttribute("messageDto") MessageDTO messageDTO, Principal principal,
                              @RequestParam("idRecipient") int idRecipient) {
        messageDTO.setDepartureDate(DateUtils.convertDateToSqlDate(LocalDateTime.now()));
        messageDTO.setSender(userDetailsService.findUserByName(principal.getName()));
        messageDTO.setIdRecipient(idRecipient);
        messageServiceImpl.create(messageDTO);

        return "redirect:/message/conversation/" + messageDTO.getIdRecipient();
    }

}
