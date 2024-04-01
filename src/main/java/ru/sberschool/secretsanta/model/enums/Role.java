package ru.sberschool.secretsanta.model.enums;

public enum Role {

    PARTICIPANT(1),
    ORGANIZER(2),
    ;

    private int id;

    Role(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }
}
