package org.example.secretsanta.dto;

public class UserInfoDTO {
    private int idUserInfo;
    private String name;
    private String password;
    private String telegram;

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
}
