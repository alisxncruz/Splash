package com.example.mysplash;

import android.content.Intent;
import android.os.Bundle;
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

import com.example.mysplash.BaseDatos.BDPass;
import com.example.mysplash.api.mainApi;

import java.util.List;

public class WelcomeTJ extends AppCompatActivity {


    private List<infoRegistro> list;
    public static String TAG = "Alison";
    public static String json = null;
    public static ListView listView;
    private List<infoC> listaC;
    public static infoRegistro info = null;
    infoC infoC = new infoC();
    public int pos = 0;
    EditText editTextR, editTextP;
    Object object = null;
    public MyDesUtil myDesUtil = new MyDesUtil().addStringKeyBase64(Registro.KEY);

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
        editTextR = findViewById(R.id.editTextR);
        editTextP = findViewById(R.id.editTextpass);
        Button btnA = findViewById(R.id.mas);
        Button btnE = findViewById(R.id.delete);
        Button btnEd = findViewById(R.id.editing);
        listView = (ListView) findViewById(R.id.listViewId);

        BDPass bdPass = new BDPass(WelcomeTJ.this);
        listaC = bdPass.getPass(info.getId_Usr());

        MyAdapter myAdapter = new MyAdapter(listaC, getBaseContext());
        listView.setAdapter(myAdapter);
        btnE.setEnabled(false);
        btnEd.setEnabled(false);

        if (listaC == null) {
            Toast.makeText(getApplicationContext(), "Para agregar una contraseña de clic en el menú o en el boton +", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "Escriba en los campos", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), String.valueOf(info.getId_Usr()), Toast.LENGTH_LONG).show();
        }//Aqui
        Toast.makeText(getApplicationContext(), "Para modificar o eliminar una contraseña de click en ella", Toast.LENGTH_LONG).show();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                infoC = listaC.get(i);
                editTextR.setText(infoC.getRedPass());
                editTextP.setText(infoC.getPass());
                pos = i;
                btnE.setEnabled(true);
                btnEd.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Para guardar los cambios de click en guardar cambios", Toast.LENGTH_LONG).show();
            }
        });

        btnE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BDPass bdPass = new BDPass(WelcomeTJ.this);
                boolean id = bdPass.eliminarContrasena(info.getId_Usr(), infoC.getRedPass(), infoC.getPass());
                if (id) {
                    listaC = bdPass.getPass(info.getId_Usr());
                    MyAdapter myAdapter = new MyAdapter(listaC, getBaseContext());
                    listView.setAdapter(myAdapter);
                    editTextP.setText("");
                    editTextR.setText("");
                    Toast.makeText(getApplicationContext(), "Se eliminó la contraseña", Toast.LENGTH_LONG).show();
                    btnE.setEnabled(false);
                    btnEd.setEnabled(false);
                } else {
                    Toast.makeText(getApplicationContext(), "Error al eliminar", Toast.LENGTH_LONG).show();
                }
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
                    info2.setId_red(info.getId_Usr());
                    Toast.makeText(getApplicationContext(), String.valueOf(info.getId_Usr()), Toast.LENGTH_LONG).show();
                    BDPass bdPass = new BDPass(WelcomeTJ.this);
                    long id = bdPass.AnadirPass(info2);
                    if (id > 0) {
                        listaC = bdPass.getPass(info.getId_Usr());
                        MyAdapter myAdapter = new MyAdapter(listaC, getBaseContext());
                        listView.setAdapter(myAdapter);
                        editTextP.setText("");
                        editTextR.setText("");
                        Toast.makeText(getApplicationContext(), info2.getRedPass() + " " + info2.getPass(), Toast.LENGTH_LONG).show();
                        Toast.makeText(WelcomeTJ.this, "REGISTRO GURADADO", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(WelcomeTJ.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String red = String.valueOf(editTextR.getText());
                String contra = String.valueOf(editTextP.getText());
                if (red.equals("") || contra.equals("")) {
                    Toast.makeText(getApplicationContext(), "Llene los campos", Toast.LENGTH_LONG).show();
                } else {
                    BDPass bdPass = new BDPass(WelcomeTJ.this);
                    boolean id = bdPass.EditarContra(red, contra, info.getId_Usr(), infoC.getId_pass());
                    if (id) {
                        listaC = bdPass.getPass(info.getId_Usr());
                        MyAdapter myAdapter = new MyAdapter(listaC, getBaseContext());
                        listView.setAdapter(myAdapter);
                        editTextR.setText("");
                        editTextP.setText("");
                        Toast.makeText(getApplicationContext(), "Se modificó la contraseña", Toast.LENGTH_LONG).show();
                        btnE.setEnabled(false);
                        btnEd.setEnabled(false);
                    } else {
                        Toast.makeText(getApplicationContext(), "Error al modificar", Toast.LENGTH_LONG).show();
                    }
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
                info2.getId_red();
                Toast.makeText(getApplicationContext(), String.valueOf(info.getId_Usr()), Toast.LENGTH_LONG).show();
                BDPass bdPass = new BDPass(WelcomeTJ.this);
                long pw = bdPass.AnadirPass(info2);
                if (pw > 0) {
                    listaC = bdPass.getPass(info.getId_Usr());
                    MyAdapter myAdapter = new MyAdapter(listaC, getBaseContext());
                    listView.setAdapter(myAdapter);
                    editTextP.setText("");
                    editTextR.setText("");
                    Toast.makeText(getApplicationContext(), info2.getRedPass() + " " + info2.getPass(), Toast.LENGTH_LONG).show();
                    Toast.makeText(WelcomeTJ.this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(WelcomeTJ.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_LONG).show();
                }
            }
        }
        if (id == R.id.item2) {
            Intent intent = new Intent(WelcomeTJ.this, mainApi.class);
            startActivity(intent);
            return true;

        }
        if (id == R.id.item3) {
            Intent intent = new Intent(WelcomeTJ.this, Login.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

        /*private void toast ( int i ){
            Toast.makeText(getBaseContext(), listaC.get(i).getPass(), Toast.LENGTH_SHORT);
        }

        public void ListJson (infoRegistro info1, List < infoRegistro > list){
            Gson gson = null;
            String json = null;
            gson = new Gson();
            list.add(info1);
            json = gson.toJson(list, ArrayList.class);
            if (json == null) {
                Log.d(TAG, "Error json");
            } else {
                Log.d(TAG, json);
                json = myDesUtil.cifrar(json);
                Log.d(TAG, json);
                writeFile(json);
            }
            Toast.makeText(getApplicationContext(), "okay", Toast.LENGTH_LONG).show();
        }
        private boolean writeFile (String text){
            File file = null;
            FileOutputStream fileOutputStream = null;
            try {
                file = getFile();
                fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(text.getBytes(StandardCharsets.UTF_8));
                fileOutputStream.close();
                Log.d(TAG, "Hola");
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        private File getFile () {
            return new File(getDataDir(), archivo);
        }
    }*/
}
