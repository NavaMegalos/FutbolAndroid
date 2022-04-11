package com.nava.mijornada.equipo;

import static com.nava.mijornada.database.ControlEstadistico.*;
import static com.nava.mijornada.database.DataBaseHelper.realizarConexion;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nava.mijornada.listviewadaptadores.AdaptadorDosItems;
import com.nava.mijornada.listviewadaptadores.DosItemsView;
import com.nava.mijornada.R;

import java.util.ArrayList;

public class CambiosEquipo extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<DosItemsView> equipos = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cambios_equipos);
        mRecyclerView = findViewById(R.id.list_item_cambios_equipo);
        inicializarLista(mRecyclerView);
    }

    private boolean validarNombreBD(String nombreNuevo) {
        nombreNuevo = nombreNuevo.toUpperCase().trim();
        SQLiteDatabase db = realizarConexion("readable", this);

        assert db != null;
        Cursor cursor = db.query(
                Equipo.TABLE_NAME,
                null,
                Equipo.NOMBRE + " LIKE ?",
                new String[]{nombreNuevo},
                null, null, null
        );
        if( cursor.getCount() >= 1 ) {
            Toast.makeText(getApplicationContext(), "El nombre ya se encuentra en uso...", Toast.LENGTH_SHORT).show();
            cursor.close();
            db.close();
            return false;
        }
        return true;
    }

    private void actualizarNombre(String nombreActual, String nombreNuevo) {
        SQLiteDatabase db = realizarConexion("writable", this);
        ContentValues values = new ContentValues();
        values.put(Equipo.NOMBRE, nombreNuevo);
        assert db != null;
        db.update(
                Equipo.TABLE_NAME,
                values,
                Equipo.NOMBRE + " LIKE ?",
                new String[]{nombreActual}
        );
    }

    private void inicializarLista(RecyclerView mRecyclerView) {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        equipos = obtenerEquipos();
        AdaptadorDosItems mAdapter = new AdaptadorDosItems(equipos);

        //LISTENER PARA CUANDO EL USUARIO DE CLICK EN ALGUN EQUIPO Y SE LE DESPLIEGUE
        //LO QUE VIENE SIENDO UN CUADRO DE DIALOGO EN EL CUAL PUEDA ASIGNAR OTRO NOMBRE
        mAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SE OBTIENE EL NOMBRE DEL EQUIPO AL QUE SE LE DIO CLICK
                String nombre = equipos.get(
                        mRecyclerView.getChildAdapterPosition(view)
                ).getTextViewOne();

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                EditText nombreNuevo = new EditText(view.getContext());
                builder.setTitle("Nombre Nuevo");
                builder.setMessage("Ingresa el nuevo nombre: ");
                nombreNuevo.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(nombreNuevo);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nombreAAsignar = nombreNuevo.getText().toString().toUpperCase().trim();
                        if(validarNombreBD(nombreAAsignar) && !nombreAAsignar.isEmpty() ) {
                            actualizarNombre(nombre, nombreAAsignar);
                            finish();
                            Toast.makeText(
                                    view.getContext(),
                                    "Se actualizo con exito el nombre del equipo!...",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }
    private ArrayList<DosItemsView> obtenerEquipos() {
        ArrayList<DosItemsView> equipos = new ArrayList<>();
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
                equipos.add(new DosItemsView(id, nombreEquipo));
            }
            cursor.close();
        }

        return equipos;
    }

    public void onClickRegresar(View view) {
        this.finish();
    }
}
