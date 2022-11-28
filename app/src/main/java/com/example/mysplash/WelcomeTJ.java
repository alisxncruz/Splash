package com.example.mysplash;

import static com.example.mysplash.Login.list;
import static com.example.mysplash.R.id.editTextRS;
import static com.example.mysplash.Registro.archivo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myjson.infoC;
import com.example.myjson.infoRegistro;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WelcomeTJ extends AppCompatActivity {


    private List<infoRegistro> lista;
    public static String TAG = "Alison";
    public static String json = null;
    public static ListView listView;
    private List<infoC> listaC;
    public int pos = 0;

    public static infoRegistro info1 = null;
    EditText editTextR, editTextP;
    Object object = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_tj);


        Button btnA = findViewById(R.id.mas);
        Button btnE = findViewById(R.id.delete);
        Button btnEd = findViewById(R.id.editing);


        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getExtras() != null) {
                object = intent.getExtras().get("infoRegistro");
                if (object != null) {
                    if (object instanceof infoRegistro) {
                        info1 = (infoRegistro) object;
                    }
                }
            }
        }
        btnEd.setVisibility(View.GONE);
        btnE.setVisibility(View.GONE);

        lista = new ArrayList<>();
        lista = list;
        listView = (ListView) findViewById(R.id.listViewId);
        listaC = new ArrayList<infoC>();
        listaC = info1.getPassword();
        MyAdapter myAdapter = new MyAdapter(listaC, getBaseContext());
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editTextR.setText(listaC.get(i).getRedPass());
                editTextP.setText(listaC.get(i).getPass());
                toast(i);
                pos = i;
                btnEd.setVisibility(view.VISIBLE);
                btnE.setVisibility(view.VISIBLE);
            }
        });

        btnE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaC.remove(pos);
                Object object = null;
                infoRegistro info1 = null;
                List<infoRegistro> list = new ArrayList<infoRegistro>();
                object = intent.getExtras().get("infoRegistro");
                info1 = (infoRegistro) object;
                info1.setPassword(listaC);
                ListJson(info1, list);
                MyAdapter myAdapter = new MyAdapter(listaC, getBaseContext());
                listView.setAdapter(myAdapter);
                editTextP.setText("");
                editTextR.setText("");
                Toast.makeText(getApplicationContext(), "Contraseña borrada", Toast.LENGTH_LONG).show();
                btnE.setVisibility(view.GONE);
                btnEd.setVisibility(view.GONE);

            }
        });


        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String red = String.valueOf(editTextR.getText());
                String pswd = String.valueOf(editTextP.getText());

                if (red.equals("") || pswd.equals("")) {
                    Toast.makeText(getApplicationContext(), "Llena los campos", Toast.LENGTH_LONG).show();
                } else {
                    infoC info2 = null;
                    Object object = null;
                    infoRegistro info1 = null;
                    Intent intent = getIntent();
                    object = intent.getExtras().get("infoRegistro");
                    info1 = (infoRegistro) object;
                    listaC = info1.getPassword();
                    info2 = new infoC();
                    info2.setRedPass(String.valueOf(editTextR.getText()));
                    info2.setPass(String.valueOf(editTextP.getText()));
                    listaC.add(info2);
                    info1.setPassword(listaC);
                    ListJson(info1, lista);
                    /*MyAdapter myAdapter = new MyAdapter(listaC, getBaseContext());
                    listView.setAdapter(myAdapter);*/
                    Toast.makeText(getApplicationContext(), "Contraseña guardada", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String redS = String.valueOf(editTextR.getText());
                String pswd = String.valueOf(editTextP.getText());
                Button editar = findViewById(R.id.editing);
                Button eliminar = findViewById(R.id.delete);

                if (redS.equals("") || pswd.equals("")) {
                    Toast.makeText(getApplicationContext(), "Llena los campos", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = getIntent();
                    listaC.get(pos).setRedPass(String.valueOf(editTextR.getText()));
                    listaC.get(pos).setPass(String.valueOf(editTextP.getText()));
                    List<infoRegistro> list = new ArrayList<infoRegistro>();
                    object = intent.getExtras().get("infoRegistro");
                    info1 = (infoRegistro) object;
                    info1.setPassword(listaC);
                    ListJson(info1, lista);
                    /*MyAdapter myAdapter = new MyAdapter(listaC, getBaseContext());
                    listView.setAdapter(myAdapter);*/
                    Toast.makeText(getApplicationContext(), "Se guardaron los cambios", Toast.LENGTH_LONG).show();
                    /*editar.setVisibility(view.GONE);
                    eliminar.setVisibility(view.GONE);*/
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean flag = false;
        flag = super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menuu, menu);
        return flag;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item1) {
            String red = String.valueOf(editTextR.getText());
            String pswd = String.valueOf(editTextP.getText());
            if (red.equals("") || pswd.equals("")) {
                Toast.makeText(getApplicationContext(), "Campos Vacios", Toast.LENGTH_LONG).show();
            } else {
                infoC info2 = new infoC();
                info2.setPass(pswd);
                info2.setRedPass(red);
                listaC.add(info2);
                MyAdapter myAdapter = new MyAdapter(listaC, getBaseContext());
                listView.setAdapter(myAdapter);
                editTextP.setText("");
                editTextR.setText("");
                Toast.makeText(getApplicationContext(), "Se agregó la contraseña", Toast.LENGTH_LONG).show();
            }
            return true;
        }
            if (id == R.id.item2) {
                int i =0;
                for(infoRegistro info : list){
                    if(info1.getUser().equals(info.getUser())){
                        list.get(i).setPassword(listaC);
                    }
                    i++;
                }
                ListJson(info1,list);
                return true;

            }
            if (id == R.id.item3) {
                Intent intent= new Intent(WelcomeTJ.this,Login.class);
                startActivity(intent);
                return true;
            }

        return super.onOptionsItemSelected(item);
    }

        private void toast ( int i ){
         Toast.makeText(getBaseContext(), listaC.get(i).getPass(), Toast.LENGTH_SHORT);
        }

    public void ListJson(infoRegistro info1,List<infoRegistro> list){
        Gson gson =null;
        String json= null;
        gson =new Gson();
        list.add(info1);
        json =gson.toJson(list, ArrayList.class);
        if (json == null)
        {
            Log.d(TAG, "Error json");
        }
        else
        {
            Log.d(TAG, json);
            writeFile(json);
        }

    }
    private boolean writeFile(String text){
        File file =null;
        FileOutputStream fileOutputStream =null;
        try{
            file=getFile();
            fileOutputStream = new FileOutputStream( file );
            fileOutputStream.write( text.getBytes(StandardCharsets.UTF_8) );
            fileOutputStream.close();
            Log.d(TAG, "Hola");
            return true;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }
    private File getFile(){
        return new File(getDataDir(),archivo);
    }
}
