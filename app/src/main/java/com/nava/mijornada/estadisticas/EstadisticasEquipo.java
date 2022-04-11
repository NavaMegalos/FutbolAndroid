package com.nava.mijornada.estadisticas;

import static com.nava.mijornada.database.ControlEstadistico.*;
import static com.nava.mijornada.database.DataBaseHelper.realizarConexion;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nava.mijornada.listviewadaptadores.AdaptadorDosItems;
import com.nava.mijornada.listviewadaptadores.DosItemsView;
import com.nava.mijornada.R;

import java.util.ArrayList;

public class EstadisticasEquipo extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<DosItemsView> datos = new ArrayList<>();
    private TextView nombreEquipo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estadisticas_equipo);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String nombre = extras.getString("nombre");
            mRecyclerView = findViewById(R.id.list_item_estadisticas_equipo);
            nombreEquipo = findViewById(R.id.tv_nombre_estadisticas_equipo);
            nombreEquipo.setText(nombre);

            inicializarLista(mRecyclerView, nombre);
        }
    }

    private void inicializarLista(RecyclerView mRecyclerView, String nombreEquipo) {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        datos = obtenerDatos(nombreEquipo);
        AdaptadorDosItems mAdapter = new AdaptadorDosItems(datos);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }

    private String obtenerResultadosPartidos(String id, String idResultado) {
        SQLiteDatabase db = realizarConexion("readable", this);
        assert db != null;
        Cursor cursor = db.query(
                PartidoEquipo.TABLE_NAME,
                null,
                PartidoEquipo._ID_EQUIPO + " LIKE ? AND " + PartidoEquipo._ID_RESULTADO + " LIKE ? ",
                new String[]{id, idResultado},
                null, null, null
        );
        return String.valueOf(cursor.getCount());
    }

    private String obtenerPartidosJugados(String id) {
        SQLiteDatabase db = realizarConexion("readable", this);
        assert db != null;
        Cursor cursor = db.query(
                PartidoEquipo.TABLE_NAME,
                null,
                PartidoEquipo._ID_EQUIPO + " LIKE ? AND " + PartidoEquipo._ID_RESULTADO + " NOT LIKE ? ",
                new String[]{id, "1"},
                null, null, null
        );
        return String.valueOf(cursor.getCount());
    }

    private ArrayList<DosItemsView> obtenerDatos(String nombreEquipo) {
        ArrayList<DosItemsView> datosEstadisticas = new ArrayList<>();
        SQLiteDatabase db = realizarConexion("readable", this);
        assert db != null;
        Cursor cursor = db.query(
                Equipo.TABLE_NAME,
                new String[]{Equipo._ID},
                Equipo.NOMBRE + " LIKE ? ",
                new String[]{nombreEquipo},
                null, null, null
        );
        cursor.moveToFirst();
        String id = cursor.getString(0);
        String partidosTotales = obtenerPartidosJugados(id);
        String partidosGanados = obtenerResultadosPartidos(id, "2");
        String partidosPerdidos = obtenerResultadosPartidos(id, "3");
        String partidosEmpatados = obtenerResultadosPartidos(id, "4");
        datosEstadisticas.add(new DosItemsView("Victorias", partidosGanados));
        datosEstadisticas.add(new DosItemsView("Derrotas", partidosPerdidos));
        datosEstadisticas.add(new DosItemsView("Empates", partidosEmpatados));
        partidosGanados = String.valueOf(Integer.parseInt(partidosGanados)*3);
        partidosEmpatados = String.valueOf(Integer.parseInt(partidosEmpatados));
        int sumaPuntos = Integer.parseInt(partidosGanados) + Integer.parseInt(partidosEmpatados);
        String puntosTotales = String.valueOf(sumaPuntos);
        datosEstadisticas.add(0, new DosItemsView("Puntos Totales", puntosTotales));
        datosEstadisticas.add(1,new DosItemsView("Partidos Jugados", partidosTotales));

        cursor.close();
        return datosEstadisticas;
    }

    public void onClickRegresar(View view) {
        this.finish();
    }
}
