package com.example.mysplash.BaseDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.mysplash.infoRegistro;

import java.util.ArrayList;
import java.util.List;

public class BDUsuarios extends UsuariosService {

    Context context;
    public BDUsuarios(@Nullable Context context){
        super(context);
        this.context=context;
    }

    public long saveUser (infoRegistro info){
        long id = 0;
        try{
            UsuariosService bdS = new UsuariosService(context);
            SQLiteDatabase bd = bdS.getWritableDatabase();

            ContentValues values = new ContentValues();
            id = bd.insert(TABLE_USUARIOS, null, UsuariosContract.UsuarioEntry.toContentValues(info));

        }catch (Exception ex){
            ex.toString();
        }
        finally {
            return id;
        }
    }

    public List<infoRegistro>getUsuarios(){
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        List<infoRegistro> usuarios = null;
        infoRegistro info = null;

        sqLiteDatabase = getReadableDatabase();
        cursor = sqLiteDatabase.rawQuery("SELECT*FROM "+TABLE_USUARIOS,null);
        if( cursor == null ){
          return null;
        }
        if(cursor.getCount()<1){
            return null;
        }
        if(!cursor.moveToFirst()){
            return null;
        }
        Log.d(TAG, ""+ cursor.getCount());
        usuarios = new ArrayList<infoRegistro>();
        for (int i=0; i < cursor.getCount(); i++){
            info = new infoRegistro();
            info.setNomCompleto(cursor.getString(0));
            info.setEdad(cursor.getString(1));
            info.setEmail(cursor.getString(2));
            info.setPswd(cursor.getString(3));
            info.setSexo(Boolean.valueOf(cursor.getString(4)));
            info.setTelefono(cursor.getString(5));
            info.setUser(cursor.getString(6));
            usuarios.add(info);
            cursor.moveToNext();
        }
        return usuarios;
    }
    public infoRegistro GetUsuario(String usuario) {
        infoRegistro info = new infoRegistro();
        UsuariosService bdS = new UsuariosService(context);
        SQLiteDatabase db = bdS.getReadableDatabase();
        Cursor cursor = null;
        String query = "SELECT * FROM t_Usuarios WHERE usuario = ? ";
        String[] args = {usuario};

        cursor = db.rawQuery(query, args);
        if (cursor.moveToFirst()) {
            info.setId_Usr(cursor.getInt(0));
            info.setNomCompleto(cursor.getString(1));
            info.setEdad(cursor.getString(2));
            info.setEmail(cursor.getString(3));
            info.setPswd(cursor.getString(4));
            info.setTelefono(cursor.getString(5));
            info.setUser(cursor.getString(6));
            return info;
        }
        cursor.close();
        return null;
    }

    public infoRegistro GetUsuario(String usuario, String email){
        infoRegistro info = new infoRegistro();
        UsuariosService usuariosService = new UsuariosService(context);
        SQLiteDatabase bd = usuariosService.getReadableDatabase();
        Cursor cursor = null;
        String query = "SELECT * FROM t_Usuarios WHERE usuario = ? AND email = ?";
        String[] args = {usuario, email};

        cursor = bd.rawQuery(query, args);

        if (cursor.moveToFirst()){
            info.setNomCompleto(cursor.getString(0));
            info.setEdad(cursor.getString(1));
            info.setEmail(cursor.getString(2));
            info.setPswd(cursor.getString(3));
            info.setTelefono(cursor.getString(4));
            info.setUser(cursor.getString(5));
            return info;
        }
        cursor.close();
        return null;
    }

    public boolean EditaUsuario (String usuario,String contrasena){
        boolean correcto = false;
        UsuariosService bdService = new UsuariosService(context);
        SQLiteDatabase db = bdService.getWritableDatabase();
        try{
            db.execSQL("UPDATE " + TABLE_USUARIOS + " SET contrasena = '" + contrasena + "' WHERE usuario ='" + usuario + "'");
            correcto = true;
        }catch(Exception ex){
            ex.toString();
        }
        return correcto;
    }
}
