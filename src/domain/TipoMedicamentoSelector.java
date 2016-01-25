/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import abstractt.ComboBox;
import static domain.TipoMedicamento.cargarTipoMedicamento;


/**
 *
 * @author Developer GAGS
 */
public class TipoMedicamentoSelector  extends ComboBox{

    private TipoMedicamento tipoMedicamento;
    
     public TipoMedicamentoSelector(){
        
    }
    
    public void cargar(){
        
        addArray(cargarTipoMedicamento());        
    }   
    
    public TipoMedicamento getTipoMedicamento(){
        
        tipoMedicamento = new TipoMedicamento();
        
        tipoMedicamento.cargarPorDescripcion(this.getSelectedItem().toString());
        
        return tipoMedicamento;
    }   
    
 
}