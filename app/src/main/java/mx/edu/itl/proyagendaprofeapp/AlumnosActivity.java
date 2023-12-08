package mx.edu.itl.proyagendaprofeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class AlumnosActivity extends AppCompatActivity {

    private ArrayList<String> seleccionados;

    private ListView listaAlumnos;
    private BDHelper bdHelper;

    String nombreMateria, nombreTarea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bdHelper = new BDHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumnos);

        // Traer argumentos enviados
        Intent intent = getIntent();
        nombreMateria = String.valueOf(intent.getIntExtra ( "nombreMateria", -1 ));
        nombreTarea = String.valueOf(intent.putExtra ( "nombreTarea", -1 ));

        String [] alumnos = bdHelper.getAlumnosMateria(bdHelper.IdMateriaPorNombre(nombreMateria));

        listaAlumnos = findViewById(R.id.listaAlumnos);
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice
                , alumnos);
        listaAlumnos.setAdapter(adaptador);

        Boolean checked[]= bdHelper.getAlumnosTareaCumplio(1);
        for(int i = 0; i < alumnos.length;i++){
                listaAlumnos.setItemChecked(i,checked[i]);
        }
    }

    public void btnElementosSeleccionadosClick ( View v ) {
        //SELECT alumnos_tabla.nombre, alumnos_tabla.No_control, alumnos_tareas_tabla.cumplio from alumnos_tareas_tabla, alumnos_tabla WHERE alumnos_tabla.ID = alumnos_tareas_tabla.ID_ALUMNO AND alumnos_tareas_tabla.ID_TAREA = 1
        //seleccionados = new ArrayList<String>();
        //SparseBooleanArray elementosMarcados = listaAlumnos.getCheckedItemPositions();

        //bdHelper.updateTareaCumplida(1, elementosMarcados);
        generarReporte( v, bdHelper );
    }

    public void generarReporte ( View v, BDHelper db ) {
        // Crear ruta del archivo ( el archivo debe guardarse en la carpeta de descargas )
        String rutaArchivo = Environment.getExternalStoragePublicDirectory ( Environment.DIRECTORY_DOWNLOADS ).getPath();

        // Formatear nombre del archivo basado en la fecha y hora del sistema
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyyMMddHHmmss" );
        String fechaHora = simpleDateFormat.format( new Date() );
        String nombreArchivo = "reporte - " + fechaHora + ".txt";

        File archivo = new File( rutaArchivo, nombreArchivo );

        // Escribir sobre el archivo
        try {
            BufferedWriter bw = new BufferedWriter ( new FileWriter( archivo ) );

            bw.write ( nombreTarea );

            // Escribir alumnos
            String alumnos[] = db.getAlumnosTareas ( nombreTarea );

            for ( int i = 0; i < alumnos.length; i++ )
                bw.write ( alumnos [ i ] );

            bw.close();

            Toast.makeText ( this, "Reporte creado, revise sus descargas", Toast.LENGTH_LONG ).show();
        } catch ( IOException ex) {
            Toast.makeText ( this, "ERROR: " + ex, Toast.LENGTH_LONG ).show();
        }
    }

}