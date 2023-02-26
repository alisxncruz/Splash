package com.example.mysplash;

import java.io.Serializable;

public class infoC extends infoRegistro implements Serializable {

    private int id_red;
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

    public int getId_red() {
        return id_red;
    }

    public void setId_red(int id_red) {
        this.id_red = id_red;
    }
}
