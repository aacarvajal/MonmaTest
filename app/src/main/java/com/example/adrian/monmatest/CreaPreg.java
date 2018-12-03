package com.example.adrian.monmatest;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

public class CreaPreg extends AppCompatActivity {

    private EditText edt1, edt2, edt3, edt4, edt5;
    private Button btn, btnCat;
    private static final String TAG = "CreaPreg";
    private final int CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 123;
    private Context myContext = this;
    private RelativeLayout relativeCreaPreg;
    private int codigo;
    private Repositorio r = Repositorio.getInstance();
    private ArrayList<String> categorias;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_preg);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        categorias = new ArrayList<>();

        //establece un titulo en la actividad
        getSupportActionBar().setTitle("Preguntas");//cambiar el titulo de la actividad

        items = new ArrayList<String>();

        //carga las categorias guardadas en la BD
        r.cargarCategorias(myContext);

        //guarda en el array las categorias
        items = r.getCategorias();

        //para usar el spinner
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        edt1 = (EditText) findViewById(R.id.preg1);
        edt2 = (EditText) findViewById(R.id.rsp1);
        edt3 = (EditText) findViewById(R.id.rsp2);
        edt4 = (EditText) findViewById(R.id.rsp3);
        edt5 = (EditText) findViewById(R.id.rsp4);
        btn = (Button) findViewById(R.id.btnguardar);
        btnCat = (Button) findViewById(R.id.anadirCat);

        try {
            codigo = getIntent().getExtras().getInt("codigo");
            edt1.setText(getIntent().getExtras().getString("enunciado"));
            edt2.setText(getIntent().getExtras().getString("rsp1"));
            edt3.setText(getIntent().getExtras().getString("rsp2"));
            edt4.setText(getIntent().getExtras().getString("rsp3"));
            edt5.setText(getIntent().getExtras().getString("rsp4"));
            spinner.setSelection(categorias.indexOf(getIntent().getExtras().getString("categoria")));
        } catch (NullPointerException e) {
            codigo = -1;
        }

        //este boton se encarga de guardar una nueva pregunta
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //estos if se encargan de que los campos no esten vacios, en caso de que los estuvieran
                //mostraria una notificacion en la parte inferior especificando cual de ellos esta vacio
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
                    relativeCreaPreg = findViewById(R.id.recyclerView);

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

                    //aqui controlamos la funcion de guardado y edicion
                    //si codigo es igual a -1 quiere decir que no tiene ningun campo relleno y que sera una nueva pregunta
                    //pero si el codigo tiene cualquier otro numero quiere decir que sera una pregunta ya rellena que se editara
                    if (codigo == -1) {

                        Pregunta p = new Pregunta(edt1.getText().toString(), edt2.getText().toString(), edt3.getText().toString(), edt4.getText().toString(), edt5.getText().toString(), spinner.getSelectedItem().toString());
                        r.insertar(p, myContext);
                        finish();

                    } else {

                        Pregunta p = new Pregunta(edt1.getText().toString(), edt2.getText().toString(), edt3.getText().toString(), edt4.getText().toString(), edt5.getText().toString(), spinner.getSelectedItem().toString());
                        p.setId(codigo);
                        r.Actualizar(p, myContext);
                        finish();

                    }

                }

            }
        });

        //este boton se encargara de añadir nuevas categorias y guardarlas en la BD
        btnCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutActivity = LayoutInflater.from(myContext);
                View viewAlertDialog = layoutActivity.inflate(R.layout.alert_dialog, null);

                //Definición del AlertDialog
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(myContext);

                //Asignación del AlertDialog a su vista
                alertDialog.setView(viewAlertDialog);

                //Recuperación del EditTextdel AlertDialog
                final EditText dialogInput = (EditText) viewAlertDialog.findViewById(R.id.dialogInput);

                //Configuración del AlertDialog
                alertDialog
                        .setCancelable(false)
                        //BotónAñadir
                        .setPositiveButton(getResources().getString(R.string.add),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        adapter.add(dialogInput.getText().toString());
                                        spinner.setSelection(adapter.getPosition(dialogInput.getText().toString()));
                                    }
                                })
                        //BotónCancelar
                        .setNegativeButton(getResources().getString(R.string.cancel),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                }).create()
                        .show();

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
