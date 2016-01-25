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
public class Color {

    public Integer id_color;
    public String descripcion;   
    
     public void cargarPorId(Integer a_id_color) {

        manejadorBD.consulta(
                "Select descripcion "
                + "from color_arete "
                + "where id_color   =    " +a_id_color);

        if (manejadorBD.getColumnCount() > 0) {
  
            descripcion = manejadorBD.getValorString(0, 0);
            id_color = a_id_color;
        }else{
            descripcion = "";
            id_color = 0;
        }
    }
    
    public void cargarPorDescripcion(String a_descripcion) {

        manejadorBD.consulta(
                "Select id_color "
                + "from color_arete "
                + "where descripcion  =    '" + a_descripcion + "'");

        if (manejadorBD.getColumnCount() > 0) {

            descripcion = a_descripcion;
            id_color = manejadorBD.getValorInt(0, 0);
        }else{
            descripcion = "";
            id_color = 0;
        }
    }

    public String toString(){
        
        String salida;
        
        salida = "{id_color = "+id_color+","
                + " descripcion = '"+descripcion+"'}";
        
        return salida;
    }
    
    
    public static ArrayList cargarColores() {

        ArrayList array = new ArrayList();

        array.add("");
        manejadorBD.consulta(""
                + "SELECT descripcion "
                + "FROM color_arete "
                + "ORDER BY descripcion");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }
        return array;
    }

    public static String color(Integer id_color) {

        manejadorBD.consulta(
                "Select descripcion "
                + "from color_arete "
                + "where id_color  =   " + id_color);

        if (manejadorBD.getColumnCount() > 0) {

            return manejadorBD.getValorString(0, 0);
        }

        return "";
    }

    public static Integer color(String descripcion) {

        manejadorBD.consulta(
                "Select id_color "
                + "from color_arete "
                + "where descripcion  =   '" + descripcion + "'");

        if (manejadorBD.getColumnCount() > 0) {

            return manejadorBD.getValorInt(0, 0);
        }

        return 0;
    }
}
