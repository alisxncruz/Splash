package com.example.mysplash;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mysplash.BaseDatos.UsuariosService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UsuariosService usuariosService = new UsuariosService(MainActivity.this);
        SQLiteDatabase database = usuariosService.getWritableDatabase();

        if (database != null){
            Toast.makeText(this, "Se creó la base", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "No se creo", Toast.LENGTH_LONG).show();
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        },4000);
    }
}