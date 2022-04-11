package com.nava.mijornada.partido;

import static com.nava.mijornada.database.ControlEstadistico.*;
import static com.nava.mijornada.database.DataBaseHelper.realizarConexion;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nava.mijornada.R;

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
        //CODE FROM STACKOVERFLOW
        //https://stackoverflow.com/questions/16889502/how-to-mask-an-edittext-to-show-the-dd-mm-yyyy-date-format
        TextWatcher twFecha = new TextWatcher() {
            private int previouslength;
            private boolean backspace;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                previouslength = charSequence.length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()+1 == 3 || charSequence.length()+1 == 6) {
                    String textoActual = asignarFecha.getText().toString();
                    textoActual += "/";
                    asignarFecha.setText(textoActual);
                    asignarFecha.setSelection(asignarFecha.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                backspace = previouslength > editable.length();
                if(backspace) {
                    asignarFecha.setText("");
                }

            }
        };

        TextWatcher twHora = new TextWatcher() {
            private int previouslength;
            private boolean backspace;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                previouslength = charSequence.length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()+1 == 3 || charSequence.length()+1 == 6) {
                    String textoActual = asignarHora.getText().toString();
                    textoActual += ":";
                    asignarHora.setText(textoActual);
                    asignarHora.setSelection(asignarHora.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                backspace = previouslength > editable.length();
                if(backspace) {
                    asignarHora.setText("");
                }

            }
        };

        asignarFecha.addTextChangedListener(twFecha);
        asignarHora.addTextChangedListener(twHora);
    }

    public int crearPartido(String fecha, String hora) {
        SQLiteDatabase db = realizarConexion("writable", this);
        ContentValues values = new ContentValues();
        values.put(Partido.FECHA, fecha);
        values.put(Partido.HORA_INICIO, hora);

        assert db != null;
        long id = db.insert(Partido.TABLE_NAME,null, values);
        db.close();
        return (int)id;
    }

    public void agregarEquipo(String idPartido, String idEquipo) {
        SQLiteDatabase db = realizarConexion("writable", this);
        ContentValues values = new ContentValues();
        values.put(PartidoEquipo._ID_PARTIDO, idPartido);
        values.put(PartidoEquipo._ID_EQUIPO, idEquipo);
        values.put(PartidoEquipo._ID_RESULTADO, "1");

        assert db != null;
        db.insert(PartidoEquipo.TABLE_NAME,null, values);
        db.close();
    }

    public String obtenerIdEquipo(String nombreEquipo) {
        SQLiteDatabase db = realizarConexion("readable", this);
        assert db != null;
        Cursor cursor = db.query(
                    Equipo.TABLE_NAME,
                    null,
                    " nombre LIKE ?",
                    new String[]{"%" + nombreEquipo + "%"},
                    null, null, null
            );
            cursor.moveToFirst();
            if( cursor.getCount() >= 1 ) {
                db.close();
                return cursor.getString(0);
            }
        cursor.close();
        db.close();
        return null;
    }

    private ArrayList<String> obtenerEquipos() {
        ArrayList<String> nombresEquipos = new ArrayList<>();
        SQLiteDatabase db = realizarConexion("readable", this);
        assert db != null;
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
                    nombresEquipos.add(nombreEquipo);
                }
            }
        cursor.close();
        db.close();
        return nombresEquipos;
    }

    public String[] obtenerDatos() {
        String nombreEquipo1 = equipo1.getSelectedItem().toString();
        String nombreEquipo2 = equipo2.getSelectedItem().toString();
        String fecha = asignarFecha.getText().toString();
        String hora = asignarHora.getText().toString();
        return new String[]{nombreEquipo1,nombreEquipo2,fecha,hora};
    }

    public String[] generarIdEquipos(String... nombres) {
        String idEquipo1 = obtenerIdEquipo(nombres[0]);
        String idEquipo2 = obtenerIdEquipo(nombres[1]);

        return new String[]{idEquipo1, idEquipo2};
    }

    private boolean validarFechaHora(String fecha, String hora) {
        if(fecha.length() < 10 || fecha.isEmpty()) return false;
        if(hora.length() < 8 || hora.isEmpty()) return false;
        return true;
    }

    public void onClickAgregar(View view) {
        String[] datos = obtenerDatos();
        if(datos[0].equals(datos[1])) {
            Toast.makeText(this, "Favor de Elegir Equipos Diferentes!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!validarFechaHora(datos[2], datos[3])) {
            Toast.makeText(
                    this,
                    "Favor de asignar una fecha u hora correctamente!...",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }
        String[] idEquipos = generarIdEquipos(datos[0], datos[1]);
        String idNuevoPartido = String.valueOf(crearPartido(datos[2], datos[3]));

        agregarEquipo(idNuevoPartido, idEquipos[0]);
        agregarEquipo(idNuevoPartido, idEquipos[1]);

        Toast.makeText(getApplicationContext(), "Partido Agregado Exitosamente!...", Toast.LENGTH_SHORT).show();
        this.finish();
    }

    public void onClickRegresar(View view) {
        this.finish();
    }
}
