package com.nava.mijornada.partido;

import static com.nava.mijornada.database.DataBaseHelper.realizarConexion;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nava.mijornada.R;
import com.nava.mijornada.database.ControlEstadistico;

import java.util.ArrayList;

public class CambiosPartidoEspecifico extends AppCompatActivity {
    private String idPartido;
    private TextView partido, equipo1, equipo2;
    private EditText fecha, hora;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cambios_partido_especifico);
        Bundle extras = getIntent().getExtras();
        equipo1 = findViewById(R.id.tv_equipo1_cambios_partido);
        equipo2 = findViewById(R.id.tv_equipo2_cambios_partido);
        fecha = findViewById(R.id.et_fecha_cambios_partido);
        hora = findViewById(R.id.et_hora_cambios_partido);
        partido = findViewById(R.id.tv_id_partido_cambios);
        if (extras != null) {
            idPartido = extras.getString("id");
            partido.setText(idPartido);

        }
        establecerDatos(idPartido);

    }

    private void establecerDatos(String idPartido) {
        ArrayList<String> idEquipos = obtenerEquipos(idPartido);
        String nombreEquipo1 = obtenerNombre(idEquipos.get(0));
        String nombreEquipo2 = obtenerNombre(idEquipos.get(1));
        equipo1.setText(nombreEquipo1);
        equipo2.setText(nombreEquipo2);
    }

    private String obtenerNombre(String idEquipo) {
        SQLiteDatabase db = realizarConexion("readable", this);
        assert db != null;
        Cursor cursor = db.query(
                ControlEstadistico.Equipo.TABLE_NAME,
                null,
                ControlEstadistico.Equipo._ID + " LIKE ? ",
                new String[]{idEquipo},
                null, null, null
        );
        cursor.moveToFirst();
        if( cursor.getCount() >= 1 ) {
            return cursor.getString(1);
        }
        return null;
    }

    private ArrayList<String> obtenerEquipos(String idPartido) {
        SQLiteDatabase db = realizarConexion("readable", this);
        ArrayList<String> nombreEquipos = new ArrayList<>();
        assert db != null;
        Cursor cursor = db.query(
                ControlEstadistico.PartidoEquipo.TABLE_NAME,
                new String[]{ControlEstadistico.PartidoEquipo._ID_EQUIPO},
                ControlEstadistico.PartidoEquipo._ID_PARTIDO + " LIKE ? AND " + ControlEstadistico.PartidoEquipo._ID_RESULTADO + " LIKE ? ",
                new String[]{idPartido, String.valueOf(1)},
                null, null, null
        );
//        cursor.moveToFirst();
        if (cursor.getCount() >= 1) {
            while (cursor.moveToNext()) {
                Log.i("INFO", cursor.getString(0));
                nombreEquipos.add(cursor.getString(0));
            }
        }
        return nombreEquipos;
    }
}
