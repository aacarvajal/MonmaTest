package com.example.adrian.monmatest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import static com.example.adrian.monmatest.Constantes.*;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

public class Repositorio {

    private static ArrayList<Pregunta> pregArray;
    private static final Repositorio instance = new Repositorio();
    private static ArrayList<String> categorias;

    private Repositorio() {
    }

    public static Repositorio getInstance() {
        return instance;
    }

    //inserta en la base de datos todas las preguntas
    public static boolean insertar(Pregunta p, Context myContext) {

        boolean bool = true;
        //si es true que lo meta en la BBDD

        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        PreguntaSQLiteHelper helper =
                new PreguntaSQLiteHelper(myContext, BD, null, 1);

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

            bool = false;

        }

        return bool;

    }

    //inserta en la base de datos todas las preguntas incluyendo una foto
    public static boolean insertarFoto(Pregunta p, Context myContext) {

        boolean bool = true;
        //si es true que lo meta en la BBDD

        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        PreguntaSQLiteHelper helper =
                new PreguntaSQLiteHelper(myContext, BD, null, 1);

        SQLiteDatabase db = helper.getWritableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            //Insertamos los getter de las variables

            //Insertar un registro
            db.execSQL("INSERT INTO Pregunta (enunciado, rsp1, rsp2, rsp3, rsp4, categoria, foto)" +
                    "VALUES ('" + p.getEnunciado() + "','" + p.getRsp1() + "'," + " '" + p.getRsp2() + "'," +
                    " '" + p.getRsp3() + "', '" + p.getRsp4() + "','" + p.getCategoria() + "','" + p.getFoto() + "')");


            //Cerramos la base de datos
            db.close();
        } else {

            bool = false;

        }

        return bool;

    }

    //este metodo se encarga de actualizar los campos de una pregunta
    public void Actualizar(Pregunta p, Context myContext) {

        PreguntaSQLiteHelper helper =
                new PreguntaSQLiteHelper(myContext, BD, null, 1);

        SQLiteDatabase db = helper.getWritableDatabase();

        if (db != null) {
            //Insertamos los datos en la tabla

            db.execSQL("UPDATE Pregunta SET enunciado = " + "'" + p.getEnunciado()
                    + "', rsp1 = " + "'" + p.getRsp1()
                    + "', rsp2 = " + "'" + p.getRsp2()
                    + "', rsp3 = " + "'" + p.getRsp3()
                    + "', rsp4 = " + "'" + p.getRsp4()
                    + "', categoria = " + "'" + p.getCategoria()
                    + "', foto = " + "'" + p.getFoto()
                    + "' WHERE codigo = " + p.getId());

            db.close();

        }

        //db.close();


    }

    //este metodo se encarga de borrar una pregunta  haciendo una comparacion de ID
    public static void Borrar(Pregunta p, Context myContext) {

        PreguntaSQLiteHelper helper =
                new PreguntaSQLiteHelper(myContext, BD, null, 1);

        SQLiteDatabase db = helper.getReadableDatabase();

        int codigo = p.getId();

        db.delete("Pregunta", "codigo=" + codigo, null);

    }

    //este metodo se encarga de mostrar todos los datos de la BD en el listado
    public ArrayList<Pregunta> Consultar(Context myContext) {

        pregArray = new ArrayList<>();

        pregArray.clear();

        PreguntaSQLiteHelper helper =
                new PreguntaSQLiteHelper(myContext, BD, null, 1);

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Pregunta", null);

        if (c.moveToFirst()) {

            do {

                int codigo = c.getInt(c.getColumnIndex("codigo"));
                String enunciado = c.getString(c.getColumnIndex("enunciado"));
                String rsp1 = c.getString(c.getColumnIndex("rsp1"));
                String rsp2 = c.getString(c.getColumnIndex("rsp2"));
                String rsp3 = c.getString(c.getColumnIndex("rsp3"));
                String rsp4 = c.getString(c.getColumnIndex("rsp4"));
                String categoria = c.getString(c.getColumnIndex("categoria"));
                String foto = c.getString(c.getColumnIndex("foto"));
                Pregunta p = new Pregunta(codigo, enunciado, rsp1, rsp2, rsp3, rsp4, categoria, foto);
                pregArray.add(p);

            } while (c.moveToNext());

        }

        return pregArray;

    }

    public static Pregunta buscPreg(int id, Context myContext) {

        Pregunta p = new Pregunta();

        PreguntaSQLiteHelper helper =
                new PreguntaSQLiteHelper(myContext, BD, null, 1);

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Pregunta WHERE codigo='" + id + "'", null);

        if (c.moveToFirst()) {


            String enunciado = c.getString(1);
            String rsp1 = c.getString(2);
            String rsp2 = c.getString(3);
            String rsp3 = c.getString(4);
            String rsp4 = c.getString(5);
            String categoria = c.getString(6);
            String foto = c.getString(7);
            p = new Pregunta(enunciado, rsp1, rsp2, rsp3, rsp4, categoria, foto);

        }

        c.close();
        db.close();

        return p;

    }

    //este metodo se encarga de coger y mostrar de la BD todas las categorias que ya estaban guardadas
    public static void cargarCategorias(Context myContext) {


        categorias = new ArrayList<>();


        PreguntaSQLiteHelper helper =
                new PreguntaSQLiteHelper(myContext, BD, null, 1);

        SQLiteDatabase db = helper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT DISTINCT categoria FROM Pregunta", null);

        //comprobamos si hay algun registro en la BD
        if (c.moveToFirst()) {

            do {

                String categoria = c.getString(c.getColumnIndex("categoria"));

                categorias.add(categoria);

            } while (c.moveToNext());
        }


    }

    //Este metodo devolvera el total de las preguntas que se hayan creado en la BD
    public static String getTotalPreg(Context myContext) {

        String totalPreg = "";

        PreguntaSQLiteHelper helper =
                new PreguntaSQLiteHelper(myContext, BD, null, 1);

        SQLiteDatabase db = helper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT count(distinct codigo) FROM '" + Pregunta + "';", null);


        if (c.moveToFirst()) {
            totalPreg = c.getString(0);
        }

        c.close();

        return totalPreg;
    }

    public static String getTotalCat(Context myContext) {

        String totalCat = "";

        PreguntaSQLiteHelper helper =
                new PreguntaSQLiteHelper(myContext, BD, null, 1);

        SQLiteDatabase db = helper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT count(distinct categoria) FROM '" + Pregunta + "';", null);


        if (c.moveToFirst()) {
            totalCat = c.getString(0);
        }

        c.close();

        db.close();

        return totalCat;
    }

    //guarda en un array todas las preguntas
    public static ArrayList<Pregunta> getListaPreguntas() {
        return pregArray;
    }

    //guarda en un array todas las categorias
    public static ArrayList<String> getCategorias() {

        return categorias;

    }

    //este metodo se encarga de recoger todos los datos de las preguntas
    public static void cogerDatos(Context myContext) {
        pregArray = new ArrayList<>();
        PreguntaSQLiteHelper psdbh = new PreguntaSQLiteHelper(myContext, Constantes.BD, null, 1);

        SQLiteDatabase db = psdbh.getWritableDatabase();
        String[] cat = new String[]{"categoria", "Pregunta"};

        Cursor c = db.rawQuery("SELECT * FROM " + Constantes.Pregunta, null);

//Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                int codigo = c.getInt(c.getColumnIndex("codigo"));
                String enunciado = c.getString(c.getColumnIndex("enunciado"));
                String spinner = c.getString(c.getColumnIndex("categoria"));
                String rsp1 = c.getString(c.getColumnIndex("rsp1"));
                String rsp2 = c.getString(c.getColumnIndex("rsp2"));
                String rsp3 = c.getString(c.getColumnIndex("rsp3"));
                String rsp4 = c.getString(c.getColumnIndex("rsp4"));
                String foto = c.getString(c.getColumnIndex("foto"));

                Pregunta p = new Pregunta(codigo, enunciado, spinner, rsp1, rsp2, rsp3, rsp4, foto);
                pregArray.add(p);
            } while (c.moveToNext());
        }

    }

    public static String CreateXMLString() throws IllegalArgumentException, IllegalStateException, IOException {
        ArrayList<Pregunta> preguntasXML = new ArrayList<Pregunta>();
        preguntasXML = getListaPreguntas();


        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        xmlSerializer.setOutput(writer);

        //Start Document
        xmlSerializer.startDocument("UTF-8", true);
        xmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

        //Open Tag <file>
        xmlSerializer.startTag("", "quiz");

        for (Pregunta p : preguntasXML) {
            //Categoria
            xmlSerializer.startTag("", "question");
            xmlSerializer.attribute("", "type", p.getCategoria());
            xmlSerializer.startTag("", "category");
            xmlSerializer.text(p.getCategoria());
            xmlSerializer.endTag("", "category");
            xmlSerializer.endTag("", "question");
            //Pregunta
            xmlSerializer.startTag("", "question");
            xmlSerializer.attribute("", "type", "multichoice");
            xmlSerializer.startTag("", "name");
            xmlSerializer.text(p.getEnunciado());
            xmlSerializer.endTag("", "name");
            xmlSerializer.startTag("", "questiontext");
            xmlSerializer.attribute("", "format", "html");
            xmlSerializer.text(p.getEnunciado());
            xmlSerializer.startTag("", "file");
            xmlSerializer.attribute("", "name", p.getFoto());
            xmlSerializer.attribute("", "path", "/");
            xmlSerializer.attribute("", "encoding", "base64");
            xmlSerializer.endTag("", "file");
            xmlSerializer.endTag("", "questiontext");
            xmlSerializer.startTag("", "answernumbering");
            xmlSerializer.endTag("", "answernumbering");
            xmlSerializer.startTag("", "answer");
            xmlSerializer.attribute("", "fraction", "100");
            xmlSerializer.attribute("", "format", "html");
            xmlSerializer.text(p.getRsp1());
            xmlSerializer.endTag("", "answer");
            xmlSerializer.startTag("", "answer");
            xmlSerializer.attribute("", "fraction", "0");
            xmlSerializer.attribute("", "format", "html");
            xmlSerializer.text(p.getRsp2());
            xmlSerializer.endTag("", "answer");
            xmlSerializer.startTag("", "answer");
            xmlSerializer.attribute("", "fraction", "0");
            xmlSerializer.attribute("", "format", "html");
            xmlSerializer.text(p.getRsp3());
            xmlSerializer.endTag("", "answer");
            xmlSerializer.startTag("", "answer");
            xmlSerializer.attribute("", "fraction", "0");
            xmlSerializer.attribute("", "format", "html");
            xmlSerializer.text(p.getRsp4());
            xmlSerializer.endTag("", "answer");
            xmlSerializer.endTag("", "question");
        }

        xmlSerializer.endTag("", "quiz");
        xmlSerializer.endDocument();
        return writer.toString();


    }

}
