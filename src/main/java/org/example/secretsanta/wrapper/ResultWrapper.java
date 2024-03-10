package org.example.secretsanta.wrapper;

import org.example.secretsanta.model.entity.UserInfoEntity;

public class ResultWrapper {

    private UserInfoEntity santa;
    private UserInfoEntity ward;

    public ResultWrapper(UserInfoEntity santa, UserInfoEntity ward) {
        this.santa = santa;
        this.ward = ward;
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
}
