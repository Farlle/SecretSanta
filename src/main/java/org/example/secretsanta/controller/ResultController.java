package org.example.secretsanta.controller;

import org.example.secretsanta.model.entity.ResultEntity;
import org.example.secretsanta.model.entity.RoomEntity;
import org.example.secretsanta.model.entity.UserInfoEntity;
import org.example.secretsanta.service.ResultService;
import org.example.secretsanta.service.RoomService;
import org.example.secretsanta.service.UserInfoService;
import org.example.secretsanta.wrapper.ResultWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/result")
public class ResultController {

    private final ResultService resultService;
    private final UserInfoService userInfoService;
    private final RoomService roomService;

    public ResultController(ResultService resultService, UserInfoService userInfoService, RoomService roomService) {
        this.resultService = resultService;
        this.userInfoService = userInfoService;
        this.roomService = roomService;
    }

    @GetMapping("/show/{idRoom}")
    public String showResultDraw(@PathVariable("idRoom") int idRoom, Model model) {
        List<ResultEntity> results = resultService.showDrawInRoom(idRoom);
        List<ResultWrapper> resultWrapper = new ArrayList<>();

        for(ResultEntity result : results) {
            UserInfoEntity santa = userInfoService.getUserInfoEntityById(result.getIdSanta());
            UserInfoEntity ward = userInfoService.getUserInfoEntityById(result.getIdWard());
            resultWrapper.add(new ResultWrapper(santa, ward));

        }

        model.addAttribute("resultWrappers", resultWrapper);
        return "result-show-in-room";
    }

    @GetMapping("/drawing/{idRoom}")
    public String drawingLots(@PathVariable("idRoom") int id) {
        RoomEntity room = roomService.getRoomEntityById(id);
        resultService.performDraw(room);
        return "redirect:/result/show/{idRoom}";
    }

}
