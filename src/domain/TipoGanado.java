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
public class TipoGanado {

    public String id_tipo_ganado;
    public String descripcion;

    public TipoGanado() {
        id_tipo_ganado = "";
        descripcion = "";
    }

    public void cargarPorId(String id_tipo_ganado) {

        manejadorBD.consulta(""
                + "SELECT id_tipo_ganado, descripcion "
                + "FROM tipo_ganado "
                + "WHERE id_tipo_ganado = " + id_tipo_ganado);

        if (manejadorBD.getRowCount() > 0) {
            asignarValores();
        }
    }

    public void cargarPorDescripcion(String descripcion) {

        manejadorBD.consulta(""
                + "SELECT id_tipo_ganado, descripcion "
                + "FROM tipo_ganado "
                + "WHERE descripcion = '" + descripcion + "'");

        if (manejadorBD.getRowCount() > 0) {
            asignarValores();
        }
    }

    private void asignarValores() {

        id_tipo_ganado = manejadorBD.getValorString(0, 0);
        descripcion = manejadorBD.getValorString(0, 1);
    }

    public static ArrayList cargarTipoGanado() {

        ArrayList array = new ArrayList();

        //  corralSelector.removeAllItems();
        array.add("");
        manejadorBD.consulta(""
                + "SELECT descripcion "
                + "FROM tipo_ganado ");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }

        return array;
    }
    
    public String toString() {

        String salida;

        salida = "{id_tipo_ganado=" + id_tipo_ganado + ","
                + " descripcion='" + descripcion + "'}";

        return salida;
    }
}
