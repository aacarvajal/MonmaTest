package com.example.adrian.monmatest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import java.util.Collections;
import java.util.List;

public class Listado extends AppCompatActivity {

    private static final String TAG = "ResumenInicio";
    private Intent intent;
    private Context myContext = this;
    private List<Pregunta> preguntas;
    private Repositorio r;
    private RecyclerView rv;
    private CardViewAdapter rva;

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
                Intent in = new Intent(Listado.this, CreaPreg.class);
                //in.putExtra("editar", false );
                startActivity(in);
            }
        });

    }

    @Override
    public void onBackPressed() {//para volver hacia atras pulsando el boton retroceso
        // Asignar la acción necesaria. En este caso terminar la actividad
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menuActivity; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_nuevo:
                Log.i("ActionBar", "Nuevo!");
                intent = new Intent(Listado.this, CreaPreg.class);
                return true;
            case R.id.action_buscar:
                Log.i("ActionBar", "Buscar!");
                return true;
            case R.id.action_acercade:
                Log.i("ActionBar", "Acercade!");
                intent = new Intent(Listado.this, AcercadeActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_listado:
                Log.i("ActionBar", "Inicio!");
                intent = new Intent(Listado.this, ResumenActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

        //recycler view
        /*cargarListado();

        r = Repositorio.getInstance();
        Collections.reverse(preguntas);//invertir el orden de la lista para incluir en la primera posicion la ultima creada
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
        }*/

        //CardView

        rv = findViewById(R.id.recyclerView);//id del recyclerview
        rv.setLayoutManager(new LinearLayoutManager(this));

        r = Repositorio.getInstance();

        preguntas = r.getListaPreguntas();

        //rv = new RecyclerView((Context) cargarListado());

        rva = new CardViewAdapter(cargarListado());

        //rv.setAdapter(rva);
        Collections.reverse(preguntas);

        if (!preguntas.isEmpty()) {
            TextView e = findViewById(R.id.sinprg);
            e.setVisibility(View.INVISIBLE);

            // Crea el Adaptador con los datos de la lista anterior
            CardViewAdapter adaptador = new CardViewAdapter(preguntas);

            // Asocia el Adaptador al RecyclerView
            rv.setAdapter(adaptador);

            ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback
                    (0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                      RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                    final int position = viewHolder.getAdapterPosition(); //get position which is swipe

                    /*if (direction == ItemTouchHelper.LEFT) { //if swipe left

                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Listado.this);
                        mBuilder.setTitle("Atención");
                        mBuilder.setMessage("¿Quiere borrar el contenido?");
                        Button btn_Eliminar = (Button) mView.findViewById(R.id.btnConfirm);
                        btn_Eliminar.setText("Eliminar");
                        btn_Eliminar.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                r.eliminarPegunta(items.get(position).getCodigo(), miContexto);
                                recreate();
                                return;
                            }
                        });
                        Button btn_Cancelar = (Button) mView.findViewById(R.id.btnCancel);
                        btn_Cancelar.setText("Cancelar");
                        btn_Cancelar.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                recreate();
                                return;
                            }
                        });

                        mBuilder.setView(mView);
                        final AlertDialog dialog = mBuilder.create();
                        dialog.show();
                    }*/

                    if (direction == ItemTouchHelper.RIGHT) { //if swipe right

                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Listado.this);
                        mBuilder.setTitle("Atención");
                        mBuilder.setMessage("¿Quiere editar el contenido?");

                        mBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int codigo = preguntas.get(position).getId();
                                String enunciado = preguntas.get(position).getEnunciado();
                                String rsp1 = preguntas.get(position).getRsp1();
                                String rsp2 = preguntas.get(position).getRsp2();
                                String rsp3 = preguntas.get(position).getRsp3();
                                String rsp4 = preguntas.get(position).getRsp4();
                                String categoria = preguntas.get(position).getCategoria();

                                Pregunta p = new Pregunta(codigo, enunciado, rsp1, rsp2, rsp3, rsp4, categoria);

                                r.Actualizar(p, myContext);

                                //int position = rv.getChildAdapterPosition(viewHolder);

                                Intent it = new Intent(Listado.this, CreaPreg.class);

                                it.putExtra("codigo", preguntas.get(position).getId());
                                it.putExtra("enunciado", preguntas.get(position).getEnunciado());
                                it.putExtra("rsp1", preguntas.get(position).getRsp1());
                                it.putExtra("rsp2", preguntas.get(position).getRsp2());
                                it.putExtra("rsp3", preguntas.get(position).getRsp3());
                                it.putExtra("rsp4", preguntas.get(position).getRsp4());
                                it.putExtra("categoria", preguntas.get(position).getCategoria());


                                //it.putExtra("editar", true);

                                startActivity(it);

                                return;
                            }
                        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                recreate();
                                return;
                            }
                        });

                        //mBuilder.setView(mView);
                        final AlertDialog dialog = mBuilder.create();
                        dialog.show();
                    }
                }
            };

            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
            itemTouchHelper.attachToRecyclerView(rv); //set swipe to recylcerview

            // Muestra el RecyclerView en vertical
            rv.setLayoutManager(new LinearLayoutManager(this));
            MyLog.d(TAG, "Finalizando OnResume");

            /*MyLog.d(TAG, "PruebaPruebaPruebaPruebaPruebaPruebaPruebaPruebaPrueba");

                    int position = rv.getChildAdapterPosition(v);

                    Intent it = new Intent(Listado.this, CreaPreg.class);

                    it.putExtra("codigo", preguntas.get(position).getId());
                    it.putExtra("enunciado", preguntas.get(position).getEnunciado());
                    it.putExtra("respuestaCorrecta", preguntas.get(position).getRsp1());
                    it.putExtra("respuestaIncorrecta1", preguntas.get(position).getRsp2());
                    it.putExtra("respuestaIncorrecta2", preguntas.get(position).getRsp3());
                    it.putExtra("respuestaIncorrecta3", preguntas.get(position).getRsp4());
                    it.putExtra("categoria", preguntas.get(position).getCategoria());
                    it.putExtra("editar", true);

                    startActivity(it);*/

            // Muestra el RecyclerView en vertical
            rv.setLayoutManager(new LinearLayoutManager(this));

            MyLog.d(TAG, "Finalizando onResume...");

        } else {
            TextView e = findViewById(R.id.sinprg);
            e.setVisibility(View.VISIBLE);
        }

        /*new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                preguntas.remove(viewHolder.getAdapterPosition());

                Toast.makeText(Listado.this, "Pregunta borrada", Toast.LENGTH_SHORT).show();

                rva.notifyDataSetChanged();


            }
        }).attachToRecyclerView(rv);*/

    }

    @Override
    protected void onRestart() {
        MyLog.d(TAG, "Iniciando onRestart...");//es como un print para mostrar mensajes y depurar
        super.onRestart();
        MyLog.d(TAG, "Finalizando onRestart...");
    }

    private List<Pregunta> cargarListado() {
        Repositorio r = Repositorio.getInstance();
        preguntas = r.Consultar(this);

        return preguntas;

    }

}
