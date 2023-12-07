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
    private static final String ALUMNOSTAREAS_COL1    = "ID_ALUMNO";
    private static final String ALUMNOSTAREAS_COL2       = "ID_TAREA";
    private static final String ALUMNOSTAREAS_COL3       = "cumplio";

    // Relacion - Una alumno puede estar en varias materias
    //          y una materia puede tener varios alumnos
    private static final String ALUMNOSMATERIAS_TABLE_NAME = "alumnos_materias_tabla";
    private static final String ALUMNOSMATERIAS_COL1    = "ID_ALUMNO";
    private static final String ALUMNOSMATERIAS_COL2     = "ID_MATERIA";

    //----------------------------------------------------------------------------------------------


    public BDHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION );
    }

    //----------------------------------------------------------------------------------------------


    @Override
    public void onCreate ( SQLiteDatabase db ) {
        crearTablas ( db );
    }

    @Override
    public void onUpgrade ( SQLiteDatabase db, int i, int i1 ) {
        crearTablas(db);
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
                ALUMNOS_COL3 + " INTEGER)";

        String crearAluMatTabla = "CREATE TABLE " + ALUMNOSMATERIAS_TABLE_NAME + " (" +
                ALUMNOSMATERIAS_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ALUMNOSMATERIAS_COL2 + " INTEGER, " +
                "FOREIGN KEY(" + ALUMNOSMATERIAS_COL1 + ") REFERENCES " + ALUMNOS_TABLE_NAME + "(" + ALUMNOS_COL1 +  " )," +
                "FOREIGN KEY(" + ALUMNOSMATERIAS_COL2 + ") REFERENCES " + MATERIAS_TABLE_NAME + "(" + MATERIAS_COL1 + "))";

        String crearAluTarTabla = "CREATE TABLE " + ALUMNOSTAREAS_TABLE_NAME + " ( " +
                ALUMNOSTAREAS_COL1 + " INTEGER, " +
                ALUMNOSTAREAS_COL2 + " INTEGER, " +
                ALUMNOSTAREAS_COL3 + " INTEGER, " +
                "FOREIGN KEY(" + ALUMNOSTAREAS_COL1 + ") REFERENCES " + ALUMNOS_TABLE_NAME + "(" + ALUMNOS_COL1 + " )," +
                "FOREIGN KEY(" + ALUMNOSTAREAS_COL2 + ") REFERENCES " + TAREAS_TABLE_NAME + "(" + TAREAS_COL1 + " ))";

        // Ejecutar sentencias SQL para crear las tablas
        db.execSQL ( crearMateriasTabla );
        db.execSQL ( crearTareasTabla );
        db.execSQL ( crearAlumnosTabla );
        db.execSQL ( crearAluMatTabla );
        db.execSQL ( crearAluTarTabla );
    }

    public boolean insertarMaterias ( String materia, int materiaImg ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put ( MATERIAS_COL1, 1);
        contentValues.put ( MATERIAS_COL2, materia );
        contentValues.put ( MATERIAS_COL3, materiaImg );

        Log.d(TAG, "addDatos: Agregando " + materia + " a " + MATERIAS_TABLE_NAME);

        long resultado = db.insert(MATERIAS_TABLE_NAME, null, contentValues);

        // si se insertó correctamente resultado valdrá -1
        if ( resultado == -1 ) {
            return  false;
        } else {
            return true;
        }
    }

    public void insertarAlumno ( String nombre, String materia ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Primero registrar el alumno
        contentValues.put ( ALUMNOS_COL1, 1 );
        contentValues.put ( ALUMNOS_COL2, nombre );

        // Realiar consulta para obtener id's de materia y alumno
        long idAlumno = db.insert ( ALUMNOS_TABLE_NAME, null, contentValues );
        long idMateria = IdMateriaPorNombre ( db, materia );

        // Insertar registro en la tabla de la relación
        ContentValues alumno_materia = new ContentValues();
        alumno_materia.put ( ALUMNOSMATERIAS_COL1, 1 );
        alumno_materia.put ( ALUMNOSMATERIAS_COL2, idAlumno );
        alumno_materia.put ( ALUMNOSMATERIAS_COL3, idMateria );

        long idAlumno_Materia = db.insert ( ALUMNOSMATERIAS_TABLE_NAME, null, alumno_materia );

        // Veificar si la insercion funcionó
        if ( idAlumno != -1 && idMateria != -1 && idAlumno_Materia != -1  ) {
            Log.e ( TAG, "No se pudo registrar alumno" );
        }

    }

    public long IdMateriaPorNombre ( SQLiteDatabase db, String materia ) {
        // Columna a salvar
        String columnas[] = { MATERIAS_COL1 };

        // Seleccionar por...
        String selccion = MATERIAS_COL2 + "=?";

        // Parametro que reemplaza "?"
        String args[] = { materia };

        // Consulta
        Cursor cursor = db.query ( MATERIAS_TABLE_NAME, columnas, selccion, args, null, null, null );

        // Verificar si se realizo correctamente la consulta
        long idMateria = -1;

        if ( cursor != null && cursor.moveToFirst() ) {
            int colIndex = cursor.getColumnIndex ( MATERIAS_COL1 );
            idMateria = cursor.getLong ( colIndex );
            cursor.close();
        }

        return idMateria;
    }

    public void insertarTarea ( String nombre, String materia ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues tareaValues = new ContentValues();

        // Primero registrar id y nombre de la tarea
        tareaValues.put ( TAREAS_COL1, 1 );
        tareaValues.put ( TAREAS_COL2, nombre );

        // Buscar el id de la materia correspondiente a la tarea
        long idMateria = IdMateriaPorNombre ( db, materia );

        // Terminar registro en la tabla de tareas
        tareaValues.put ( TAREAS_COL3, idMateria );
        long idTarea = db.insert ( TAREAS_TABLE_NAME, null, tareaValues );

        // Registrar tarea para todos los alumnos
        asignarTarea ( db, idTarea);

        // Verificar si se realizó la consulta
        if ( idTarea != -1 ) {
            Log.d ( TAG, "Registro de tarea correcto" );
        } else {
            Log.e ( TAG, "No se registró la tarea" );
        }
    }

    public void asignarTarea (SQLiteDatabase db , long tarea_pk ) {
        // Reliza el procedimiento de asignar la tarea registrada a todos los alumnos registrado
        // Columna deseada
        String columnas[] = { ALUMNOS_COL1 };

        Cursor cursor = db.query ( ALUMNOS_TABLE_NAME, columnas,
                null, null, null, null, null );

        // Si se encontraron alumnos
        if ( cursor != null && cursor.moveToFirst() ) {
            do {
                // Alumno actual (obtener id)
                int colIndex = cursor.getColumnIndex ( ALUMNOS_COL1 );
                long idAlumno = cursor.getLong ( colIndex );

                ContentValues tarea_alumno = new ContentValues();
                tarea_alumno.put ( ALUMNOSTAREAS_COL1, 1 );
                tarea_alumno.put ( ALUMNOSTAREAS_COL3, tarea_pk );
                tarea_alumno.put ( ALUMNOSTAREAS_COL2, idAlumno );
                tarea_alumno.put ( ALUMNOSTAREAS_COL4, 0 );

                long idAsignacion = db.insert ( ALUMNOSTAREAS_TABLE_NAME, null, tarea_alumno );

                if ( idAsignacion != -1 ) {
                    Log.e ( TAG, "No se asignó la tarea al alumno" );
                } else {
                    Log.d(TAG, "Se asigno la tarea correctamente");
                }
            } while ( cursor.moveToFirst() );
        }
    }
    // ------------------------ METODOS DE CONSULTAS SQL -------------------------------------------

}