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
public class ColorSelector extends ComboBox{
    
    public ColorSelector(){
        
    }
    
    public void cargar(){
        
        addArray(domain.Color.cargarColores());        
    }
}