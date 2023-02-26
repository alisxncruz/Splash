package com.example.mysplash.BaseDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UsuariosService extends SQLiteOpenHelper {
    public static final String TAG = "UsuariosService";
    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "REGISTROS.db";

    public static final String TABLE_USUARIOS = "t_Usuarios";
    public static final String TABLE_PASS = "t_Pass";

    public UsuariosService(@Nullable Context context ){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE "+ TABLE_USUARIOS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "edad INTEGER," +
                "email TEXT NOT NULL," +
                "contrasena TEXT NOT NULL," +
                "telefono TEXT,"+
                "usuario TEXT NOT NULL UNIQUE)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_PASS +
                "(id_pass INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "pass TEXT NOT NULL, " +
                "red_S TEXT NOT NULL, " +
                "id_red INTEGER NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_USUARIOS);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_PASS);
        onCreate(sqLiteDatabase);

    }
}
