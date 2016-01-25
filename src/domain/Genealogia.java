/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import static gui.Desktop.manejadorBD;

/**
 *
 * @author Developer GAGS
 */
public class Genealogia {

    String id_animal;
    public Animal madre;
    public Animal padre;

    public Genealogia() {

        id_animal = "";
      //  madre = new Animal();
      //  padre = new Animal();
    }

    public void cargarGenealogia(String animal) {

        id_animal = animal;       
        
        manejadorBD.consulta(""
                + "SELECT id_madre, id_padre\n"
                + "FROM   genealogia\n"
                + "WHERE  id_animal = '" + id_animal + "'");

        if (manejadorBD.getRowCount() > 0) {
            asignarValores();
        }
    }
    
    public void asignarValores(){
        
        String id_madre, id_padre;
        
        madre = new Animal();
        padre = new Animal();
        
        id_madre = manejadorBD.getValorString(0, 0);
        id_padre = manejadorBD.getValorString(0, 1);
        
        madre.cargarPorId(id_madre);
        padre.cargarPorId(id_padre);                
    }

}
