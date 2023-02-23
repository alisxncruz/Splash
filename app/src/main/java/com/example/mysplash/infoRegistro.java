package com.example.mysplash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class infoRegistro implements Serializable {
    private String nomCompleto;
    private String edad;
    private String telefono;
    private String email;
    private String pswd;
    private String user;
    private String[] redesS;
    private Boolean sexo;
    private List<infoC> password = new ArrayList<>();

    public String getNomCompleto() {
        return nomCompleto;
    }

    public void setNomCompleto(String nomCompleto) {
        this.nomCompleto = nomCompleto;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Byte getRedesS() {
        return redesS;
    }

    public void setRedesS(String[] redesS) {
        this.redesS = redesS;
    }

    public Boolean getSexo() {
        return sexo;
    }

    public void setSexo(Boolean sexo) {
        this.sexo = sexo;
    }

    public List<infoC> getPassword() {
        return password;
    }

    public void setPassword(List<infoC> password) {
        this.password = password;
    }
}
