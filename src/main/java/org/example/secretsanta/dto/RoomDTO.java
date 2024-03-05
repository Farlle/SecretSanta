package org.example.secretsanta.dto;

import org.example.secretsanta.model.entity.ResultEntity;

import java.sql.Date;

public class RoomDTO {

    private String name;
    private int idOrganizer;
    private Date tossDate;
    private Date drawDate;
    private String place;
    private ResultEntity resultEntity;


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

    public ResultEntity getResultEntity() {
        return resultEntity;
    }

    public void setResultEntity(ResultEntity resultEntity) {
        this.resultEntity = resultEntity;
    }
}
