package ru.sberschool.secretsanta.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_info_telegram_chats")
public class UserInfoTelegramChatsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user_info_telegram_chat")
    private int idUserInfoTelegramChat;

    @Column(name = "id_chat")
    private Long idChat;

    @OneToOne
    @JoinColumn(name = "id_user_info", referencedColumnName = "id_user_info")
    private UserInfoEntity userInfo;

    public UserInfoTelegramChatsEntity(int idUserInfoTelegramChat, Long idChat, UserInfoEntity userInfo) {
        this.idUserInfoTelegramChat = idUserInfoTelegramChat;
        this.idChat = idChat;
        this.userInfo = userInfo;
    }

    public UserInfoTelegramChatsEntity() {

    }

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

    public UserInfoEntity getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoEntity userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInfoTelegramChatsEntity)) return false;
        UserInfoTelegramChatsEntity that = (UserInfoTelegramChatsEntity) o;
        return idUserInfoTelegramChat == that.idUserInfoTelegramChat && Objects.equals(idChat, that.idChat)
                && Objects.equals(userInfo, that.userInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUserInfoTelegramChat, idChat, userInfo);
    }
}
