/*------------------------------------------------------------------------------------------
:*                         TECNOLOGICO NACIONAL DE MEXICO
:*                                CAMPUS LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                             DESARROLLO EN ANDROID "A"
:*
:*                   SEMESTRE: AGO-DIC/2023    HORA: 08-09 HRS
:*
:*                  Activity que presenta la pantalla de inicio
:*
:*  Archivo     : InicioActivity.java
:*  Autor       : David Alejandro Pruneda Meraz     19130960
:*                Carlos Castorena Lugo             19130899
:*                Owen Ortega Flores                19130953
:*                José Eduardo Espino Ramírez       19130905
:*                Arturo Fernández Álvarez          19130910
:*
:*  Fecha       : 06/Dic/2023
:*  Compilador  : Android Studio Giraffe
:*  Descripción : Dentro de esta clase se encuentran los botones
:*                principales, agregar datos, eliminar info,
:*                lista de materias y el info de acerca de...
:*
:*  Ultima modif: 12/Dic/2023
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:* 07/Dic/2023  Arturo Fernández	Creación del layout de inicio con activity
:* 08/Dic/2023  David Pruneda 	    Finalización de la implementación del activity
:* 08/Dic/2023  Carlos Castorena	Creación del prólogo
:* 09/Dic/2023  Eduardo Espino	    Corrección de detalles del prólogo
:* 11/Dic/2023  Eduardo Espino      Botón para  agregar datos por defecto eliminado
:* 11/Dic/2023  Eduardo Espino      Despligue de layout que indica como agregar datos
:* 11/Dic/2023  Eduardo Espino      Implementación de Alert Dialog para eliminación de la BD
:* 11/Dic/2023  Eduardo Espino      Checador de permisos agregado
:* 12/Dic/2023  Carlos Castorena    Prólogo Modificado
:*------------------------------------------------------------------------------------------*/


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
    }

    public  void btnIrLista ( View v ) {
        Intent intent = new Intent( InicioActivity.this, MainActivity.class );
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