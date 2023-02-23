package com.example.mysplash;

import static com.example.mysplash.UsuariosService.TABLE_PASS;
import static com.example.mysplash.UsuariosService.TABLE_USUARIOS;

import android.content.ContentValues;
import android.provider.BaseColumns;

import java.io.Serializable;

public class UsuariosContract implements Serializable {
    public static final String TAG = "UsuariosContract";

    public static abstract class UsuarioEntry implements BaseColumns{
        public static final String USUARIO = "usuario";

        public static final String getCreateTable(){

            String table = "CREATE TABLE "+TABLE_USUARIOS+ "(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT," +
                    "edad INTEGER," +
                    "email TEXT NOT NULL," +
                    "contrasena TEXT NOT NULL," +
                    "redS1 TEXT," +
                    "redS2 TEXT,"+
                    "redS3 TEXT," +
                    "sexo INTEGER," +
                    "telefono TEXT,"+
                    "usuario TEXT NOT NULL UNIQUE," +
                    ")";
            return table;
        }

        public static ContentValues toContentValues (infoRegistro info){

            ContentValues values = new ContentValues ();
            values.put("nombre", info.getNomCompleto());
            values.put("edad", info.getEdad());
            values.put("email", info.getEmail());
            values.put("contrasena", info.getPswd());
            values.put("redS1", info.getRedesS());
            values.put("redS2", info.getRedesS());
            values.put("redS3", info.getRedesS());
            values.put("sexo", info.getSexo());
            values.put("telefono", info.getTelefono());
            values.put("usuario", info.getUser());
            return values;
        }
    }

    public abstract static class MyDataEntry implements BaseColumns{
        public static final String getCreateTable(){
            String table ="CREATE TABLE "+TABLE_PASS+"(" +
                    "id_pass INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "pass TEXT NOT NULL," +
                    "red_S TEXT NOT NULL)";
            return table;
        }
        public static ContentValues toContentValues (infoC infoc){
            ContentValues values = new ContentValues();
            values.put("id_pass", infoc.getId_pass());
            values.put("pass", infoc.getPass());
            values.put("red_S", infoc.getRedPass());
            return values;
        }
    }
}
