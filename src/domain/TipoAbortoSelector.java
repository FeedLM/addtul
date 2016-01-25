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
public class TipoAbortoSelector extends ComboBox {

    TipoAborto tipo_aborto;

    public TipoAbortoSelector() {

       // tipo_aborto = new TipoAborto();
    }

    public void cargar() {

        addArray(domain.TipoAborto.cargarTiposAborto());
    }

    public TipoAborto getTipoAborto() {

        tipo_aborto = new TipoAborto();
        try {
            tipo_aborto.cargarPorDescripcion(this.getSelectedItem().toString());

        } catch (Exception e) {

        }
        return tipo_aborto;
    }
    
    public void setTipoAborto(TipoAborto aTipoAborto){
        
        tipo_aborto = aTipoAborto;
        
        setSelectedItem(tipo_aborto.descripcion);
    }

}
