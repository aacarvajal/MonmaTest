package com.example.adrian.monmatest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PreguntaResuelta extends AppCompatActivity {

    private static final String TAG = "PreguntaResuelta";
    private TextView txt1, txt2,txt3,txt4,txt5;
    private Button btnProbar;
    private int codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta_resuelta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
            Log.d("Acerca de", "Error al cargar toolbar");
        }

        txt1 = findViewById(R.id.enunciado);
        txt2 = findViewById(R.id.rsp1);
        txt3 = findViewById(R.id.rsp2);
        txt4 = findViewById(R.id.rsp3);
        txt5 = findViewById(R.id.rsp4);
        btnProbar = findViewById(R.id.btnProbar);

        try {

            codigo = getIntent().getExtras().getInt("codigo");
            txt1.setText(getIntent().getExtras().getString("enunciado"));
            txt2.setText(getIntent().getExtras().getString("rsp1"));
            txt3.setText(getIntent().getExtras().getString("rsp2"));
            txt4.setText(getIntent().getExtras().getString("rsp3"));
            txt5.setText(getIntent().getExtras().getString("rsp4"));

        } catch (NullPointerException e) {
            codigo = -1;
        }


    }

}
