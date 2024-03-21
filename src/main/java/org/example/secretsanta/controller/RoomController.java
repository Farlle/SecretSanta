package org.example.secretsanta.controller;

import org.example.secretsanta.dto.*;
import org.example.secretsanta.model.enums.Role;
import org.example.secretsanta.service.security.CustomUserDetailsService;
import org.example.secretsanta.service.serviceinterface.*;
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

    private final RoomService roomService;
    private final CustomUserDetailsService userDetailsService;
    private final WishService wishService;
    private final RoleService roleService;
    private final UserRoleWishRoomService userRoleWishRoomService;
    private final InviteService inviteService;


    public RoomController(RoomService roomService, CustomUserDetailsService userDetailsService,
                          WishService wishService, RoleService roleService,
                          UserRoleWishRoomService userRoleWishRoomService, InviteService inviteService) {

        this.roomService = roomService;
        this.userDetailsService = userDetailsService;
        this.wishService = wishService;
        this.roleService = roleService;
        this.userRoleWishRoomService = userRoleWishRoomService;
        this.inviteService = inviteService;
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
        return "redirect:/room/" + room.getIdRoom() + "/join";
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
        if ((!inviteService.checkInvite(currentUser.getTelegram(), idRoom)) &&
                (roomService.getRoomOrganizer(roomDTO).getIdUserInfo()) != currentUser.getIdUserInfo()) {
            model.addAttribute("errorMessage", "У вас нет приглашения!!!");
            return "join-room-page";
        }
        if (roomService.getRoomsWhereUserJoin(currentUser.getIdUserInfo())
                .contains(roomService.getRoomById(idRoom))) {
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

        userRoleWishRoomDTO.setUserInfoDTO(currentUser);
        userRoleWishRoomDTO.setWishDTO(savedWish);
        userRoleWishRoomDTO.setRoleDTO(roleDTO);
        userRoleWishRoomDTO.setRoomDTO(roomService.findRoomByName(roomDTO.getName()));

        userRoleWishRoomService.create(userRoleWishRoomDTO);

        model.addAttribute("room", roomService.readAll());

        if ((roomService.getRoomOrganizer(roomDTO).getIdUserInfo()) != currentUser.getIdUserInfo()) {
            inviteService.UserAcceptInvite(currentUser.getTelegram(), idRoom);
        }

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
    public String getRoomWhereJoin(Model model, Principal principal,
                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "size", defaultValue = "5") int size) {
        int idUser = userDetailsService.findUserByName(principal.getName()).getIdUserInfo();
        Page<RoomDTO> roomDTOPage = roomService.getRoomsWhereUserJoin(idUser, PageRequest.of(page, size));
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
