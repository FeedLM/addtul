/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import static gui.Desktop.manejadorBD;
import static gui.Splash.formatoDateTime;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Developer GAGS
 */
public class UnidadMedida {

    public String id_unidad;
    public String descripcion;

    public void cargarPorId(String id_unidad) {

        manejadorBD.consulta(""
                + "SELECT   id_unidad,  descripcion \n"
                + "FROM     unidades_de_medida      \n"
                + "WHERE    id_unidad   =   '" + id_unidad + "'");

        if (manejadorBD.getRowCount() > 0) {

            asignarValores();
        }
    }

    public void cargarPorDescripcion(String descripcion) {

        manejadorBD.consulta(""
                + "SELECT   id_unidad,  descripcion \n"
                + "FROM     unidades_de_medida      \n"
                + "WHERE    descripcion   =   '" + descripcion + "'");

        if (manejadorBD.getRowCount() > 0) {

            asignarValores();
        }
    }

    public void asignarValores() {

        id_unidad = manejadorBD.getValorString(0, 0);
        descripcion = manejadorBD.getValorString(0, 1);
    }

    public static ArrayList cargarUnidades() {

        ArrayList array = new ArrayList();

        array.add("");
        manejadorBD.consulta(""
                + "SELECT descripcion "
                + "FROM unidades_de_medida "
                + "ORDER BY descripcion");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }
        return array;
    }
}
