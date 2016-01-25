/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abstractt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Developer GAGS
 */
public class Fondo2 extends JLabel {

    public Fondo2() {

    }

    public void cargar(Dimension tamaño) {

     //   setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/backgroudbeige.png")));
        
        tamaño = new Dimension(tamaño.width - 10, tamaño.height - 40 );
        
        System.out.println(tamaño.width+", "+ tamaño.height);
        
        setLocation(0, 0);
        setSize(tamaño);
        this.setText("");

        ImageIcon fot = (ImageIcon) new javax.swing.ImageIcon(getClass().getResource("/resources/background.png"));
        Icon icono = new ImageIcon(fot.getImage().getScaledInstance(tamaño.width, tamaño.height, Image.SCALE_DEFAULT));
        setIcon(icono);
    }
}
