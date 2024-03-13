package org.example.secretsanta.controller;


import org.example.secretsanta.dto.*;
import org.example.secretsanta.mapper.RoleMapper;
import org.example.secretsanta.mapper.RoomMapper;
import org.example.secretsanta.mapper.UserInfoMapper;
import org.example.secretsanta.mapper.WishMapper;
import org.example.secretsanta.model.enums.Role;
import org.example.secretsanta.service.impl.RoleServiceImpl;
import org.example.secretsanta.service.impl.RoomServiceImpl;
import org.example.secretsanta.service.impl.UserRoleWishRoomServiceImpl;
import org.example.secretsanta.service.impl.WishServiceImpl;
import org.example.secretsanta.service.security.CustomUserDetailsService;
import org.example.secretsanta.wrapper.RoomAndOrganizerWrapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/room")
public class RoomController {

    private final RoomServiceImpl roomServiceImpl;
    private final CustomUserDetailsService userDetailsService;
    private final WishServiceImpl wishServiceImpl;
    private final RoleServiceImpl roleServiceImpl;
    private final UserRoleWishRoomServiceImpl userRoleWishRoomServiceImpl;

    public RoomController(RoomServiceImpl roomServiceImpl, CustomUserDetailsService userDetailsService,
                          WishServiceImpl wishServiceImpl, RoleServiceImpl roleServiceImpl,
                          UserRoleWishRoomServiceImpl userRoleWishRoomServiceImpl) {

        this.roomServiceImpl = roomServiceImpl;
        this.userDetailsService = userDetailsService;
        this.wishServiceImpl = wishServiceImpl;
        this.roleServiceImpl = roleServiceImpl;
        this.userRoleWishRoomServiceImpl = userRoleWishRoomServiceImpl;
    }

    @GetMapping("/create")
    public String showAddRoomPage(Model model) {
        RoomDTO roomDTO = new RoomDTO();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            UserInfoDTO user = userDetailsService.findUserByName(username);
            if (user == null) {
                throw new UsernameNotFoundException("UserInfo not found with user:" + user);
            }
            roomDTO.setIdOrganizer(user.getIdUserInfo());
        }

