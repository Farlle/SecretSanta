package org.example.secretsanta.controller;

import org.example.secretsanta.dto.ResultDTO;
import org.example.secretsanta.dto.RoomDTO;
import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.dto.WishDTO;
import org.example.secretsanta.exception.DrawingAlreadyPerformedException;
import org.example.secretsanta.exception.NotEnoughUsersException;
import org.example.secretsanta.service.impl.ResultServiceImpl;
import org.example.secretsanta.service.impl.RoomServiceImpl;
import org.example.secretsanta.service.impl.UserInfoServiceImpl;
import org.example.secretsanta.service.impl.WishServiceImpl;
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

    private final ResultServiceImpl resultServiceImpl;
    private final UserInfoServiceImpl userInfoServiceImpl;
    private final RoomServiceImpl roomServiceImpl;
    private final CustomUserDetailsService userDetailsService;
    private final WishServiceImpl wishServiceImpl;

    public ResultController(ResultServiceImpl resultServiceImpl, UserInfoServiceImpl userInfoServiceImpl, RoomServiceImpl roomServiceImpl, CustomUserDetailsService userDetailsService, WishServiceImpl wishServiceImpl) {
        this.resultServiceImpl = resultServiceImpl;
        this.userInfoServiceImpl = userInfoServiceImpl;
        this.roomServiceImpl = roomServiceImpl;
        this.userDetailsService = userDetailsService;
        this.wishServiceImpl = wishServiceImpl;
    }

    @GetMapping("/show/{idRoom}")
    public String showResultDraw(@PathVariable("idRoom") int idRoom, Model model, Principal principal) {
        List<ResultDTO> results = resultServiceImpl.showDrawInRoom(idRoom);
        List<ResultWrapper> resultWrapper = new ArrayList<>();
        UserInfoDTO currentUser = userDetailsService.findUserByName(principal.getName());

        for (ResultDTO result : results) {
            UserInfoDTO santa = userInfoServiceImpl.getUserInfoById(result.getIdSanta());
            UserInfoDTO ward = userInfoServiceImpl.getUserInfoById(result.getIdWard());
            WishDTO wish = wishServiceImpl.getUserWishInRoom(idRoom, ward.getIdUserInfo());
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

        UserInfoDTO currentUser = userDetailsService.findUserByName(principal.getName());
        RoomDTO roomDTO = roomServiceImpl.getRoomById(idRoom);

        if (!(roomServiceImpl.getRoomOrganizer(roomDTO).getIdUserInfo() == currentUser.getIdUserInfo())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Вы не можете проводить жеребьевку");
            return "redirect:/room/show/" + idRoom;
        }

        RoomDTO room = roomServiceImpl.getRoomById(idRoom);
        try {
            resultServiceImpl.performDraw(room);
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
