package com.example.mysplash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WelcomeTJ extends AppCompatActivity {

    private ListView listV;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_tj);

        listV = (ListView) findViewById(R.id.list);
        list = new ArrayList<String>();

        for( int i = 0; i < 100; i++){
            list.add(String.format("Contraseña%d", i+1));
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_edit_list, R.id.textView, list);
        listV.setAdapter(arrayAdapter);

    }
}