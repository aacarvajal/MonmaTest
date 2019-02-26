package com.example.adrian.monmatest;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.adrian.monmatest.Constantes.CODE_WRITE_EXTERNAL_STORAGE_PERMISSION;
import static com.example.adrian.monmatest.Constantes.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE;
import static com.example.adrian.monmatest.Constantes.REQUEST_CAPTURE_IMAGE;
import static com.example.adrian.monmatest.Constantes.REQUEST_SELECT_IMAGE;

public class CreaPreg extends AppCompatActivity {

    private ImageView foto;
    private EditText edt1, edt2, edt3, edt4, edt5;
    private Button btn, btnCat, btnBorrar;
    private static final String TAG = "CreaPreg";
    //private final int CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 123;
    private Context myContext;
    private RelativeLayout relativeCreaPreg;
    private int codigo;
    private boolean editar = false;
    private Repositorio r = Repositorio.getInstance();
    private ArrayAdapter<String> adapter;
    private ArrayList<String> items;
    private static Spinner spinner;
    //seccion fotos
    final String pathFotos = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/demoAndroidImages/";
    private Uri uri;
    private Bitmap bmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_preg);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myContext = this;

        btnBorrar = findViewById(R.id.borrarImagen);
        //establece un titulo en la actividad
        getSupportActionBar().setTitle("Preguntas");//cambiar el titulo de la actividad

        //crea una flecha que nos permitira retroceder a la actividad anterior
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
            Log.d("CreaPreg", "Error al cargar toolbar");
        }

        //se le asigna la foto que debera de aparecer cuando eliminemos la foto
        //que se eligio de la galeria o la que se hizo nueva con la camara
        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foto.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));
                //foto.setImageResource(0);
                foto.setRotation(0);
            }
        });

        items = new ArrayList<String>();

        //carga las categorias guardadas en la BD
        r.cargarCategorias(myContext);

        //guarda en el array las categorias
        items = r.getCategorias();

        //para usar el spinner
        spinner = (Spinner) findViewById(R.id.spinner);

        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        edt1 = (EditText) findViewById(R.id.preg1);
        edt2 = (EditText) findViewById(R.id.rsp1);
        edt3 = (EditText) findViewById(R.id.rsp2);
        edt4 = (EditText) findViewById(R.id.rsp3);
        edt5 = (EditText) findViewById(R.id.rsp4);
        btn = (Button) findViewById(R.id.btnguardar);
        btnCat = (Button) findViewById(R.id.anadirCat);
        relativeCreaPreg = findViewById(R.id.recyclerView);
        foto = findViewById(R.id.foto);

        editar = getIntent().getExtras().getBoolean(Constantes.editar);
        codigo = getIntent().getExtras().getInt(Constantes.codPreg);

        if (editar) {

            Pregunta p = new Pregunta();

            p = r.buscPreg(codigo, myContext);

            //se rellenaran los campos al editar la pregunta
            codigo = getIntent().getExtras().getInt("codigo");

            /*edt1.setText(getIntent().getExtras().getString("enunciado"));
            edt2.setText(getIntent().getExtras().getString("rsp1"));
            edt3.setText(getIntent().getExtras().getString("rsp2"));
            edt4.setText(getIntent().getExtras().getString("rsp3"));
            edt5.setText(getIntent().getExtras().getString("rsp4"));
            spinner.setSelection(items.indexOf(getIntent().getExtras().getString("categoria")));*/

            edt1.setText(p.getEnunciado());
            edt2.setText(p.getRsp1());
            edt3.setText(p.getRsp2());
            edt4.setText(p.getRsp3());
            edt5.setText(p.getRsp4());
            spinner.setSelection(items.indexOf(getIntent().getExtras().getString("categoria")));

            //Pregunta p = new Pregunta();

            byte[] stringDecod = Base64.decode(p.getFoto(), Base64.DEFAULT);
            Bitmap byteDecod = BitmapFactory.decodeByteArray(stringDecod, 0, stringDecod.length);
            foto.setImageBitmap(byteDecod);


        }


        //este boton se encarga de guardar una nueva pregunta
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //estos if se encargan de que los campos no esten vacios, en caso de que los estuvieran
                //mostraria una notificacion en la parte inferior especificando cual de ellos esta vacio

                if (edt1.getText().toString().isEmpty()) {

                    Snackbar.make(view, "Rellene el campo de la pregunta", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else if (edt2.getText().toString().isEmpty()) {

                    Snackbar.make(view, "Rellene el campo de la respuesta 1", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else if (edt3.getText().toString().isEmpty()) {

                    Snackbar.make(view, "Rellene el campo de la respuesta 2", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else if (edt4.getText().toString().isEmpty()) {

                    Snackbar.make(view, "Rellene el campo de la respuesta 3", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else if (edt5.getText().toString().isEmpty()) {

                    Snackbar.make(view, "Rellene el campo de la respuesta 4", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else if (r.getCategorias().isEmpty()) {

                    Snackbar.make(view, "Añada una nueva categoria", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else {

                    //INCLUSION DE PERMISOS

                    //permisosComprobacion();

                    ImageView imageView = findViewById(R.id.foto);

                    BitmapDrawable bmDr = (BitmapDrawable) imageView.getDrawable();

                    if (bmDr != null) {

                        bmap = bmDr.getBitmap();

                    } else {

                        bmap = null;

                    }

                    Pregunta p = new Pregunta(edt1.getText().toString(), edt2.getText().toString(),
                            edt3.getText().toString(), edt4.getText().toString(), edt5.getText().toString(),
                            spinner.getSelectedItem().toString(), conver64(bmap));

                    //aqui controlamos la funcion de guardado y edicion
                    //si codigo es igual a -1 quiere decir que no tiene ningun campo relleno y que sera una nueva pregunta
                    //pero si el codigo tiene cualquier otro numero quiere decir que sera una pregunta ya rellena que se editara
                    if (/*codigo == -1*/ editar == false) {



                        /*Pregunta p = new Pregunta(edt1.getText().toString(), edt2.getText().toString(),
                                edt3.getText().toString(), edt4.getText().toString(), edt5.getText().toString(),
                                spinner.getSelectedItem().toString(), conver64(bmap));*/

                        r.insertarFoto(p, myContext);
                        //finish();

                    } else {

                        /*Pregunta p = new Pregunta(edt1.getText().toString(), edt2.getText().toString(),
                                edt3.getText().toString(), edt4.getText().toString(), edt5.getText().toString(),
                                spinner.getSelectedItem().toString(), conver64(bmap));*/
                        p.setId(codigo);
                        r.Actualizar(p, myContext);
                        uri = null;
                        //finish();

                    }

                    finish();

                }

            }
        });

        incluirCategoria();

        //seccion fotos

        Button buttonCamera = findViewById(R.id.buttonCamera);
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            buttonCamera.setEnabled(false);
        } else {
            buttonCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                        takePicture();
                    }
                }
            });
        }

        Button buttonGallery = (Button) findViewById(R.id.buttonGallery);
        buttonGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPicture();
            }
        });

    }

    public boolean onSupportNavigateUp() {

        onBackPressed();
        return false;

    }

    @Override
    protected void onStart() {
        MyLog.d(TAG, "Iniciando OnStart");
        super.onStart();
        MyLog.d(TAG, "Finalizando OnStart");
    }

    @Override
    protected void onResume() {
        MyLog.d(TAG, "Iniciando OnResume");
        super.onResume();
        MyLog.d(TAG, "Finalizando OnResume");
    }

    @Override
    protected void onPause() {
        MyLog.d(TAG, "Iniciando OnPause");
        super.onPause();
        MyLog.d(TAG, "Finalizando OnPause");
    }

    @Override
    protected void onStop() {
        MyLog.d(TAG, "Iniciando OnStop");
        super.onStop();
        MyLog.d(TAG, "Finalizando OnStop");
    }

    @Override
    protected void onRestart() {
        MyLog.d(TAG, "Iniciando OnRestart");
        super.onRestart();
        MyLog.d(TAG, "Finalizando OnRestart");
    }

    @Override
    protected void onDestroy() {
        MyLog.d(TAG, "Iniciando OnDestroy");
        super.onDestroy();
        MyLog.d(TAG, "Finalizando OnDestroy");
    }

    //metodo que comprobara la peticion de permisos de escritura en el dispositivo
    private void permisosComprobacion() {

        if (ContextCompat.checkSelfPermission(CreaPreg.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(CreaPreg.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(CreaPreg.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

            }
        }

        /* Se mostrara una ventana emergente que pedira la confirmacion
         * para la escritura en el dispositivo
         */

    }

    //Maneja la respuesta del comprueba permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CODE_WRITE_EXTERNAL_STORAGE_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permiso aceptado
                    Snackbar.make(relativeCreaPreg, getResources().getString(R.string.write_permission_accepted), Snackbar.LENGTH_LONG)
                            .show();
                } else {
                    // Permiso rechazado
                    Snackbar.make(relativeCreaPreg, getResources().getString(R.string.write_permission_not_accepted), Snackbar.LENGTH_LONG)
                            .show();
                }

                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void incluirCategoria() {

        //este boton se encargara de añadir nuevas categorias y guardarlas en la BD
        btnCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutActivity = LayoutInflater.from(myContext);
                View viewAlertDialog = layoutActivity.inflate(R.layout.alert_dialog, null);

                //Definición del AlertDialog
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(myContext);

                //Asignación del AlertDialog a su vista
                alertDialog.setView(viewAlertDialog);

                //Recuperación del EditTextdel AlertDialog
                final EditText dialogInput = (EditText) viewAlertDialog.findViewById(R.id.dialogInput);

                //Configuración del AlertDialog
                alertDialog
                        .setCancelable(false)
                        //BotónAñadir
                        .setPositiveButton(getResources().getString(R.string.add),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        adapter.add(dialogInput.getText().toString());
                                        spinner.setSelection(adapter.getPosition(dialogInput.getText().toString()));
                                    }
                                })
                        //BotónCancelar
                        .setNegativeButton(getResources().getString(R.string.cancel),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                }).create()
                        .show();

            }
        });

    }

    //seccion para las fotos

    private void takePicture() {
        try {
            // Se crea el directorio para las fotografías
            File dirFotos = new File(pathFotos);
            dirFotos.mkdirs();

            // Se crea el archivo para almacenar la fotografía
            File fileFoto = File.createTempFile(getFileCode(), ".jpg", dirFotos);

            // Se crea el objeto Uri a partir del archivo
            // A partir de la API 24 se debe utilizar FileProvider para proteger
            // con permisos los archivos creados
            // Con estas funciones podemos evitarlo
            // https://stackoverflow.com/questions/42251634/android-os-fileuriexposedexception-file-jpg-exposed-beyond-app-through-clipdata
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            uri = Uri.fromFile(fileFoto);
            Log.d(TAG, uri.getPath().toString());

            // Se crea la comunicación con la cámara
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Se le indica dónde almacenar la fotografía
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            // Se lanza la cámara y se espera su resultado
            startActivityForResult(cameraIntent, REQUEST_CAPTURE_IMAGE);

        } catch (IOException ex) {

            Log.d(TAG, "Error: " + ex);
            RelativeLayout relativeLayout = findViewById(R.id.relativeCreaPreg);
            Snackbar snackbar = Snackbar
                    .make(relativeLayout, getResources().getString(R.string.error_files), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    private void selectPicture() {
        // Se le pide al sistema una imagen del dispositivo
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, getResources().getString(R.string.choose_picture)),
                REQUEST_SELECT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case (REQUEST_CAPTURE_IMAGE):
                if (resultCode == Activity.RESULT_OK) {
                    // Se carga la imagen desde un objeto URI al imageView
                    ImageView imageView = findViewById(R.id.foto);
                    imageView.setImageURI(uri);

                    // Se le envía un broadcast a la Galería para que se actualice
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    mediaScanIntent.setData(uri);
                    sendBroadcast(mediaScanIntent);
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    // Se borra el archivo temporal
                    File file = new File(uri.getPath());
                    file.delete();
                }
                break;

            case (REQUEST_SELECT_IMAGE):
                if (resultCode == Activity.RESULT_OK) {
                    // Se carga la imagen desde un objeto Bitmap
                    Uri selectedImage = data.getData();
                    String selectedPath = selectedImage.getPath();

                    if (selectedPath != null) {
                        // Se leen los bytes de la imagen
                        InputStream imageStream = null;
                        try {
                            imageStream = getContentResolver().openInputStream(selectedImage);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        // Se transformam los bytes de la imagen a un Bitmap
                        Bitmap bmp = BitmapFactory.decodeStream(imageStream);

                        // Se carga el Bitmap en el ImageView
                        ImageView imageView = findViewById(R.id.foto);
                        imageView.setImageBitmap(bmp);
                    }
                }
                break;
        }
    }

    private String getFileCode() {
        // Se crea un código a partir de la fecha y hora
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss", java.util.Locale.getDefault());
        String date = dateFormat.format(new Date());
        // Se devuelve el código
        return "pic_" + date;
    }

    public static String conver64(Bitmap bmap) {

        String imgCod = "";

        if (bmap != null) {
            //Bitmap bm = BitmapFactory.decodeFile(bmap.getPath());
            Bitmap resized = Bitmap.createScaledBitmap(bmap, 500, 500, true);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            resized.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
            byte[] b = baos.toByteArray();
            imgCod = Base64.encodeToString(b, Base64.DEFAULT);
            return imgCod;
        } else {
            return imgCod;
        }
    }

}
