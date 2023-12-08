package mx.edu.itl.proyagendaprofeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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

public class MainActivity extends AppCompatActivity {
    private ListView lstvMaterias;
    BDHelper baseDatosHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

            do {
                // Se obtienen los valores del renglon actual
                ids [ i ] = cursor.getString(0);
                materias [ i ] = cursor.getString(1);
                portadas [ i ] = Integer.parseInt(cursor.getString(2));
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

                    Toast.makeText(MainActivity.this, "Jalo", Toast.LENGTH_SHORT).show();

                    // Mandar id de la materia
                    intent.putExtra ( "idMateria", ids [ i ] );
                    intent.putExtra ( "nombreMateria", materias [ i ] );
                    startActivity(intent);
                }
            });
        }
    };

    public void btnAcercaDe ( View v ) {
        setContentView ( R.layout.acercade_layout );
    }

    public void btnVolverMain( View v ){
        setContentView ( R.layout.activity_main );
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
            ImageView portada = convertView.findViewById ( R.id.imgvMateria );
            TextView titulo = convertView.findViewById ( R.id.txtMateria );

            // Sobreescribir valores
            portada.setImageResource ( portadas[ position ] );
            titulo.setText ( materias [ position ] );

            return convertView;
        }
    }
}

