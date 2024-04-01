package ru.sberschool.secretsanta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sberschool.secretsanta.dto.MessageDTO;
import ru.sberschool.secretsanta.dto.UserInfoDTO;
import ru.sberschool.secretsanta.service.MessageService;
import ru.sberschool.secretsanta.service.UserInfoService;
import ru.sberschool.secretsanta.service.security.CustomUserDetailsService;
import ru.sberschool.secretsanta.utils.DateUtils;
import ru.sberschool.secretsanta.wrapper.DialogWrapper;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Контроллер для отправки сообщений пользователями
 */
@Controller
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;
    private final UserInfoService userInfoService;
    private final CustomUserDetailsService userDetailsService;

    public MessageController(MessageService messageService, UserInfoService userInfoService,
                             CustomUserDetailsService userDetailsService) {
        this.messageService = messageService;
        this.userInfoService = userInfoService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Метод для получения страницы с диалогами пользователя
     *
     * @param model     Модель для передачи данных на страницу
     * @param principal Представляет текущегог пользователя
     * @return Страница с диалогами
     */
    @GetMapping("/dialogs")
    public String getDialogs(Model model, Principal principal) {
        UserInfoDTO currentUser = userDetailsService.findUserByName(principal.getName());
        List<MessageDTO> messageDTOS = messageService.getDistinctDialog(currentUser.getIdUserInfo());
        List<DialogWrapper> dialogs = new ArrayList<>();

        for (int i = 0; i < messageDTOS.size(); i++) {
            dialogs.add(new DialogWrapper(userInfoService.getUserInfoById(messageDTOS.get(i).getSender()
                    .getIdUserInfo()),
                    userInfoService.getUserInfoById(messageDTOS.get(i).getIdRecipient()),
                    messageDTOS.get(i).getMessage()));
        }
        model.addAttribute("dialogs", dialogs);
        return "dialogs";
    }

    /**
     * Метод для переххода в диалог с пользователем
     *
     * @param idUserInfo Идентификатор пользователя с которым ведется беседа
     * @param model      Модель для передачи данных в представление
     * @param principal  Представляет текущего пользователя
     * @return Страница с беседой между пользователями
     */
    @GetMapping("/conversation/{idUserInfo}")
    public String receiveMessages(@PathVariable("idUserInfo") int idUserInfo, Model model, Principal principal) {
        UserInfoDTO currentUser = userDetailsService.findUserByName(principal.getName());
        List<MessageDTO> messagesTo = messageService.getConversation(currentUser.getIdUserInfo(), idUserInfo);
        List<MessageDTO> messagesFrom = messageService.getConversation(idUserInfo, currentUser.getIdUserInfo());
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

    /**
     * Метод ядля отправки сообщения
     *
     * @param messageDTO  Объект сообщения, которое надо отправить
     * @param principal   Представляет текущего пользователя
     * @param idRecipient Идентификатор получателя сообщения
     * @return Страница с беседой после отправки сообщени
     */
    @PostMapping("/sendMessage")
    @Transactional
    public String sendMessage(@ModelAttribute("messageDto") MessageDTO messageDTO, Principal principal,
                              @RequestParam("idRecipient") int idRecipient) {
        messageDTO.setDepartureDate(DateUtils.convertDateToSqlDate(LocalDateTime.now()));
        messageDTO.setSender(userDetailsService.findUserByName(principal.getName()));
        messageDTO.setIdRecipient(idRecipient);
        messageService.create(messageDTO);

        return "redirect:/message/conversation/" + messageDTO.getIdRecipient();
    }

}
