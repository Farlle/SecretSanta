package org.example.secretsanta.model.entity;

import javax.persistence.*;

@Entity
@Table(name="wish")
public class WishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_wish")
    private int idWish;

    @Column(name = "wish", nullable = false)
    private String wish;

    public int getIdWish() {
        return idWish;
    }

    public void setIdWish(int idWish) {
        this.idWish = idWish;
    }

    public String getWish() {
        return wish;
    }

    public void setWish(String wish) {
        this.wish = wish;
    }
}
