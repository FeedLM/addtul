/*
 * ComboBox.java
 *
 * Created on 6 de mayo de 2008, 09:20 AM
 *
 */
package abstractt;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author Gilberto Adan Gonzalez Silva
 */
public class ComboBox extends JComboBox {

    /**
     * Creates a new instance of ComboBox
     */
    public ComboBox() {

        editor = (JTextField) this.getEditor().getEditorComponent();

       // getEditor().getEditorComponent().setBackground(Color.YELLOW);
        // ((JTextField) getEditor().getEditorComponent()).setBackground(Color.YELLOW);
        editor.setFont(FormatoControles.Fuente1);

        editor.setBackground(Color.white);
        editor.setForeground(FormatoControles.color1);

        editor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                editorKeyReleased(evt);
            }

            public void keyPressed(java.awt.event.KeyEvent evt) {
                editorKeyPressed(evt);
            }
        });

        editor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                editorFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                editorFocusLost(evt);
            }
        });

        editor.setHorizontalAlignment(JTextField.CENTER);
        setEditable(true);
        // setFont(new java.awt.Font("Calibri", 0, 12));

        setFont(FormatoControles.Fuente1);
        setBackground(Color.WHITE);
        setForeground(FormatoControles.color1);

        ((JComponent) getEditor().getEditorComponent()).setOpaque(true);
    }

    public boolean vacio() {

        if (getSelectedIndex() == -1) {

            return true;
        } else {

            return false;
        }
    }

    public void addArray(ArrayList array) {

        this.removeAllItems();

        //   Dimension d;      
        //  d = this.getSize();  
        //  System.out.println(d.toString());
        for (int i = 0; i < array.size(); i++) {

            addItem(array.get(i));
        }

        // this.setSize(d);
    }

    /**
     * pasa el foco al siguiente componente
     */
    public void añadirActionPerformed() {

        editor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editorActionPerformed(evt);
            }
        });
    }

    private void editorActionPerformed(java.awt.event.ActionEvent evt) {

        editor.transferFocus();
    }

    private void pasaFoco() {

        setSelectedItem(editor.getText());
        
         String seleccion = editor.getText();

        if (!estaEnLista(seleccion) && !valor_nuevo && this.getItemCount() > 0) {

            editor.setText(getItemAt(0).toString());
            javax.swing.JOptionPane.showMessageDialog(null, "El texto que introdujo no pertence a la lista de valores".toUpperCase());
            editor.transferFocus();
        }
        setSelectedItem(editor.getText());
    }

    private boolean estaEnLista(String seleccion) {

        String campo;
        for (int n = 0; n < this.getItemCount(); n++) {

            campo = getItemAt(n).toString();
            if (seleccion.equals(campo)) {

                return true;
            }
        }
        return false;
    }

    private void editorFocusGained(java.awt.event.FocusEvent evt) {

        this.seleccionarTexto(editor);
    }

    private void editorFocusLost(java.awt.event.FocusEvent evt) {

        // if (ActionTranferfocus) {
        pasaFoco();
        // }
    }

    /**
     * Dado un TextFiled el texto se muestra seleccionado para su edicion rapida
     */
    private String seleccionarTexto(javax.swing.JTextField tf) {

        String texto = tf.getText();
        tf.select(0, texto.length());
        transfiereFoco = false;
        return texto;
    }

    /**
     *
     */
    public void actualiza(String campo) {

        int filas = getItemCount();
        String ca;
        for (int i = 0; i < filas; i++) {

            ca = getItemAt(i).toString();
            if (campo.equals(ca)) {

                setSelectedItem(campo);
                return;
            }
        }

        setSelectedIndex(0);
    }

    /**
     */
    private void editorKeyReleased(java.awt.event.KeyEvent e) {

        int m = 0;
        int tecla = e.getKeyCode();

        switch (tecla) {
            //enter;
            case 10:
                pasaFoco();
                break;
            //back space
            case 8:
                break;
            case KeyEvent.VK_SHIFT:
                break;
            case KeyEvent.VK_CAPS_LOCK:
                break;
            case 38:
                editor.select(0, editor.getText().length());
                break;
            case 40:
                editor.select(0, editor.getText().length());
                break;
            case 115:
                editor.select(0, editor.getText().length());
                break;

            default:
                String search = editor.getText();
                Boolean found = false;
                m = 0;
                int tam = search.length();

                for (int n = 0; n < this.getItemCount(); n++) {
                    if (!found) {

                        String s = this.getItemAt(n).toString().toLowerCase();
                        try {
                            if (s.startsWith(search.toLowerCase())) {

                                found = true;
                                m = n;

                                editor.setText(this.getItemAt(m).toString());
                                editor.select(tam, editor.getText().length());
                            }
                        } catch (Exception ex) {
                        }
                    }
                }
                break;
        }
    }

    /**
     */
    private void editorKeyPressed(java.awt.event.KeyEvent e) {

        int tecla = e.getKeyCode();
        switch (tecla) {
            //enter;
            case 10:
                this.transferFocus();
                pasaFoco();
                break;
            case 37:
                this.transferFocusBackward();
                break;
            case 39:
                this.transferFocus();
                break;
        }
    }

    /**
     */
    public boolean tieneAlgo() {

        String texto = editor.getText();

        StringTokenizer st = new StringTokenizer(texto);

        if (st.countTokens() > 0) {
            return true;
        }
        return false;
    }

    /**
     */
    public void ordenar() {

        java.util.LinkedList arreglo = new java.util.LinkedList();

        int tamaño = this.getItemCount();

        for (int i = 0; i < tamaño; i++) {

            arreglo.add(getItemAt(i));
        }

        removeAllItems();

        java.util.Collections.sort(arreglo);

        for (int i = 0; i < tamaño; i++) {

            addItem(arreglo.get(i).toString());
        }
    }

    /**
     */
    public String getText() {

        return editor.getText();
    }

    /**
     */
    public JTextField getEditor2() {

        return editor;
    }

    /**
     */
    public int getSelectedInt() {

        return Integer.parseInt(getSelectedItem().toString());
    }

    private JTextField editor;
    private boolean transfiereFoco;
    public boolean valor_nuevo;
    public boolean ActionTranferfocus;
}
