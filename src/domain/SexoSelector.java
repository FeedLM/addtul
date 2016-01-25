/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import abstractt.ComboBox;
//import domain.Animal.

/**
 *
 * @author Developer GAGS
 */
public class SexoSelector extends ComboBox{
    
    Sexo sexo;
    
    public SexoSelector(){
        
       
    }
    
    public void cargarTodos(){
        
        addArray(domain.Sexo.cargarSexoTodos());        
    }
    
    public void cargar(){
        
        addArray(domain.Sexo.cargarSexo());        
    }
    
    public Sexo getSexo(){
        
         sexo = new Sexo();
         //if (this.getSelectedItem()!= null) {
            sexo.cargarPorDescripcion(this.getSelectedItem().toString());
      /*  }else{
             sexo.descripcion="";
         }*/
             
         
         return sexo;
    }
}