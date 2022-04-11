package com.nava.mijornada.partido;

import static com.nava.mijornada.database.ControlEstadistico.*;
import static com.nava.mijornada.database.DataBaseHelper.realizarConexion;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nava.mijornada.listviewadaptadores.AdaptadorTresItems;
import com.nava.mijornada.R;
import com.nava.mijornada.listviewadaptadores.TresItemsView;

import java.util.ArrayList;

public class CambiosPartido extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<TresItemsView> datos = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cambios_partidos);
        mRecyclerView = findViewById(R.id.item_list_cambios_partidos);
        inicializarLista(mRecyclerView);
    }

    private void redireccionarCambiosPartido(String idPartido) {
        Intent i = new Intent(CambiosPartido.this, CambiosPartidoEspecifico.class);
        i.putExtra("id", idPartido);
        startActivity(i);
    }

    private void inicializarLista(RecyclerView mRecyclerView) {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        datos = obtenerDatos();
        AdaptadorTresItems mAdapter = new AdaptadorTresItems(datos);
        mAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idPartido = datos.get(mRecyclerView.getChildAdapterPosition(view)).getTextViewThree();
                redireccionarCambiosPartido(idPartido);
            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }

    private ArrayList<String> obtenerEquipos(String idPartido) {
        SQLiteDatabase db = realizarConexion("readable", this);
        ArrayList<String> nombreEquipos = new ArrayList<>();
        assert db != null;
        Cursor cursor = db.query(
                PartidoEquipo.TABLE_NAME,
                new String[]{PartidoEquipo._ID_EQUIPO},
                PartidoEquipo._ID_PARTIDO + " LIKE ? AND " + PartidoEquipo._ID_RESULTADO + " LIKE ? ",
                new String[]{idPartido, String.valueOf(1)},
                null, null, null
        );
//        cursor.moveToFirst();
        if(cursor.getCount() >= 1) {
            while(cursor.moveToNext()) {
                Log.i("INFO", cursor.getString(0));
                nombreEquipos.add(cursor.getString(0));
            }
        }
        return nombreEquipos;
    }

    private boolean verificarPartidoEnProceso(String idPartido) {
        SQLiteDatabase db = realizarConexion("readable", this);
        assert db != null;
        Cursor cursor = db.query(
                PartidoEquipo.TABLE_NAME,
                new String[]{PartidoEquipo._ID_EQUIPO},
                PartidoEquipo._ID_PARTIDO + " LIKE ? AND " + PartidoEquipo._ID_RESULTADO + " LIKE ? ",
                new String[]{idPartido, String.valueOf(1)},
                null, null, null
        );
        if( cursor.getCount() > 0 ) {
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }

    private String obtenerNombre(String idEquipo) {
        SQLiteDatabase db = realizarConexion("readable", this);
        assert db != null;
        Cursor cursor = db.query(
                Equipo.TABLE_NAME,
                null,
                Equipo._ID + " LIKE ? ",
                new String[]{idEquipo},
                null, null, null
        );
        cursor.moveToFirst();
        if( cursor.getCount() >= 1 ) {
            return cursor.getString(1);
        }
        return null;
    }
    private ArrayList<TresItemsView> obtenerDatos() {
        ArrayList<TresItemsView> equiposPartido = new ArrayList<>();
        SQLiteDatabase db = realizarConexion("readable", this);
        assert db != null;
        Cursor cursor = db.query(
                Partido.TABLE_NAME,
                null,
                null,
                null,
                null, null, null
        );
//        cursor.moveToFirst();
        if( cursor.getCount() >= 1 ) {
            while(cursor.moveToNext()) {
                String idPartido = cursor.getString(0);
                if(verificarPartidoEnProceso(idPartido)) {
                    ArrayList<String> equipos = obtenerEquipos(idPartido);
                    String equipoPrincipal = obtenerNombre(equipos.get(0));
                    String equipoSecundario = obtenerNombre(equipos.get(1));
                    equiposPartido.add(new TresItemsView(equipoPrincipal, equipoSecundario, idPartido));
                }
            }
        }
        return equiposPartido;
    }

    public void onClickRegresar(View view) {
        this.finish();
    }
}
