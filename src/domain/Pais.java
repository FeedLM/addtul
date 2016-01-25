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
public class Pais {

    public String id_pais;
    public String descripcion;

    public void cargarPorDescripcion(String sDescripcion) {

        manejadorBD.consulta(
                "SELECT	 id_pais,   descripcion "
                + "FROM  pais "
                + "WHERE descripcion = '" + sDescripcion + "'");

        if (manejadorBD.getRowCount() > 0) {

            asignarValores();
        }
    }

    public void cargarPorId(String sId_pais) {

        manejadorBD.consulta(
                "SELECT	 id_pais, descripcion "
                + "FROM  pais "
                + "WHERE id_pais = '" + sId_pais+ "'");

        if (manejadorBD.getRowCount() > 0) {

            asignarValores();
        }
    }

    private void asignarValores() {

        id_pais = manejadorBD.getValorString(0, 0);
        descripcion = manejadorBD.getValorString(0, 1);
    }

    public static ArrayList cargarPaises() {

        ArrayList array = new ArrayList();

        array.add("");
        manejadorBD.consulta(""
                + "SELECT descripcion "
                + "FROM pais "
                + "ORDER BY descripcion");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }
        return array;
    }
}
