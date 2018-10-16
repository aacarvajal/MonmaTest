package com.example.adrian.monmatest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ResumenActivity extends AppCompatActivity {

    private static final String TAG = "ResumenInicio";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);
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
