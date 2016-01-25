/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Imagen extends javax.swing.JPanel {

    public String imagen;

    public void setImagen(String SImagen) {

        imagen = SImagen;

        ImageIcon icon = new ImageIcon(imagen);
        JLabel label = new JLabel();
        label.setIcon(icon);
        add(label);

    }

    public Imagen() {
        this.setSize(5, 5); //se selecciona el tamaño del panel
    }

}
