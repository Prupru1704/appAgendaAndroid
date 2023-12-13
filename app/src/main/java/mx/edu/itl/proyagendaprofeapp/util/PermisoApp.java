
/*------------------------------------------------------------------------------------------
:*                         TECNOLOGICO NACIONAL DE MEXICO
:*                                CAMPUS LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                             DESARROLLO EN ANDROID "A"
:*
:*                   SEMESTRE: AGO-DIC/2023    HORA: 08-09 HRS
:*
:*                       Clase abstracta sobre un permiso
:*
:*  Archivo     : PermisoApp.java
:*  Autor       : David Alejandro Pruneda Meraz     19130960
:*                Carlos Castorena Lugo             19130899
:*                Owen Ortega Flores                19130953
:*                José Eduardo Espino Ramirez       19130905
:*                Arturo Fernandez Alvarez          19130910
:*
:*  Fecha       : 12/Dic/2023
:*  Compilador  : Android Studio Giraffe
:*  Descripción : Esta clase abstrae un permiso de Android sobre una aplicación
:*
:*  Ultima modif: 12/Dic/2023
:*  Fecha       Modificó            Motivo
:*==========================================================================================
:* 12/Dic/2023  Eduardo Espino      Prólogo añadido
:*------------------------------------------------------------------------------------------*/


package mx.edu.itl.proyagendaprofeapp.util;

public class PermisoApp {
    private String permiso = "";
    private String nombreCorto = "";
    private boolean obligatorio = false;
    private boolean otorgado = false;

    public PermisoApp ( String permiso, String nombreCorto, boolean obligatorio ) {
        this.permiso = permiso;
        this.nombreCorto = nombreCorto;
        this.obligatorio = obligatorio;
    }

    public String getPermiso() {
        return permiso;
    }

    public void setPermiso(String permiso) {
        this.permiso = permiso;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public boolean isObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

    public boolean isOtorgado() {
        return otorgado;
    }

    public void setOtorgado(boolean otorgado) {
        this.otorgado = otorgado;
    }
}
