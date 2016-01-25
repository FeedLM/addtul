/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import abstractt.ComboBox;

/**
 *
 * @author Marco
 */
public class LoteSelector  extends ComboBox{
    
    public LoteSelector(){
        
    }
    
    public void cargar(){
        
        addArray(domain.Recepcion.cargarLotes());        
    }
    
    public void cargarTodos(){
        addArray(domain.Recepcion.cargarLotesTodos());
    }
}