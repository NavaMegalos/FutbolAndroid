package com.nava.mijornada;

import static com.nava.mijornada.ControlEstadistico.*;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AltasPartido extends AppCompatActivity {
    Spinner equipo1, equipo2;
    EditText asignarHora, asignarFecha;
    ArrayList<String> equiposBD = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.altas_partidos);
        equipo1 = findViewById(R.id.sp1_altas_partido);
        equipo2 = findViewById(R.id.sp2_altas_partido);
        asignarFecha = findViewById(R.id.et_fecha_altas_partido);
        asignarHora = findViewById(R.id.et_hora_altas_partido);
        equiposBD = obtenerEquipos();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AltasPartido.this,android.R.layout.simple_spinner_item, equiposBD);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        equipo1.setAdapter(arrayAdapter);
        equipo2.setAdapter(arrayAdapter);
    }

    public int crearPartido(String fecha, String hora) {
        try {
            DataBaseHelper dbHelper = new DataBaseHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Partido.FECHA, fecha);
            values.put(Partido.HORA_INICIO, hora);
            long id = db.insert(Partido.TABLE_NAME,null, values);
//            db.close();
//            dbHelper.close();
            return (int)id;
        }catch (Exception e) {
            Log.i("ERROR", e.getMessage());
        }
        return -1;
    }

    public void agregarEquipo(String idPartido, String idEquipo) {
        try {
            DataBaseHelper dbHelper = new DataBaseHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(PartidoEquipo._ID_PARTIDO, idPartido);
            values.put(PartidoEquipo._ID_EQUIPO, idEquipo);
            values.put(PartidoEquipo._ID_RESULTADO, "1");
            db.insert(PartidoEquipo.TABLE_NAME,null, values);
//            db.close();
//            dbHelper.close();
        }catch (Exception e) {
            Log.i("ERROR", e.getMessage());
        }
    }

    public String obtenerIdEquipo(String nombreEquipo) {
        try {
            DataBaseHelper dbHelper = new DataBaseHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(
                    Equipo.TABLE_NAME,
//                    new String[]{"id"},
                    null,
                    " nombre LIKE ?",
                    new String[]{"%" + nombreEquipo + "%"},
                    null, null, null
            );
            cursor.moveToFirst();
            if( cursor.getCount() >= 1 ) {
                return cursor.getString(0);
            }
        }catch(Exception e) {
            Log.i("ERROR", e.getMessage());
        }


        return null;
    }

    private ArrayList<String> obtenerEquipos() {
        ArrayList<String> nombresEquipos = new ArrayList<>();
        try{
            DataBaseHelper dbHelper = new DataBaseHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(
                    Equipo.TABLE_NAME,
                    null,
                    null,
                    null,
                    null, null, null
            );
            if( cursor.getCount() >= 1 ) {
                while(cursor.moveToNext()) {
                    String nombreEquipo = cursor.getString(1);
//                    System.out.println(nombreEquipo);
                    nombresEquipos.add(nombreEquipo);
                }
            }
        }catch (Exception e) {
            Log.i("ERROR", e.getMessage());
        }
        return nombresEquipos;
    }

    public void onClickAgregar(View view) {
        String nombreEquipo1 = equipo1.getSelectedItem().toString();
        String nombreEquipo2 = equipo2.getSelectedItem().toString();
        String fecha = asignarFecha.getText().toString();
        String hora = asignarHora.getText().toString();
        String idNuevoPartido = String.valueOf(crearPartido(fecha, hora));
        String idEquipo1 = obtenerIdEquipo(nombreEquipo1);
        String idEquipo2 = obtenerIdEquipo(nombreEquipo2);
        Toast.makeText(this, "ID " + idEquipo1 + idEquipo2, Toast.LENGTH_SHORT).show();
        agregarEquipo(idNuevoPartido, idEquipo1);
        agregarEquipo(idNuevoPartido, idEquipo2);

        ProgressDialog dialog = ProgressDialog.show(AltasPartido.this, "Generando Partido Nuevo",
                "Creando partido...", false );
        Toast.makeText(getApplicationContext(), "Partido Agregado Exitosamente!...", Toast.LENGTH_SHORT).show();
        this.finish();
    }

    public void onClickRegresar(View view) {
        this.finish();
    }


}
