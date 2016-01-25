/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import abstractt.ComboBox;
import static domain.TipoGanado.cargarTipoGanado;

/**
 *
 * @author Developer GAGS
 */
public class TipoGanadoSelector extends ComboBox{

    private TipoGanado tipoGanado;
    
     public TipoGanadoSelector(){
        
    }
    
    public void cargar(){
        
        addArray(cargarTipoGanado());        
    }   
    
    public TipoGanado getTipoGanado(){
        
        tipoGanado = new TipoGanado();
        
        tipoGanado.cargarPorDescripcion(this.getSelectedItem().toString());
        
        return tipoGanado;
    }   
    
    
}