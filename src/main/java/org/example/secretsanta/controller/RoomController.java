package org.example.secretsanta.controller;


import org.example.secretsanta.dto.RoomDTO;
import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.dto.UserRoleWishRoomDTO;
import org.example.secretsanta.dto.WishDTO;
import org.example.secretsanta.mapper.RoomMapper;
import org.example.secretsanta.mapper.UserInfoMapper;
import org.example.secretsanta.model.entity.RoleEntity;
import org.example.secretsanta.model.entity.RoomEntity;
import org.example.secretsanta.model.entity.UserInfoEntity;
import org.example.secretsanta.model.entity.WishEntity;
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
            UserInfoEntity user = userDetailsService.findUserByName(username);
            if (user == null) {
                throw new UsernameNotFoundException("UserInfo not found with user:" + user);
            }
            roomDTO.setIdOrganizer(user.getId());
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
        RoomEntity room = roomService.create(dto);
        model.addAttribute("roomEntity", room);
        return "redirect:/room/show";
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
    public String getAllRoom(Model model) {
        model.addAttribute("roomsDto", RoomMapper.toRoomDTOList(roomService.readAll()));
        return "room-list";
    }

    @GetMapping("/show/{idRoom}")
    public String getRoomById(@PathVariable("idRoom") int idRoom, Model model, Principal principal) {

        RoomDTO room = RoomMapper.toRoomDTO(roomService.getRoomEntityById(idRoom));
        UserInfoDTO loginUser = UserInfoMapper.toUserInfoDTO(userDetailsService.findUserByName(principal.getName()));
        UserInfoDTO organizer = UserInfoMapper.toUserInfoDTO(roomService.getRoomOrganizer(room));

        RoomAndOrganizerWrapper roomAndOrganizerWrapper = new RoomAndOrganizerWrapper(room, organizer);

        model.addAttribute("loginUser", loginUser);
        model.addAttribute("roomAndOrganizerWrapper", roomAndOrganizerWrapper);

        return "room-show";
    }

    @GetMapping("/{id}/join")
    public String joinRoomForm(@PathVariable("id") int idRoom, Model model, Principal principal) {
        UserInfoEntity currentUser = userDetailsService.findUserByName(principal.getName());

        RoomDTO roomDTO = RoomMapper.toRoomDTO(roomService.getRoomEntityById(idRoom));
        if (roomService.getRoomsWhereUserJoin(currentUser.getId()).contains(roomService.getRoomEntityById(idRoom))) {
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

        WishEntity savedWish = wishService.create(wishDto);

        RoomDTO roomDTO = RoomMapper.toRoomDTO(roomService.getRoomEntityById(idRoom));
        UserInfoEntity currentUser = userDetailsService.findUserByName(principal.getName());
        UserRoleWishRoomDTO userRoleWishRoomDTO = new UserRoleWishRoomDTO();
        RoleEntity roleEntity;

        if (roomService.getRoomOrganizer(roomDTO).equals(currentUser)) {
            roleEntity = roleService.getRoleById(Role.ORGANIZER.getId());
        } else {
            roleEntity = roleService.getRoleById(Role.PARTICIPANT.getId());
        }

        userRoleWishRoomDTO.setUserInfoEntity(currentUser);
        userRoleWishRoomDTO.setWishEntity(savedWish);
        userRoleWishRoomDTO.setRoleEntity(roleEntity);
        userRoleWishRoomDTO.setRoomEntity(roomService.findRoomByName(roomDTO.getName()));

        userRoleWishRoomService.create(userRoleWishRoomDTO);

        model.addAttribute("roomEntity", roomService.readAll());


        return "redirect:/room/show/" + idRoom;
    }

    @GetMapping("/{idRoom}/users-and-roles")
    public String getUsersAndRoles(@PathVariable("idRoom") int idRoom, Model model, Principal principal) {
        UserInfoDTO userInfoDTO = UserInfoMapper.toUserInfoDTO(userDetailsService.findUserByName(principal.getName()));
        RoomDTO roomDTO = RoomMapper.toRoomDTO(roomService.getRoomEntityById(idRoom));
        if (UserInfoMapper.toUserInfoDTO(roomService.getRoomOrganizer(roomDTO)).equals(userInfoDTO)) {
            model.addAttribute("user", userInfoDTO);
        }

        List<Object[]> usersAndRoles = roomService.getUsersAndRolesByRoomId(idRoom);
        model.addAttribute("usersAndRoles", usersAndRoles);
        return "user-in-room";
    }

    @GetMapping("/show/participant")
    public String getRoomWhereJoin(Model model, Principal principal) {
        int idUser = userDetailsService.findUserByName(principal.getName()).getId();
        List<RoomDTO> rooms = RoomMapper.toRoomDTOList(roomService.getRoomsWhereUserJoin(idUser));
        model.addAttribute("roomsDto", rooms);
        return "room-list";

    }

    @PostMapping("/{nameRoom}/users/{UserInfoName}")
    public String deleteUserFromRoom(@PathVariable("nameRoom") String nameRoom,
                                     @PathVariable("UserInfoName") String userInfoName) {

        UserInfoDTO user = UserInfoMapper.toUserInfoDTO(userDetailsService.findUserByName(userInfoName));
        RoomDTO room = RoomMapper.toRoomDTO(roomService.getRoomByName(nameRoom));
        userRoleWishRoomService.deleteUserEntityFromRoom(room.getIdRoom(), user.getIdUserInfo());
        return "redirect:/room/" + room.getIdRoom() + "/users-and-roles";
    }

}
