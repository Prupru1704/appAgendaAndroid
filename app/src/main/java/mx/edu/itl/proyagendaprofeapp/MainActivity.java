package mx.edu.itl.proyagendaprofeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int CODIGO_SELECCIONAR_ARCHIVO = 2908;
    private ListView lstvMaterias;

    BDHelper baseDatosHelper ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        baseDatosHelper = new BDHelper ( this );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Cargar los datos de sqlite
        Cursor cursor = baseDatosHelper.getMaterias();

        // Se ejecuta este codigo si ya hay datos
        if ( cursor.getCount() > 0 ) {

            String materias[] = new String[cursor.getCount()];
            int portadas[] = new int[cursor.getCount()];
            String ids[] = new String[cursor.getCount()];
            int i = 0;
            cursor.moveToFirst();
            do {
                // Se obtienen los valores del renglon actual
                ids [ i ] = String.valueOf(cursor.getInt(0));
                materias [ i ] = cursor.getString(1);
                portadas [ i ] = cursor.getInt(2);
                i++;
            } while (cursor.moveToNext());

            // Inciar SQLite
            baseDatosHelper = new BDHelper(MainActivity.this);

            // Iniciar listview
            lstvMaterias = findViewById(R.id.listaMaterias);

            // Se establece el adaptador para este listview
            AdaptadorMateria adaptador = new AdaptadorMateria(this, materias, portadas);
            lstvMaterias.setAdapter(adaptador);

            // Establecemos el listener para el evento OnItemClick  del ListView
            lstvMaterias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(MainActivity.this, TareasActivity.class);

                    // Mandar id de la materia
                    intent.putExtra( "idMateria", ids [ i ] );
                    startActivity(intent);
                }
            });
        }
    };

    public void btnImportarMaterias ( View v ) {
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
                    BufferedReader br = new BufferedReader ( new InputStreamReader ( getContentResolver().openInputStream ( archivoUri ) ));

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
                            String materias[] = lineasLeidas.get ( i ).split(",");

                            for (int j = 0; j < materias.length; j++) {
                                // Insertar materias
                                Toast.makeText(this, materias[j], Toast.LENGTH_SHORT).show();
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

    ///---------------------------------------------------------------------------------------------
    class AdaptadorMateria extends ArrayAdapter {
        private Context contexto;
        private String [] materias;
        private int    [] portadas;

        public AdaptadorMateria ( Context c, String [] materias, int [] portadas ) {
            super ( c, R.layout.materia_item, R.id.txtMateria, materias );
            contexto = c;
            this.materias = materias;
            this.portadas = portadas;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if ( convertView == null ) {
                //LayoutInflater layoutInflater = ( LayoutInflater ) contexto.getSystemService ( Context.LAYOUT_INFLATER_SERVICE );
                LayoutInflater layoutInflater = LayoutInflater.from( contexto );

                convertView = layoutInflater.inflate ( R.layout.materia_item, parent, false );
            }

            // Obteniendo las referenacias del layout del renglon
            //ImageView portada = convertView.findViewById ( R.id.imgvMateria );
            TextView titulo = convertView.findViewById ( R.id.txtMateria );

            // Sobreescribir valores
            //portada.setImageResource ( portadas[ position ] );
            titulo.setText ( materias [ position ] );

            return convertView;
        }
    }
}

