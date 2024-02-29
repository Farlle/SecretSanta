package org.example.secretsanta.model;

import java.util.Date;

public class RoomEntity {

    private int idRoom;
    private String name;
    private UserEntity organizer;
    private Date tossDate;
    private Date drawDate;
    private String place;
    private int idResultDraw;

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserEntity getOrganizer() {
        return organizer;
    }

    public void setOrganizer(UserEntity organizer) {
        this.organizer = organizer;
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

    public int getIdResultDraw() {
        return idResultDraw;
    }

    public void setIdResultDraw(int idResultDraw) {
        this.idResultDraw = idResultDraw;
    }
}
