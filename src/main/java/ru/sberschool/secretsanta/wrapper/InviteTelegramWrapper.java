package ru.sberschool.secretsanta.wrapper;

import ru.sberschool.secretsanta.dto.RoomDTO;
import ru.sberschool.secretsanta.dto.UserInfoDTO;

public class InviteTelegramWrapper {

    private RoomDTO room;
    private UserInfoDTO santa;
    private String participantTelegram;

    public InviteTelegramWrapper(RoomDTO room, UserInfoDTO santa, String participantTelegram) {
        this.room = room;
        this.santa = santa;
        this.participantTelegram = participantTelegram;
    }

    public InviteTelegramWrapper() {
    }

    public RoomDTO getRoom() {
        return room;
    }

    public void setRoom(RoomDTO room) {
        this.room = room;
    }

    public UserInfoDTO getSanta() {
        return santa;
    }

    public void setSanta(UserInfoDTO santa) {
        this.santa = santa;
    }

    public String getParticipantTelegram() {
        return participantTelegram;
    }

    public void setParticipantTelegram(String participantTelegram) {
        this.participantTelegram = participantTelegram;
    }
}
