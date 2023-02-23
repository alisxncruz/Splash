package com.example.mysplash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;

public class Registro extends AppCompatActivity {


    String json = null;
    String usuario = null;
    String contrasena =null;
    public static boolean desliza = false;
    public static boolean  tipoSexo= false;
    public static List<infoRegistro> list = new ArrayList<infoRegistro>();
    public static List<infoC> lista;
    public static final String archivo = "registro.json";
    private static final String TAG = "Registro";
    public static final String KEY = "+4xij6jQRSBdCymMxweza/uMYo+o0EUg";
    public MyDesUtil myDesUtil= new MyDesUtil().addStringKeyBase64(KEY);

    infoRegistro infos = null;
    Gson gson = null;
    String usr = null;
    String email = null;
    String mensaje = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        lista = new ArrayList<>();
        infoC info2 = null;

        EditText nomCompleto = findViewById(R.id.nomCompleto);
        EditText edad = findViewById(R.id.edad);
        EditText telefono = findViewById(R.id.telefono);
        EditText email = findViewById(R.id.email);
        EditText pswd = findViewById(R.id.pswd);
        EditText user = findViewById(R.id.user);
        Button btnRegistrarme = findViewById(R.id.btnRegistrarme);
        RadioButton masculino = findViewById(R.id.masculino);
        RadioButton femenino = findViewById(R.id.femenino);
        Switch terminos = findViewById(R.id.acepta);
        CheckBox twit = findViewById(R.id.twitter);
        CheckBox ig = findViewById(R.id.insta);
        CheckBox face = findViewById(R.id.fb);
        AddListeners();
        jsonList(json);


        btnRegistrarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                infoRegistro infos = new infoRegistro();

                usuario = String.valueOf(user.getText());
                contrasena = String.valueOf(pswd.getText());
                String[] redesS = new String[3];



                if (twit.isChecked() == true) {
                    redesS[0] = "Twitter";
                } else {
                    redesS[0] = null;
                }

                if (face.isChecked() == true) {
                    redesS[0] = "Facebook";
                } else {
                    redesS[0] = null;
                }

                if (ig.isChecked() == true) {
                    redesS[0] = "Instagram";
                } else {
                    redesS[0] = null;
                }

                if (terminos.isChecked()) {
                    desliza = true;
                }

                if (femenino.isChecked()) {
                    tipoSexo = true;
                }

                if (masculino.isChecked()) {
                    tipoSexo = true;
                }

                if (usuario.equals("")||contrasena.equals("")) {
                    Log.d(TAG, "no hay nada");
                    Log.d(TAG, usuario);
                    Log.d(TAG, contrasena);
                Toast.makeText(getApplicationContext(), "Por favor, escribe un Usuario y Contraseña", Toast.LENGTH_LONG).show();
                }
                else {
                    if (list.isEmpty()) {
                        Log.d(TAG, "Registro Completo");
                        infos.setNomCompleto(String.valueOf(nomCompleto.getText()));
                        infos.setEdad(Integer.parseInt(String.valueOf(edad.getText().toString())));
                        infos.setEmail(String.valueOf(email.getText()));
                        infos.setPswd(Digest.bytesToHex(Digest.createSha1(String.valueOf(pswd.getText()))));
                        infos.setRedesS(redesS);
                        infos.setSexo(tipoSexo);
                        infos.setTelefono(String.valueOf(telefono.getText()));
                        infos.setUser(usuario);
                        infos.setPassword(lista);
                        ListJson(infos, list);

                    } else {
                        if (miembros(list, usuario)) {
                            Log.d(TAG, "Existe");
                            Toast.makeText(getApplicationContext(), "El usuario ya existe", Toast.LENGTH_LONG).show();
                        } else {
                            infos.setNomCompleto(String.valueOf(nomCompleto.getText()));
                            infos.setEdad(Integer.parseInt(String.valueOf(edad.getText().toString())));
                            infos.setEmail(String.valueOf(email.getText()));
                            infos.setPswd(Digest.bytesToHex(Digest.createSha1(String.valueOf(pswd.getText()))));
                            infos.setRedesS(redesS);
                            infos.setSexo(tipoSexo);
                            infos.setTelefono(String.valueOf(telefono.getText()));
                            infos.setUser(usuario);
                            infos.setPassword(lista);
                            ListJson(infos, list);
                        }
                    }
                }
            }
        });
    }

        public boolean miembros (List<infoRegistro> registros, String usuario){
            boolean yaEsta=false;
            for(infoRegistro infoUsuario: registros){
                if(infoUsuario.getUser().equals(usuario)){
                    yaEsta=true;
                }
            }
            return yaEsta;
        }

        public void Object2 (infoRegistro registros){
            Gson gson =null;
            String json= null;
            String mensaje = null;
            gson =new Gson();
            json = gson.toJson(registros);
            if (json != null) {
                Log.d(TAG, json);
                mensaje = "bien Object2";
            } else {
                mensaje = "Error object2";
            }
            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
        }

        public void ListJson (infoRegistro registros, List<infoRegistro> info){
            Gson gson =null;
            String json= null;
            gson =new Gson();
            list.add(registros);
            json =gson.toJson(list, ArrayList.class);
            if (json == null)
            {
                Log.d(TAG, "Error");
            }
            else
            {
                Log.d(TAG, json);
                json=myDesUtil.cifrar(json);
                Log.d(TAG, json);
                writeFile(json);
            }
            Toast.makeText(getApplicationContext(), "Ok", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Registro.this, Login.class);
            startActivity(intent);
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

    public boolean AddListeners(){
        if(!isFileExits()){
            return false;
        }
        File file = getFile();
        FileInputStream fileInputStream = null;
        byte[] bytes = null;
        bytes = new byte[(int)file.length()];
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            json=new String(bytes);
            json = myDesUtil.desCifrar(json);
            Log.d(TAG,json);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    private boolean isFileExits( )
    {
        File file = getFile( );
        if( file == null )
        {
            return false;
        }
        return file.isFile() && file.exists();
    }
    public void jsonList( String json )
    {
        Gson gson = null;
        String mensaje = null;
        if (json == null || json.length() == 0)
        {
            Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
            return;
        }
        gson = new Gson();
        Type listType = new TypeToken<ArrayList<infoRegistro>>(){}.getType();
        list = gson.fromJson(json, listType);
        if (list == null || list.size() == 0 )
        {
            Toast.makeText(getApplicationContext(), "Error list is null or empty", Toast.LENGTH_LONG).show();
            return;
        }
    }

}