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
public class DestinoVenta {

    public static ArrayList cargarDestinoVenta() {

        ArrayList array = new ArrayList();

        manejadorBD.consulta(""
                + "SELECT   descripcion "
                + "FROM     destino "
                + "ORDER BY descripcion ");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }

        return array;
    }

    public static Integer idDestinoDescripcion(String descripcion) {
        
        manejadorBD.consulta(""
                + "SELECT   id_destino  "
                + "FROM     destino "
                + "WHERE     descripcion    =   '" + descripcion + "'");

        if (manejadorBD.getRowCount() > 0) {
            
            return manejadorBD.getValorInt(0, 0);
        }
        
        return 0;
    }
}
