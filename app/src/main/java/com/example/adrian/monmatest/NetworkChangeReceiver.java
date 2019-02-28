package com.example.adrian.monmatest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        boolean wifiIsConnected = wifi != null && wifi.isConnectedOrConnecting();
        boolean mobileIsConnected = mobile != null && mobile.isConnectedOrConnecting();

        if (wifiIsConnected) {
            Log.d("Network Available ", "YES");
            Toast.makeText(context, "Conexion wifi activada", Toast.LENGTH_SHORT).show();
            if (mobileIsConnected) {
                Log.d("Network Available ", "YES");
                Toast.makeText(context, "Conexion de datos activada", Toast.LENGTH_SHORT).show();
            }
        }else{
            Log.d("Network Available ", "NO");
            Toast.makeText(context, "No hay conexion a internet", Toast.LENGTH_SHORT).show();
        }
        
        
    }

}
