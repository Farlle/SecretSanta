package org.example.secretsanta.service.impl;

import org.example.secretsanta.dto.InviteDTO;
import org.example.secretsanta.dto.RoomDTO;
import org.example.secretsanta.mapper.InviteMapper;
import org.example.secretsanta.mapper.UserInfoMapper;
import org.example.secretsanta.model.entity.InviteEntity;
import org.example.secretsanta.model.enums.Status;
import org.example.secretsanta.repository.InviteRepository;
import org.example.secretsanta.service.InviteService;
import org.example.secretsanta.service.RoomService;
import org.example.secretsanta.service.TelegramService;
import org.example.secretsanta.service.UserInfoTelegramChatsService;
import org.example.secretsanta.utils.UrlUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Сервис для работы с приглашениями
 */
@Service
public class InviteServiceImpl implements InviteService {

    private final InviteRepository inviteRepository;
    private final RoomService roomService;
    private final TelegramService telegramService;
    private final UserInfoTelegramChatsService userInfoTelegramChatsService;
    private static final String HOST = "http://localhost:8080/room/";
    private static final String YOU_INVITE = "Тебя пригласили в комнату ";
    private static final String ACCEPT = " присоединяйся по ссылке ";
    private static final String JOIN = "/join";

    public InviteServiceImpl(InviteRepository inviteRepository, RoomService roomService,
                             TelegramService telegramService,
                             UserInfoTelegramChatsService userInfoTelegramChatsService) {
        this.inviteRepository = inviteRepository;
        this.roomService = roomService;
        this.telegramService = telegramService;
        this.userInfoTelegramChatsService = userInfoTelegramChatsService;
    }

    /**
     * Метод для создания нового пригалешения
     *
     * @param dto Объект который надо сохранить
     * @return Созданный объект
     */
    @Override
    public InviteDTO create(InviteDTO dto) {
        InviteEntity invite = new InviteEntity();
        invite.setTelegram(dto.getTelegram());
        invite.setStatus(dto.getStatus());
        invite.setUserInfo(UserInfoMapper.toUserInfoEntity(dto.getUserInfoDTO()));
        invite.setText(dto.getText());

        return InviteMapper.toInviteDTO(inviteRepository.save(invite));
    }

    /**
     * Метод для получения всех приглашений
     *
     * @return Список всех приглашений
     */
    @Override
    public List<InviteDTO> readAll() {
        return InviteMapper.toInviteDTOList(inviteRepository.findAll());
    }

    /**
     * Метод для обновления информации о приглашении
     *
     * @param id Идентификатор приглашения
     * @param dto Ообъект который необходимо сохранить
     * @return Сохраненный объект
     */
    @Override
    public InviteDTO update(int id, InviteDTO dto) {
        InviteEntity inviteEntity = inviteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invite not found with id: " + id));

        inviteEntity.setUserInfo(UserInfoMapper.toUserInfoEntity(dto.getUserInfoDTO()));
        inviteEntity.setStatus(dto.getStatus());
        inviteEntity.setTelegram(dto.getTelegram());
        inviteEntity.setText(dto.getText());

        return InviteMapper.toInviteDTO(inviteRepository.save(inviteEntity));
    }

    /**
     * Метод для удаления приглашения
     *
     * @param id идентификатор удаляемого приглашения
     */
    @Override
    public void delete(int id) {
        inviteRepository.deleteById(id);
    }

    /**
     * Метод ядл отправки приглагения в телегарм
     *
     * @param idRoom Идентификатор комнаты
     * @param inviteDTO Приглашение, которое надо отправить
     */
    @Transactional
    @Override
    public void sendInvite(int idRoom, InviteDTO inviteDTO) {

        inviteDTO.setStatus(Status.SENT);
        inviteDTO.setText(generatedTextInvite(idRoom));
        telegramService.sendMessage(userInfoTelegramChatsService
                .getIdChatByTelegramName(inviteDTO.getTelegram()), inviteDTO.getText());
        create(inviteDTO);
    }

    /**
     * Метод для проверки наличия приглашения в комнату
     *
     * @param telegram Ник пользователя в телеграм
     * @param idRoom Идентификатор комнаты
     * @return true если приглашений найден, false если не найдено
     */
    @Override
    public boolean checkInvite(String telegram, int idRoom) {
        List<InviteDTO> allUsersInvite = getAllUsersInvite(telegram);
        for (InviteDTO inviteDTO : allUsersInvite) {
            if (idRoom == Integer.parseInt(UrlUtils.extractRoomNumberFromUrl(inviteDTO.getText()))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Метод для получения всех приглашений пользователя
     *
     * @param telegram Телеграм пользвоателя
     * @return Список всех приглашений пользователя
     */
    @Override
    public List<InviteDTO> getAllUsersInvite(String telegram) {
        return InviteMapper.toInviteDTOList(inviteRepository.getAllUsersInvite(telegram));
    }

    /**
     * Метод для поддтверждения принятия приглашеняи пользователем
     *
     * @param telegram Телеграм пользователя
     * @param idRoom Идентификатор комнаты
     */
    @Override
    public void userAcceptInvite(String telegram, int idRoom) {
        List<InviteDTO> inviteDTOs = InviteMapper.toInviteDTOList(
                inviteRepository.getAllInviteUsersInRoom(telegram, generatedTextInvite(idRoom)));
        for (InviteDTO inviteDTO : inviteDTOs) {
            inviteDTO.setStatus(Status.ACCEPTED);
            update(inviteDTO.getIdInvite(), inviteDTO);
        }
    }

    /**
     * Метод для генерации сообщения пользователю
     *
     * @param idRoom Идентификатор пользоватлея
     * @return Текст приглашения
     */
    @Override
    public String generatedTextInvite(int idRoom) {
        RoomDTO roomDTO = roomService.getRoomById(idRoom);
        return YOU_INVITE + roomDTO.getName()
                + ACCEPT + HOST + roomDTO.getIdRoom() + JOIN;

    }

}
