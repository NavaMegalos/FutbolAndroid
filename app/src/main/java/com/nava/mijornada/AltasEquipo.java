package com.nava.mijornada;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nava.mijornada.ControlEstadistico.Equipo;

public class AltasEquipo extends AppCompatActivity {
    private EditText nombreEquipo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.altas_equipos);
        nombreEquipo = findViewById(R.id.nombre_equipo_altas);
    }


    private String obtenerNombre() {
        String nombre = nombreEquipo.getText().toString();
        nombre = nombre.trim();
        return nombre;
    }

    private boolean validarStringEntrada() {
        String nombre = obtenerNombre();
        if(nombre.isEmpty()) {
            nombreEquipo.setError("Este campo es requerido, favor de llenarlo");
            return false;
        }
        return true;
    }

    private boolean validarNombreBD() {
        String nombre = obtenerNombre().toUpperCase();
        try {
            DataBaseHelper dbHelper = new DataBaseHelper(this);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(
                    Equipo.TABLE_NAME,
                    new String[]{"nombre"},
                    Equipo.NOMBRE + " LIKE ?",
                    new String[]{nombre},
                    null, null, null
            );

            if( cursor.getCount() >= 1 ) {
                Toast.makeText(getApplicationContext(), "El nombre \""+ nombre +"\" ya se encuentra en uso...", Toast.LENGTH_SHORT).show();
                cursor.close();
                return false;
            }
        }catch(Exception e) {
            Log.i("ERROR", e.getMessage());
        }

        return true;
    }

    private void registrar() {
        String nombre = obtenerNombre().toUpperCase();
        try {
            DataBaseHelper dbHelper = new DataBaseHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Equipo.NOMBRE, nombre);
            db.insert(Equipo.TABLE_NAME,null, values);
        }catch (Exception e) {
            Log.i("ERROR", e.getMessage());
        }

        ProgressDialog dialog = ProgressDialog.show(AltasEquipo.this, "Generando Equipo Nuevo",
                "Creando equipo...", true );
        Toast.makeText(getApplicationContext(), "Equipo Agregado Exitosamente!...", Toast.LENGTH_SHORT).show();
    }

    public void onClickAgregar(View view) {
        if(!validarStringEntrada() || !validarNombreBD()) {
            return;
        }
        registrar();
        this.finish();
    }

    public void onClickRegresar(View view) {
        this.finish();
    }
}
