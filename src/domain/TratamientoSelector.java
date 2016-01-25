/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import abstractt.ComboBox;
import static domain.Tratamiento.cargarCodigoTratamiento;
import static domain.Tratamiento.cargarTratamientos;

/**
 *
 * @author Developer GAGS
 */
public class TratamientoSelector extends ComboBox {

    private Tratamiento tratamiento;
    
    public TratamientoSelector() {

    }

    public void cargar() {

        addArray(cargarTratamientos());
    }

    public void cargar2() {

        addArray(cargarCodigoTratamiento());
    }

    public Tratamiento getTratamientoNombre() {

        tratamiento = new Tratamiento();

        tratamiento.cargarPorNombre(this.getSelectedItem().toString());

        return tratamiento;
    }
    
     public Tratamiento getTratamientoCodigo() {

        tratamiento = new Tratamiento();

        tratamiento.cargarPorCodigo(this.getSelectedItem().toString());

        return tratamiento;
    }

}
