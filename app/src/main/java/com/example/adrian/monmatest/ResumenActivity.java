package com.example.adrian.monmatest;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import static com.example.adrian.monmatest.Constantes.CAMERA_PERMISSION_REQUEST_CODE;
import static com.example.adrian.monmatest.Constantes.CODE_WRITE_EXTERNAL_STORAGE_PERMISSION;
import static com.example.adrian.monmatest.Constantes.MULTIPLE_PERMISSIONS_REQUEST_CODE;
import static com.example.adrian.monmatest.Constantes.TYPE_MOBILE;
import static com.example.adrian.monmatest.Constantes.TYPE_WIFI;
import static com.example.adrian.monmatest.Constantes.TYPE_NOT_CONNECTED;
import static com.example.adrian.monmatest.Constantes.permissions;


public class ResumenActivity extends AppCompatActivity {

    private static final String TAG = "ResumenInicio";
    private Context myContext;
    private Intent intent;
    private ImageView iv;
    private Button btn;
    private TextView numP, numCat, testhechos, calmedia, conexion;
    private Menu menuItems;
    private RelativeLayout rel;
    private AlertDialog aldi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);

        todosPermisos();

        myContext = this;
        rel = findViewById(R.id.pantallaInicio);
        iv = findViewById(R.id.ivanim);
        btn = findViewById(R.id.anim);
        //conexion = findViewById(R.id.conexion);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarAlert();
            }
        });

        NetworkChangeReceiver ncr = new NetworkChangeReceiver();
        Intent in = new Intent();

        ncr.onReceive(myContext, in);

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
                Repositorio.cogerDatosXML(myContext);
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

    /**
     * En el onResume, nos encargamos de que los textos mostrados en la actividad resumen
     * se traduzcan haciendo referencia al textview al que estan enlazados y pasandole
     * la traduccion.
     */
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
        btn.setText(getString(R.string.anim));

        MyLog.d(TAG, "Finalizando onResume...");


    }

    @Override
    protected void onRestart() {
        MyLog.d(TAG, "Iniciando onRestart...");//es como un print para mostrar mensajes y depurar
        super.onRestart();
        MyLog.d(TAG, "Finalizando onRestart...");
    }

    /**
     * Este metodo que comprobara la peticion de permisos de escritura y camara en el dispositivo
     */
    private void todosPermisos() {

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

    /**
     * @param requestCode
     * @param permissions
     * @param grantResults
     * En este metodo le pasamos un entero que sera el codigo del permiso, tendra un array
     * que guardara los permisos y otro array que guardara los resultados.
     */
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
                //finish();
                break;
            }
        }
        return allGranted;
    }

    /**
     * Este metodo muestra un toast que nos dira que se han aceptado los permisos
     */
    private void permissionGranted() {
        Toast.makeText(ResumenActivity.this, getString(R.string.write_permission_granted), Toast.LENGTH_SHORT).show();
    }

    /**
     * Este metodo muestra un toast que nos dira que se han denegado los permisos
     */
    private void permissionRejected() {
        Toast.makeText(ResumenActivity.this, getString(R.string.write_permission_not_accepted), Toast.LENGTH_SHORT).show();
    }


    /**
     * Este metodo se encarga crear un fichero de formato xml
     * en el que se guardaran todos los datos que se saquen de la base de datos
     * por cada pregunta que este almacenada.
     */
    private void exportarXML() {
        String root = Environment.getExternalStorageDirectory().toString();
        File ruta = new File(root + "/preguntasExportadas");
        String fname = "preguntas.xml";
        File file = new File(ruta, fname);
        try {
            if (!ruta.exists()) {
                ruta.mkdirs();
            }
            if (file.exists())
                file.delete();
            FileWriter fw = new FileWriter(file);
            //Escribimos en el fichero un String
            fw.write(Repositorio.crearXML());
            //Cierro el stream
            fw.close();
        } catch (Exception ex) {
            MyLog.e("Ficheros", "Error al escribir fichero");
        }
        String cadena = ruta.getAbsolutePath() + "/" + fname;
        Uri path = Uri.parse("file://" + cadena);

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "adrycarpe94@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Preguntas para Moodle");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Preguntas MonMatest");
        emailIntent.putExtra(Intent.EXTRA_STREAM, path);
        startActivity(Intent.createChooser(emailIntent, "Enviar por email..."));
    }

    /**
     * En este metodo, llamamos a todos los campos de las diferentes opciones
     * del menu, para asi despues poder aplicarle sus diferentes traducciones
     * a la hora de cambiar de idioma.
     */
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

    /**
     * Con este metodo, lo que hacemos es crear un alert dialog,
     * para que cuando se pulse un boton, se muestra una serie de opciones
     * para las distintas animaciones
     */
    private void mostrarAlert() {

        AlertDialog.Builder build = new AlertDialog.Builder(this);

        final CharSequence[] animacion = {"Entrar desde abajo", "Salir desde arriba",
                "Entrar desde derecha", "Salir desde izquierda"};

        build.setTitle("Opcion de animaciones").setItems(animacion, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (i == 0) {

                    Animation anim = AnimationUtils.loadAnimation(ResumenActivity.this, R.anim.enter_bottom);
                    iv.startAnimation(anim);

                } else if (i == 1) {

                    Animation anim = AnimationUtils.loadAnimation(ResumenActivity.this, R.anim.exit_up);
                    iv.startAnimation(anim);

                } else if (i == 2) {

                    Animation anim = AnimationUtils.loadAnimation(ResumenActivity.this, R.anim.enter_right);
                    iv.startAnimation(anim);

                } else if (i == 3) {

                    Animation anim = AnimationUtils.loadAnimation(ResumenActivity.this, R.anim.exit_left);
                    iv.startAnimation(anim);

                }

            }
        });

        aldi = build.show();

    }

}
