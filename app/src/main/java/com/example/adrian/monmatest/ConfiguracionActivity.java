package com.example.adrian.monmatest;

import android.app.Activity;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class ConfiguracionActivity extends AppCompatActivity {

    private static final String TAG = "ConfiguracionActivity";
    private static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //comprueba el idioma guardado
        Funciones.cambiarIdioma(this, null);

        setContentView(R.layout.activity_configuracion);

        activity = this;

        actBar();

        getFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutSettings, new PreferencesFragment())
                .commit();

    }

    /**
     * Dar funcionalidad a la flecha de la toolbar
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //capturar click de la flecha de la toolbar
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        //Funciones.finishActivityAnimated(this);
    }

    public boolean onSupportNavigateUp() {

        onBackPressed();
        return false;

    }

    @Override
    protected void onStart() {
        MyLog.d(TAG, "Iniciando onStart...");//es como un print para mostrar mensajes y depurar
        super.onStart();
        MyLog.d(TAG, "Finalizando onStart...");
    }

    @Override
    protected void onResume() {
        MyLog.d(TAG, "Iniciando onResume...");//es como un print para mostrar mensajes y depurar
        super.onResume();
        MyLog.d(TAG, "Finalizando onResume...");
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

    public static class PreferencesFragment extends PreferenceFragment implements
            Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {

        private static final String TAG = "PreferencesFragment";

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.prefidioma);

            //poner listener al pulsar el idioma
            Preference preference = findPreference(Constantes.ID_IDIOMA);

            preference.setOnPreferenceClickListener(this);

        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            MyLog.d(TAG, String.format("Pulsado: '%s'", preference.getKey()));

            preference.setOnPreferenceChangeListener(this);
            return false;
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            MyLog.d(TAG, String.format("Cambió: '%s', '%s'", preference.getKey(), value));

            if (preference.getKey().equals(Constantes.ID_IDIOMA)) {

                Funciones.cambiarIdioma(
                        activity,
                        value.toString()
                );

            }
            return false;
        }

    }

    private void actBar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            ActionBar actionBar = getSupportActionBar();

            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(getString(R.string.action_settings));
        }
    }

}
