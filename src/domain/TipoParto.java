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
public class TipoParto {

    public String id_tipo_parto;
    public String descripcion;
    
    public TipoParto(){
        id_tipo_parto = "";
        descripcion = "";
    }

    public void cargarPorDescripcion(String sDescripcion) {

        manejadorBD.consulta(
                "SELECT	 id_tipo_parto,   descripcion "
                + "FROM  tipo_parto "
                + "WHERE descripcion = '" + sDescripcion + "'");

        if (manejadorBD.getRowCount() > 0) {

            asignarValores();
        }
    }

    public void cargarPorId(String sId_tipo_parto) {

        manejadorBD.consulta(
                "SELECT	 id_tipo_parto, descripcion "
                + "FROM  tipo_parto "
                + "WHERE id_tipo_parto = '" + sId_tipo_parto+ "'");

        if (manejadorBD.getRowCount() > 0) {

            asignarValores();
        }
    }

    private void asignarValores() {

        id_tipo_parto = manejadorBD.getValorString(0, 0);
        descripcion = manejadorBD.getValorString(0, 1);
    }
    
    public static ArrayList cargarTiposParto() {

        ArrayList array = new ArrayList();

        array.add("");
        manejadorBD.consulta(""
                + "SELECT descripcion \n"
                + "FROM   tipo_parto");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }
        return array;
    }

    
}
