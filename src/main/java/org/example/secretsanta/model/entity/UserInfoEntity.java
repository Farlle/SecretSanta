package org.example.secretsanta.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="user_info")
public class UserInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user_info")
    private int idUserInfo;
    @Column(name ="name", nullable = false)
    private String name;
    @Column(name ="password", nullable = false)
    private String password;
    @Column(name ="telegram", nullable = false)
    private String telegram;

    public UserInfoEntity() {
    }

    public UserInfoEntity(int idUserInfo, String name, String password, String telegram) {
        this.idUserInfo = idUserInfo;
        this.name = name;
        this.password = password;
        this.telegram = telegram;
    }

    public int getId() {
        return idUserInfo;
    }
    public void setId(int id) {
        this.idUserInfo = id;
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
        if (!(o instanceof UserInfoEntity)) return false;
        UserInfoEntity userInfo = (UserInfoEntity) o;
        return idUserInfo == userInfo.idUserInfo && Objects.equals(name, userInfo.name)
                && Objects.equals(password, userInfo.password) && Objects.equals(telegram, userInfo.telegram);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUserInfo, name, password, telegram);
    }
}
