package com.example.mysplash;

import static com.example.mysplash.Registro.archivo;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mysplash.api.mainApi;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WelcomeTJ extends AppCompatActivity {


    private List<infoRegistro> list;
    public MyDesUtil myDesUtil = new MyDesUtil().addStringKeyBase64(Registro.KEY);
    public static String TAG = "msj";
    public static String json = null;
    public static ListView listView;
    private List<infoC> listaC;
    public int pos = 0;
    public static infoRegistro info = null;
    EditText editTextR, editTextP;
    Object object = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_tj);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getExtras() != null) {
                object = intent.getExtras().get("Objeto");
                if (object != null) {
                    if (object instanceof infoRegistro) {
                        info = (infoRegistro) object;
                    }
                }
            }
        }
        list = new ArrayList<>();
        list = Login.list;
        editTextR = findViewById(R.id.editTextR);
        editTextP = findViewById(R.id.editTextpass);
        Button btnA = findViewById(R.id.mas);
        Button btnE = findViewById(R.id.delete);
        Button btnEd = findViewById(R.id.editing);
        listView = (ListView) findViewById(R.id.listViewId);
        listaC = new ArrayList<infoC>();
        listaC = info.getPassword();
        MyAdapter myAdapter = new MyAdapter(listaC, getBaseContext());
        listView.setAdapter(myAdapter);
        btnEd.setEnabled(false);
        btnE.setEnabled(false);

        if(listaC.isEmpty()){
            Toast.makeText(getApplicationContext(), "Clic en +", Toast.LENGTH_LONG).show();
        }
        Toast.makeText(getApplicationContext(), "Da clic en la contraseña si quieres eliminarla o editarla", Toast.LENGTH_LONG).show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editTextR.setText(listaC.get(i).getRedPass());
                editTextP.setText(listaC.get(i).getPass());
                pos = i;
                btnEd.setEnabled(true);
                btnE.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Clic en guardar cambios", Toast.LENGTH_LONG).show();
            }
        });

        btnE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaC.remove(pos);
                info.setPassword(listaC);
                MyAdapter myAdapter = new MyAdapter(listaC, getBaseContext());
                listView.setAdapter(myAdapter);
                editTextP.setText("");
                editTextR.setText("");
                Toast.makeText(getApplicationContext(), "Eliminada", Toast.LENGTH_LONG).show();
                btnE.setEnabled(false);
                btnEd.setEnabled(false);
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
                    infoC info2 = new infoC();
                    info2.setRedPass(red);
                    info2.setPass(pswd);
                    listaC.add(info2);
                    MyAdapter myAdapter = new MyAdapter(listaC, getBaseContext());
                    listView.setAdapter(myAdapter);
                    editTextP.setText("");
                    editTextR.setText("");
                    Toast.makeText(getApplicationContext(), "Contraseña guardada", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String redS = String.valueOf(editTextR.getText());
                String pswd = String.valueOf(editTextP.getText());

                if (redS.equals("") || pswd.equals("")) {
                    Toast.makeText(getApplicationContext(), "Llena los campos", Toast.LENGTH_LONG).show();
                } else {
                    listaC.get(pos).setRedPass(redS);
                    listaC.get(pos).setPass(pswd);
                    info.setPassword(listaC);
                    MyAdapter myAdapter = new MyAdapter(listaC, getBaseContext());
                    listView.setAdapter(myAdapter);
                    editTextP.setText("");
                    editTextR.setText("");
                    Toast.makeText(getApplicationContext(), "Contraseña modificada", Toast.LENGTH_LONG).show();
                    btnE.setEnabled(false);
                    btnEd.setEnabled(false);
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
                    if(info.getUser().equals(info.getUser())){
                        list.get(i).setPassword(listaC);
                    }
                    i++;
                }
                ListJson(info,list);
                return true;

            }
            if (id == R.id.item3) {
                Intent intent= new Intent(WelcomeTJ.this,Login.class);
                startActivity(intent);
                return true;
            }
            if(id == R.id.item4){
                Intent intent = new Intent(WelcomeTJ.this, mainApi.class);
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
            json = myDesUtil.cifrar(json);
            Log.d(TAG, json);
            writeFile(json);
        }
        Toast.makeText(getApplicationContext(), "okay", Toast.LENGTH_LONG).show();
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
