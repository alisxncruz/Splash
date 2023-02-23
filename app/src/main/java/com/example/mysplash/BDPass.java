package com.example.mysplash;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BDPass extends UsuariosService {

    Context context;

    public BDPass (@Nullable Context context){
        super(context);
        this.context=context;
    }

    public long savePass (infoC infoc){
        long id=0;
        try{
            UsuariosService usuariosDBService = new UsuariosService(context);
            SQLiteDatabase db =usuariosDBService.getWritableDatabase();

            ContentValues values= new ContentValues();
            id = db.insert(TABLE_PASS,null, UsuariosContract.MyDataEntry.toContentValues(infoc));
        }catch (Exception ex){
            ex.toString();
        }
        finally{
            return id;
        }
    }

    public List<infoC> getPass (int id){
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        List<infoC> passs = null;
        infoC infoc = null;
        sqLiteDatabase = getReadableDatabase();
        cursor = sqLiteDatabase.rawQuery("SELECT*FROM" + TABLE_PASS + " WHERE id = "+id,null);
        if(cursor == null){
             return new ArrayList<infoC>();
        }
        if (cursor.getCount()<1){
             return new ArrayList<infoC>();
        }
        if(!cursor.moveToFirst()){
            return new ArrayList<infoC>();
        }
        Log.d(TAG, ""+ cursor.getCount());
        passs = new ArrayList<infoC>();
        for (int i = 0; i<cursor.getCount(); i++){
            infoc = new infoC();
            infoc.setId_pass(cursor.getInt(0));
            infoc.setPass(cursor.getString(1));
            infoc.setRedPass(cursor.getString(2));
            passs.add(infoc);
            cursor.moveToNext();
        }
        Log.d("Contrasenas", passs.toString());
        return passs;
    }
    public boolean AlterContra (String red, String pass, int id_pass){
        boolean correcto = false;
        UsuariosService usuariosService = new UsuariosService(context);
        SQLiteDatabase bd = usuariosService.getWritableDatabase();

        try{
            bd.execSQL("UPDATE" + TABLE_PASS + "SET pass = '"+ pass + "', red_S= '"+red+"' WHERE id_pass= '"+id_pass+"'");
            correcto=true;
        }catch (Exception ex){
            ex.toString();
            correcto = false;
        }finally{
            bd.close();
        }
        return correcto;
    }

    public boolean eliminarUsuario (String red, String pass, int id_pass){
        boolean correcto = false;

        UsuariosService bdHelper = new UsuariosService(context);
        SQLiteDatabase bd = bdHelper.getWritableDatabase();

        try{
            bd.execSQL("DELETE FROM" + TABLE_PASS + "WHERE id_pass='"+id_pass+"' AND pass= '"+pass+"' AND red_S ='"+red+"'");
            correcto = true;
        }catch (Exception ex){
            ex.toString();
            correcto = false;
        } finally {
            bd.close();
        }
        return correcto;
    }
}
