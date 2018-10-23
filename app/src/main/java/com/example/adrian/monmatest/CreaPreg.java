package com.example.adrian.monmatest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreaPreg extends AppCompatActivity {

    EditText edt1, edt2, edt3, edt4, edt5;
    Button btn;

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

        edt1 = (EditText)findViewById(R.id.preg1);
        edt2 = (EditText)findViewById(R.id.rsp1);
        edt3 = (EditText)findViewById(R.id.rsp2);
        edt4 = (EditText)findViewById(R.id.rsp3);
        edt5 = (EditText)findViewById(R.id.rsp4);
        btn = (Button)findViewById(R.id.btnguardar);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edt1.getText().toString().isEmpty() || edt2.getText().toString().isEmpty() ||
                        edt3.getText().toString().isEmpty() || edt4.getText().toString().isEmpty()
                        || edt5.getText().toString().isEmpty()){

                    Snackbar.make(view, "Rellene todos los campos", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }else{

                    Intent in = new Intent(CreaPreg.this,Listado.class);
                    startActivity(in);

                }



            }
        });

    }

}
