package com.example.adrian.monmatest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class Repositorio{

    //Pregunta p = new Pregunta();

    public static void insertar(Pregunta p, Context contexto) {

        boolean valor = false;
        //si es true que lo meta en la BBDD

        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        PreguntaSQLiteHelper helper =
                new PreguntaSQLiteHelper(contexto, "monmatest", null, 1);

        SQLiteDatabase db = helper.getWritableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            //Insertamos los getter de las variables

            for (int i = 1; i <= 5; i++) {
                //Generamos los datos
                int codigo = i;
                String nombre = "Pregunta" + i;
                //Insertar un registro
                db.execSQL("INSERT INTO Pregunta (enunciado, rsp1, rsp2, rsp3, rsp4, categoria)" +
                        "VALUES ('" + p.getEnunciado() + "','" + p.getRsp1() + "'," + " '" + p.getRsp2() + "'," +
                        " '" + p.getRsp3() + "', '" + p.getRsp4() + "','" + p.getCategoria() + "')");

                //codigo, enunciado,categoria, respuestaCorrecta, respuestaIncorrecta1, respuestaIncorrecta2 , respuestaIncorrecta3
                //Cerramos la base de datos
            }
            db.close();
        } else {

            valor = false;

        }

        /*//Creamos el registro a insertar como objeto ContentValues
        ContentValues nuevoRegistro = new ContentValues();
        nuevoRegistro.put("enunciado", p.getEnunciado());
        nuevoRegistro.put("respuesta1", p.getRsp1());
        nuevoRegistro.put("respuesta2", p.getRsp2());
        nuevoRegistro.put("respuesta3", p.getRsp3());
        nuevoRegistro.put("respuesta4", p.getRsp4());

        //Insertamos el registro en la base de datos
        db.insert("monmatest", null, nuevoRegistro);*/

    }

}
