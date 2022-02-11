package com.nava.mijornada;

import static com.nava.mijornada.ControlEstadistico.*;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void close(Cursor c, SQLiteDatabase db, DataBaseHelper dbHelper) {
        c.close();
        dbHelper.close();
        db.close();
    }

    public boolean checkEquipos(int minimo_equipos) {
        boolean bandera = false;
        DataBaseHelper dbHelper = new DataBaseHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ Equipo.TABLE_NAME, null);
        bandera = cursor.getCount() >= minimo_equipos;
        close(cursor, db, dbHelper);

        return bandera;
    }

    public void onClickEquipos(View view) {
        Intent i = new Intent(this, EquipoView.class);
        startActivity(i);
    }

    public void onClickPartidos(View view) {
        if (!checkEquipos(2)) {
            Toast.makeText(getApplicationContext(), "Necesitas crear por lo menos dos equipos para acceder a esta funcionalidad!...", Toast.LENGTH_SHORT).show();;
            return;
        }
        Intent i = new Intent(this, PartidoView.class);
        startActivity(i);
    }

    public void onClickEstadisticas(View view) {
        if (!checkEquipos(1)) {
            Toast.makeText(getApplicationContext(), "Necesitas crear por lo menos un equipo para acceder a esta funcionalidad!...", Toast.LENGTH_SHORT).show();;
            return;
        }
        Intent i = new Intent(this, EstadisticaView.class);
        startActivity(i);
    }

    public void onClickSalir(View view) {
        this.finish();
    }
}