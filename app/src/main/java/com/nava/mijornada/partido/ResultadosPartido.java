package com.nava.mijornada.partido;

import static com.nava.mijornada.database.ControlEstadistico.*;
import static com.nava.mijornada.database.DataBaseHelper.realizarConexion;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nava.mijornada.R;

import java.util.ArrayList;

public class ResultadosPartido extends AppCompatActivity {
    Spinner partidos, resultados;
    TextView equipo1, equipo2, fecha, hora;
    ArrayList<String> partidosBD = new ArrayList<>();
    ArrayList<String> resultadosBD = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultados_partidos);
        partidos = findViewById(R.id.sp1_resultados_partido);
        resultados = findViewById(R.id.sp2_resultados_partido);
        equipo1 = findViewById(R.id.tv_equipo1_resultados_partido);
        equipo2 = findViewById(R.id.tv_equipo2_resultados_partido);
        fecha = findViewById(R.id.tv_fecha_resultados_partido);
        hora = findViewById(R.id.tv_hora_resultados_partido);
        partidosBD = obtenerPartidos();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ResultadosPartido.this,android.R.layout.simple_spinner_item, partidosBD);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        partidos.setAdapter(arrayAdapter);

        partidos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                String idPartido = obtenerSubString(selectedItem, "_");

                ArrayList<String> equiposVs;
                equiposVs = obtenerEquipos(idPartido);
                String[] datosPartido;
                datosPartido = obtenerFechaHora(idPartido);

                assert datosPartido != null;
                assert equiposVs != null;

                equipo1.setText(equiposVs.get(0));
                equipo2.setText(equiposVs.get(1));
                fecha.setText(datosPartido[0]);
                hora.setText(datosPartido[1]);

                resultadosBD = obtenerEquipos(idPartido);
                assert resultadosBD != null;

                resultadosBD.add("EMPATE");
                ArrayAdapter<String> misResultados =
                        new ArrayAdapter<String>
                                (ResultadosPartido.this,android.R.layout.simple_spinner_item, resultadosBD);
                misResultados.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                resultados.setAdapter(misResultados);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private String obtenerSubString(String cadena, String limitante) {
        int separador = cadena.indexOf(limitante)+1;
        return cadena.substring(separador);
    }

    private String[] obtenerFechaHora(String idPartido) {
        String nombreEquipo = "";
        SQLiteDatabase db = realizarConexion("readable", this);
        assert db != null;
        Cursor cursor = db.query(
                    Partido.TABLE_NAME,
                    new String[]{Partido.FECHA, Partido.HORA_INICIO},
                    Partido._ID + " LIKE ? ",
                    new String[]{idPartido},
                    null, null, null
            );
            cursor.moveToFirst();
            if( cursor.getCount() >= 1 ) {
                String fecha = cursor.getString(0);
                String hora = cursor.getString(1);
                return new String[]{fecha, hora};
            }
        cursor.close();
        db.close();
        return null;
    }
    private String obtenerNombresEquipo(String idEquipo) {
        String nombreEquipo = "";
        SQLiteDatabase db = realizarConexion("readable", this);
        assert db != null;
        Cursor cursor = db.query(
                    Equipo.TABLE_NAME,
                    new String[]{Equipo.NOMBRE},
                    Equipo._ID + " LIKE ? ",
                    new String[]{idEquipo},
                    null, null, null
            );
            cursor.moveToFirst();
            if( cursor.getCount() >= 1 ) {
                nombreEquipo = cursor.getString(0);
            }
        cursor.close();
        db.close();
        return nombreEquipo;
    }
    private ArrayList<String> obtenerEquipos(String idPartido) {
        ArrayList<String> equipos = new ArrayList<>();
        SQLiteDatabase db = realizarConexion("readable", this);
        assert db != null;
        Cursor cursor = db.query(
                    PartidoEquipo.TABLE_NAME,
                    new String[]{PartidoEquipo._ID_EQUIPO},
                    PartidoEquipo._ID_PARTIDO + " LIKE ? AND " + PartidoEquipo._ID_RESULTADO + " LIKE ?",
                    new String[]{idPartido, "1"},
                    null, null, null
            );
            if( cursor.getCount() > 0 ) {
                while(cursor.moveToNext()) {
                    String idEquipo = cursor.getString(0);
                    equipos.add(obtenerNombresEquipo(idEquipo));
                }
            }else {
                return null;
            }
        cursor.close();
        db.close();
        return equipos;
    }
    private ArrayList<String> obtenerPartidos() {
        ArrayList<String> detalles = new ArrayList<>();
        ArrayList<String> nombres;
        SQLiteDatabase db = realizarConexion("readable", this);
        assert db != null;
        Cursor cursor = db.query(
                    Partido.TABLE_NAME,
                    null,
                    null,
                    null,
                    null, null, null
            );
            if( cursor.getCount() >= 1 ) {
                while(cursor.moveToNext()) {
                    String idPartido = cursor.getString(0);
                    nombres = obtenerEquipos(idPartido);
                    if(nombres != null) {
                        String versus = nombres.get(0) + " vs " + nombres.get(1) + " NO_" + idPartido;
                        detalles.add(versus);
                    }
                }
            }
        cursor.close();
        db.close();
        return detalles;
    }

    private ArrayList<String> obtenerIdEquipos(String idPartido) {
        ArrayList<String> equipos = new ArrayList<>();
        SQLiteDatabase db = realizarConexion("readable", this);
        assert db != null;
        Cursor cursor = db.query(
                PartidoEquipo.TABLE_NAME,
                new String[]{PartidoEquipo._ID_EQUIPO},
                PartidoEquipo._ID_PARTIDO + " LIKE ? AND " + PartidoEquipo._ID_RESULTADO + " LIKE ?",
                new String[]{idPartido, "1"},
                null, null, null
        );
        if( cursor.getCount() > 0 ) {
            while(cursor.moveToNext()) {
                String idEquipo = cursor.getString(0);
                equipos.add(idEquipo);
            }
        }else {
            return null;
        }
        cursor.close();
        db.close();
        return equipos;
    }

    private void asignarVictoriaDerrota(String idPartido, String idEquipo, String resultado) {
        String nombreEquipo = obtenerNombresEquipo(idEquipo);
        SQLiteDatabase db = realizarConexion("writable", this);
        ContentValues values = new ContentValues();
        if(nombreEquipo.equals(resultado))
            values.put(PartidoEquipo._ID_RESULTADO, 2);
        else
            values.put(PartidoEquipo._ID_RESULTADO, 3);
        assert db != null;
        db.update(
                PartidoEquipo.TABLE_NAME,
                values,
                PartidoEquipo._ID_PARTIDO + " LIKE ? AND " + PartidoEquipo._ID_EQUIPO + " LIKE ? ",
                new String[]{idPartido, idEquipo}
        );

    }

    private void asignarEmpate(String idPartido) {
        SQLiteDatabase db = realizarConexion("writable", this);
        ContentValues values = new ContentValues();
        values.put(PartidoEquipo._ID_RESULTADO, 4);
        assert db != null;
        db.update(
                PartidoEquipo.TABLE_NAME,
                values,
                PartidoEquipo._ID_PARTIDO + " LIKE ?",
                new String[]{idPartido}
        );
    }

    private void asignarResultado(String idPartido, String resultado) {
        if(resultado.equals("EMPATE")) {
            asignarEmpate(idPartido);
        } else {
            ArrayList<String> idEquipos = obtenerIdEquipos(idPartido);
            assert idEquipos != null;
            asignarVictoriaDerrota(idPartido, idEquipos.get(0), resultado);
            asignarVictoriaDerrota(idPartido, idEquipos.get(1), resultado);
        }

    }

    public void onClickAceptar(View view) {
        String partidoAFinalizar = partidos.getSelectedItem().toString();
        partidoAFinalizar = obtenerSubString(partidoAFinalizar, "_");
        String ganador = resultados.getSelectedItem().toString();
        asignarResultado(partidoAFinalizar, ganador);

        Toast.makeText(getApplicationContext(), "Partido Actualizado Exitosamente!...", Toast.LENGTH_SHORT).show();
        this.finish();
    }

    public void onClickRegresar(View view) {
        this.finish();
    }
}
