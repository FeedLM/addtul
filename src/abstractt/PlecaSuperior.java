/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abstractt;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Home
 */
public class PlecaSuperior extends JLabel {

    public PlecaSuperior() {

    }

    public void cargar() {

        //   setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/backgroudbeige.png")));
        this.setText("");
        ImageIcon fot = (ImageIcon) new javax.swing.ImageIcon(getClass().getResource("/resources/pleca superior.png"));
        
        //Icon icono = new ImageIcon(fot.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT));
        setIcon(fot);
    }
    
}
