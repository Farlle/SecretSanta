package org.example.secretsanta.dto;

import java.util.Objects;

public class UserInfoDTO {
    private int idUserInfo;
    private String name;
    private String password;
    private String telegram;

    public UserInfoDTO(int idUserInfo, String name, String password, String telegram) {
        this.idUserInfo = idUserInfo;
        this.name = name;
        this.password = password;
        this.telegram = telegram;
    }

    public UserInfoDTO() {
    }

    public int getIdUserInfo() {
        return idUserInfo;
    }

    public void setIdUserInfo(int idUserInfo) {
        this.idUserInfo = idUserInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInfoDTO)) return false;
        UserInfoDTO that = (UserInfoDTO) o;
        return idUserInfo == that.idUserInfo && Objects.equals(name, that.name) && Objects.equals(password, that.password) && Objects.equals(telegram, that.telegram);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUserInfo, name, password, telegram);
    }
}
