package com.nava.mijornada.equipo;

import static com.nava.mijornada.database.ControlEstadistico.*;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nava.mijornada.R;
import com.nava.mijornada.database.DataBaseHelper;

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
        if(obtenerNombre().isEmpty()) {
            nombreEquipo.setError("Este campo es requerido, favor de llenarlo");
            return false;
        }
        return true;
    }

    private SQLiteDatabase realizarConexion(String readableOrWritable) {
        try {
            DataBaseHelper dbHelper = new DataBaseHelper(this);
            return readableOrWritable.equals("readable")
                    ? dbHelper.getReadableDatabase()
                    : dbHelper.getWritableDatabase();
        }catch(Exception e) {
            Log.i("ERROR", e.getMessage());
        }
        return null;
    }

    private boolean validarNombreBD() {
        String nombre = obtenerNombre().toUpperCase();
        SQLiteDatabase db = realizarConexion("readable");

        assert db != null;
        Cursor cursor = db.query(
                Equipo.TABLE_NAME,
                new String[]{"nombre"},
                Equipo.NOMBRE + " LIKE ?",
                new String[]{nombre},
                null, null, null
        );
        if( cursor.getCount() >= 1 ) {
            cursor.close();
            db.close();
            return true;
        }

        cursor.close();
        db.close();
        Toast.makeText(getApplicationContext(), "El equipo con el nombre \""+ nombre +"\" no existe...", Toast.LENGTH_SHORT).show();
        return false;
    }

    private void eliminar() {
        String nombre = obtenerNombre().toUpperCase();
        SQLiteDatabase db = realizarConexion("writable");

        assert db != null;
        db.delete(Equipo.TABLE_NAME, Equipo.NOMBRE + " =?", new String[]{nombre});

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
