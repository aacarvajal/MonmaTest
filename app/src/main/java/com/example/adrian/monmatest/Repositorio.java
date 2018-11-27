package com.example.adrian.monmatest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Repositorio {

    private static ArrayList<Pregunta> pregArray = new ArrayList<>();
    private static Pregunta p;
    private static final Repositorio instance = new Repositorio();

    private Repositorio() {
    }

    public static Repositorio getInstance() {
        return instance;
    }

    public static void insertar(Pregunta p, Context contexto) {

        boolean valor = false;
        //si es true que lo meta en la BBDD

        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        PreguntaSQLiteHelper helper =
                new PreguntaSQLiteHelper(contexto, "monmatest.db", null, 1);

        SQLiteDatabase db = helper.getWritableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            //Insertamos los getter de las variables

                //Insertar un registro
                db.execSQL("INSERT INTO Pregunta (enunciado, rsp1, rsp2, rsp3, rsp4, categoria)" +
                        "VALUES ('" + p.getEnunciado() + "','" + p.getRsp1() + "'," + " '" + p.getRsp2() + "'," +
                        " '" + p.getRsp3() + "', '" + p.getRsp4() + "','" + p.getCategoria() + "')");



                //Cerramos la base de datos
            db.close();
        } else {

            valor = false;

        }


    }

    public void Actualizar(Pregunta p, Context contexto) {

        PreguntaSQLiteHelper helper =
                new PreguntaSQLiteHelper(contexto, "monmatest.db", null, 1);

        SQLiteDatabase db = helper.getWritableDatabase();

        /*int codigo = p.getId();
        String enunciado = p.getEnunciado();
        String rsp1 = p.getRsp1();
        String rsp2 = p.getRsp2();
        String rsp3 = p.getRsp3();
        String rsp4 = p.getRsp4();
        String categoria = p.getCategoria();

        ContentValues values = new ContentValues();

        //values.put("Codigo", codigo);
        values.put("enunciado", enunciado);
        values.put("rsp1", rsp1);
        values.put("rsp2", rsp2);
        values.put("rsp3", rsp3);
        values.put("rsp4", rsp4);
        values.put("categoria", categoria);

        db.update("Pregunta", values, "codigo=" + codigo, null);*/

        if (db != null) {
            //Insertamos los datos en la tabla Usuarios

            db.execSQL("UPDATE Pregunta SET enunciado = " + "'" + p.getEnunciado()
                    + "', rsp1 = " + "'" + p.getRsp1()
                    + "', rsp2 = " + "'" + p.getRsp2()
                    + "', rsp3 = " + "'" + p.getRsp3()
                    + "', rsp4 = " + "'" + p.getRsp4()
                    + "', categoria = " + "'" + p.getCategoria()
                    + "' WHERE codigo = " + p.getId());
        }

        db.close();


    }

    public static void Borrar(Context contexto) {

        PreguntaSQLiteHelper helper =
                new PreguntaSQLiteHelper(contexto, "monmatest.db", null, 1);

        SQLiteDatabase db = helper.getReadableDatabase();

        int codigo = p.getId();
        String enunciado = p.getEnunciado();
        String rsp1 = p.getRsp1();
        String rsp2 = p.getRsp2();
        String rsp3 = p.getRsp3();
        String rsp4 = p.getRsp4();
        String categoria = p.getCategoria();

        ContentValues values = new ContentValues();

        values.put("codigo", codigo);
        values.put("enunciado", enunciado);
        values.put("rsp1", rsp1);
        values.put("rsp2", rsp2);
        values.put("rsp3", rsp3);
        values.put("rsp4", rsp4);
        values.put("categoria", categoria);

        db.delete("Pregunta", "codigo=" + codigo, null);

    }

    public ArrayList<Pregunta> Consultar(Context contexto){

        pregArray.clear();

        PreguntaSQLiteHelper helper =
                new PreguntaSQLiteHelper(contexto, "monmatest.db", null, 1);

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Pregunta", null);

        if(c.moveToFirst()){

            do{

                int codigo = c.getInt( c.getColumnIndex("codigo"));
                String enunciado = c.getString( c.getColumnIndex("enunciado"));
                String rsp1 = c.getString( c.getColumnIndex("rsp1"));
                String rsp2 = c.getString( c.getColumnIndex("rsp2"));
                String rsp3 = c.getString( c.getColumnIndex("rsp3"));
                String rsp4 = c.getString( c.getColumnIndex("rsp4"));
                String categoria = c.getString( c.getColumnIndex("categoria"));
                Pregunta p = new Pregunta(codigo,enunciado,rsp1,rsp2,rsp3,rsp4,categoria);
                pregArray.add(p);

            }while(c.moveToNext());

        }

        return pregArray;

    }

    public ArrayList<Pregunta> getListaPreguntas() {
        return pregArray;
    }

    public void setListaPreguntas(ArrayList<Pregunta> pregArray) {
        this.pregArray = pregArray;
    }

}
