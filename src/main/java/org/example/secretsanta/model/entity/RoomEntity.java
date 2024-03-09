package org.example.secretsanta.model.entity;

import javax.persistence.*;
import java.sql.Date;

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
    @OneToOne
    @JoinColumn(name = "id_result_draw")
    private ResultEntity result;

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

    public ResultEntity getResult() {
        return result;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }
}
