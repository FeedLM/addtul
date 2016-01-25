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
public class RecuadroLogo extends JLabel{

    public RecuadroLogo(){
        
    }   
    
    public void cargar(Dimension tamaño){
            
        setLocation(50,50);
        
        this.setSize(tamaño.width-100, 200);
        
     //   setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/backgroudbeige.png")));
     
        ImageIcon fot  = (ImageIcon) new javax.swing.ImageIcon(getClass().getResource("/resources/barra superior.png"));
        Icon icono = new ImageIcon(fot.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT));
        setIcon(icono);               
    }
}