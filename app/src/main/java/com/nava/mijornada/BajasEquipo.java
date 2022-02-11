package com.nava.mijornada;

import static com.nava.mijornada.ControlEstadistico.*;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BajasEquipo extends AppCompatActivity {
    private EditText nombreEquipo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bajas_equipos);
        nombreEquipo = findViewById(R.id.nombre_equipo_bajas);
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
        String nombre = obtenerNombre();
        try {
            DataBaseHelper dbHelper = new DataBaseHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(
                    Equipo.TABLE_NAME,
                    new String[]{"nombre"},
                    " nombre LIKE ?",
                    new String[]{nombre},
                    null, null, null
            );
            if( cursor.getCount() >= 1 ) {
                cursor.close();
                return true;
            }
            cursor.close();
        }catch(Exception e) {
            Log.i("ERROR", e.getMessage());
        }

        Toast.makeText(getApplicationContext(), "El equipo con el nombre \""+ nombre +"\" no existe...", Toast.LENGTH_SHORT).show();
        return false;
    }

    private void eliminar() {
        String nombre = obtenerNombre().toUpperCase();
        try {
            DataBaseHelper dbHelper = new DataBaseHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(Equipo.TABLE_NAME, Equipo.NOMBRE + " =?", new String[]{nombre});
        }catch (Exception e) {
            Log.i("ERROR", e.getMessage());
        }

//        ProgressDialog dialog = ProgressDialog.show(BajasEquipo.this, "Verificando Registros",
//                "Eliminando equipo...", false );
        Toast.makeText(getApplicationContext(), "Equipo Eliminado Exitosamente!...", Toast.LENGTH_SHORT).show();
    }

    public void onClickEliminar(View view) {
        if(!validarStringEntrada() || !validarNombreBD()) {
            return;
        }
        eliminar();
        this.finish();
    }

    public void onClickRegresar(View view) {
        this.finish();
    }
}
