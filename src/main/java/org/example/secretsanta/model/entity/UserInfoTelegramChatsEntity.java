package org.example.secretsanta.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "user_info_telegram_chats")
public class UserInfoTelegramChatsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user_info_telegram_chat")
    private int idUserInfoTelegramChat;

    @Column(name = "id_chat")
    private int idChat;

    @OneToOne
    @JoinColumn(name = "id_user_info", referencedColumnName = "id_user_info")
    private UserInfoEntity userInfo;

    public UserInfoTelegramChatsEntity(int idUserInfoTelegramChat, int idChat, UserInfoEntity userInfo) {
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

    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    public UserInfoEntity getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoEntity userInfo) {
        this.userInfo = userInfo;
    }
}
