package mx.edu.itl.proyagendaprofeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class TareasActivity extends AppCompatActivity {


    private ListView listaTareas;

    private BDHelper bdHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bdHelper = new BDHelper(this);
        setContentView(R.layout.activity_tareas);

        String [] tareas = bdHelper.getTareasMateria(bdHelper.IdMateriaPorNombre("Android"));
        listaTareas   = findViewById(R.id.listaTareas);
        // Se crea un ArrayAdapter: El 2o argumento debe ser el id de un recurso TEXTVIEW
        //                          El 3er argumento es la lista de Strings con los que se va a llenar

        ArrayAdapter miadaptador = new ArrayAdapter(this,R.layout.lista_item_sencilla, tareas);
        listaTareas.setAdapter(miadaptador);

        // Establecemos el listener para el evento OnItemClick  del ListView
        listaTareas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(TareasActivity.this, AlumnosActivity.class);


                //bdHelper.insertarAlumno("David","Android");
                //bdHelper.insertarAlumno("Carlos","Android");
                //bdHelper.insertarAlumno("Eduardo","Android");
                //bdHelper.insertarTarea("appBanderas","Android");
                //bdHelper.insertarTarea("appFotos","Android");


                    startActivity(intent);





            }});
    };

    }
