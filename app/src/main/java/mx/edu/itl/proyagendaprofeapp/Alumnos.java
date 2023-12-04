package mx.edu.itl.proyagendaprofeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class Alumnos extends AppCompatActivity {
    private String [] alumnos = {"carlito"," lalo","prupru"};


    private ArrayList<String> seleccionados;

    private ListView listaAlumnos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumnos);
        listaAlumnos = findViewById(R.id.listaAlumnos);
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice
                , alumnos);
        listaAlumnos.setAdapter(adaptador);
    }
    public void btnElementosSeleccionadosClick ( View v ) {
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