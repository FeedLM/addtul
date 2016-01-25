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
public class EstadoSelector extends ComboBox {

    Estado estado;

    public EstadoSelector() {

        estado = new Estado();
    }

    public void cargar() {

        addArray(domain.Estado.cargarEstados());
    }

    public Estado getEstado() {

        estado = new Estado();
        try {
            estado.cargarPorDescripcion(this.getSelectedItem().toString());

        } catch (Exception e) {

        }
        return estado;
    }
    
    public void setEstado(Estado aEstado){
        
        estado = aEstado;
        
        setSelectedItem(estado.descripcion);
    }

}
