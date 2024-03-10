package org.example.secretsanta.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "result")
public class ResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_result")
    private int idResult;
    @Column(name = "id_santa", nullable = false)
    private int idSanta;
    @Column(name = "id_ward", nullable = false)
    private int idWard;

    @ManyToOne
    @JoinColumn(name = "id_room")
    private RoomEntity room;

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

    public RoomEntity getRoom() {
        return room;
    }

    public void setRoom(RoomEntity room) {
        this.room = room;
    }
}
