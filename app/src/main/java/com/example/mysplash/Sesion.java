package com.example.mysplash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myjson.infoRegistro;

public class Sesion extends AppCompatActivity {
    String auxiliar;
    public static infoRegistro infos = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Object object = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesion);
        TextView userr = findViewById(R.id.textView18);
        Button buttonpswd = findViewById(R.id.btnpswd);

        Intent intent = getIntent();
        if(intent != null){
            if(intent.getExtras() != null){
                object = intent.getExtras().get("Objeto");
                if(object != null){
                    if(object instanceof infoRegistro){
                        infos = (infoRegistro)object;
                        userr.setText(infos.getUser());
                    }
                }
            }
        }

        buttonpswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sesion.this, WelcomeTJ.class);
                startActivity(intent);
                finish();
            }
        });

    }
}