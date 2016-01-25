/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import abstractt.ComboBox;
import gui.EspecificacionesAnimal;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
//import domain.Animal.

/**
 *
 * @author Developer GAGS
 */
public class AnimalSelector extends ComboBox {

    Animal animal;

    public AnimalSelector() {

        /**
         * 2015-09-22
         *
         * Al dar doble Click sobre cualquier animal selector abrira su kardex
         *
         * Falta ponerle el parent si se va a usar el baston y/o bascula
         *
         */
        Component[] comps = getComponents();
        for (int i = 0; i < comps.length; i++) {
            comps[i].addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent me) {

                    if (me.getClickCount() == 2) {

                        if (especificacionesAnimal != null) {

                            especificacionesAnimal.dispose();
                        }

                        especificacionesAnimal = new EspecificacionesAnimal(null);

                        animal = getAnimal();

                        if (!animal.id_animal.equals("")) {

                            especificacionesAnimal.setId_animal(animal.id_animal);
                            especificacionesAnimal.setVisible(true);
                        }
                    }
                }
            });
        }
    }

    public void cargararete_visuals() {

        addArray(domain.Animal.cargararete_visuals());
    }

    public void cargararete_visuals_2() {

        addArray(domain.Animal.cargararete_visuals_2());
    }

    public void cargararete_visualshembrasSinEmparejar() {

        addArray(domain.Animal.cargararete_visualshembrasSinEmparejar());
    }

    public void cargararete_visualshembrasEmparejadas() {

        addArray(domain.Animal.cargararete_visualshembrasEmparejadas());
    }

    public void cargarTagsIdsSementales() {

        addArray(domain.Animal.cargararete_visualsSementales());
    }

    public Animal getAnimal() {

        animal = new Animal();
        animal.cargarPorAreteVisual(this.getSelectedItem().toString(), "A");
        return animal;
    }

    public void setAnimal(Animal Aanimal) {

        if (Aanimal != null) {

            animal = Aanimal;
            this.setSelectedItem(animal.arete_visual);
        }
    }

    public EspecificacionesAnimal especificacionesAnimal;
}
