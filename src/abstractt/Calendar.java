/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abstractt;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Font;
import java.util.Date;

/**
 *
 * @author González Silva Gilberto Adan
 */
public class Calendar extends JDateChooser {
    
    public Calendar(){
        
        JTextFieldDateEditor dateEditor;
        dateEditor = (JTextFieldDateEditor)getComponent(1);
        dateEditor.setHorizontalAlignment(JTextFieldDateEditor.CENTER);
        this.setFont(new Font("Calibri", Font.PLAIN, 12));
        this.setDate(new Date());
        
    }
}