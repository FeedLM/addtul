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
public class CiudadSelector extends ComboBox{
    
    Ciudad ciudad;
    Estado estado;
    
    public CiudadSelector(){
        
        ciudad = new Ciudad();
    }
    
    public void cargar(Estado aEstado){
        estado = aEstado;
        addArray(domain.Ciudad.cargarCiudades(estado));        
    }
    
    public void cargarTodos(){
        
        addArray(domain.Ciudad.cargarCiudadesTodas());        
    }
    
    public Ciudad getCiudad(){
        
        ciudad.cargarPorDescripcion(this.getSelectedItem().toString());
        return ciudad;
    }
    
    
    public void setCiudad(Estado aEstado, Ciudad aCiudad){
        
        cargar(aEstado);
        ciudad = aCiudad;        
        this.setSelectedItem(ciudad.descripcion);        
    }
    
}