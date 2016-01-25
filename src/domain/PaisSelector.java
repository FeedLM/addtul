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
public class PaisSelector extends ComboBox{
    
    Pais pais;
    
    public PaisSelector(){
        pais = new Pais();
    }
    
    public void cargar(){
        
        addArray(domain.Pais.cargarPaises());        
    }
    
    public Pais getPais(){
        
        pais.cargarPorDescripcion(this.getSelectedItem().toString());
        return pais;
    }
    
    public void setPais(Pais aPais){
        
        pais = aPais;
        
        setSelectedItem(pais.descripcion);
    }
}