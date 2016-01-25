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
public class RanchoSelector extends ComboBox{
    
    public RanchoSelector(){
        
    }
    
    public void cargar(){
        
        addArray(domain.Rancho.cargarRanchos());        
    }
}