package ru.sberschool.secretsanta.dto;

import java.util.Objects;

public class UserInfoTelegramChatsDTO {

    private int idUserInfoTelegramChat;
    private Long idChat;
    private UserInfoDTO userInfoDTO;

    public int getIdUserInfoTelegramChat() {
        return idUserInfoTelegramChat;
    }

    public void setIdUserInfoTelegramChat(int idUserInfoTelegramChat) {
        this.idUserInfoTelegramChat = idUserInfoTelegramChat;
    }

    public Long getIdChat() {
        return idChat;
    }

    public void setIdChat(Long idChat) {
        this.idChat = idChat;
    }

    public UserInfoDTO getUserInfoDTO() {
        return userInfoDTO;
    }

    public void setUserInfoDTO(UserInfoDTO userInfoDTO) {
        this.userInfoDTO = userInfoDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInfoTelegramChatsDTO)) return false;
        UserInfoTelegramChatsDTO that = (UserInfoTelegramChatsDTO) o;
        return idUserInfoTelegramChat == that.idUserInfoTelegramChat && Objects.equals(idChat, that.idChat)
                && Objects.equals(userInfoDTO, that.userInfoDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUserInfoTelegramChat, idChat, userInfoDTO);
    }
}
