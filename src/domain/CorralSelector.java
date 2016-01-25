/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import abstractt.ComboBox;
import static domain.Corral.cargarCorrales;
import static domain.Corral.cargarCorralesRancho;

/**
 *
 * @author Developer GAGS
 */
public class CorralSelector extends ComboBox{

    public CorralSelector(){
        
    }
    
    public void cargar(){
        
        addArray(cargarCorrales());        
    }    
    
    public void cargarPorRancho(Rancho aRancho){
        
        addArray(cargarCorralesRancho(aRancho));
    }
    
}
