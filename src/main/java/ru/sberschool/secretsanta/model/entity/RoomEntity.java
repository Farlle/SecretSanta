package ru.sberschool.secretsanta.model.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "room")
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_room")
    private int idRoom;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "id_Organizer", nullable = false)
    private int idOrganizer;
    @Column(name = "toss_date", nullable = false)
    private Date tossDate;
    @Column(name = "draw_date", nullable = false)
    private Date drawDate;
    @Column(name = "place", nullable = false)
    private String place;


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
        if (!(o instanceof RoomEntity)) return false;
        RoomEntity room = (RoomEntity) o;
        return idRoom == room.idRoom && idOrganizer == room.idOrganizer && Objects.equals(name, room.name)
                && Objects.equals(tossDate, room.tossDate)
                && Objects.equals(drawDate, room.drawDate) && Objects.equals(place, room.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRoom, name, idOrganizer, tossDate, drawDate, place);
    }
}
