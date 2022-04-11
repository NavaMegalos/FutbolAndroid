package com.nava.mijornada.database;

import static com.nava.mijornada.database.ConsultasTablas.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "control_estadistico.db";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_DELETE_TABLE_PARTIDO);
        db.execSQL(SQL_DELETE_TABLE_EQUIPO);
        db.execSQL(SQL_DELETE_TABLE_RESULTADO);
        db.execSQL(SQL_DELETE_TABLE_PARTIDO_EQUIPO);

        db.execSQL(SQL_CREATE_TABLE_PARTIDO);
        db.execSQL(SQL_CREATE_TABLE_EQUIPO);
        db.execSQL(SQL_CREATE_TABLE_RESULTADO);
        db.execSQL(SQL_CREATE_TABLE_PARTIDO_EQUIPO);
        db.execSQL(SQL_INSERT_RESULTADOS);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE_PARTIDO);
        db.execSQL(SQL_DELETE_TABLE_EQUIPO);
        db.execSQL(SQL_DELETE_TABLE_RESULTADO);
        db.execSQL(SQL_DELETE_TABLE_PARTIDO_EQUIPO);

        db.execSQL(SQL_CREATE_TABLE_PARTIDO);
        db.execSQL(SQL_CREATE_TABLE_EQUIPO);
        db.execSQL(SQL_CREATE_TABLE_RESULTADO);
        db.execSQL(SQL_CREATE_TABLE_PARTIDO_EQUIPO);
        db.execSQL(SQL_INSERT_RESULTADOS);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public static SQLiteDatabase realizarConexion(String readableOrWritable, Context context) {
        try {
            DataBaseHelper dbHelper = new DataBaseHelper(context);
            return readableOrWritable.equals("readable")
                    ? dbHelper.getReadableDatabase()
                    : dbHelper.getWritableDatabase();
        }catch(Exception e) {
            Log.i("ERROR", e.getMessage());
        }
        return null;
    }
}
