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
    private static final String TAG        = "BDHelper";
    private static final String DB_NAME    = "agendaDB";
    private static final int    DB_VERSION = 1;

    // Constantes de la tabla materias
    private static final String MATERIAS_TABLE_NAME = "materias_tabla";
    private static final String MATERIAS_COL1       = "ID";
    private static final String MATERIAS_COL2       = "nombre";
    private static final String MATERIAS_COL3       = "portada";

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

    //----------------------------------------------------------------------------------------------


    public BDHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION );
    }

    //----------------------------------------------------------------------------------------------


    @Override
    public void onCreate ( SQLiteDatabase db ) {
        crearTablas ( db );
        insertarMaterias ( "Desarrolo en Android", R.drawable.android );
    }

    @Override
    public void onUpgrade ( SQLiteDatabase db, int i, int i1 ) {
        db.execSQL ( "DROP IF TABLE EXISTS " + MATERIAS_TABLE_NAME );
        onCreate ( db );
    }

    // ------------------------     METODOS NORMALES     -------------------------------------------
    public void crearTablas ( SQLiteDatabase db ) {
        String crearMateriasTabla = "CREATE TABLE " + MATERIAS_TABLE_NAME + " (" +
                MATERIAS_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MATERIAS_COL2 + " TEXT," +
                MATERIAS_COL3 + " INTEGER)";

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

    public void insertarMaterias ( String materia, int materiaImg ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put ( MATERIAS_COL2, materia );
        contentValues.put ( MATERIAS_COL3, materiaImg );

        Log.d(TAG, "addDatos: Agregando " + materia + " a " + MATERIAS_TABLE_NAME);

        long resultado = db.insert(MATERIAS_TABLE_NAME, null, contentValues);

        // si se insertó correctamente resultado valdrá -1
        if ( resultado == -1 ) {
            Log.d ( MATERIAS_TABLE_NAME, "TODO BIEN" );
        } else {
            Log.e ( MATERIAS_TABLE_NAME, "TODO MAL" );
        }
    }

    // ------------------------ METODOS DE CONSULTAS SQL -------------------------------------------

}