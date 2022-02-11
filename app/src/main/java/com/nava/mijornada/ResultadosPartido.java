package com.nava.mijornada;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
                String idPartido = obtenerSubString(selectedItem);

                ArrayList<String> equiposVs;
                equiposVs = obtenerEquipos(idPartido);
                String[] datosPartido;
                datosPartido = obtenerFechaHora(idPartido);
                assert datosPartido != null;
                equipo1.setText(equiposVs.get(0));
                equipo2.setText(equiposVs.get(1));
                fecha.setText(datosPartido[0]);
                hora.setText(datosPartido[1]);

                resultadosBD = obtenerEquipos(idPartido);
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

    private String obtenerSubString(String cadena) {
        int separador = cadena.indexOf('_')+1;
        return cadena.substring(separador);
    }

    private String[] obtenerFechaHora(String idPartido) {
        try{
            DataBaseHelper dbHelper = new DataBaseHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(
                    ControlEstadistico.Partido.TABLE_NAME,
                    new String[]{ControlEstadistico.Partido.FECHA, ControlEstadistico.Partido.HORA_INICIO},
                    ControlEstadistico.Partido._ID + " LIKE ? ",
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
            dbHelper.close();
        }catch (Exception e) {
            Log.i("ERROR", e.getMessage());
        }
        return null;
    }
    private String obtenerNombresEquipo(String idEquipo) {
        String nombreEquipo = "";
        try{
            DataBaseHelper dbHelper = new DataBaseHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(
                    ControlEstadistico.Equipo.TABLE_NAME,
                    new String[]{ControlEstadistico.Equipo.NOMBRE},
                    ControlEstadistico.Equipo._ID + " LIKE ? ",
                    new String[]{idEquipo},
                    null, null, null
            );
            cursor.moveToFirst();
            if( cursor.getCount() >= 1 ) {
                nombreEquipo = cursor.getString(0);
            }
            cursor.close();
            db.close();
            dbHelper.close();
        }catch (Exception e) {
            Log.i("ERROR", e.getMessage());
        }
        return nombreEquipo;
    }
    private ArrayList<String> obtenerEquipos(String idPartido) {
        ArrayList<String> equipos = new ArrayList<>();
        try{
            DataBaseHelper dbHelper = new DataBaseHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(
                    ControlEstadistico.PartidoEquipo.TABLE_NAME,
                    new String[]{ControlEstadistico.PartidoEquipo._ID_EQUIPO},
                    ControlEstadistico.PartidoEquipo._ID_PARTIDO + " LIKE ? AND " + ControlEstadistico.PartidoEquipo._ID_RESULTADO + " LIKE ?",
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
            dbHelper.close();
        }catch (Exception e) {
            Log.i("ERROR", e.getMessage());
        }
        return equipos;
    }
    private ArrayList<String> obtenerPartidos() {
        ArrayList<String> detalles = new ArrayList<>();
        ArrayList<String> nombres;
        try{
            DataBaseHelper dbHelper = new DataBaseHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(
                    ControlEstadistico.Partido.TABLE_NAME,
                    null,
                    null,
                    null,
                    null, null, null
            );
            if( cursor.getCount() >= 1 ) {
                while(cursor.moveToNext()) {
//                String id = cursor.getString(1);
//                String idEquipo = cursor.getString(2);
//                String cadena = "id: " + id + " id equipo: " + idEquipo;
//                detalles.add(cadena);
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
            dbHelper.close();
        }catch (Exception e) {
            Log.i("ERROR", e.getMessage());
        }
        return detalles;
    }

    public void onClickEliminar(View view) {
        String partidoAEliminar = partidos.getSelectedItem().toString();
        partidoAEliminar = obtenerSubString(partidoAEliminar);
//        Toast.makeText(this, "ID: " + partidoAEliminar, Toast.LENGTH_SHORT).show();
        try {
            DataBaseHelper dbHelper = new DataBaseHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(ControlEstadistico.Partido.TABLE_NAME, ControlEstadistico.Partido._ID + " =?", new String[]{partidoAEliminar});
        }catch (Exception e) {
            Log.i("ERROR", e.getMessage());
        }

        //DIALOGO PARA MOSTRAR CARGANDO AL INSERTAR EL NOMBRE EN LA BD
        ProgressDialog dialog = ProgressDialog.show(ResultadosPartido.this, "Verificando Registros",
                "Eliminando partido...", true );
        Toast.makeText(getApplicationContext(), "Partido Eliminado Exitosamente!...", Toast.LENGTH_SHORT).show();
//

    }

    public void onClickRegresar(View view) {
        this.finish();
    }
}
