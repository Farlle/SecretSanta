package org.example.secretsanta.controller;


import org.example.secretsanta.dto.*;
import org.example.secretsanta.model.enums.Role;
import org.example.secretsanta.service.impl.*;
import org.example.secretsanta.service.security.CustomUserDetailsService;
import org.example.secretsanta.wrapper.RoomAndOrganizerWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private final InviteServiceImpl inviteServiceImpl;


    public RoomController(RoomServiceImpl roomServiceImpl, CustomUserDetailsService userDetailsService,
                          WishServiceImpl wishServiceImpl, RoleServiceImpl roleServiceImpl,
                          UserRoleWishRoomServiceImpl userRoleWishRoomServiceImpl, InviteServiceImpl inviteServiceImpl) {

        this.roomServiceImpl = roomServiceImpl;
        this.userDetailsService = userDetailsService;
        this.wishServiceImpl = wishServiceImpl;
        this.roleServiceImpl = roleServiceImpl;
        this.userRoleWishRoomServiceImpl = userRoleWishRoomServiceImpl;
        this.inviteServiceImpl = inviteServiceImpl;
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
        return "redirect:/room/" + room.getIdRoom() + "/join";
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
        if ((!inviteServiceImpl.checkInvite(currentUser.getTelegram(), idRoom)) &&
                (roomServiceImpl.getRoomOrganizer(roomDTO).getIdUserInfo()) != currentUser.getIdUserInfo()) {
            model.addAttribute("errorMessage", "У вас нет приглашения!!!");
            return "join-room-page";
        }
        if (roomServiceImpl.getRoomsWhereUserJoin(currentUser.getIdUserInfo())
                .contains(roomServiceImpl.getRoomById(idRoom))) {
            model.addAttribute("errorMessage", "Вы уже являетесь участником этой комнаты.");
            return "join-room-page";
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

        userRoleWishRoomDTO.setUserInfoDTO(currentUser);
        userRoleWishRoomDTO.setWishDTO(savedWish);
        userRoleWishRoomDTO.setRoleDTO(roleDTO);
        userRoleWishRoomDTO.setRoomDTO(roomServiceImpl.findRoomByName(roomDTO.getName()));

        userRoleWishRoomServiceImpl.create(userRoleWishRoomDTO);

        model.addAttribute("room", roomServiceImpl.readAll());

        if ((roomServiceImpl.getRoomOrganizer(roomDTO).getIdUserInfo()) != currentUser.getIdUserInfo()) {
            inviteServiceImpl.UserAcceptInvite(currentUser.getTelegram(), idRoom);
        }

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
    public String getRoomWhereJoin(Model model, Principal principal,
                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "size", defaultValue = "5") int size) {
        int idUser = userDetailsService.findUserByName(principal.getName()).getIdUserInfo();
        Page<RoomDTO> roomDTOPage = roomServiceImpl.getRoomsWhereUserJoin(idUser, PageRequest.of(page, size));
        model.addAttribute("roomsDto", roomDTOPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", roomDTOPage.getTotalPages());

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
