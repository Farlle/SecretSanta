package ru.sberschool.secretsanta.wrapper;

import ru.sberschool.secretsanta.dto.RoomDTO;
import ru.sberschool.secretsanta.dto.UserInfoDTO;

public class RoomAndOrganizerWrapper {
    private RoomDTO room;
    private UserInfoDTO userInfo;

    public RoomAndOrganizerWrapper(RoomDTO room, UserInfoDTO userInfo) {
        this.room = room;
        this.userInfo = userInfo;
    }

    public RoomDTO getRoom() {
        return room;
    }

    public void setRoom(RoomDTO room) {
        this.room = room;
    }

    public UserInfoDTO getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoDTO userInfo) {
        this.userInfo = userInfo;
    }
}
