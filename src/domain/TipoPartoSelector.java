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
public class TipoPartoSelector extends ComboBox {

    TipoParto tipo_parto;

    public TipoPartoSelector() {

       // tipo_parto = new TipoParto();
    }

    public void cargar() {

        addArray(domain.TipoParto.cargarTiposParto());
    }

    public TipoParto getTipoParto() {

        tipo_parto = new TipoParto();
        try {
            tipo_parto.cargarPorDescripcion(this.getSelectedItem().toString());

        } catch (Exception e) {

        }
        return tipo_parto;
    }
    
    public void setTipoParto(TipoParto aTipoParto){
        
        tipo_parto = aTipoParto;
        
        setSelectedItem(tipo_parto.descripcion);
    }

}
