/*
 * textField.java
 *
 * Created on 16 de mayo de 2008, 02:40 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package abstractt;

import domain.FormatoNumero;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.StringTokenizer;

/**
 *
 * @author Administrator
 */
public class TextField extends javax.swing.JTextField {

    private String placeholder = "";

    /**
     * Creates a new instance of textField
     */
    public TextField() {

        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {

                FocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {

                FocusLost(evt);
            }
        });

        addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                ActionPerformed(evt);
            }
        });

        setFont(FormatoControles.Fuente1);
        setHorizontalAlignment(javax.swing.JTextField.CENTER);
//        setBackground(FormatoControles.color3);
//        setForeground(FormatoControles.color2);

        //     setContentAreaFilled(false);
//        this.setOpaque(true);
    }

    private void ActionPerformed(java.awt.event.ActionEvent evt) {

        transferFocus();
    }

    private void FocusGained(java.awt.event.FocusEvent evt) {
        //  System.out.println("alimento ingresado 01");
        if (isDouble) {

            //  System.out.println("alimento ingresado 02");
            this.setText(obtenerNumero(getText()));
        }

        //  System.out.println("alimento ingresado 03");
        seleccionarTexto();
    }

    private void FocusLost(java.awt.event.FocusEvent evt) {

        // System.out.println("alimento ingresado 04");
        if (isDouble) {

            //   System.out.println("alimento ingresado 05");
            if (getText().equals("")) {

                setText("0.0");
            }

            //  System.out.println("alimento ingresado 06");
            //this.setText(obtenerNumero(getText()));
        }
    }

    public boolean esNumero(char caracter) {

        if (((caracter < '0') || (caracter > '9'))) {

            return true;
        }

        return false;
    }

    /**
     * Modifica eventos del teclado para la jtextFiled para que solo permita
     * Escribir numeros con punto flotante
     */
    public void textFieldDouble() {

        addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {

                char caracter = e.getKeyChar();
                String texto = getText();
                StringTokenizer s = new StringTokenizer(texto, ".");
                String textoSeleccionado = getSelectedText();

                //*Modificacion, para que pueda empezar a escribir con un punto*///
                if (texto.length() == 0 || (textoSeleccionado != null && texto.length() == textoSeleccionado.length())) {

                    if (caracter == '.') {

                        setText("0.");
                        e.consume();
                    }
                }
                ///**///
                if (texto.length() < 1) {

                    if (((caracter < '0') || (caracter > '9')) && (caracter != KeyEvent.VK_BACK_SPACE)) {
                        e.consume();
                    }
                } else {

                    char ultimoCaracter = texto.charAt(texto.length() - 1);

                    if (s.countTokens() >= 2 || ultimoCaracter == '.') {
                        if (((caracter < '0') || (caracter > '9')) && (caracter != KeyEvent.VK_BACK_SPACE)) {
                            e.consume();
                        }
                    } else {
                        if (((caracter < '0') || (caracter > '9')) && (caracter != '.') && (caracter != KeyEvent.VK_BACK_SPACE)) {
                            e.consume();
                        }
                    }
                }
            }
        });

        isDouble = true;
    }

    private boolean isDouble = false;

    public Double getDouble() {

        try {

            String texto = obtenerNumero(getText());
            return Double.parseDouble(texto);

        } catch (NumberFormatException e) {

        }

        return 0.0;
    }

    public void setDouble(Double SDouble) {

        setText(new FormatoNumero(SDouble.toString()).convierte(SDouble));
    }

    public int getInt() {

        try {

            String texto = getText();
            return Integer.parseInt(texto);

        } catch (NumberFormatException e) {

        }

        return 0;
    }

    /**
     * Modifica eventos del teclado para la jtextFiled para que solo permita
     * Escribir letras
     */
    public void textFieldSoloLetras() {

        addKeyListener(new KeyAdapter() {

            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();
                if (((caracter < 'a') || (caracter > 'z'))
                        && (caracter != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                }
            }
        });
    }

    /**
     * Modifica eventos del teclado para la jtextFiled para que solo permita
     * Escribir numeros y letras
     */
    public void textFieldSoloNumerosYLetras() {

        addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();
                if (((caracter < '0') || (caracter > '9'))
                        && ((caracter < 'a') || (caracter > 'z'))
                        && (caracter != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                }
            }
        });
    }

    /**
     * Modifica eventos del teclado para la jtextFiled para que solo permita
     * Escribir numeros
     */
    public void textFieldSoloNumeros() {

        addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();
                if (((caracter < '0') || (caracter > '9'))
                        && (caracter != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                }
            }
        });
    }

    /**
     * Dado un TextFiled el texto se muestra seleccionado para su edicion rapida
     */
    public String seleccionarTexto() {

        select(0, getText().length());
        return getText();
    }

    public void quitarSeleccion() {

        select(getText().length() - 3, getText().length() - 1);
        //return getText();
    }

    /**
     * Si el numero tiene comas se las quita
     */
    protected String obtenerNumero(String numero) {

        StringTokenizer num = new StringTokenizer(numero, ",");

        numero = "";

        while (num.hasMoreTokens()) {
            numero += num.nextToken();
        }
        return numero;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    @Override
    public void paintComponent(final Graphics pG) {
        super.paintComponent(pG);

        if (placeholder.length() == 0 || getText().length() > 0) {
            return;
        }

        final Graphics2D g = (Graphics2D) pG;
        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(getDisabledTextColor());
        g.drawString(placeholder, getInsets().left, pG.getFontMetrics().getMaxAscent() + getInsets().top);
    }

    public void setPlaceholder(final String s) {
        placeholder = s;
    }

}
