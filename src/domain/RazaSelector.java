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
public class RazaSelector extends ComboBox{
    
    public RazaSelector(){
        
    }
    
    public void cargarTodas(){
        
        addArray(domain.Raza.cargarRazasTodas());        
    }
    
    public void cargarSeleccionar(){
        
        addArray(domain.Raza.cargarRazasSeleccionar());        
    }
    
    public Raza razaSeleccionada(){
        
        Raza raza;
        raza = new Raza();
        
        raza.cargarPorDescripcion(this.getSelectedItem().toString());
        
        return raza;        
    }
}