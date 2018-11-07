package com.example.adrian.monmatest;

import android.Manifest;
import android.content.Context;
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

    private EditText edt1, edt2, edt3, edt4, edt5;
    private Button btn;
    private static final String TAG = "activity_splash_screen";
    private final int CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 123;
    private Context myContext;
    private RelativeLayout relativeCreaPreg;
    private Spinner spiner;

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
        String[] categoria = {"Montaje y mantenimiento de equipos", "FOL", "Redes locales", "Aplicaciones ofimáticas", "Sistemas operativos monopuesto"};
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoria));

        //COMPROBAR SI LOS CAMPOS ESTAN VACIOS

        edt1 = (EditText) findViewById(R.id.preg1);
        edt2 = (EditText) findViewById(R.id.rsp1);
        edt3 = (EditText) findViewById(R.id.rsp2);
        edt4 = (EditText) findViewById(R.id.rsp3);
        edt5 = (EditText) findViewById(R.id.rsp4);
        btn = (Button) findViewById(R.id.btnguardar);
        spiner = (Spinner) findViewById(R.id.spinner);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edt1.getText().toString().isEmpty()) {

                    Snackbar.make(view, "Rellene el campo de la pregunta", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else if (edt2.getText().toString().isEmpty()) {

                    Snackbar.make(view, "Rellene el campo de la respuesta 1", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else if (edt3.getText().toString().isEmpty()) {

                    Snackbar.make(view, "Rellene el campo de la respuesta 2", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else if (edt4.getText().toString().isEmpty()) {

                    Snackbar.make(view, "Rellene el campo de la respuesta 3", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else if (edt5.getText().toString().isEmpty()) {

                    Snackbar.make(view, "Rellene el campo de la respuesta 4", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else {

                    //INCLUSION DE PERMISOS

                    // Recuperamos el Layout donde mostrar el Snackbar con las notificaciones
                    relativeCreaPreg = findViewById(R.id.relativeCreaPreg);

                    // Almacenamos el contexto de la actividad para utilizar en las clases internas
                    myContext = CreaPreg.this;

                    btn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            int WriteExternalStoragePermission = ContextCompat.checkSelfPermission(myContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            Log.d("MainActivity", "WRITE_EXTERNAL_STORAGE Permission: " + WriteExternalStoragePermission);

                            if (WriteExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                                // Permiso denegado
                                // A partir de Marshmallow (6.0) se pide aceptar o rechazar el permiso en tiempo de ejecución
                                // En las versiones anteriores no es posible hacerlo
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    ActivityCompat.requestPermissions(CreaPreg.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
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

                    //AQUI VA LO DE INSERTAR
                    Pregunta p = new Pregunta(edt1.getText().toString(), spiner.getSelectedItem().toString(),
                            edt2.getText().toString(), edt3.getText().toString(), edt4.getText().toString(),
                            edt5.getText().toString());

                    Repositorio.insertar(p, myContext);

                    finish();


                }

            }
        });


    }

    @Override
    protected void onStart() {
        MyLog.d(TAG, "Iniciando OnStart");
        super.onStart();
        MyLog.d(TAG, "Finalizando OnStart");
    }

    @Override
    protected void onResume() {
        MyLog.d(TAG, "Iniciando OnResume");
        super.onResume();
        MyLog.d(TAG, "Finalizando OnResume");
    }

    @Override
    protected void onPause() {
        MyLog.d(TAG, "Iniciando OnPause");
        super.onPause();
        MyLog.d(TAG, "Finalizando OnPause");
    }

    @Override
    protected void onStop() {
        MyLog.d(TAG, "Iniciando OnStop");
        super.onStop();
        MyLog.d(TAG, "Finalizando OnStop");
    }

    @Override
    protected void onRestart() {
        MyLog.d(TAG, "Iniciando OnRestart");
        super.onRestart();
        MyLog.d(TAG, "Finalizando OnRestart");
    }

    @Override
    protected void onDestroy() {
        MyLog.d(TAG, "Iniciando OnDestroy");
        super.onDestroy();
        MyLog.d(TAG, "Finalizando OnDestroy");
    }

}
