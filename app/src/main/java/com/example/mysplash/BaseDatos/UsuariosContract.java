package com.example.mysplash.BaseDatos;

import static com.example.mysplash.BaseDatos.UsuariosService.TABLE_PASS;
import static com.example.mysplash.BaseDatos.UsuariosService.TABLE_USUARIOS;

import android.content.ContentValues;
import android.provider.BaseColumns;

import com.example.mysplash.infoC;
import com.example.mysplash.infoRegistro;

import java.io.Serializable;

public class UsuariosContract implements Serializable {
    public static final String TAG = "UsuariosContract";

    public static abstract class UsuarioEntry implements BaseColumns{
        public static final String USUARIO = "usuario";

        public static final String getCreateTable(){

            String table = "CREATE TABLE "+ TABLE_USUARIOS + "(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT NOT NULL," +
                    "edad INTEGER," +
                    "email TEXT NOT NULL," +
                    "contrasena TEXT NOT NULL," +
                    "telefono TEXT,"+
                    "usuario TEXT NOT NULL UNIQUE)";
            return table;
        }

        public static ContentValues toContentValues (infoRegistro info){

            ContentValues values = new ContentValues ();
            values.put("nombre", info.getNomCompleto());
            values.put("edad", info.getEdad());
            values.put("email", info.getEmail());
            values.put("contrasena", info.getPswd());
            values.put("telefono", info.getTelefono());
            values.put("usuario", info.getUser());
            return values;
        }
    }

    public abstract static class MyDataEntry implements BaseColumns{
        public static final String getCreateTable(){
            String table ="CREATE TABLE " + TABLE_PASS +
                    "(id_pass INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "pass TEXT NOT NULL, " +
                    "red_S TEXT NOT NULL, " +
                    "id_red INTEGER NOT NULL)";
            return table;
        }
        public static ContentValues toContentValues (infoC infoc){
            ContentValues values = new ContentValues();
            values.put("id_red", infoc.getId_red());
            values.put("pass", infoc.getPass());
            values.put("red_S", infoc.getRedPass());
            return values;
        }
    }
}
