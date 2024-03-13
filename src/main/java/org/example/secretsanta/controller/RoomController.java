package org.example.secretsanta.controller;


import org.example.secretsanta.dto.*;
import org.example.secretsanta.mapper.RoleMapper;
import org.example.secretsanta.mapper.RoomMapper;
import org.example.secretsanta.mapper.UserInfoMapper;
import org.example.secretsanta.mapper.WishMapper;
import org.example.secretsanta.model.enums.Role;
import org.example.secretsanta.service.RoleService;
import org.example.secretsanta.service.RoomService;
import org.example.secretsanta.service.UserRoleWishRoomService;
import org.example.secretsanta.service.WishService;
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

    private final RoomService roomService;
    private final CustomUserDetailsService userDetailsService;
    private final WishService wishService;
    private final RoleService roleService;
    private final UserRoleWishRoomService userRoleWishRoomService;

    public RoomController(RoomService roomService, CustomUserDetailsService userDetailsService,
                          WishService wishService, RoleService roleService,
                          UserRoleWishRoomService userRoleWishRoomService) {

        this.roomService = roomService;
        this.userDetailsService = userDetailsService;
        this.wishService = wishService;
        this.roleService = roleService;
        this.userRoleWishRoomService = userRoleWishRoomService;
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
        RoomDTO room = roomService.create(dto);
        model.addAttribute("room", room);
        return "redirect:/room/show";
    }

    @GetMapping("/update/{id}")
    public String updateRoom(@PathVariable int id, Model model) {
        RoomDTO room = roomService.getRoomById(id);
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
    public String getAllRoom(Model model) {
        model.addAttribute("roomsDto", roomService.readAll());
        return "room-list";
    }

    @GetMapping("/show/{idRoom}")
    public String getRoomById(@PathVariable("idRoom") int idRoom, Model model, Principal principal) {

        RoomDTO room = roomService.getRoomById(idRoom);
        UserInfoDTO loginUser = userDetailsService.findUserByName(principal.getName());
        UserInfoDTO organizer = roomService.getRoomOrganizer(room);

        RoomAndOrganizerWrapper roomAndOrganizerWrapper = new RoomAndOrganizerWrapper(room, organizer);

        model.addAttribute("loginUser", loginUser);
        model.addAttribute("roomAndOrganizerWrapper", roomAndOrganizerWrapper);

        return "room-show";
    }

    @GetMapping("/{id}/join")
    public String joinRoomForm(@PathVariable("id") int idRoom, Model model, Principal principal) {
        UserInfoDTO currentUser = userDetailsService.findUserByName(principal.getName());

        RoomDTO roomDTO = roomService.getRoomById(idRoom);
        if (roomService.getRoomsWhereUserJoin(currentUser.getIdUserInfo())
                .contains(roomService.getRoomById(idRoom))) {
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

        WishDTO savedWish = wishService.create(wishDto);

        RoomDTO roomDTO = roomService.getRoomById(idRoom);
        UserInfoDTO currentUser = userDetailsService.findUserByName(principal.getName());
        UserRoleWishRoomDTO userRoleWishRoomDTO = new UserRoleWishRoomDTO();
        RoleDTO roleDTO;

        if ((roomService.getRoomOrganizer(roomDTO).getIdUserInfo()) == currentUser.getIdUserInfo()) {
            roleDTO = roleService.getRoleById(Role.ORGANIZER.getId());
        } else {
            roleDTO = roleService.getRoleById(Role.PARTICIPANT.getId());
        }

        userRoleWishRoomDTO.setUserInfoEntity(UserInfoMapper.toUserInfoEntity(currentUser));
        userRoleWishRoomDTO.setWishEntity(WishMapper.toWishEntity(savedWish));
        userRoleWishRoomDTO.setRoleEntity(RoleMapper.toRoleEntity(roleDTO));
        userRoleWishRoomDTO.setRoomEntity(RoomMapper.toRoomEntity(roomService.findRoomByName(roomDTO.getName())));

        userRoleWishRoomService.create(userRoleWishRoomDTO);

        model.addAttribute("room", roomService.readAll());


        return "redirect:/room/show/" + idRoom;
    }

    @GetMapping("/{idRoom}/users-and-roles")
    public String getUsersAndRoles(@PathVariable("idRoom") int idRoom, Model model, Principal principal) {
        UserInfoDTO userInfoDTO = userDetailsService.findUserByName(principal.getName());
        RoomDTO roomDTO = roomService.getRoomById(idRoom);

        UserInfoDTO organizer = roomService.getRoomOrganizer(roomDTO);

        List<Object[]> usersAndRoles = roomService.getUsersAndRolesByRoomId(idRoom);
        model.addAttribute("user", userInfoDTO);
        model.addAttribute("organizer", organizer);
        model.addAttribute("usersAndRoles", usersAndRoles);
        return "user-in-room";
    }

    @GetMapping("/show/participant")
    public String getRoomWhereJoin(Model model, Principal principal) {
        int idUser = userDetailsService.findUserByName(principal.getName()).getIdUserInfo();
        List<RoomDTO> rooms = roomService.getRoomsWhereUserJoin(idUser);
        model.addAttribute("roomsDto", rooms);
        return "room-list";

    }

    @PostMapping("/{nameRoom}/users/{UserInfoName}")
    public String deleteUserFromRoom(@PathVariable("nameRoom") String nameRoom,
                                     @PathVariable("UserInfoName") String userInfoName, Principal principal,
                                     RedirectAttributes redirectAttributes) {

        UserInfoDTO userInfoDTO = userDetailsService.findUserByName(userInfoName);
        RoomDTO roomDTO = roomService.getRoomByName(nameRoom);
        UserInfoDTO loginUser = userDetailsService.findUserByName(principal.getName());

        if (!(roomService.getRoomOrganizer(roomDTO).getIdUserInfo() == loginUser.getIdUserInfo())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Не можешь удалять");
            return "redirect:/room/" + roomDTO.getIdRoom() + "/users-and-roles";
        }

        userRoleWishRoomService.deleteUserFromRoom(roomDTO.getIdRoom(), userInfoDTO.getIdUserInfo());
        return "redirect:/room/" + roomDTO.getIdRoom() + "/users-and-roles";
    }

}
