package org.example.secretsanta.dto;

import org.example.secretsanta.model.entity.RoomEntity;

import java.util.Objects;

public class ResultDTO {

    private int idResult;
    private int idSanta;
    private int idWard;
    private RoomDTO roomDTO;

    public ResultDTO() {
    }

    public ResultDTO(int idResult, int idSanta, int idWard, RoomDTO roomDTO) {
        this.idResult = idResult;
        this.idSanta = idSanta;
        this.idWard = idWard;
        this.roomDTO = roomDTO;
    }

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

    public RoomDTO getRoomDTO() {
        return roomDTO;
    }

    public void setRoomDTO(RoomDTO roomDTO) {
        this.roomDTO = roomDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResultDTO)) return false;
        ResultDTO resultDTO = (ResultDTO) o;
        return idResult == resultDTO.idResult && idSanta == resultDTO.idSanta && idWard == resultDTO.idWard
                && Objects.equals(roomDTO, resultDTO.roomDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idResult, idSanta, idWard, roomDTO);
    }
}
