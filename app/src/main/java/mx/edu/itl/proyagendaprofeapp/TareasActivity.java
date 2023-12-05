package mx.edu.itl.proyagendaprofeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class TareasActivity extends AppCompatActivity {

    private final String [] tareas   = {"android tarea 1", "android tarea 2" };
    private ListView listaTareas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);


        listaTareas   = findViewById(R.id.listaTareas);
        // Se crea un ArrayAdapter: El 2o argumento debe ser el id de un recurso TEXTVIEW
        //                          El 3er argumento es la lista de Strings con los que se va a llenar

        ArrayAdapter miadaptador = new ArrayAdapter(this,R.layout.lista_sencilla, tareas);
        listaTareas.setAdapter(miadaptador);

        // Establecemos el listener para el evento OnItemClick  del ListView
        listaTareas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(TareasActivity.this, AlumnosActivity.class);

                Toast.makeText(TareasActivity.this,"Jalo",Toast.LENGTH_SHORT );

                startActivity(intent);

            }});
    };

    }
