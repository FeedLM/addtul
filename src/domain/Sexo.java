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
public class Sexo {

    public String id_sexo;
    public String descripcion;
    public String seleccionar;

    public void cargarPorId(String aSexo) {

        manejadorBD.consulta(""
                + "SELECT   id_sexo,    COALESCE(descripcion,''),    COALESCE(seleccionar,'') \n"
                + "FROM     sexo \n"
                + "WHERE    id_sexo   =    '" + aSexo+"'");

        if (manejadorBD.getColumnCount() > 0) {

            asignarValores();
        } else {
            id_sexo = "";
        }
    }

    public void cargarPorDescripcion(String aSexo) {

        manejadorBD.consulta(""
                + "SELECT   id_sexo,    COALESCE(descripcion,''),    COALESCE(seleccionar,'') \n"
                + "FROM     sexo \n"
                + "WHERE    descripcion   =    '" + aSexo+"'");

        if (manejadorBD.getColumnCount() > 0) {

            asignarValores();
        } else {
            id_sexo = "";
        }
    }
    
    public void asignarValores(){
        
        id_sexo =   manejadorBD.getValorString(0, 0);
        descripcion =   manejadorBD.getValorString(0, 1);
        seleccionar =   manejadorBD.getValorString(0, 2);
    }
    
    public static ArrayList cargarSexo() {

        ArrayList array = new ArrayList();

        array.add("");
        manejadorBD.consulta(""
                + "SELECT   descripcion \n"
                + "FROM     sexo \n"
                + "WHERE    seleccionar =   'S'\n"
                + "ORDER BY descripcion");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }
        return array;
    }

    public static ArrayList cargarSexoTodos() {

        ArrayList array = new ArrayList();

        array.add("");
        manejadorBD.consulta(""
                + "SELECT   descripcion \n"
                + "FROM     sexo \n"
                + "ORDER BY descripcion");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }
        return array;
    }

}
