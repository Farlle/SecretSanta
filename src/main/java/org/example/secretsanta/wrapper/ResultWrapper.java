package org.example.secretsanta.wrapper;

import org.example.secretsanta.model.entity.UserInfoEntity;
import org.example.secretsanta.model.entity.WishEntity;

public class ResultWrapper {

    private UserInfoEntity santa;
    private UserInfoEntity ward;

    private WishEntity wish;

    public ResultWrapper(UserInfoEntity santa, UserInfoEntity ward, WishEntity wish) {
        this.santa = santa;
        this.ward = ward;
        this.wish = wish;
    }

    public UserInfoEntity getSanta() {
        return santa;
    }

    public void setSanta(UserInfoEntity santa) {
        this.santa = santa;
    }

    public UserInfoEntity getWard() {
        return ward;
    }

    public void setWard(UserInfoEntity ward) {
        this.ward = ward;
    }

    public WishEntity getWish() {
        return wish;
    }

    public void setWish(WishEntity wish) {
        this.wish = wish;
    }
}
