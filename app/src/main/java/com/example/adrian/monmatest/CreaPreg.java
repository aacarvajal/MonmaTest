package com.example.adrian.monmatest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

public class CreaPreg extends AppCompatActivity {

    EditText edt1, edt2, edt3, edt4, edt5;
    Button btn;
    private final int CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 123;
    private Context myContext;
    private RelativeLayout relativeCreaPreg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_preg);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Preguntas");//cambiar el titulo de la actividad

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //para usar el spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        String[] categoria = {"Montaje y mantenimiento de equipos","FOL","Redes locales","Aplicaciones ofimáticas","Sistemas operativos monopuesto"};
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoria));

        //COMPROBAR SI LOS CAMPOS ESTAN VACIOS

        edt1 = (EditText)findViewById(R.id.preg1);
        edt2 = (EditText)findViewById(R.id.rsp1);
        edt3 = (EditText)findViewById(R.id.rsp2);
        edt4 = (EditText)findViewById(R.id.rsp3);
        edt5 = (EditText)findViewById(R.id.rsp4);
        btn = (Button)findViewById(R.id.btnguardar);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edt1.getText().toString().isEmpty()){

                    Snackbar.make(view, "Rellene el campo de la pregunta", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }else if(edt2.getText().toString().isEmpty()){

                    //edt2.setBackgroundColor(edt2.getCurrentHintTextColor());

                    Snackbar.make(view, "Rellene el campo de la respuesta 1", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    /*Boolean bol;

                    bol = edt2.setOnClickListener(edt2.setBackground(none);*/

                }else if(edt3.getText().toString().isEmpty()){

                    Snackbar.make(view, "Rellene el campo de la respuesta 2", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }else if(edt4.getText().toString().isEmpty()){

                    Snackbar.make(view, "Rellene el campo de la respuesta 3", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }else if(edt5.getText().toString().isEmpty()){

                    Snackbar.make(view, "Rellene el campo de la respuesta 4", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }else{

                    //INCLUSION DE PERMISOS

                    // Recuperamos el Layout donde mostrar el Snackbar con las notificaciones
                    relativeCreaPreg = findViewById(R.id.relativeCreaPreg);

                    // Almacenamos el contexto de la actividad para utilizar en las clases internas
                    myContext = CreaPreg.this;

                    // Los permisos peligrosos se deben solicitar en tiempo de ejecución si no se poseen
                    // Si se acepta un permiso del grupo al que pertenezca se están aceptando también el resto de permisos
                    // Los permisos deben aparecer en Manifest.xml
                    Button buttonDangerous = findViewById(R.id.btnguardar);
                    buttonDangerous.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            int WriteExternalStoragePermission = ContextCompat.checkSelfPermission(myContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            Log.d("MainActivity", "WRITE_EXTERNAL_STORAGE Permission: " + WriteExternalStoragePermission);

                            if (WriteExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                                // Permiso denegado
                                // A partir de Marshmallow (6.0) se pide aceptar o rechazar el permiso en tiempo de ejecución
                                // En las versiones anteriores no es posible hacerlo
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                    ActivityCompat.requestPermissions(CreaPreg.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
                                    // Una vez que se pide aceptar o rechazar el permiso se ejecuta el método "onRequestPermissionsResult" para manejar la respuesta
                                    // Si el usuario marca "No preguntar más" no se volverá a mostrar este diálogo
                                } else {
                                    Snackbar.make(relativeCreaPreg, getResources().getString(R.string.write_permission_denied), Snackbar.LENGTH_LONG)
                                            .show();
                                }
                            } else {
                                // Permiso aceptado
                                Snackbar.make(relativeCreaPreg, getResources().getString(R.string.write_permission_granted), Snackbar.LENGTH_LONG)
                                        .show();
                            }

                        }
                    });

                    /* in = new Intent(CreaPreg.this,Listado.class);
                    startActivity(in);*/

                }

            }
        });




    }

}
