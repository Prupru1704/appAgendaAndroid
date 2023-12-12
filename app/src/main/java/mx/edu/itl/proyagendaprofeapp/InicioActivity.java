package mx.edu.itl.proyagendaprofeapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import mx.edu.itl.proyagendaprofeapp.util.ChecadorDePermisos;
import mx.edu.itl.proyagendaprofeapp.util.PermisoApp;

public class InicioActivity extends AppCompatActivity {
    BDHelper baseDatosHelper;

    // Permisos
    // Arreglo de permisos
    private PermisoApp permisos [] = new PermisoApp[] {
            new PermisoApp( Manifest.permission.WRITE_EXTERNAL_STORAGE, "Almacenamiento", true ),
            new PermisoApp( Manifest.permission.READ_EXTERNAL_STORAGE, "Almacenamiento", true ),
            new PermisoApp( Manifest.permission.MANAGE_EXTERNAL_STORAGE, "Almacenamiento", true )
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        baseDatosHelper = new BDHelper ( this );
        ChecadorDePermisos.checarPermisos ( this, permisos );
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
        Intent intent = new Intent(  InicioActivity.this, MainActivity.class );
        startActivity ( intent );
    }

    public  void btnBorrar ( View v ) {
        AlertDialog.Builder builder = new AlertDialog.Builder ( this );
        builder.setTitle ( "Eliminar Base de Datos" )
        .setMessage ( "¿Está seguro de eliminar todos los datos referente a materias, tareas y alumnos?" )
        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                baseDatosHelper.borrarBD();
                Toast.makeText( InicioActivity.this, "Datos eliminados, inserte nuevos", Toast.LENGTH_SHORT).show();
            }
        })
        .setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        })
        .setCancelable ( false )
        .create()
        .show();
    }

    public void btnAcercaDe ( View v ) {
        setContentView ( R.layout.acercade_layout );
    }

    public void btnVolverMain( View v ){
        setContentView ( R.layout.activity_inicio );
    }

    public void btnFormato ( View v ) { setContentView ( R.layout.formato_layout ); }
}