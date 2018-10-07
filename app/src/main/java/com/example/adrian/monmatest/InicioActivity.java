package com.example.adrian.monmatest;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class InicioActivity extends AppCompatActivity {

    private static int DURACION = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(InicioActivity.this, SegundoActivity.class);
                startActivity(intent);
                finish();

            }
        },DURACION);

    }
}
