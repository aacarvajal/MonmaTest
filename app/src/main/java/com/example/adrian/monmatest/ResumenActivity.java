package com.example.adrian.monmatest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;
import static com.example.adrian.monmatest.Constantes.CODE_WRITE_EXTERNAL_STORAGE_PERMISSION;
import static com.example.adrian.monmatest.Constantes.permissions;

import static com.example.adrian.monmatest.Constantes.MY_PERMISSIONS_REQUEST_CAMERA;
import static com.example.adrian.monmatest.Constantes.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE;
import static com.example.adrian.monmatest.ParserXml.ETIQUETA_ENUNCIADO;

public class ResumenActivity extends AppCompatActivity {

    private static final String TAG = "ResumenInicio";
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1;
    private static final int MULTIPLE_PERMISSIONS_REQUEST_CODE = 3;
    private Context myContext;
    private Intent intent;
    private TextView numP, numCat, testhechos, calmedia;
    private Menu menuItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);

        todosPermisos();
        //permisosCamara();
        myContext = this;


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

        this.menuItems = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*case R.id.action_nuevo:
                Log.i("ActionBar", "Nuevo!");
                intent = new Intent(ResumenActivity.this, CreaPreg.class);
                startActivity(intent);
                return true;*/
            case R.id.action_listado:
                Log.i("ActionBar", "Preguntas!");
                intent = new Intent(ResumenActivity.this, Listado.class);
                startActivity(intent);
                return true;
            /*case R.id.action_buscar:
                Log.i("ActionBar", "Buscar!");
                return true;*/
            case R.id.action_acercade:
                Log.i("ActionBar", "Acercade!");
                intent = new Intent(ResumenActivity.this, AcercadeActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                Log.i("ActionBar", "Configuracion!");
                intent = new Intent(ResumenActivity.this, ConfiguracionActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_exportar:
                Repositorio.cogerDatos(myContext);
                exportarXML();
                Log.i("ActionBar", "Exportar!");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        MyLog.d(TAG, "Iniciando onStart...");//es como un print para mostrar mensajes y depurar
        super.onStart();

        actIdiomaMenu();
        
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

        numP = findViewById(R.id.numPreg);
        numCat = findViewById(R.id.numCat);
        testhechos = findViewById(R.id.testhechos);
        calmedia = findViewById(R.id.calmedia);

        numP.setText(getString(R.string.preguntas_disponibles_10) + " " + Repositorio.getTotalPreg(myContext));
        numCat.setText(getString(R.string.categor_as_disponibles_2) + " " + Repositorio.getTotalCat(myContext));
        testhechos.setText(getString(R.string.cuestionarios_realizados_10));
        calmedia.setText(getString(R.string.calificaci_n_media_6_7_10));

        MyLog.d(TAG, "Finalizando onResume...");
    }

    @Override
    protected void onRestart() {
        MyLog.d(TAG, "Iniciando onRestart...");//es como un print para mostrar mensajes y depurar
        super.onRestart();
        MyLog.d(TAG, "Finalizando onRestart...");
    }

    //metodo que comprobara la peticion de permisos de escritura en el dispositivo
    private void todosPermisos() {

        /* Se mostrara una ventana emergente que pedira la confirmacion
         * para la escritura en el dispositivo
         */

        //Verifica si los permisos establecidos se encuentran concedidos
        if (ActivityCompat.checkSelfPermission(ResumenActivity.this, permissions[0]) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(ResumenActivity.this, permissions[1]) != PackageManager.PERMISSION_GRANTED) {
            //Si alguno de los permisos no esta concedido lo solicita
            ActivityCompat.requestPermissions(ResumenActivity.this, permissions, MULTIPLE_PERMISSIONS_REQUEST_CODE);
        } else {
            //Si todos los permisos estan concedidos prosigue con el flujo normal
            //permissionGranted();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CODE_WRITE_EXTERNAL_STORAGE_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(ResumenActivity.this, getString(R.string.write_permission_granted), Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(ResumenActivity.this, getString(R.string.write_permission_denied), Toast.LENGTH_SHORT).show();

                    MyLog.e("Permisos: ", "Rechazados");

                }

                break;

            case CAMERA_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(ResumenActivity.this, getString(R.string.write_permission_granted), Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(ResumenActivity.this, getString(R.string.write_permission_denied), Toast.LENGTH_SHORT).show();

                }
            case MULTIPLE_PERMISSIONS_REQUEST_CODE:
                //Verifica si todos los permisos se aceptaron o no
                if (validatePermissions(grantResults)) {
                    //Si todos los permisos fueron aceptados continua con el flujo normal
                    permissionGranted();
                } else {
                    //Si algun permiso fue rechazado no se puede continuar
                    permissionRejected();
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private boolean validatePermissions(int[] grantResults) {
        boolean allGranted = false;
        //Revisa cada uno de los permisos y si estos fueron aceptados o no
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                //Si todos los permisos fueron aceptados retorna true
                allGranted = true;
            } else {
                //Si algun permiso no fue aceptado retorna false
                allGranted = false;
                break;
            }
        }
        return allGranted;
    }

    private void permissionGranted() {
        Toast.makeText(ResumenActivity.this, getString(R.string.write_permission_granted), Toast.LENGTH_SHORT).show();
    }

    private void permissionRejected() {
        Toast.makeText(ResumenActivity.this, getString(R.string.write_permission_not_accepted), Toast.LENGTH_SHORT).show();
    }

    private void exportarXML() {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/preguntasExportar");
        String fname = "preguntas.xml";
        File file = new File(myDir, fname);
        try {
            if (!myDir.exists()) {
                myDir.mkdirs();
            }
            if (file.exists())
                file.delete();
            FileWriter fw = new FileWriter(file);
            //Escribimos en el fichero un String
            fw.write(Repositorio.CreateXMLString());
            //Cierro el stream
            fw.close();
        } catch (Exception ex) {
            MyLog.e("Ficheros", "Error al escribir fichero a memoria interna");
        }
        String cadena = myDir.getAbsolutePath() + "/" + fname;
        Uri path = Uri.parse("file://" + cadena);

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "ii.sho.hai@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Preguntas para plataforma Moodle");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Adjunto las preguntas");
        emailIntent.putExtra(Intent.EXTRA_STREAM, path);
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    private void XmlPullParser() throws XmlPullParserException, IOException {

        InputStream inputStream = new FileInputStream(new File("preguntas.xml"));

        String result = "";

        XmlPullParser parser = Xml.newPullParser();

        parser.setInput(inputStream, null);

        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        //return result;

        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }

        parser.require(XmlPullParser.START_TAG, "enunciado", ETIQUETA_ENUNCIADO);

    }

    private void actIdiomaMenu() {
        //botones del menu
        if (menuItems != null) {
            MenuItem itemAcercaDe = menuItems.findItem(R.id.action_acercade);
            MenuItem itemConfig = menuItems.findItem(R.id.action_settings);
            MenuItem itemExportar = menuItems.findItem(R.id.action_exportar);
            MenuItem itemLista = menuItems.findItem(R.id.action_listado);

            itemAcercaDe.setTitle(R.string.action_acercade);
            itemConfig.setTitle(R.string.action_settings);
            itemExportar.setTitle(R.string.action_exportar);
            itemLista.setTitle(R.string.action_listado);
        }

    }

}
