package com.example.adrian.monmatest;

import android.Manifest;

public class Constantes {

    public static final String USER = "root";
    public static final String PASSWORD = "";

    //Base de datos
    public static final String BD = "monmatest.db";
    public static final int VersionBD = 1;
    public static final String Pregunta = "Pregunta";
    public static final String codPreg = "codigo";
    public static final String enunciado = "enunciado";
    public static final String rsp1 = "rsp1";
    public static final String rsp2 = "rsp2";
    public static final String rsp3 = "rsp3";
    public static final String rsp4 = "rsp4";
    public static final String categoria = "categoria";
    public static final String foto = "foto";
    public static final String editar = "editar";
    //public static final String codigo = "codigo";

    //Permisos
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1 ;
    //public static final int MY_PERMISSIONS_REQUEST_CAMERA = 23 ;
    public static final int CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 123;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 1;
    public static final int MULTIPLE_PERMISSIONS_REQUEST_CODE = 3;
    public static final int REQUEST_CODE_INTERNET = 32;
    public static final String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.INTERNET};

    //fotos
    public static final int REQUEST_CAPTURE_IMAGE = 200;
    public static final int REQUEST_SELECT_IMAGE = 201;

    //configuracion-pref
    public static final String ID_IDIOMA = "opc_idioma";
    public static final String IDIOMA_DEFAULT = "es";

    //conexiones internet
    public static final int TYPE_WIFI = 1;
    public static final int TYPE_MOBILE = 2;
    public static final int TYPE_NOT_CONNECTED = 324;
}
