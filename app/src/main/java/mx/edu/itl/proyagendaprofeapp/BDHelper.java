package mx.edu.itl.proyagendaprofeapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.BoringLayout;
import android.util.Log;
import android.util.SparseBooleanArray;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

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
    private static final String ALUMNOS_COL3       = "No_control";


    // Relacion - Una tarea se realiza por muchos alumnos
    //          y muchos alumnos realizan una tarea
    private static final String ALUMNOSTAREAS_TABLE_NAME = "alumnos_tareas_tabla";
    private static final String ALUMNOSTAREAS_COL1       = "ID_ALUMNO";
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
                ALUMNOS_COL2 + " TEXT, "+
                ALUMNOS_COL3 + " INTEGER UNIQUE " + ")";

        String crearAluMatTabla = "CREATE TABLE " + ALUMNOSMATERIAS_TABLE_NAME + " (" +
                ALUMNOSMATERIAS_COL1 + " INTEGER, " +
                ALUMNOSMATERIAS_COL2 + " INTEGER, " +
                "FOREIGN KEY(" + ALUMNOSMATERIAS_COL1 + ") REFERENCES " + ALUMNOS_TABLE_NAME + "(" + ALUMNOS_COL1 +  " )," +
                "FOREIGN KEY(" + ALUMNOSMATERIAS_COL2 + ") REFERENCES " + MATERIAS_TABLE_NAME + "(" + MATERIAS_COL1 + "))";

        String crearAluTarTabla = "CREATE TABLE " + ALUMNOSTAREAS_TABLE_NAME + " ( " +
                ALUMNOSTAREAS_COL1 + " INTEGER, " +
                ALUMNOSTAREAS_COL2 + " INTEGER, " +
                ALUMNOSTAREAS_COL3 + " INTEGER,  " +
                "FOREIGN KEY(" + ALUMNOSTAREAS_COL1 + ") REFERENCES " + ALUMNOS_TABLE_NAME + "(" + ALUMNOS_COL1 + " )," +
                "FOREIGN KEY(" + ALUMNOSTAREAS_COL2 + ") REFERENCES " + TAREAS_TABLE_NAME + "(" + TAREAS_COL1 + " ))";

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
        if ( resultado != -1 ) {
            Log.d ( TAG, "Registro de Materia correcto" );
        } else {
            Log.e ( TAG, "No se registró la Materia" );
        }
    }

    public void insertarAlumno (String no_Control, String nombre ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Primero registrar el alumno
        contentValues.put ( ALUMNOS_COL2, nombre );
        contentValues.put ( ALUMNOS_COL3, no_Control);

        long idAlumno = db.insert ( ALUMNOS_TABLE_NAME, null, contentValues );




        // Veificar si la insercion funcionó
        if ( idAlumno != -1  ) {
            Log.e ( TAG, "Se pudo registrar alumno" );
        }

    }

    public void AsignarAlumnoMateria(String nombre,String materia){
        SQLiteDatabase db = this.getWritableDatabase();
        long idMateria = IdMateriaPorNombre ( materia );
        long idAlumno = IdAlumnoPorNombre(nombre);


        // Insertar registro en la tabla de la relación
        ContentValues alumno_materia = new ContentValues();
        alumno_materia.put( ALUMNOSMATERIAS_COL1, idAlumno);
        alumno_materia.put ( ALUMNOSMATERIAS_COL2, idMateria );
        long idAlumno_Materia = db.insert ( ALUMNOSMATERIAS_TABLE_NAME, null, alumno_materia );
        if(idMateria != -1 && idAlumno_Materia != -1 )
        {

            Log.e ( TAG, "Se pudo registrar alumno a materia" );
        }
        else{
            Log.e ( TAG, "Error no se pudo registrar alumno a materia" );
        }
    }
    public long IdAlumnoPorNombre ( String alumno ) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Columna a salvar
        String columnas[] = { ALUMNOS_COL1 };

        // Seleccionar por...
        String selccion = ALUMNOS_COL2 + "=?";

        // Parametro que reemplaza "?"
        String args[] = { alumno };

        // Consulta
        Cursor cursor = db.query ( ALUMNOS_TABLE_NAME, columnas, selccion, args, null, null, null );

        // Verificar si se realizo correctamente la consulta
        long idAlumno = -1;

        if ( cursor != null && cursor.moveToFirst() ) {
            int colIndex = cursor.getColumnIndex ( ALUMNOS_COL1 );
            idAlumno = cursor.getLong ( colIndex );
            cursor.close();
        }

        return  idAlumno;
    }

    public String NombrePorIDAlumno ( String ID_alumno ) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Columna a salvar
        String columnas[] = { ALUMNOS_COL3 , ALUMNOS_COL2 };

        // Seleccionar por...
        String selccion = ALUMNOS_COL1 + "=?";

        // Parametro que reemplaza "?"
        String args[] = {ID_alumno};

        // Consulta
        Cursor cursor = db.query ( ALUMNOS_TABLE_NAME, columnas, selccion, args, null, null, ALUMNOS_COL2 +" DESC" );

        // Verificar si se realizo correctamente la consulta
        String nombreNoControl = "";

        if ( cursor != null && cursor.moveToFirst() ) {
            int colIndex1 = cursor.getColumnIndex ( ALUMNOS_COL3 );
            nombreNoControl = cursor.getString( colIndex1);
            int colIndex2 = cursor.getColumnIndex ( ALUMNOS_COL2 );
            nombreNoControl = nombreNoControl + " "+ cursor.getString(colIndex2);
            cursor.close();
        }

        return  nombreNoControl;

    }

    public long IdMateriaPorNombre (  String materia ) {
        SQLiteDatabase db = this.getWritableDatabase();
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
        tareaValues.put ( TAREAS_COL2, nombre );

        // Buscar el id de la materia correspondiente a la tarea
        long idMateria = IdMateriaPorNombre (  materia );

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
                tarea_alumno.put ( ALUMNOSTAREAS_COL1, idAlumno );
                tarea_alumno.put ( ALUMNOSTAREAS_COL2, tarea_pk );
                tarea_alumno.put( ALUMNOSTAREAS_COL3,0);

                long idAsignacion = db.insert ( ALUMNOSTAREAS_TABLE_NAME, null, tarea_alumno );

                if ( idAsignacion != -1 ) {
                    Log.e ( TAG, "No se asignó la tarea al alumno" );
                } else {
                    Log.d(TAG, "Se asigno la tarea correctamente");
                }
            } while ( cursor.moveToNext() );
        }
    }
    // ------------------------ METODOS DE CONSULTAS SQL -------------------------------------------
    public Cursor getMaterias () {
        SQLiteDatabase db = this.getReadableDatabase();

        String columnas[] = { MATERIAS_COL1,MATERIAS_COL2 , MATERIAS_COL3};
        String orden = MATERIAS_COL2 + " DESC";
        Cursor data = db.query (MATERIAS_TABLE_NAME, columnas,null,null,null,null, orden);
        return data;
    }




    public String[] getAlumnosMateria ( long id_materia ) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Columnas deseadas
        String columnas[] = { ALUMNOSMATERIAS_COL1 ,ALUMNOS_COL3,ALUMNOS_COL2};

        // Seleccionar por...
        String selccion = ALUMNOS_COL1 + "="+ ALUMNOSMATERIAS_COL1 + " AND " + ALUMNOSMATERIAS_COL2+ "=?";

        // Parametro que reemplaza "?"
        String args[] = { id_materia + "" };

        String tablas = ALUMNOSMATERIAS_TABLE_NAME + "," + ALUMNOS_TABLE_NAME;
        // Consulta
        Cursor cursor = db.query (tablas , columnas, selccion, args, null, null,ALUMNOS_COL2 + " DESC" );

        String[] result = new String[cursor.getCount()];
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++){
            String row = cursor.getString(0);
            //You can here manipulate a single string as you please
            result[i] = this.NombrePorIDAlumno(row);
            cursor.moveToNext();
        }
        return result;
    }

    public String[] getTareasMateria ( long id_materia ) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Columnas deseadas
        String columnas[] = { TAREAS_COL1, TAREAS_COL2 };

        // Seleccionar por...
        String selccion = TAREAS_COL3 + "=?";

        // Parametro que reemplaza "?"
        String args[] = { id_materia + "" };

        // Consulta
        Cursor cursor = db.query ( TAREAS_TABLE_NAME, columnas, selccion, args, null, null, null );

        String[] result = new String[cursor.getCount()];
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++){
            String row = cursor.getString(1);
            //You can here manipulate a single string as you please
            result[i] = row;
            cursor.moveToNext();
        }
        return result;
    }

    public Boolean[] getAlumnosTareaCumplio ( long id_tarea ) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Columnas deseadas
        String columnas[] = { ALUMNOSTAREAS_COL3 };

        // Seleccionar por...
        String selccion = ALUMNOSTAREAS_COL2 + "=? AND "+ ALUMNOSTAREAS_COL1 + " = " + ALUMNOS_COL1 ;

        // Parametro que reemplaza "?"
        String args[] = { id_tarea + "" };

        String tablas = ALUMNOS_TABLE_NAME + "," + ALUMNOSTAREAS_TABLE_NAME;
        // Consulta - Obtener id de los alumnos en ALUMNOSTAREAS
        Cursor cursor = db.query (
                tablas,
                columnas,
                selccion,
                args,
                null, null, ALUMNOS_COL2 + " DESC" );

        // Agrupar id's
        Boolean cumplio[]= new Boolean[cursor.getCount()];
        int contador =0;

        if ( cursor != null && cursor.moveToFirst()) {
            do {
                int colIndex = cursor.getColumnIndex ( ALUMNOSTAREAS_COL3 );
                if ( 0 ==cursor.getInt( colIndex )){
                    cumplio[contador]=false;
                }else
                {
                    cumplio[contador]=true;
                }
                contador++;
            } while ( cursor.moveToNext() );

            cursor.close();
        }

        return cumplio;
    }

    @SuppressLint("Range")
    public void updateTareaCumplida (long id_tarea , SparseBooleanArray cumplio) {

        SQLiteDatabase db = this.getWritableDatabase();


        // Columnas deseadas
        String columnas[] = { ALUMNOS_COL1 };

        // Seleccionar por...
        String selccion = ALUMNOSTAREAS_COL2 + "=? AND "+ ALUMNOSTAREAS_COL1 + " = " + ALUMNOS_COL1 ;

        // Parametro que reemplaza "?"
        String args[] = { id_tarea + "" };

        String tablas = ALUMNOS_TABLE_NAME + "," + ALUMNOSTAREAS_TABLE_NAME;
        // Consulta - Obtener id de los alumnos en ALUMNOSTAREAS
        Cursor cursor1 = db.query (
                tablas,
                columnas,
                selccion,
                args,
                null, null, ALUMNOS_COL2 + " DESC" );

        // Agrupar id's
        int cumplioIDS[]= new int[cursor1.getCount()];
        int contador =0;
        if ( cursor1 != null && cursor1.moveToFirst()) {
            do {
                int colIndex = cursor1.getColumnIndex ( ALUMNOS_COL1 );
                cumplioIDS[contador] = cursor1.getInt ( colIndex );


                contador++;
            } while ( cursor1.moveToNext() );

            cursor1.close();
        }

        for(int i = 0; i<cumplio.size();i++){
            ContentValues valor = new ContentValues();
            int llave = cumplio. keyAt(i);
            boolean check = cumplio.get(llave);
            if(check)
                valor.put ( ALUMNOSTAREAS_COL3, 1 ); // Si el alumno cunmplio la tarea se marca un 1
            else
                valor.put( ALUMNOSTAREAS_COL3,0);

            int id_alumno = cumplioIDS[i];
            // Se cambiará el registro donde los ids coincidan con el los que se indican
            String where = ALUMNOSTAREAS_COL2 + "=? AND " + ALUMNOSTAREAS_COL1 + "=?";
            String args1[] = { id_tarea + "", id_alumno + "" };

            int registroActualizado = db.update ( ALUMNOSTAREAS_TABLE_NAME, valor, where, args1 );

            // Verificar si se realizaron actualizaciones
            if ( registroActualizado > 0 ) {
                Log.d ( TAG, "Se actualizó el cumplimiento de la tarea correctamente" );
            } else {
                Log.e ( TAG, "No se actualizó ningún registro" );
            }
        }
    }
    public long idTarea(String tarea, String idMateria){
        long idTarea;
        SQLiteDatabase db = this.getReadableDatabase();
        // Columna a salvar
        String columnas[] = {TAREAS_COL1  };

        // Seleccionar por...
        String selccion = TAREAS_COL2 + "=? AND " + TAREAS_COL3+ "=?";

        // Parametro que reemplaza "?"
        String args[] = { tarea,idMateria };

        // Consulta
        Cursor cursor = db.query ( TAREAS_TABLE_NAME, columnas, selccion, args, null, null, null );

        // Verificar si se realizo correctamente la consulta
        idTarea = -1;

        if ( cursor != null && cursor.moveToFirst() ) {
            int colIndex = cursor.getColumnIndex ( MATERIAS_COL1 );
            idTarea = cursor.getLong ( colIndex );
            cursor.close();
        }


        return idTarea;
    }
    public String[] getAlumnosTareas ( String nombreTarea ) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Otener id de la tarea por nombre
        String queryID = "SELECT " + TAREAS_COL1 +
                "FROM " + TAREAS_TABLE_NAME +
                " WHERE " + TAREAS_COL2 + " = " + nombreTarea;

        Cursor cursorTemp = db.rawQuery ( queryID, null );
        int id_tarea = Integer.parseInt ( cursorTemp.getString ( 0 ) );

        // Construir raw query para alumnos
        String query = "SELECT " +
                ALUMNOS_COL3 + ", " +
                ALUMNOS_COL2 + ", " +
                ALUMNOSTAREAS_COL3 + " FROM " +
                ALUMNOSTAREAS_TABLE_NAME +
                " INNER JOIN " + ALUMNOS_TABLE_NAME +
                " ON " + ALUMNOSTAREAS_TABLE_NAME + "." + ALUMNOSTAREAS_COL2 + " = " + ALUMNOS_TABLE_NAME + "." + ALUMNOS_COL1 +
                " WHERE " + ALUMNOSTAREAS_COL2 + " = " + id_tarea;

        Cursor cursor = db.rawQuery(query, null);

        // Verificar si el cursor contiene datos
        if (cursor != null && cursor.moveToFirst()) {
            // Concatenar columnas
            String resultados[] = new String [ cursor.getCount() ];
            int i = 0;
            do {

                String alumno = cursor.getString ( 0 ) +
                        "," + cursor.getString ( 1 ) +
                        "," + cursor.getString ( 2 );

                resultados [ i ] = alumno;
                i++;
            } while ( cursor.moveToFirst() );

            cursor.close();
            return  resultados;
        } else {
            // Manejar el caso donde no hay datos
            if (cursor != null) {
                cursor.close();
            }
            return new String[0];
        }
    }

    public void borrarBD(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TAREAS_TABLE_NAME,null,null);
        db.delete(MATERIAS_TABLE_NAME,null,null);
        db.delete(ALUMNOS_TABLE_NAME,null,null);
        db.delete(ALUMNOSMATERIAS_TABLE_NAME,null,null);
        db.delete(ALUMNOSTAREAS_TABLE_NAME,null,null);
        db.delete("SQLITE_SEQUENCE",null,null);
    }
}