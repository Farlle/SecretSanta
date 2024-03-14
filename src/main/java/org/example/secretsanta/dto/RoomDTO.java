package org.example.secretsanta.dto;

import java.sql.Date;
import java.util.Objects;

public class RoomDTO {

    private int idRoom;
    private String name;
    private int idOrganizer;
    private Date tossDate;
    private Date drawDate;
    private String place;

    public RoomDTO(int idRoom, String name, int idOrganizer, Date tossDate, Date drawDate, String place) {
        this.idRoom = idRoom;
        this.name = name;
        this.idOrganizer = idOrganizer;
        this.tossDate = tossDate;
        this.drawDate = drawDate;
        this.place = place;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    public RoomDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdOrganizer() {
        return idOrganizer;
    }

    public void setIdOrganizer(int idOrganizer) {
        this.idOrganizer = idOrganizer;
    }

    public Date getTossDate() {
        return tossDate;
    }

    public void setTossDate(Date tossDate) {
        this.tossDate = tossDate;
    }

    public Date getDrawDate() {
        return drawDate;
    }

    public void setDrawDate(Date drawDate) {
        this.drawDate = drawDate;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomDTO roomDTO = (RoomDTO) o;
        return idRoom == roomDTO.idRoom && idOrganizer == roomDTO.idOrganizer && Objects.equals(name, roomDTO.name) && Objects.equals(tossDate, roomDTO.tossDate) && Objects.equals(drawDate, roomDTO.drawDate) && Objects.equals(place, roomDTO.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRoom, name, idOrganizer, tossDate, drawDate, place);
    }
}
