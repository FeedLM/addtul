/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import gui.DialogoCiudadSelector;
import static gui.Desktop.manejadorBD;
import java.util.ArrayList;

/**
 *
 * @author Developer GAGS
 */
public class Ciudad {

    public Estado estado;
    public String id_ciudad;
    public String descripcion;

    public Ciudad() {
        limpiarVariables();
    }

    public void limpiarVariables() {

        estado = new Estado();
        id_ciudad = "";
        descripcion = "";
    }

    public void cargarPorDescripcion(String sDescripcion) {

        manejadorBD.consulta(""
                + "SELECT   id_estado,          id_ciudad, \n"
                + "         descripcion_ciudad \n"
                + "FROM     ciudad \n"
                + "WHERE    descripcion_ciudad = '" + sDescripcion + "'");

        if (manejadorBD.getRowCount() > 0) {

            asignarValores();
        }else{
            limpiarVariables();
        }
    }

    public void cargarPorId(String ciudad, Estado estado) {

        manejadorBD.consulta(""
                + "SELECT   id_estado,          id_ciudad, \n"
                + "         descripcion_ciudad \n"
                + "FROM     ciudad \n"
                + "WHERE    id_estado   =   '" + estado.id_estado + "'  \n"
                + "AND      id_ciudad = '" + ciudad + "'");

        if (manejadorBD.getRowCount() > 0) {

            asignarValores();
        }else{
            limpiarVariables();
        }
    }

    private void asignarValores() {

        String id_estado;
        
        id_estado = manejadorBD.getValorString(0, 0);
        id_ciudad = manejadorBD.getValorString(0, 1);
        descripcion = manejadorBD.getValorString(0, 2);
        
        estado.cargarPorId(id_estado);
    }

    public static ArrayList cargarCiudades(Estado estado) {

        ArrayList array = new ArrayList();

        array.add("");

        manejadorBD.consulta(""
                + "SELECT   descripcion_ciudad  \n"
                + "FROM     ciudad  \n"
                + "WHERE    ciudad.id_estado = '" + estado.id_estado + "'  \n"
                + "ORDER BY descripcion_ciudad");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }
        return array;
    }

    public static ArrayList cargarCiudadesTodas() {

        ArrayList array = new ArrayList();

        array.add("");

        manejadorBD.consulta(""
                + "SELECT   descripcion_ciudad \n"
                + "FROM     ciudad             \n"
                + "ORDER BY descripcion_ciudad");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }
        return array;
    }
    
     public Ciudad mostrarDialogoCiudad(Estado estado ) {

        DialogoCiudadSelector dialogoCiudadSelector;
        dialogoCiudadSelector = new DialogoCiudadSelector(null, estado, this);
        dialogoCiudadSelector.setVisible(true);
        return dialogoCiudadSelector.getCiudad();
    }
}
