package com.nava.mijornada.database;

import android.provider.BaseColumns;

public final class ControlEstadistico {
    private ControlEstadistico() {}
    public static class Partido implements BaseColumns {
        public static final String TABLE_NAME = "partido";
        public static final String _ID = "id";
        public static final String FECHA = "fecha";
        public static final String HORA_INICIO = "hora_inicio";
        public static final String HORA_FIN = "hora_final";
    }

    public static class Equipo implements BaseColumns {
        public static final String TABLE_NAME = "equipo";
        public static final String _ID = "id";
        public static final String NOMBRE = "nombre";
    }

    public static class Resultado implements BaseColumns {
        public static final String TABLE_NAME = "resultado";
        public static final String _ID = "id";
        public static final String DESCRIPCION = "descripcion";
        public static final String RESULTADO_EN_PROCESO = "sin resultado";
        public static final String RESULTADO_VICTORIA = "victoria";
        public static final String RESULTADO_DERROTA = "derrota";
        public static final String RESULTADO_EMPATE = "empate";
    }

    public static class PartidoEquipo implements BaseColumns {
        public static final String TABLE_NAME = "partido_equipo";
        public static final String _ID = "id_partido_equipo";
        public static final String _ID_PARTIDO = "id_partido";
        public static final String _ID_EQUIPO = "id_equipo";
        public static final String _ID_RESULTADO = "id_resultado";
    }

}
