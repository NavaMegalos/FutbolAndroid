package com.nava.mijornada;

import static com.nava.mijornada.ControlEstadistico.*;

public class ConsultasTablas {

//    ESTADO
    static final String SQL_CREATE_TABLE_ESTADO =
            "CREATE TABLE " + Estado.TABLE_NAME + " (" +
                    Estado._ID + " INTEGER PRIMARY KEY," +
                    Estado.DESCRIPCION + " TEXT" +
                    ")";
    static final String SQL_DELETE_TABLE_ESTADO =
            "DROP TABLE IF EXISTS " + Estado.TABLE_NAME;

//    PARTIDO
    static final String SQL_CREATE_TABLE_PARTIDO =
            "CREATE TABLE " + Partido.TABLE_NAME + " (" +
                    Partido._ID + " INTEGER PRIMARY KEY," +
                    Partido._ID_ESTADO + " INTEGER," +
                    Partido.FECHA + " TEXT," +
                    Partido.HORA_INICIO + " TEXT," +
                    Partido.HORA_FIN + " TEXT," +
                    "FOREIGN KEY (" + Partido._ID_ESTADO +") " +
                        "REFERENCES " + Estado.TABLE_NAME + " (" + Estado._ID + ") " +
                        "ON UPDATE CASCADE " +
                        "ON DELETE CASCADE " +
                    ")";
    static final String SQL_DELETE_TABLE_PARTIDO =
            "DROP TABLE IF EXISTS " + Partido.TABLE_NAME;

//    EQUIPO
    static final String SQL_CREATE_TABLE_EQUIPO =
            "CREATE TABLE " + Equipo.TABLE_NAME + " (" +
                    Equipo._ID + " INTEGER PRIMARY KEY," +
                    Equipo.NOMBRE + " TEXT" +
                    ")";
    static final String SQL_DELETE_TABLE_EQUIPO =
            "DROP TABLE IF EXISTS " + Equipo.TABLE_NAME;

//    PARTIDO|EQUIPOS
    static final String SQL_CREATE_TABLE_PARTIDO_EQUIPO =
            "CREATE TABLE " + PartidoEquipo.TABLE_NAME + " (" +
                    PartidoEquipo._ID + " INTEGER PRIMARY KEY," +
                    PartidoEquipo._ID_PARTIDO + " INTEGER," +
                    PartidoEquipo._ID_EQUIPO + " INTEGER," +
                    "FOREIGN KEY (" + PartidoEquipo._ID_PARTIDO +") " +
                    "REFERENCES " + Partido.TABLE_NAME + " (" + Partido._ID + ") " +
                    "ON UPDATE CASCADE " +
                    "ON DELETE CASCADE," +
                    "FOREIGN KEY (" + PartidoEquipo._ID_EQUIPO +") " +
                    "REFERENCES " + Equipo.TABLE_NAME + " (" + Equipo._ID + ") " +
                    "ON UPDATE CASCADE " +
                    "ON DELETE CASCADE " +
                    ")";
    static final String SQL_DELETE_TABLE_PARTIDO_EQUIPO =
            "DROP TABLE IF EXISTS " + PartidoEquipo.TABLE_NAME;
}
