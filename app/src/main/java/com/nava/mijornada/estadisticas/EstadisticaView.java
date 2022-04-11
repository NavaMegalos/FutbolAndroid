package com.nava.mijornada.estadisticas;

import static com.nava.mijornada.database.ControlEstadistico.*;
import static com.nava.mijornada.database.DataBaseHelper.realizarConexion;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nava.mijornada.listviewadaptadores.AdaptadorTresItems;
import com.nava.mijornada.R;
import com.nava.mijornada.listviewadaptadores.TresItemsView;

import java.util.ArrayList;

public class EstadisticaView extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<TresItemsView> equipos = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estadisticas);
        mRecyclerView = findViewById(R.id.item_list_estadisticas);
        inicializarLista(mRecyclerView);
//        arrayList.add(new TresItemsView("La Loma", "5", "15"));
    }

    private void redireccionarEstadisticasEquipo(String nombreEquipo) {
        Intent i = new Intent(EstadisticaView.this, EstadisticasEquipo.class);
        i.putExtra("nombre", nombreEquipo);
        startActivity(i);
    }

    private void inicializarLista(RecyclerView mRecyclerView) {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        equipos = obtenerEquipos();
//        mAdapter = new AdaptadorTresItems(equipos);
        AdaptadorTresItems mAdapter = new AdaptadorTresItems(equipos);
        mAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = equipos.get(mRecyclerView.getChildAdapterPosition(view)).getTextViewOne();
                redireccionarEstadisticasEquipo(nombre);


            }
        });
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

    private ArrayList<TresItemsView> obtenerEquipos() {
        ArrayList<TresItemsView> equipos = new ArrayList<>();
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
                    String id = cursor.getString(0);
                    String nombreEquipo = cursor.getString(1);
                    String partidosTotales = obtenerPartidosJugados(id);
                    String partidosGanados = obtenerResultadosPartidos(id, "2");
                    String partidosEmpatados = obtenerResultadosPartidos(id, "4");
                    partidosGanados = String.valueOf(Integer.parseInt(partidosGanados)*3);
                    partidosEmpatados = String.valueOf(Integer.parseInt(partidosEmpatados));
                    int sumaPuntos = Integer.parseInt(partidosGanados) + Integer.parseInt(partidosEmpatados);
                    String puntosTotales = String.valueOf(sumaPuntos);
                    equipos.add(new TresItemsView(nombreEquipo, partidosTotales, puntosTotales));
                }
                cursor.close();
            }
        return equipos;
    }

    public void onClickRegresar(View view) {
        this.finish();
    }
}
