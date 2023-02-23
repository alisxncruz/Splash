package com.example.mysplash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class OlvidePsw extends AppCompatActivity {

    public static List<infoRegistro> list;
    public static String json = null;
    public static String TAG = "Hola";
    public static String TOG = "Error";
    public MyDesUtil myDesUtil = new MyDesUtil().addStringKeyBase64(Registro.KEY);
    public String usuario = null;
    public String correo, msj,pass,nuevapass,nueva2;
    EditText user, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvide_psw);

        Button buttonRec = findViewById(R.id.button);
        user= findViewById(R.id.usuarioOlv);
        email=findViewById(R.id.correoOlv);
        list = Login.list;

        buttonRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usuario = String.valueOf(user.getText());
                correo = String.valueOf(email.getText());
                if(user.length()==0 || correo.length()==0){
                    Toast.makeText(getApplicationContext(), "Llena los campos", Toast.LENGTH_LONG).show();
                }else{
                    int i = 0;
                    int j=0;
                    for(infoRegistro info : list){
                        if (info.getUser().equals(user)|| info.getEmail().equals(email)){
                            correo = info.getEmail();
                            pass = info.getPswd();
                            nuevapass = String.format("%d", (int)(Math.random()*1500));
                            nueva2 = Digest.bytesToHex(Digest.createSha1(nuevapass));
                            //msj = "";
                            correo = myDesUtil.cifrar(correo);
                            msj = myDesUtil.cifrar(msj);
                            list.get(j).setPswd(nueva2);
                            Log.i(TAG, nueva2);
                            Log.i(TAG, list.get(j).getPswd());
                            JsonList(list);
                            i=1;
                        }
                        j++;
                    }
                    if(i==1){
                        Log.i(TAG,usuario);
                        Log.i(TAG,correo);
                        Log.i(TAG,msj);
                        if( Enviar( correo,msj ) )
                        {
                            Toast.makeText(getBaseContext() , "Se envío el texto" , Toast.LENGTH_LONG ).show();
                            Intent intent = new Intent(OlvidePsw.this, Login.class);
                            startActivity(intent);
                            finish();
                            return;
                        }
                        Toast.makeText(getBaseContext() , "Error en el envío" , Toast.LENGTH_LONG ).show();
                    }else{
                        if(i==0){
                            Log.i(TAG,"no hay usuarios");
                            Toast.makeText(getBaseContext() , "No existen usuarios" , Toast.LENGTH_LONG ).show();
                            return;
                        }
                    }
                }

            }
        });
    }

    public boolean Enviar ( String correo, String msj){
        JsonObjectRequest jsonObjectRequest = null;
        JSONObject jsonObject = null;
        String url = "https://us-central1-nemidesarrollo.cloudfunctions.net/envio_correo";
        RequestQueue requestQueue = null;
        if(correo==null||correo.length()==0){
            return false;
        }
        jsonObject = new JSONObject();

        try {
            jsonObject.put("correo", correo);
            jsonObject.put("mensaje", msj);
            String hi = jsonObject.toString();
            Log.i(TAG, hi);
        }
        catch(JSONException e){
            e.printStackTrace();
        }
        jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                Log.i(TAG, response.toString());
            }
        } , new  Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e  (TAG, error.toString());
            }
        } );
        requestQueue = Volley.newRequestQueue( getBaseContext() );
        requestQueue.add(jsonObjectRequest);

        return true;
    }

    /*public boolean AddList(){
        if(!isFileExists()) {
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
            json= myDesUtil.desCifrar(json);
            Log.d(TAG,json);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }/*/

    private File getFile( )
    {
        return new File( getDataDir() , Registro.archivo );
    }
    private boolean isFileExists( )
    {
        File file = getFile( );
        if( file == null )
        {
            return false;
        }
        return file.isFile() && file.exists();
    }
    public void JsonList(List<infoRegistro> list){
        Gson gson =null;
        String json= null;
        gson =new Gson();
        json =gson.toJson(list, ArrayList.class);
        if (json == null)
        {
            Log.d(TAG, "Error json");
        }
        else
        {
            Log.d(TAG, json);
            json=myDesUtil.cifrar(json);
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
}
