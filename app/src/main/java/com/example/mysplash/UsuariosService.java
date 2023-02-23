package com.example.mysplash;

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
        sqLiteDatabase.execSQL(UsuariosContract.UsuarioEntry.getCreateTable());
        sqLiteDatabase.execSQL(UsuariosContract.MyDataEntry.getCreateTable());

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_USUARIOS);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_PASS);
        onCreate(sqLiteDatabase);

    }
}
