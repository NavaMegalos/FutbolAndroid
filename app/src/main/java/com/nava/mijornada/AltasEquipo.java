package com.nava.mijornada;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AltasEquipo extends AppCompatActivity {

    private final String TABLE_NAME = "equipo";
    private EditText nombreEquipo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.altas_equipos);
        nombreEquipo = findViewById(R.id.nombre_equipo_altas);
    }

    public void onClickAgregar(View view) {
        String nombre = nombreEquipo.getText().toString();
        nombre = nombre.trim();
        if(nombre.isEmpty()) {
            nombreEquipo.setError("Este campo es requerido, favor de llenarlo");
            return;
        }

        DataBaseHelper dbHelper = new DataBaseHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        db.insert(TABLE_NAME,null, values);
        ProgressDialog dialog = ProgressDialog.show(AltasEquipo.this, "Generando Equipo Nuevo",
                "Creando equipo...", true );
        Toast.makeText(getApplicationContext(), "Equipo Agregado Exitosamente!...", Toast.LENGTH_SHORT).show();
        this.finish();
    }

    public void onClickRegresar(View view) {
        this.finish();
    }
}
