package com.example.mysplash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mysplash.BaseDatos.BDUsuarios;

import java.util.List;

public class Login extends AppCompatActivity {

    public static final String KEY = "+4xij6jQRSBdCymMxweza/uMYo+o0EUg";
    public MyDesUtil myDesUtil = new MyDesUtil().addStringKeyBase64(KEY);
    private String testClaro = "Hola mundo";

    public static List<infoRegistro> list;

    public static String usuario;
    public static String pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button button = findViewById(R.id.registraId);
        Button button1 = findViewById(R.id.forgetId);
        Button button2 = findViewById(R.id.loginId);
        EditText user1 = findViewById(R.id.userNameId);
        EditText contra = findViewById(R.id.passwordId);



        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = String.valueOf(user1.getText());
                pass = (String.valueOf(contra.getText()));
                paginaInicio(usuario, pass);
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
        if(usuario.equals("")||contrasena.equals("")){
            Toast.makeText(getApplicationContext(), "Completa los campos", Toast.LENGTH_LONG).show();
        }else{
            BDUsuarios bdUsuarios = new BDUsuarios(Login.this);
            infoRegistro info = bdUsuarios.GetUsuario(usuario);
            if(info != null){
                if (info.getPswd().equals(contrasena)){
                    Toast.makeText(getApplicationContext(), "Iniciaste Sesión", Toast.LENGTH_LONG);
                    Intent intent = new Intent(Login.this, WelcomeTJ.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "No se encontro este usuario", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}

  /*  public boolean AddLIsteners(){
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
            //Log.d(TAG,json);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void jsonList (String json){

        Button button1 = findViewById(R.id.loginId);
        Button button2 = findViewById(R.id.forgetId);

        Gson gson = null;
        String mensaje = null;
        if (json == null || json.length() == 0)
        {
            Toast.makeText(getApplicationContext(), "Error json null or empty", Toast.LENGTH_LONG).show();
            button1.setEnabled(false);
            button2.setEnabled(false);
            return;
        }
        gson = new Gson();
        Type listType = new TypeToken<ArrayList<infoRegistro>>(){}.getType();
        list = gson.fromJson(json, listType);
        if (list == null || list.size() == 0 )
        {
            Toast.makeText(getApplicationContext(), "Error list is null or empty", Toast.LENGTH_LONG).show();
            button1.setEnabled(false);
            button2.setEnabled(false);
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
        return file.isFile() && file.exists();*/

