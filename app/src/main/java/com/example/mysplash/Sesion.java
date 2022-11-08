package com.example.mysplash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myjson.infoRegistro;

public class Sesion extends AppCompatActivity {
    String auxiliar;
    infoRegistro infos = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Object object = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesion);
        TextView userr = findViewById(R.id.textView18);
        Intent intent = getIntent();
        if(intent != null){
            if(intent.getExtras() != null){
                object = intent.getExtras().get("Objeto");
                if(object != null){
                    if(object instanceof infoRegistro){
                        infos = (infoRegistro)object;
                        userr.setText(infos.get);
                    }
                }
            }
        }

    }
}