package org.example.secretsanta.controller;


import org.example.secretsanta.dto.RoomDTO;
import org.example.secretsanta.model.entity.RoomEntity;
import org.example.secretsanta.model.entity.UserInfoEntity;
import org.example.secretsanta.service.RoomService;
import org.example.secretsanta.service.UserInfoService;
import org.example.secretsanta.service.security.CustomUserDetailsService;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;
    private final UserInfoService userInfoService;

    private final CustomUserDetailsService userDetailsService;

    public RoomController(RoomService roomService, UserInfoService userInfoService, CustomUserDetailsService userDetailsService) {
        this.roomService = roomService;
        this.userInfoService = userInfoService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/create")
    public String showAddRoomPage(Model model) {
        RoomDTO roomDTO = new RoomDTO();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            UserInfoEntity user = userDetailsService.findUserByName(username);
            System.out.println(user.getId());
            if (user==null){
                throw new UsernameNotFoundException("UserInfo not found with user:" + user);
            }
            roomDTO.setIdOrganizer(user.getId());
        }

        model.addAttribute("roomDto", roomDTO);
        return "room-add-page";
    }

    @PostMapping("/create")
    public String createRoom(@ModelAttribute RoomDTO dto, Model model) {
        RoomEntity room = roomService.create(dto);
        model.addAttribute("roomEntity", room);
        return "redirect:/show";
    }

    @GetMapping("/update/{id}")
    public String updateRoom(@PathVariable int id, Model model) {
        RoomEntity room = roomService.getRoomEntityById(id);
        model.addAttribute("room", room);
        return "room-update";
    }

    @PostMapping("/update/{id}")
    public String updateRoom(@PathVariable int id, @ModelAttribute("room") RoomDTO dto, Model model) {
        roomService.update(id, dto);
        model.addAttribute("room", dto);
        return "redirect:/show";
    }

    @DeleteMapping("delete/{id}")
    public String deleteRoom(@PathVariable int id) {
        roomService.delete(id);
        return "redirect:/show";
    }

    @GetMapping("/show")
    public String getAllUserInfo(Model model) {
        model.addAttribute("roomEntity", roomService.readAll());
        return "room-list";
    }




}
