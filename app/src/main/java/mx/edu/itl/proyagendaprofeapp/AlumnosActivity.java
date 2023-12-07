package mx.edu.itl.proyagendaprofeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class AlumnosActivity extends AppCompatActivity {



    private ArrayList<String> seleccionados;

    private ListView listaAlumnos;
    private BDHelper bdHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        bdHelper = new BDHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumnos);

        String [] alumnos = bdHelper.getAlumnosMateria(bdHelper.IdMateriaPorNombre("Android"));

        listaAlumnos = findViewById(R.id.listaAlumnos);
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice
                , alumnos);
        listaAlumnos.setAdapter(adaptador);
        for(int i = 0; i < alumnos.length;i++){
                listaAlumnos.setItemChecked(i,bdHelper.getAlumnosTareaCumplio(1));
        }
    }
    public void btnElementosSeleccionadosClick ( View v ) {
        //SELECT alumnos_tabla.nombre, alumnos_tabla.No_control, alumnos_tareas_tabla.cumplio from alumnos_tareas_tabla, alumnos_tabla WHERE alumnos_tabla.ID = alumnos_tareas_tabla.ID_ALUMNO AND alumnos_tareas_tabla.ID_TAREA = 1
        seleccionados = new ArrayList<String>();
        SparseBooleanArray elementosMarcados = listaAlumnos.getCheckedItemPositions();
        for (int i = 0; i < elementosMarcados.size(); i++) {
            int llave = elementosMarcados. keyAt(i);
            boolean valor = elementosMarcados.get(llave);
            if( valor){
                seleccionados.add(listaAlumnos.getItemAtPosition(llave).toString());
            }

        }

        if(seleccionados.isEmpty()){
            Toast.makeText(this,"No hay elementos seleccionados",Toast.LENGTH_SHORT).show();
        }else{

            Toast.makeText(this,"Seleccionados"+ seleccionados,Toast.LENGTH_SHORT).show();
        }
    }

}