        model.addAttribute("roomDto", roomDTO);
        return "room-add-page";
    }

    @PostMapping("/create")
    public String createRoom(@ModelAttribute RoomDTO dto, Model model, RedirectAttributes redirectAttributes) {
        if (!dto.getTossDate().after(dto.getDrawDate())) {
            redirectAttributes.addFlashAttribute("error", "Draw Date must be before Toss Date.");
            return "redirect:/room/create";
        }
        RoomDTO room = roomServiceImpl.create(dto);
        model.addAttribute("room", room);
        return "redirect:/room/show";
    }

    @GetMapping("/update/{id}")
    public String updateRoom(@PathVariable int id, Model model) {
        RoomDTO room = roomServiceImpl.getRoomById(id);
        model.addAttribute("room", room);
        return "room-update";
    }

    @PostMapping("/update/{id}")
    public String updateRoom(@PathVariable int id, @ModelAttribute("room") RoomDTO dto, Model model) {
        roomServiceImpl.update(id, dto);
        model.addAttribute("room", dto);
        return "redirect:/show";
    }

    @DeleteMapping("delete/{id}")
    public String deleteRoom(@PathVariable int id) {
        roomServiceImpl.delete(id);
        return "redirect:/show";
    }

    @GetMapping("/show")
    public String getAllRoom(Model model) {
        model.addAttribute("roomsDto", roomServiceImpl.readAll());
        return "room-list";
    }

    @GetMapping("/show/{idRoom}")
    public String getRoomById(@PathVariable("idRoom") int idRoom, Model model, Principal principal) {

        RoomDTO room = roomServiceImpl.getRoomById(idRoom);
        UserInfoDTO loginUser = userDetailsService.findUserByName(principal.getName());
        UserInfoDTO organizer = roomServiceImpl.getRoomOrganizer(room);

        RoomAndOrganizerWrapper roomAndOrganizerWrapper = new RoomAndOrganizerWrapper(room, organizer);

        model.addAttribute("loginUser", loginUser);
        model.addAttribute("roomAndOrganizerWrapper", roomAndOrganizerWrapper);

        return "room-show";
    }

    @GetMapping("/{id}/join")
    public String joinRoomForm(@PathVariable("id") int idRoom, Model model, Principal principal) {
        UserInfoDTO currentUser = userDetailsService.findUserByName(principal.getName());

        RoomDTO roomDTO = roomServiceImpl.getRoomById(idRoom);
        if (roomServiceImpl.getRoomsWhereUserJoin(currentUser.getIdUserInfo())
                .contains(roomServiceImpl.getRoomById(idRoom))) {
            model.addAttribute("errorMessage", "Вы уже являетесь участником этой комнаты.");
        }
        model.addAttribute("roomDto", roomDTO);
        model.addAttribute("idRoom", idRoom);
        model.addAttribute("wishDto", new WishDTO());
        return "join-room-page";
    }

    @PostMapping("/{id}/join")
    @Transactional
    public String joinRoom(@PathVariable("id") int idRoom, @ModelAttribute WishDTO wishDto,
                           Model model, Principal principal) {

        WishDTO savedWish = wishServiceImpl.create(wishDto);

        RoomDTO roomDTO = roomServiceImpl.getRoomById(idRoom);
        UserInfoDTO currentUser = userDetailsService.findUserByName(principal.getName());
        UserRoleWishRoomDTO userRoleWishRoomDTO = new UserRoleWishRoomDTO();
        RoleDTO roleDTO;

        if ((roomServiceImpl.getRoomOrganizer(roomDTO).getIdUserInfo()) == currentUser.getIdUserInfo()) {
            roleDTO = roleServiceImpl.getRoleById(Role.ORGANIZER.getId());
        } else {
            roleDTO = roleServiceImpl.getRoleById(Role.PARTICIPANT.getId());
        }

        userRoleWishRoomDTO.setUserInfoEntity(UserInfoMapper.toUserInfoEntity(currentUser));
        userRoleWishRoomDTO.setWishEntity(WishMapper.toWishEntity(savedWish));
        userRoleWishRoomDTO.setRoleEntity(RoleMapper.toRoleEntity(roleDTO));
        userRoleWishRoomDTO.setRoomEntity(RoomMapper.toRoomEntity(roomServiceImpl.findRoomByName(roomDTO.getName())));

        userRoleWishRoomServiceImpl.create(userRoleWishRoomDTO);

        model.addAttribute("room", roomServiceImpl.readAll());


        return "redirect:/room/show/" + idRoom;
    }

    @GetMapping("/{idRoom}/users-and-roles")
    public String getUsersAndRoles(@PathVariable("idRoom") int idRoom, Model model, Principal principal) {
        UserInfoDTO userInfoDTO = userDetailsService.findUserByName(principal.getName());
        RoomDTO roomDTO = roomServiceImpl.getRoomById(idRoom);

        UserInfoDTO organizer = roomServiceImpl.getRoomOrganizer(roomDTO);

        List<Object[]> usersAndRoles = roomServiceImpl.getUsersAndRolesByRoomId(idRoom);
        model.addAttribute("user", userInfoDTO);
        model.addAttribute("organizer", organizer);
        model.addAttribute("usersAndRoles", usersAndRoles);
        return "user-in-room";
    }

    @GetMapping("/show/participant")
    public String getRoomWhereJoin(Model model, Principal principal) {
        int idUser = userDetailsService.findUserByName(principal.getName()).getIdUserInfo();
        List<RoomDTO> rooms = roomServiceImpl.getRoomsWhereUserJoin(idUser);
        model.addAttribute("roomsDto", rooms);
        return "room-list";

    }

    @PostMapping("/{nameRoom}/users/{UserInfoName}")
    public String deleteUserFromRoom(@PathVariable("nameRoom") String nameRoom,
                                     @PathVariable("UserInfoName") String userInfoName, Principal principal,
                                     RedirectAttributes redirectAttributes) {

        UserInfoDTO userInfoDTO = userDetailsService.findUserByName(userInfoName);
        RoomDTO roomDTO = roomServiceImpl.getRoomByName(nameRoom);
        UserInfoDTO loginUser = userDetailsService.findUserByName(principal.getName());

        if (!(roomServiceImpl.getRoomOrganizer(roomDTO).getIdUserInfo() == loginUser.getIdUserInfo())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Не можешь удалять");
            return "redirect:/room/" + roomDTO.getIdRoom() + "/users-and-roles";
        }

        userRoleWishRoomServiceImpl.deleteUserFromRoom(roomDTO.getIdRoom(), userInfoDTO.getIdUserInfo());
        return "redirect:/room/" + roomDTO.getIdRoom() + "/users-and-roles";
    }

}
