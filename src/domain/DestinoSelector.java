/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import abstractt.ComboBox;
import static domain.DestinoVenta.cargarDestinoVenta;

/**
 *
 * @author Developer GAGS
 */
public class DestinoSelector extends ComboBox{

    public DestinoSelector(){
        
    }
    
    public void cargar(){
        
        addArray(cargarDestinoVenta());        
    }    
}
