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
public class Estado {

    public String id_estado;
    public Pais pais;
    public String descripcion;
    
    public Estado(){
        
        pais = new Pais();
    }
    
     public void cargarPorDescripcion(String sDescripcion) {

        manejadorBD.consulta(
                "SELECT	 id_estado,             id_pais, "
                + "       descripcion "
                + "FROM  estado "
                + "WHERE descripcion = '" + sDescripcion+"'");

        if (manejadorBD.getRowCount() > 0) {

            asignarValores();
        }
    }

    public void cargarPorId(String sId_estado) {

        manejadorBD.consulta(
                "SELECT	 id_estado,             id_pais, "
                + "       descripcion "
                + "FROM  estado "
                + "WHERE id_estado = '" + sId_estado+ "'");

        if (manejadorBD.getRowCount() > 0) {

            asignarValores();
        }
    }

    private void asignarValores() {
               
        String id_pais;
        
        id_estado = manejadorBD.getValorString(0, 0);
        id_pais = manejadorBD.getValorString(0, 1);
        descripcion = manejadorBD.getValorString(0, 2);                       
        
        pais.cargarPorId(id_pais);        
    }
    
    public static ArrayList cargarEstados() {

        ArrayList array = new ArrayList();

        array.add("");
        manejadorBD.consulta("SELECT descripcion "
                + "FROM estado "
                + "ORDER BY descripcion");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }
        return array;
    }
}
