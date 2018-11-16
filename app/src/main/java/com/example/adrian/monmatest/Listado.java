package com.example.adrian.monmatest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Listado extends AppCompatActivity {

    private static final String TAG = "ResumenInicio";
    private Context myContext;
    private RecyclerView recycler;
    private ArrayList<Pregunta> preguntas;
    private Repositorio r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent in = new Intent(Listado.this,CreaPreg.class);
                startActivity(in);
            }
        });



    }

    @Override
    protected void onStart() {
        MyLog.d(TAG, "Iniciando onStart...");//es como un print para mostrar mensajes y depurar
        super.onStart();
        MyLog.d(TAG, "Finalizando onStart...");
    }

    @Override
    protected void onStop() {
        MyLog.d(TAG, "Iniciando onStop...");//es como un print para mostrar mensajes y depurar
        super.onStop();
        MyLog.d(TAG, "Finalizando onStop...");
    }

    @Override
    protected void onDestroy() {
        MyLog.d(TAG, "Iniciando onDestroy...");//es como un print para mostrar mensajes y depurar
        super.onDestroy();
        MyLog.d(TAG, "Finalizando onDestroy...");
    }

    @Override
    protected void onPause() {
        MyLog.d(TAG, "Iniciando onPause...");//es como un print para mostrar mensajes y depurar
        super.onPause();
        MyLog.d(TAG, "Finalizando onPause...");
    }

    @Override
    protected void onResume() {
        MyLog.d(TAG, "Iniciando onResume...");//es como un print para mostrar mensajes y depurar
        super.onResume();

        cargarListado();

        r = Repositorio.getInstance();

        preguntas = r.getListaPreguntas();
        // Inicializa el RecyclerView
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        if (!preguntas.isEmpty()) {
            TextView e = findViewById(R.id.sinprg);
            e.setVisibility(View.INVISIBLE);

            // Crea el Adaptador con los datos de la lista anterior
            PreguntaAdapter adaptador = new PreguntaAdapter(preguntas);

            // Asocia el elemento de la lista con una acción al ser pulsado
            adaptador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Acción al pulsar el elemento
                    int position = recyclerView.getChildAdapterPosition(v);
                    Toast.makeText(Listado.this, "Posición: " + String.valueOf(position)
                            + " Id: " + preguntas.get(position).getId() + " Nombre: " +
                            preguntas.get(position).getEnunciado(), Toast.LENGTH_SHORT).show();
                }
            });

            // Asocia el Adaptador al RecyclerView
            recyclerView.setAdapter(adaptador);

            // Muestra el RecyclerView en vertical
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            MyLog.d(TAG, "Finalizando onResume...");
        } else {
            TextView e = findViewById(R.id.sinprg);
            e.setVisibility(View.VISIBLE);
        }


    }

    @Override
    protected void onRestart() {
        MyLog.d(TAG, "Iniciando onRestart...");//es como un print para mostrar mensajes y depurar
        super.onRestart();
        MyLog.d(TAG, "Finalizando onRestart...");
    }

    private void cargarListado() {
        Repositorio r = Repositorio.getInstance();
        preguntas = r.Consultar(this);

    }

}
