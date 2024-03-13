package org.example.secretsanta.controller;

import org.example.secretsanta.dto.ResultDTO;
import org.example.secretsanta.dto.RoomDTO;
import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.dto.WishDTO;
import org.example.secretsanta.service.impl.ResultService;
import org.example.secretsanta.service.impl.RoomService;
import org.example.secretsanta.service.impl.UserInfoService;
import org.example.secretsanta.service.impl.WishService;
import org.example.secretsanta.service.security.CustomUserDetailsService;
import org.example.secretsanta.wrapper.ResultWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/result")
public class ResultController {

    private final ResultService resultService;
    private final UserInfoService userInfoService;
    private final RoomService roomService;
    private final CustomUserDetailsService userDetailsService;
    private final WishService wishService;

    public ResultController(ResultService resultService, UserInfoService userInfoService, RoomService roomService, CustomUserDetailsService userDetailsService, WishService wishService) {
        this.resultService = resultService;
        this.userInfoService = userInfoService;
        this.roomService = roomService;
        this.userDetailsService = userDetailsService;
        this.wishService = wishService;
    }

    @GetMapping("/show/{idRoom}")
    public String showResultDraw(@PathVariable("idRoom") int idRoom, Model model, Principal principal) {
        List<ResultDTO> results = resultService.showDrawInRoom(idRoom);
        List<ResultWrapper> resultWrapper = new ArrayList<>();
       UserInfoDTO currentUser = userDetailsService.findUserByName( principal.getName());

        for(ResultDTO result : results) {
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

    @GetMapping("/drawing/{idRoom}")
    public String drawingLots(@PathVariable("idRoom") int idRoom, Principal principal,
                              RedirectAttributes redirectAttributes) {

        UserInfoDTO currentUser = userDetailsService.findUserByName( principal.getName());
        RoomDTO roomDTO = roomService.getRoomById(idRoom);

        if (!(roomService.getRoomOrganizer(roomDTO).getIdUserInfo() == currentUser.getIdUserInfo())) {
            redirectAttributes.addFlashAttribute("error", "Вы не можете проводить жеребьевку");
            return "redirect:/room/show/" + idRoom;
        }

        RoomDTO room = roomService.getRoomById(idRoom);
        resultService.performDraw(room);
        return "redirect:/result/show/" + idRoom;
    }

}
