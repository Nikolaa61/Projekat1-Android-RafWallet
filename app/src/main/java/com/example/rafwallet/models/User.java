package com.example.rafwallet.models;

import java.io.Serializable;

public class User implements Serializable {
    private String ime;
    private String prezime;
    private String imeBanke;
    private String lozinka;

    public User(String ime, String prezime, String imeBanke, String lozinka) {
        this.ime = ime;
        this.prezime = prezime;
        this.imeBanke = imeBanke;
        this.lozinka = lozinka;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public void setImeBanke(String imeBanke) {
        this.imeBanke = imeBanke;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getImeBanke() {
        return imeBanke;
    }

    public String getLozinka() {
        return lozinka;
    }
}
