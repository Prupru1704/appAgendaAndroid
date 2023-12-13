/*------------------------------------------------------------------------------------------
:*                         TECNOLOGICO NACIONAL DE MEXICO
:*                                CAMPUS LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                             DESARROLLO EN ANDROID "A"
:*
:*                   SEMESTRE: AGO-DIC/2023    HORA: 08-09 HRS
:*
:*           Activity que presenta la pantalla de splash al lanzar la app
:*
:*  Archivo     : SplashActivity.java
:*  Autor       : David Alejandro Pruneda Meraz     19130960
:*                Carlos Castorena Lugo             19130899
:*                Owen Ortega Flores                19130953
:*                Jose Eduardo Espino Ramirez       19130905
:*                Arturo Fernandez Alvarez          19130910
:*
:*  Fecha       : 06/Dic/2023
:*  Compilador  : Android Studio Giraffe
:*  Descripción : Activity que solo muesstra una pantalla de carga o splash
:*
:*  Ultima modif: 09/Dic/2023
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:* 07/Dic/2023  Owen Ortega		Splash creado
:* 08/Dic/2023  Owen Ortega		Prólogo para la documentación
:* 09/Dic/2023	Eduardo Espino	Corrección de detalles para el prólogo
:*------------------------------------------------------------------------------------------*/


package mx.edu.itl.proyagendaprofeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView ( R.layout.activity_splash );

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent ( SplashActivity.this, InicioActivity.class );
                startActivity ( intent );
                finish ();
            }
        }, 2000);
    }
}