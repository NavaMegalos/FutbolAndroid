package com.nava.mijornada;

import android.provider.BaseColumns;

public final class ControlEstadistico {
    private ControlEstadistico() {}
    public static class Estado implements BaseColumns {
        public static final String TABLE_NAME = "estado";
        public static final String _ID = "id";
        public static final String DESCRIPCION = "descripcion";
    }

    public static class Partido implements BaseColumns {
        public static final String TABLE_NAME = "partido";
        public static final String _ID = "id";
        public static final String _ID_ESTADO = "id_estado";
        public static final String FECHA = "fecha";
        public static final String HORA_INICIO = "hora_inicio";
        public static final String HORA_FIN = "hora_final";
    }

    public static class Equipo implements BaseColumns {
        public static final String TABLE_NAME = "partido";
        public static final String _ID = "id";
        public static final String NOMBRE = "nombre";
    }

    public static class PartidoEquipo implements BaseColumns {
        public static final String TABLE_NAME = "partido_equipo";
        public static final String _ID = "id_partido_equipo";
        public static final String _ID_PARTIDO = "id_partido";
        public static final String _ID_EQUIPO = "id_equipo";
    }

}
