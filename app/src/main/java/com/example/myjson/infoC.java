package com.example.myjson;

import java.io.Serializable;

public class infoC extends infoRegistro implements Serializable {

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
}
