/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abstractt;

import java.awt.Component;
import java.awt.event.ActionListener;
import javax.swing.ComboBoxEditor;
import javax.swing.JButton;

/**
 *
 * @author Developer GAGS
 */
public class EditorComboBox implements ComboBoxEditor {

    final protected JButton editor;

    public EditorComboBox() {

        editor = new JButton("");
        editor.setFont(FormatoControles.Fuente1);
        editor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
//        editor.setBackground(FormatoControles.color1);
//        editor.setForeground(FormatoControles.color2);

    }

    @Override
    public Component getEditorComponent() {
        return editor;
    }

    @Override
    public void setItem(Object anObject) {
        editor.setFont(FormatoControles.Fuente1);
        editor.setBackground(FormatoControles.color1);
        editor.setForeground(FormatoControles.color2);
    }

    @Override
    public Object getItem() {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return null;
    }

    @Override
    public void selectAll() {
     //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addActionListener(ActionListener l) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeActionListener(ActionListener l) {
     //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
