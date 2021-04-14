package com.example.rafwallet.models;

import java.io.Serializable;
import java.util.Objects;

public class Transaction implements Serializable {
    private int id;
    private String naslov;
    private int kolicina;
    private String opis;
    private boolean prihod;
    private String filePath = null;

    public Transaction(int id, String naslov, int kolicina, String opis, boolean prihod) {
        this.id = id;
        this.naslov = naslov;
        this.kolicina = kolicina;
        this.opis = opis;
        this.prihod = prihod;
    }

    public Transaction(int id, String naslov, int kolicina, boolean prihod, String filePath) {
        this.id = id;
        this.naslov = naslov;
        this.kolicina = kolicina;
        this.prihod = prihod;
        this.filePath = filePath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getNaslov() {
        return naslov;
    }

    public int getKolicina() {
        return kolicina;
    }

    public String getOpis() {
        return opis;
    }

    public int getId() {
        return id;
    }

    public boolean isPrihod() {
        return prihod;
    }

    public void setPrihod(boolean prihod) {
        this.prihod = prihod;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
