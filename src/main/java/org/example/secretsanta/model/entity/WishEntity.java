package org.example.secretsanta.model.entity;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WishEntity)) return false;
        WishEntity wish1 = (WishEntity) o;
        return idWish == wish1.idWish && Objects.equals(wish, wish1.wish);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idWish, wish);
    }
}
