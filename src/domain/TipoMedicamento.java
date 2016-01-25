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
public class TipoMedicamento {

    public String id_tipo_medicamento;
    public String descripcion;

    public TipoMedicamento() {
        
        id_tipo_medicamento = "";
        descripcion =   "";
    }
    
    public void cargarPorId(String id_tipo_medicamento) {

        manejadorBD.consulta(""
                + "SELECT id_tipo_medicamento, descripcion "
                + "FROM tipo_medicamento "
                + "WHERE id_tipo_medicamento = " + id_tipo_medicamento);

        if (manejadorBD.getRowCount() > 0) {
            asignarValores();
        }
    }

    public void cargarPorDescripcion(String descripcion) {

        manejadorBD.consulta(""
                + "SELECT id_tipo_medicamento, descripcion "
                + "FROM tipo_medicamento "
                + "WHERE descripcion = '" + descripcion + "'");

        if (manejadorBD.getRowCount() > 0) {
            asignarValores();
        }
    }

    private void asignarValores() {

        id_tipo_medicamento = manejadorBD.getValorString(0, 0);
        descripcion = manejadorBD.getValorString(0, 1);
    }

    public static ArrayList cargarTipoMedicamento() {

        ArrayList array = new ArrayList();

        //  corralSelector.removeAllItems();
        array.add("");
        manejadorBD.consulta(""
                + "SELECT descripcion "
                + "FROM tipo_medicamento "
                + "ORDER BY descripcion");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }

        return array;
    }

    public String toString() {

        String salida;

        salida = "{id_tipo_medicamento=" + id_tipo_medicamento + ","
                + " descripcion='" + descripcion + "'}";

        return salida;
    }
}
