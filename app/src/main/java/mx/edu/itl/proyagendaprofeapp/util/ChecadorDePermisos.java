package mx.edu.itl.proyagendaprofeapp.util;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class ChecadorDePermisos {

    public static final int CODIGO_PEDIR_PERMISOS = 8;
    public static void checarPermisos (Activity activity, PermisoApp[] permisosReq) {
        // Si el arrelo de permisos tiene algo
        if ( permisosReq == null || permisosReq.length == 0 )
            return;

        // Verificar cuales permisos están otorgados, si lo están se marcan
        for ( int i = 0; i < permisosReq.length; i++ ) {
            if ( ContextCompat.checkSelfPermission ( activity, permisosReq [ i ].getPermiso() )
                 == PackageManager.PERMISSION_GRANTED )
            {
                permisosReq [ i ].setOtorgado ( true );
            }
        }

        // Determinar la lista de permisos que no fueron otorgados
        ArrayList < String > arrTmp = new ArrayList<> ();
        for ( int i = 0; i < permisosReq.length; i++ ) {
            if ( permisosReq [ i ].isOtorgado() == false )
                arrTmp.add ( permisosReq [ i ].getPermiso() );
        }

        // Si hay permisos sin otorgar, se piden de forma explicita
        if ( arrTmp.size() > 0 ) {
            // Convertir ArrList a un array de String
            String permisosPendientes [] = new String [ arrTmp.size() ];
            arrTmp.toArray ( permisosPendientes );
            ActivityCompat.requestPermissions ( activity, permisosPendientes, CODIGO_PEDIR_PERMISOS );
        }
    }


    public static void verificarPermisosSolicitados (Activity activity,
                                                     PermisoApp[] permisosReq,
                                                     String[] permissions,
                                                     int[] grantResulst)
    {
        ArrayList < String > tmp = new ArrayList<> ();
        for ( int i = 0; i < permisosReq.length; i++ )
            tmp.add ( permisosReq [ i ].getPermiso() );

        String permisosObligatoriosNoOtorgados = "";
        if ( grantResulst.length > 0 ) {
            // Recorrer el arreglo de grantResults
            for ( int i = 0; i < grantResulst.length; i++) {
                // Buscar el permiso correspondiente
                int index = tmp.indexOf ( permissions [ i ] );

                // Si el usuario otorgo permisos, tambien se marca en el arreglo recibido
                if ( grantResulst [ i ] == PackageManager.PERMISSION_GRANTED )
                    permisosReq [ index ].setOtorgado ( true );
                else if ( permisosReq [ index ].isObligatorio() ) {
                    // Si no se otorgo un permiso, se anexa al string de no orotgados
                    permisosObligatoriosNoOtorgados += permisosReq [ index ].getNombreCorto() + ", ";
                }
            }

            // Checar si hubo permisos obligatorios que no otorgó el usuario
            if ( !permisosObligatoriosNoOtorgados.isEmpty() ) {
                alertarYsalir( activity, permisosObligatoriosNoOtorgados );
            }
        }
    }

    public static void alertarYsalir ( final Activity activity, String noOtorgados ) {
        AlertDialog.Builder builder = new AlertDialog.Builder ( activity );
        builder.setTitle ( "Permisos Requeridos" )
                .setMessage ( "Los siguiente permisos son necesarios para usar la app:\n\n"
                              + noOtorgados + "." + "\n\n" + "Otorgelos." )
                .setCancelable ( false )
                .setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        activity.finish();
                    }
                })
                .create()
                .show();
    }
}
