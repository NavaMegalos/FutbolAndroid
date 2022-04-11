package com.nava.mijornada.equipo;

import static com.nava.mijornada.database.DataBaseHelper.realizarConexion;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nava.mijornada.R;
import com.nava.mijornada.database.ControlEstadistico.Equipo;

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

    private boolean validarCampoNovacio() {
        if(obtenerNombre().isEmpty()) {
            nombreEquipo.setError("Este campo es requerido, favor de llenarlo");
            return false;
        }
        return true;
    }

    private boolean validarNombreBD() {
        String nombre = obtenerNombre().toUpperCase();
        SQLiteDatabase db = realizarConexion("readable", this);

        assert db != null;
        Cursor cursor = db.query(
                Equipo.TABLE_NAME,
                new String[]{"nombre"},
                Equipo.NOMBRE + " LIKE ?",
                new String[]{nombre},
                null, null, null
        );
        if( cursor.getCount() >= 1 ) {
            Toast.makeText(getApplicationContext(), "El nombre ya se encuentra en uso...", Toast.LENGTH_SHORT).show();
            cursor.close();
            db.close();
            return false;
        }
        return true;
    }

    private void registrar() {
        String nombre = obtenerNombre().toUpperCase();
        SQLiteDatabase db = realizarConexion("writable", this);
        ContentValues values = new ContentValues();
        values.put(Equipo.NOMBRE, nombre);
        assert db != null;
        db.insert(Equipo.TABLE_NAME,null, values);
        Toast.makeText(getApplicationContext(), "Equipo Agregado Exitosamente!...", Toast.LENGTH_SHORT).show();
        db.close();
    }

    public void onClickAgregar(View view) {
        if(!validarCampoNovacio() || !validarNombreBD()) {
            return;
        }
        registrar();
        this.finish();
    }

    public void onClickRegresar(View view) {
        this.finish();
    }
}
