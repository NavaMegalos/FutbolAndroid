package com.nava.mijornada.partido;

import static com.nava.mijornada.database.ControlEstadistico.*;
import static com.nava.mijornada.database.DataBaseHelper.realizarConexion;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nava.mijornada.R;
import com.nava.mijornada.partido.AltasPartido;
import com.nava.mijornada.partido.BajasPartido;
import com.nava.mijornada.partido.CambiosPartido;
import com.nava.mijornada.partido.ResultadosPartido;

public class PartidoView extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_partidos);
    }

    public void close(Cursor c, SQLiteDatabase db) {
        c.close();
        db.close();
    }

    public boolean checkPartidos(int minimo_equipos) {
        boolean bandera;
        SQLiteDatabase db = realizarConexion("readable", this);
        assert db != null;
        Cursor cursor = db.query(
                PartidoEquipo.TABLE_NAME,
                null,
                PartidoEquipo._ID_RESULTADO + " LIKE ?",
                new String[]{ "1" },
                null, null,null
        );
        bandera = cursor.getCount() >= minimo_equipos;
        close(cursor, db);

        return bandera;
    }


    public void onClickAltas(View view) {
        Intent i = new Intent(this, AltasPartido.class);
        startActivity(i);
    }

    public void onClickBajas(View view) {
        if (!checkPartidos(1)) {
            Toast.makeText(getApplicationContext(), "Necesitas crear por lo menos un partido nuevo para acceder a esta funcionalidad!...", Toast.LENGTH_SHORT).show();;
            return;
        }
        Intent i = new Intent(this, BajasPartido.class);
        startActivity(i);
    }

    public void onClickCambios(View view) {
        if (!checkPartidos(1)) {
            Toast.makeText(getApplicationContext(), "Necesitas crear por lo menos un partido nuevo para acceder a esta funcionalidad!...", Toast.LENGTH_SHORT).show();;
            return;
        }
        Intent i = new Intent(this, CambiosPartido.class);
        startActivity(i);
    }

    public void onClickResultados(View view) {
        if (!checkPartidos(1)) {
            Toast.makeText(getApplicationContext(), "Necesitas crear por lo menos un partido nuevo para acceder a esta funcionalidad!...", Toast.LENGTH_SHORT).show();;
            return;
        }
        Intent i = new Intent(this, ResultadosPartido.class);
        startActivity(i);
    }

    public void onClickRegresar(View view) {
        this.finish();
    }
}
