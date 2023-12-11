package mx.edu.itl.proyagendaprofeapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
/*
        baseDatosHelper.insertarMaterias ( "Desarrollo en Android", R.drawable.android );
        baseDatosHelper.insertarAlumno ( "19130905", "José Eduardo Espino" );
        baseDatosHelper.insertarAlumno ( "19130899", "Carlos Castorena Lugo" );
        baseDatosHelper.AsignarAlumnoMateria( "Carlos Castorena Lugo", "Desarrollo en Android" );
        baseDatosHelper.AsignarAlumnoMateria( "José Eduardo Espino", "Desarrollo en Android" );
        baseDatosHelper.insertarTarea ( "appVideoView", "Desarrollo en Android" );
        baseDatosHelper.insertarTarea ( "appReproducciónAudio", "Desarrollo en Android" );*/
    }

    public  void btnIrLista ( View v ) {
        Intent intent = new Intent( InicioActivity.this, MainActivity.class );
        startActivity ( intent );
    }

    public  void btnBorrar ( View v ) {
        baseDatosHelper.borrarBD();
        Toast.makeText( this, "Datos eliminados, inserte nuevos", Toast.LENGTH_SHORT).show();
    }

    public void btnAcercaDe ( View v ) {
        setContentView ( R.layout.acercade_layout );
    }

    public void btnVolverMain( View v ){
        setContentView ( R.layout.activity_inicio );
    }

    public void btnFormato ( View v ) { setContentView ( R.layout.formato_layout ); }
}