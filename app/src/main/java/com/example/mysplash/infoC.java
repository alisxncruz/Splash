package com.example.mysplash;

import com.example.mysplash.infoRegistro;

import java.io.Serializable;

public class infoC extends infoRegistro implements Serializable {

    private int id_pass;
    private String pass;
    private String redPass;

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRedPass() {
        return redPass;
    }

    public void setRedPass(String redPass) {
        this.redPass = redPass;
    }

    public int getId_pass() {
        return id_pass;
    }

    public void setId_pass(int id_pass) {
        this.id_pass = id_pass;
    }
}
