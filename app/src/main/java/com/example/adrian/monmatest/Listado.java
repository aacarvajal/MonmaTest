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
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


import java.util.Collections;
import java.util.List;

public class Listado extends AppCompatActivity {

    private static final String TAG = "Listado";
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        //crea una flecha que nos permitira retroceder a la actividad anterior
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Asignar la acción necesaria. En este caso "volver atrás"
                    onBackPressed();
                }
            });
        } else {
            Log.d("Listado", "Error al cargar toolbar");
        }


        //crea un boton flotante al que se le puede dar cualquier funcionalidad
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Listado.this, CreaPreg.class);
                startActivity(in);
            }
        });

    }

    @Override
    public void onBackPressed() {//para volver hacia atras pulsando el boton retroceso
        // Asignar la acción necesaria. En este caso terminar la actividad
        finish();
    }

    public boolean onSupportNavigateUp() {

        onBackPressed();
        return false;

    }

    /*@Override
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
    }*/

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

        //CardView

        rv = findViewById(R.id.recyclerView);//id del recyclerview

        rv.setLayoutManager(new LinearLayoutManager(this));

        r = Repositorio.getInstance();

        preguntas = r.getListaPreguntas();

        rva = new CardViewAdapter(cargarListado());//aqui conseguimos que se muestren todas las preguntas guardadas

        Collections.reverse(preguntas);//con esto conseguimos que se ordenede poniendo el mas reciente en primera posicion
        //y el mas antiguo en la ultima posicion

        if (!preguntas.isEmpty()) {
            TextView e = findViewById(R.id.sinprg);
            e.setVisibility(View.INVISIBLE);

            // Crea el Adaptador con los datos de la lista anterior
            CardViewAdapter adaptador = new CardViewAdapter(preguntas);

            // Asocia el Adaptador al RecyclerView
            rv.setAdapter(adaptador);

            //accion que permite clicar en el cardview para realizar una pregunta
            /*rv.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    Intent i = new Intent(Listado.this,PreguntaResuelta.class);
                    startActivity(i);
                    return true;

                }
            });*/

            //con ItemTouchHelper conseguimos el efecto de swipe, con el que se podra desplazar el cardview
            //a derecha e izquierda
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

                    if (direction == ItemTouchHelper.LEFT) { //swipe izquierda

                        //con alertdialog builder añadimos una ventana emergente como advertencia
                        //a la hora de desplazar el cardview para preguntar si esta seguro de la accion
                        //que quiere realizar
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Listado.this);
                        //se construye el titulo del mensaje
                        mBuilder.setTitle("Atención");
                        //se construye el mensaje que se mostrara en la ventana
                        mBuilder.setMessage("¿Quiere borrar el contenido?");
                        //boton de aceptar que borrara la pregunta
                        mBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                r.Borrar(preguntas.get(position), myContext);
                                recreate();

                                return;
                                //boton que cancelara la accion
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

                    if (direction == ItemTouchHelper.RIGHT) { //swipe derecha

                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Listado.this);
                        mBuilder.setTitle("Atención");
                        mBuilder.setMessage("¿Quiere editar el contenido?");

                        mBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //se guardan en varaibles cada campo para despues pasarselos al objeto de pregunta
                                int codigo = preguntas.get(position).getId();
                                String enunciado = preguntas.get(position).getEnunciado();
                                String rsp1 = preguntas.get(position).getRsp1();
                                String rsp2 = preguntas.get(position).getRsp2();
                                String rsp3 = preguntas.get(position).getRsp3();
                                String rsp4 = preguntas.get(position).getRsp4();
                                String categoria = preguntas.get(position).getCategoria();

                                Pregunta p = new Pregunta(codigo, enunciado, rsp1, rsp2, rsp3, rsp4, categoria);

                                r.Actualizar(p, myContext);

                                Intent it = new Intent(Listado.this, CreaPreg.class);
                                //se pasan todos los datos a sus campos correspondientes
                                it.putExtra("codigo", preguntas.get(position).getId());
                                it.putExtra("enunciado", preguntas.get(position).getEnunciado());
                                it.putExtra("rsp1", preguntas.get(position).getRsp1());
                                it.putExtra("rsp2", preguntas.get(position).getRsp2());
                                it.putExtra("rsp3", preguntas.get(position).getRsp3());
                                it.putExtra("rsp4", preguntas.get(position).getRsp4());
                                it.putExtra("categoria", preguntas.get(position).getCategoria());
                                //iniciara la actividad con todos los datos introducidos
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
            itemTouchHelper.attachToRecyclerView(rv); //le da la capacidad de desplazamiento al cardview

            // Muestra el RecyclerView en vertical
            rv.setLayoutManager(new LinearLayoutManager(this));
            MyLog.d(TAG, "Finalizando OnResume");

            // Muestra el RecyclerView en vertical
            rv.setLayoutManager(new LinearLayoutManager(this));

            MyLog.d(TAG, "Finalizando onResume...");

        } else {
            //si la base de datos esta vacia se mostrara un mensaje notificando que no hay preguntas, sino el mensaje se ocultara.
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

    private List<Pregunta> cargarListado() {
        Repositorio r = Repositorio.getInstance();
        preguntas = r.Consultar(this);
        return preguntas;

    }

}
