package com.example.testapp;

import java.util.Objects;

public class Adv {

    private String adv;

    public Adv(String adv){
        this.adv=adv;
    }

    public String getAdv() {
        return this.adv;
    }

    public void setAdv(String res) {
        this.adv = adv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adv adv1 = (Adv) o;
        return Objects.equals(adv, adv1.adv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adv);
    }

    @Override
    public String toString() {
        return "Adv{" +
                "adv='" + adv + '\'' +
                '}';
    }
}
