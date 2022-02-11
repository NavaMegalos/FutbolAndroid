package com.nava.mijornada;

import static com.nava.mijornada.ControlEstadistico.*;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CambiosEquipo extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<DosItemsView> equipos = new ArrayList<>();
    private AlertDialog.Builder builder;
    private EditText nombreNuevo;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cambios_equipos);
        mRecyclerView = findViewById(R.id.list_item_cambios_equipo);
        inicializarLista(mRecyclerView);
        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

                builder = new AlertDialog.Builder(rv.getContext());
                nombreNuevo = new EditText(rv.getContext());
                builder.setTitle("Nombre Nuevo");
                builder.setMessage("Ingresa el nuevo nombre: ");
                nombreNuevo.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(nombreNuevo);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        String nombre = nombreNuevo.getText().toString();
//                        Toast.makeText(getBaseContext(), "Nombre " + nombre, Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });
    }

    private void inicializarLista(RecyclerView mRecyclerView) {
//        arrayList.add(new TresItemsView("La Loma", "5", "15")); EJEMPLO CON EL ADAPTADOR DE 3
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        equipos = obtenerEquipos();
        mAdapter = new AdaptadorDosItems(equipos);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }
    private ArrayList<DosItemsView> obtenerEquipos() {
        ArrayList<DosItemsView> equipos = new ArrayList<>();
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
                    String id = cursor.getString(0);
                    String nombreEquipo = cursor.getString(1);
                    equipos.add(new DosItemsView(nombreEquipo, id));
                }
                cursor.close();
            }
        }catch (Exception e) {
            Log.i("ERROR", e.getMessage());
        }

        return equipos;
    }
    public void onClickRegresar(View view) {
        this.finish();
    }
}
