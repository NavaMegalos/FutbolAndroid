package com.nava.mijornada;

import static com.nava.mijornada.ConsultasTablas.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "control_estadistico.db";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_ESTADO);
        db.execSQL(SQL_CREATE_TABLE_PARTIDO);
        db.execSQL(SQL_CREATE_TABLE_EQUIPO);
        db.execSQL(SQL_CREATE_TABLE_PARTIDO_EQUIPO);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE_ESTADO);
        db.execSQL(SQL_DELETE_TABLE_PARTIDO);
        db.execSQL(SQL_DELETE_TABLE_EQUIPO);
        db.execSQL(SQL_DELETE_TABLE_PARTIDO_EQUIPO);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
