package mx.edu.itl.proyagendaprofeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TareasActivity extends AppCompatActivity {


    private ListView listaTareas;

    private BDHelper bdHelper;

    private int idMateria;

    public static final int CODIGO_SELECCIONAR_ARCHIVO = 2908;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bdHelper = new BDHelper(this);

        setContentView(R.layout.activity_tareas);

        String idMateria1 = getIntent().getStringExtra ( "idMateria" );
        idMateria = Integer.parseInt ( idMateria1 );

        // Obtener intent y sacar nombre de la materia
        String [] tareas = bdHelper.getTareasMateria ( idMateria );

        listaTareas   = findViewById ( R.id.listaTareas );
        // Se crea un ArrayAdapter: El 2o argumento debe ser el id de un recurso TEXTVIEW
        //                          El 3er argumento es la lista de Strings con los que se va a llenar
        ArrayAdapter miadaptador = new ArrayAdapter( this,R.layout.lista_item_sencilla, tareas );
        listaTareas.setAdapter ( miadaptador );

        // Establecemos el listener para el evento OnItemClick  del ListView
        listaTareas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //bdHelper.insertarAlumno("David","Android");
                //bdHelper.insertarAlumno("Carlos","Android");
                //bdHelper.insertarAlumno("Eduardo","Android");
                //bdHelper.insertarTarea("appBanderas","Android");
                //bdHelper.insertarTarea("appFotos","Android");

                // Mandar el nombre de la tarea
                Intent intent = new Intent (TareasActivity.this, AlumnosActivity.class );
                Bundle extras = new Bundle();

                extras.putInt ("materia",idMateria);
                extras.putString ( "nombretarea", tareas[i] );
                intent.putExtras ( extras );
                startActivity ( intent );
            }});
        };

        public void btnImportarTareas ( View v ) {
            // Intent para llamar al explorador de archivos
            Intent intent = new Intent ( Intent.ACTION_GET_CONTENT );
            intent.setType ( "*/*" ); // Se admiten todos los archivos
            intent.addCategory ( Intent.CATEGORY_DEFAULT );

            try {
                startActivityForResult ( Intent.createChooser ( intent, "Seleccione una opción" ),
                        CODIGO_SELECCIONAR_ARCHIVO);
            } catch ( ActivityNotFoundException e ) {
                Toast.makeText(this, "Explorador de archivos no encontrado.\n" +
                        "Por favor instale un exploador de archivos.", Toast.LENGTH_LONG).show();
            }

            // ------------------ CONTINUA EN onActivityResult() ------------------ //
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        File archivo;
        if ( requestCode == CODIGO_SELECCIONAR_ARCHIVO ) {
            if ( resultCode == RESULT_OK ) {
                Uri archivoUri = data.getData();


                archivo = new File ( archivoUri.getPath() );
                // Leer archivo
                try {
                    BufferedReader br = new BufferedReader ( new InputStreamReader( getContentResolver().openInputStream ( archivoUri ) ));

                    // Guadar lineas leidas
                    ArrayList < String > lineasLeidas = new ArrayList<>();
                    String linea;
                    while ( ( linea = br.readLine() ) != null  ) {
                        // Puede haber mas de un renglon que contenga materias
                        lineasLeidas.add ( linea );
                    }

                    if ( lineasLeidas.size() > 0 ) {
                        // Separar valores por las comas (puede haber mas de una linea en el txt)
                        for ( int i = 0; i < lineasLeidas.size(); i++ ) {
                            String tareas[] = lineasLeidas.get ( i ).split(",");

                            for (int j = 0; j < tareas.length; j++) {
                                // Insertar tareas
                                Toast.makeText(this, tareas[j], Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    br.close();
                } catch ( IOException ex) {
                    Toast.makeText ( this, "ERROR: " + ex, Toast.LENGTH_LONG ).show();
                }
            }
        }
    }
}
