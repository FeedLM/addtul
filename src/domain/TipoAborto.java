/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import static gui.Desktop.manejadorBD;
import java.util.ArrayList;

/**
 *
 * @author Developer GAGS
 */
public class TipoAborto {

    public String id_tipo_aborto;
    public String descripcion;
    
    public TipoAborto(){
        id_tipo_aborto = "";
        descripcion = "";
    }

    public void cargarPorDescripcion(String sDescripcion) {

        manejadorBD.consulta(
                "SELECT	 id_tipo_aborto,   descripcion "
                + "FROM  tipo_aborto "
                + "WHERE descripcion = '" + sDescripcion + "'");

        if (manejadorBD.getRowCount() > 0) {

            asignarValores();
        }
    }

    public void cargarPorId(String sId_tipo_aborto) {

        manejadorBD.consulta(
                "SELECT	 id_tipo_aborto, descripcion "
                + "FROM  tipo_aborto "
                + "WHERE id_tipo_aborto = '" + sId_tipo_aborto+ "'");

        if (manejadorBD.getRowCount() > 0) {

            asignarValores();
        }
    }

    private void asignarValores() {

        id_tipo_aborto = manejadorBD.getValorString(0, 0);
        descripcion = manejadorBD.getValorString(0, 1);
    }
    
    public static ArrayList cargarTiposAborto() {

        ArrayList array = new ArrayList();

        array.add("");
        manejadorBD.consulta(""
                + "SELECT descripcion \n"
                + "FROM   tipo_aborto");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }
        return array;
    }

    
}
