package org.example.secretsanta.controller;

import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.mapper.UserInfoMapper;
import org.example.secretsanta.model.entity.ResultEntity;
import org.example.secretsanta.model.entity.RoomEntity;
import org.example.secretsanta.model.entity.UserInfoEntity;
import org.example.secretsanta.model.entity.WishEntity;
import org.example.secretsanta.service.ResultService;
import org.example.secretsanta.service.RoomService;
import org.example.secretsanta.service.UserInfoService;
import org.example.secretsanta.service.WishService;
import org.example.secretsanta.service.security.CustomUserDetailsService;
import org.example.secretsanta.wrapper.ResultWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
        List<ResultEntity> results = resultService.showDrawInRoom(idRoom);
        List<ResultWrapper> resultWrapper = new ArrayList<>();
       UserInfoDTO currentUser = UserInfoMapper.toUserInfoDTO(userDetailsService.findUserByName( principal.getName()));

        for(ResultEntity result : results) {
            UserInfoEntity santa = userInfoService.getUserInfoEntityById(result.getIdSanta());
            UserInfoEntity ward = userInfoService.getUserInfoEntityById(result.getIdWard());
            WishEntity wish = wishService.getUserWishInRoom(idRoom, ward.getId());
            resultWrapper.add(new ResultWrapper(santa, ward, wish));
        }



        ResultWrapper wardResultWrapper = resultWrapper.stream()
                .filter(wrapper -> wrapper.getSanta().getId() == currentUser.getIdUserInfo())
                .findFirst()
                .orElse(null);

        model.addAttribute("resultWrappers", wardResultWrapper);
        return "result-show-in-room";
    }

    @GetMapping("/drawing/{idRoom}")
    public String drawingLots(@PathVariable("idRoom") int id) {
        RoomEntity room = roomService.getRoomEntityById(id);
        resultService.performDraw(room);
        return "redirect:/result/show/{idRoom}";
    }

}
