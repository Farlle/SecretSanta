package org.example.secretsanta.wrapper;

import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.dto.WishDTO;

public class ResultWrapper {

    private UserInfoDTO santa;
    private UserInfoDTO ward;

    private WishDTO wish;

    public ResultWrapper(UserInfoDTO santa, UserInfoDTO ward, WishDTO wish) {
        this.santa = santa;
        this.ward = ward;
        this.wish = wish;
    }

    public UserInfoDTO getSanta() {
        return santa;
    }

    public void setSanta(UserInfoDTO santa) {
        this.santa = santa;
    }

    public UserInfoDTO getWard() {
        return ward;
    }

    public void setWard(UserInfoDTO ward) {
        this.ward = ward;
    }

    public WishDTO getWish() {
        return wish;
    }

    public void setWish(WishDTO wish) {
        this.wish = wish;
    }
}
