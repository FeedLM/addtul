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
public class ClaseMovimiento {

    public static ArrayList cargarClaseMovimiento() {

        ArrayList array = new ArrayList();

        manejadorBD.consulta(""
                + "SELECT   descripcion "
                + "FROM     clase_movimiento ");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }

        return array;
    }

    public static Integer idClaseDescripcion(String descripcion) {
        manejadorBD.consulta(""
                + "SELECT   id_clase_movimiento  "
                + "FROM     clase_movimiento "
                + "WHERE     descripcion    =   '" + descripcion + "'");

        if (manejadorBD.getRowCount() > 0) {

            return manejadorBD.getValorInt(0, 0);

        }
        return 0;
    }

}
