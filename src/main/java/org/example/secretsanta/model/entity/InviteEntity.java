package org.example.secretsanta.model.entity;

import org.example.secretsanta.model.enums.Status;

import javax.persistence.*;

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
}
