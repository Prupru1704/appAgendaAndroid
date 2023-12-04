package mx.edu.itl.proyagendaprofeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class BDHelper extends SQLiteOpenHelper {
    private static final String TAG        = "BaseDatosHelper";
    private static final String DB_NAME    = "proyectoDB";
    private static final int    DB_VERSION = 1;


    public BDHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Constantes de la tabla materias
    private static final String MATERIAS_TABLE_NAME = "materias_tabla";
    private static final String MATERIAS_COL1       = "ID";
    private static final String MATERIAS_COL2       = "nombre";

    // Constantes de la tabla tareas (Relacion - Una materia puede tener varias tareas)
    private static final String TAREAS_TABLE_NAME = "tareas_tabla";
    private static final String TAREAS_COL1       = "ID";
    private static final String TAREAS_COL2       = "nombre";
    private static final String TAREAS_COL3       = "ID_MATERIA";

    // Constantes de la tabla alumnos
    private static final String ALUMNOS_TABLE_NAME = "alumnos_tabla";
    private static final String ALUMNOS_COL1       = "ID";
    private static final String ALUMNOS_COL2       = "nombre";
    private static final String ALUMNOS_COL3       = "ID_MATERIA";


    // Relacion - Una tarea se realiza por muchos alumnos
    //          y muchos alumnos realizan una tarea
    private static final String ALUMNOSTAREAS_TABLE_NAME = "alumnos_tareas_tabla";
    private static final String ALUMNOSTAREAS_COL1       = "ID";
    private static final String ALUMNOSTAREAS_COL2       = "ID_ALUMNO";
    private static final String ALUMNOSTAREAS_COL3       = "ID_TAREA";
    private static final String ALUMNOSTAREAS_COL4       = "cumplio";

    // Relacion - Una alumno puede estar en varias materias
    //          y una materia puede tener varios alumnos
    private static final String ALUMNOSMATERIAS_TABLE_NAME = "alumnos_materias_tabla";
    private static final String ALUMNOSMATERIAS_COL1       = "ID";
    private static final String ALUMNOSMATERIAS_COL2       = "ID_ALUMNO";
    private static final String ALUMNOSMATERIAS_COL3       = "ID_MATERIA";


    @Override
    public void onCreate(SQLiteDatabase db) {
        String crearMateriasTabla = "CREATE TABLE " + MATERIAS_TABLE_NAME + " (" +
                MATERIAS_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MATERIAS_COL2 + " TEXT)";

        String crearTareasTabla = "CREATE TABLE " + TAREAS_TABLE_NAME + " (" +
                TAREAS_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TAREAS_COL2 + " TEXT, " +
                TAREAS_COL3 + " INTEGER, " +
                "FOREIGN KEY(" + TAREAS_COL3 + ") REFERENCES " + MATERIAS_TABLE_NAME + "(" + MATERIAS_COL1 + "))";

        String crearAlumnosTabla = "CREATE TABLE " + ALUMNOS_TABLE_NAME + " (" +
                ALUMNOS_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ALUMNOS_COL2 + " TEXT, " +
                ALUMNOS_COL3 + " INTEGER, " +
                "FOREIGN KEY(" + ALUMNOS_COL3 + ") REFERENCES " + MATERIAS_TABLE_NAME + "(" + MATERIAS_COL1 + "))";

        String crearAluMatTabla = "CREATE TABLE " + ALUMNOSMATERIAS_TABLE_NAME + " (" +
                ALUMNOSMATERIAS_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ALUMNOSMATERIAS_COL2 + " INTEGER, " +
                "FOREIGN KEY(" + ALUMNOSMATERIAS_COL2 + ") REFERENCES " + ALUMNOS_TABLE_NAME + "(" + ALUMNOS_COL1 +  " )," +
                "FOREIGN KEY(" + ALUMNOSMATERIAS_COL3 + ") REFERENCES " + MATERIAS_TABLE_NAME + "(" + MATERIAS_COL1 + "))";

        String crearAluTarTabla = "CREATE TABLE " + ALUMNOSTAREAS_TABLE_NAME + " ( " +
                ALUMNOSTAREAS_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ALUMNOSTAREAS_COL2 + " INTEGER, " +
                ALUMNOSTAREAS_COL4 + " INTEGER, " +
                "FOREIGN KEY(" + ALUMNOSTAREAS_COL2 + ") REFERENCES " + ALUMNOS_TABLE_NAME + "("  + "INTEGER," + ALUMNOS_COL1 + " )," +
                "FOREIGN KEY(" + ALUMNOSTAREAS_COL3 + ") REFERENCES " + TAREAS_TABLE_NAME + "(" + TAREAS_COL1 + " ))";

        // Ejecutar sentencias SQL para crear las tablas
        db.execSQL ( crearMateriasTabla );
        db.execSQL ( crearTareasTabla );
        db.execSQL ( crearAlumnosTabla );
        db.execSQL ( crearAluMatTabla );
        db.execSQL ( crearAluTarTabla );
    }

    @Override
    public void onUpgrade ( SQLiteDatabase db, int i, int i1 ) {
        /**
         * Este metodo se ejecuta cuando se cambia la version de la base de datos
         * y por el momento no se ha cambiado nada
         */
    }

    // ------------------------ METODOS DE CONSULTAS SQL ------------------------
}