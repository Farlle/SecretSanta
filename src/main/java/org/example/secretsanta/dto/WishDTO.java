package org.example.secretsanta.dto;

import java.util.Objects;

public class WishDTO {

    private int idWish;
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
        if (!(o instanceof WishDTO)) return false;
        WishDTO wishDTO = (WishDTO) o;
        return idWish == wishDTO.idWish && Objects.equals(wish, wishDTO.wish);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idWish, wish);
    }
}
