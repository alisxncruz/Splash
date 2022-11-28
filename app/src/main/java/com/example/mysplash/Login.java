package com.example.mysplash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myjson.infoRegistro;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

    public static final String KEY = "+4xij6jQRSBdCymMxweza/uMYo+o0EUg";
    private String testClaro = "Hola mundo";
    private String testDescifrado;

    public static List<infoRegistro> list;
    public static String TAG = "message";
    public static String TOG = "error";
    public static  String json = null;
    public static String usuario;
    public static String contrasena;
    public MyDesUtil myDesUtil = new MyDesUtil().addStringKeyBase64(KEY);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Button button = findViewById(R.id.registraId);
        Button button1 = findViewById(R.id.forgetId);
        Button button2 = findViewById(R.id.loginId);
        EditText user1 = findViewById(R.id.userNameId);
        EditText pass = findViewById(R.id.passwordId);
        AddLIsteners();
        jsonList(json);


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = String.valueOf(user1.getText());
                contrasena = Digest.bytesToHex(Digest.createSha1(String.valueOf(pass.getText())));
                paginaInicio(usuario, contrasena);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registro.class);
                startActivity(intent);
                finish();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, OlvidePsw.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void paginaInicio(String usuario , String contrasena){
        int i=0;
        if(usuario.equals("")||contrasena.equals("")){
            Toast.makeText(getApplicationContext(), "Necesitas Usuario y Contraseña", Toast.LENGTH_LONG).show();
        }else{
            for(infoRegistro infos : list){
                if(infos.getUser().equals(usuario) && infos.getPswd().equals(contrasena)){
                    Intent intent = new Intent(Login.this, WelcomeTJ.class);
                    intent.putExtra("Objeto", infos);
                    startActivity(intent);
                    i=1;
                }
            }
            if(i==0){
                Toast.makeText(getApplicationContext(), "El usuario o contraseña son incorrectos", Toast.LENGTH_LONG).show();
            }
        }
    }

    public boolean AddLIsteners(){
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

    public void jsonList (String json){
        Gson gson = null;
        String mensaje = null;
        if (json == null || json.length() == 0)
        {
            Toast.makeText(getApplicationContext(), "Error json null or empty", Toast.LENGTH_LONG).show();
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

    private File getFile( )
    {
        return new File( getDataDir() , Registro.archivo );
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
}
