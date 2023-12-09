package mx.edu.itl.proyagendaprofeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class InicioActivity extends AppCompatActivity {
    BDHelper baseDatosHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        baseDatosHelper = new BDHelper ( this );
    }

    public  void btnIrLista ( View v ) {
        Intent intent = new Intent( InicioActivity.this, MainActivity.class );
        startActivity ( intent );
    }

    public void btnAgregar ( View v ) {
        // Este boton agrega datos por default

        // Materias
        baseDatosHelper.insertarMaterias ( "Desarrollo en Android", R.drawable.android );
        baseDatosHelper.insertarMaterias ( "Tópicos Avanzados de Programación", R.drawable.topicos );
        baseDatosHelper.insertarMaterias ( "Lenguajes y Autómatas II", R.drawable.automatas );

        // Tareas - Android
        baseDatosHelper.insertarTarea ( "appVideoView", "Desarrollo en Android" );
        baseDatosHelper.insertarTarea ( "appReproducciónAudio", "Desarrollo en Android" );
        baseDatosHelper.insertarTarea ( "appProyectilloFinal", "Desarrollo en Android" );

        // Tareas - Topicos
        baseDatosHelper.insertarTarea ( "Java Beans", "Tópicos Avanzados de Programación" );
        baseDatosHelper.insertarTarea ( "appConectarMySQL", "Tópicos Avanzados de Programación" );

        // Tareas - Automatas
        baseDatosHelper.insertarTarea ( "Analizador Semántico", "Lenguajes y Autómatas II" );
        baseDatosHelper.insertarTarea ( "Código Intermedio", "Lenguajes y Autómatas II" );
        baseDatosHelper.insertarTarea ( "Código Objeto", "Lenguajes y Autómatas II" );

        // Alumnos
        baseDatosHelper.insertarAlumno ( "19130905", "José Eduardo Espino", "Desarrollo en Android" );
        baseDatosHelper.insertarAlumno ( "19130899", "Carlos Castorena Lugo", "Desarrollo en Android" );
        baseDatosHelper.insertarAlumno ( "19130915", "Jorge Alejandro Ledezma", "Desarollo en Android" );
        baseDatosHelper.insertarAlumno ( "19130966", "Josúe Benjamín Rangel", "Desarrollo en Android" );
        baseDatosHelper.insertarAlumno ( "19130910", "Arturo Fernández Alvaréz", "Tópicos Avanzados de Programación" );
        baseDatosHelper.insertarAlumno ( "19130956", "Owen Ortega Flores", "Tópicos Avanzados de Programación" );
        baseDatosHelper.insertarAlumno ( "19130966", "Josúe Benjamín Rangel", "Tópicos Avanzados de Programación" );
        baseDatosHelper.insertarAlumno ( "19130966", "Josúe Benjamín Rangel", "Lenguajes y Autómatas II" );
        baseDatosHelper.insertarAlumno ( "19130905", "José Eduardo Espino", "Lenguajes y Autómatas II" );
        baseDatosHelper.insertarAlumno ( "19130899", "Carlos Castorena", "Lenguajes y Autómatas II" );
        baseDatosHelper.insertarAlumno ( "19130900", "Héctor Manuel Chavez ", "Lenguajes y Autómatas II" );
        baseDatosHelper.insertarAlumno ( "19130913", "Eduardo Ivan Guerrero", "Lenguajes y Autómatas II" );
        baseDatosHelper.insertarAlumno ( "19130914", "Hugo René Guerra",  "Lenguajes y Autómatas II");
        baseDatosHelper.insertarAlumno ( "19130930", "Alberto Daniel Mireles", "Lenguajes y Autómatas II" );
        baseDatosHelper.insertarAlumno ( "19130940", "David Alejandro Prueneda", "Lenguajes y Autómatas II" );

        Toast.makeText ( this, "Datos por defecto agregados, volver abrir app", Toast.LENGTH_SHORT ).show();
    }
}