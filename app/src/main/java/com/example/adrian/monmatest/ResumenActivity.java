package com.example.adrian.monmatest;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class ResumenActivity extends AppCompatActivity {

    private static final String TAG = "ResumenInicio";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
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
                return true;
            case R.id.action_buscar:
                Log.i("ActionBar", "Buscar!");;
                return true;
            case R.id.action_settings:
                Log.i("ActionBar", "Settings!");;
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
        MyLog.d(TAG, "Finalizando onResume...");
    }

    @Override
    protected void onRestart() {
        MyLog.d(TAG, "Iniciando onRestart...");//es como un print para mostrar mensajes y depurar
        super.onRestart();
        MyLog.d(TAG, "Finalizando onRestart...");
    }

}
