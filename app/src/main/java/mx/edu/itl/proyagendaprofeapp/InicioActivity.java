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


        // Alumnos
        baseDatosHelper.insertarAlumno ( "19130905", "José Eduardo Espino" );
        baseDatosHelper.insertarAlumno ( "19130899", "Carlos Castorena Lugo" );
        baseDatosHelper.insertarAlumno ( "19130915", "Jorge Alejandro Ledezma" );
        baseDatosHelper.insertarAlumno ( "19130966", "Josúe Benjamín Rangel" );
        baseDatosHelper.insertarAlumno ( "19130913", "Eduardo Ivan Guerrero");
        baseDatosHelper.insertarAlumno ( "19130900", "Héctor Manuel Chavez " );
        baseDatosHelper.insertarAlumno ( "19130956", "Owen Ortega Flores" );
        baseDatosHelper.insertarAlumno ( "19130910", "Arturo Fernández Alvaréz" );
        baseDatosHelper.insertarAlumno ( "19130930", "Alberto Daniel Mireles");
        baseDatosHelper.insertarAlumno ( "19130914", "Hugo René Guerra");
        baseDatosHelper.insertarAlumno ( "19130940", "David Alejandro Prueneda" );

        baseDatosHelper.AsignarAlumnoMateria( "Carlos Castorena Lugo", "Desarrollo en Android" );
        baseDatosHelper.AsignarAlumnoMateria( "José Eduardo Espino", "Desarrollo en Android" );
        baseDatosHelper.AsignarAlumnoMateria( "Jorge Alejandro Ledezma", "Desarrollo en Android" );
        baseDatosHelper.AsignarAlumnoMateria( "Josúe Benjamín Rangel", "Desarrollo en Android" );
        baseDatosHelper.AsignarAlumnoMateria( "Arturo Fernández Alvaréz", "Tópicos Avanzados de Programación"  );
        baseDatosHelper.AsignarAlumnoMateria( "Owen Ortega Flores" , "Tópicos Avanzados de Programación" );
        baseDatosHelper.AsignarAlumnoMateria( "Josúe Benjamín Rangel", "Tópicos Avanzados de Programación");
        baseDatosHelper.AsignarAlumnoMateria( "Josúe Benjamín Rangel", "Lenguajes y Autómatas II" );
        baseDatosHelper.AsignarAlumnoMateria( "José Eduardo Espino", "Lenguajes y Autómatas II"  );
        baseDatosHelper.AsignarAlumnoMateria("Carlos Castorena" , "Lenguajes y Autómatas II");
        baseDatosHelper.AsignarAlumnoMateria( "Héctor Manuel Chavez ", "Lenguajes y Autómatas II" );
        baseDatosHelper.AsignarAlumnoMateria( "Eduardo Ivan Guerrero", "Lenguajes y Autómatas II"  );
        baseDatosHelper.AsignarAlumnoMateria( "Hugo René Guerra",  "Lenguajes y Autómatas II");
        baseDatosHelper.AsignarAlumnoMateria( "Alberto Daniel Mireles", "Lenguajes y Autómatas II" );
        baseDatosHelper.AsignarAlumnoMateria( "David Alejandro Prueneda" , "Lenguajes y Autómatas II" );


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


        Toast.makeText ( this, "Datos por defecto agregados, volver abrir app", Toast.LENGTH_SHORT ).show();
    }

    public  void btnBorrar ( View v ) {
        baseDatosHelper.borrarBD();
    }
}