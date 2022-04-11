package com.nava.mijornada.database;

import static com.nava.mijornada.database.ControlEstadistico.*;

public class ConsultasTablas {

//    PARTIDO
    static final String SQL_CREATE_TABLE_PARTIDO =
            "CREATE TABLE " + Partido.TABLE_NAME + " (" +
                    Partido._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Partido.FECHA + " TEXT," +
                    Partido.HORA_INICIO + " TEXT," +
                    Partido.HORA_FIN + " TEXT" +
                    ")";
    static final String SQL_DELETE_TABLE_PARTIDO =
            "DROP TABLE IF EXISTS " + Partido.TABLE_NAME;

//    EQUIPO
    static final String SQL_CREATE_TABLE_EQUIPO =
            "CREATE TABLE " + Equipo.TABLE_NAME + " (" +
                    Equipo._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Equipo.NOMBRE + " TEXT" +
                    ")";
    static final String SQL_DELETE_TABLE_EQUIPO =
            "DROP TABLE IF EXISTS " + Equipo.TABLE_NAME;

//    RESULTADO
    static final String SQL_CREATE_TABLE_RESULTADO =
            "CREATE TABLE " + Resultado.TABLE_NAME + " (" +
                    Resultado._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Resultado.DESCRIPCION + " TEXT" +
                    ")";
    static final String SQL_DELETE_TABLE_RESULTADO =
            "DROP TABLE IF EXISTS " + Resultado.TABLE_NAME;
//    PARTIDO|EQUIPOS
    static final String SQL_CREATE_TABLE_PARTIDO_EQUIPO =
            "CREATE TABLE " + PartidoEquipo.TABLE_NAME + " (" +
                    PartidoEquipo._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    PartidoEquipo._ID_PARTIDO + " INTEGER," +
                    PartidoEquipo._ID_EQUIPO + " INTEGER," +
                    PartidoEquipo._ID_RESULTADO + " INTEGER," +
                    "FOREIGN KEY (" + PartidoEquipo._ID_PARTIDO +") " +
                    "REFERENCES " + Partido.TABLE_NAME + " (" + Partido._ID + ") " +
                    "ON UPDATE CASCADE " +
                    "ON DELETE CASCADE," +
                    "FOREIGN KEY (" + PartidoEquipo._ID_EQUIPO +") " +
                    "REFERENCES " + Equipo.TABLE_NAME + " (" + Equipo._ID + ") " +
                    "ON UPDATE CASCADE " +
                    "ON DELETE CASCADE," +
                    "FOREIGN KEY (" + PartidoEquipo._ID_RESULTADO +") " +
                    "REFERENCES " + Resultado.TABLE_NAME + " (" + Resultado._ID + ") " +
                    "ON UPDATE CASCADE " +
                    "ON DELETE CASCADE " +
                    ")";
    static final String SQL_DELETE_TABLE_PARTIDO_EQUIPO =
            "DROP TABLE IF EXISTS " + PartidoEquipo.TABLE_NAME;

//    DEFAULT VALUES
    static final String SQL_INSERT_RESULTADOS =
            "INSERT INTO " + Resultado.TABLE_NAME + " (descripcion) VALUES " +
            " (\""+ Resultado.RESULTADO_EN_PROCESO +"\"), " +
            " (\""+ Resultado.RESULTADO_VICTORIA +"\"), " +
            " (\""+ Resultado.RESULTADO_DERROTA +"\"), " +
            " (\""+ Resultado.RESULTADO_EMPATE +"\");";

}
