package org.example.secretsanta.model.entity;

import org.example.secretsanta.model.enums.Status;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "invite")
public class InviteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_invite", nullable = false)
    private int idInvite;
    @ManyToOne
    @JoinColumn(name = "id_user_info", nullable = false)
    private UserInfoEntity userInfo;
    @Column(name = "telegram", nullable = false)
    private String telegram;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "text", nullable = false)
    private String text;

    public InviteEntity() {
    }

    public InviteEntity(int idInvite, UserInfoEntity userInfo, String telegram, Status status, String text) {
        this.idInvite = idInvite;
        this.userInfo = userInfo;
        this.telegram = telegram;
        this.status = status;
        this.text = text;
    }

    public int getIdInvite() {
        return idInvite;
    }

    public void setIdInvite(int idInvite) {
        this.idInvite = idInvite;
    }

    public UserInfoEntity getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoEntity userInfo) {
        this.userInfo = userInfo;
    }

    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InviteEntity)) return false;
        InviteEntity that = (InviteEntity) o;
        return idInvite == that.idInvite && Objects.equals(userInfo, that.userInfo)
                && Objects.equals(telegram, that.telegram) && status == that.status && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idInvite, userInfo, telegram, status, text);
    }
}
