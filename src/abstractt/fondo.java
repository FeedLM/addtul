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
public class fondo extends JLabel{

    public fondo(){
        
    }   
    
    public void cargar(Dimension tamaño){
            
     //   setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/backgroudbeige.png")));
     
        setLocation(0,0);
        setSize(tamaño);  
        this.setText("");
        
        ImageIcon fot  = (ImageIcon) new javax.swing.ImageIcon(getClass().getResource("/resources/backgroudbeige.png"));
        Icon icono = new ImageIcon(fot.getImage().getScaledInstance(getWidth() + 1, getHeight() + 1, Image.SCALE_DEFAULT));
        setIcon(icono);
       
        
    }
}
