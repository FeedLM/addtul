/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abstractt;

import javax.swing.JButton;

/**
 *
 * @author Developer GAGS
 */
public class Boton extends JButton {

    public Boton() {
        this.setBackground(FormatoControles.color1);
        this.setForeground(FormatoControles.color2);

        this.setFont(FormatoControles.Fuente1);

        setContentAreaFilled(false);
        this.setOpaque(true);
    }
    
    
}
