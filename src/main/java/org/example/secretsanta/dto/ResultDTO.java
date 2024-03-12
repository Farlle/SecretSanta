package org.example.secretsanta.dto;

import org.example.secretsanta.model.entity.RoomEntity;

public class ResultDTO {

    private int idResult;
    private int idSanta;
    private int idWard;
    private RoomEntity roomEntity;

    public int getIdResult() {
        return idResult;
    }

    public void setIdResult(int idResult) {
        this.idResult = idResult;
    }

    public int getIdSanta() {
        return idSanta;
    }

    public void setIdSanta(int idSanta) {
        this.idSanta = idSanta;
    }

    public int getIdWard() {
        return idWard;
    }

    public void setIdWard(int idWard) {
        this.idWard = idWard;
    }

    public RoomEntity getRoomEntity() {
        return roomEntity;
    }

    public void setRoomEntity(RoomEntity roomEntity) {
        this.roomEntity = roomEntity;
    }
}
