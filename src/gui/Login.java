/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import abstractt.FormatoControles;
import domain.Cifrado;
import static domain.Log.Log;
import domain.ManejadorBD;
import domain.Usuario;
import static gui.Desktop.manejadorBD;
import static gui.Splash.formatoDateTime;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Gilberto Gonzalez
 */
public class Login extends javax.swing.JFrame {

    boolean muestraSQL;
    public String file_properties;
    public static Properties properties;
    private String database;
    private Date fecha_instalacion;
    private Date fecha_limite;
    private Integer dias_vigencia;
    // public static SimpleDateFormat formatoDateTime;
    // public static SimpleDateFormat formatoDate;
    Cifrado cifrado;

    /**
     * Creates new form login
     */
    public Login(java.awt.Frame parent, boolean modal) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        //super(parent, modal);
        //     muestraSQL = true;

        if (aplicacion_corriendo("feedlot_manager.exe")) {

            System.out.println("feedlot_manager ya esta corriendo");
            System.exit(0);
        }
        FileInputStream file;
        String fecha;

        properties = new Properties();
        InputStream input = null;

        formatoDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatoDate = new SimpleDateFormat("yyyy-MM-dd");

        cifrado = new Cifrado("feedLotManager");

        System.out.println("IdDisco duro " + cifrado.obtenerIdDisco());

        properties = cifrado.leerProperties();

        file_properties = "feedLotManager.properties";

        database = properties.getProperty("database");

        if (properties.getProperty("muestraSQL").equals("S")) {

            muestraSQL = true;
        } else {

            muestraSQL = false;
        }

        System.out.println("database: " + database);

        /*
         if (!valida_vigencia()) {
         JOptionPane.showMessageDialog(this, "Se Termino el Periodo de Prueba");
         System.exit(0);
         return;
         }
         */
        initComponents();

        setLocationRelativeTo(null);

        setResizable(false);

        gs_mensaje = "FeedLot Manager";

        Image i = null;
        i = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/logo tru-test.png"));
        setIconImage(i);
        // System.load("C:\\FeedLot\\libSerialPort.dll");

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        jPanel2.setOpaque(false);
        /*
         Dimension pantallaTamano = jPanel1.getSize();
         jPanel2.setLocation((pantallaTamano.width / 2) - (jPanel2.getWidth() / 2), (pantallaTamano.height / 2) - (jPanel2.getHeight() / 2));                   
         fondo1.cargar(pantallaTamano);        
         this.recuadro1.cargar(pantallaTamano);                
         this.logo1.cargar();
         */

        contraseña.setBackground(FormatoControles.color3);
        contraseña.setForeground(FormatoControles.color2);

        arrangeObjects();
        //  this.boton1.cargar();
        // this.boton2.cargar();
    }

    public void arrangeObjects() {

        Dimension pantallaTamano = jPanel1.getSize();

        jPanel2.setLocation((pantallaTamano.width / 2) - (jPanel2.getWidth() / 2), (pantallaTamano.height / 2) - (jPanel2.getHeight() / 2));
        /*  
         fondo1.setLocation(0,0);
         fondo1.setSize(pantallaTamano);
         */
        fondo1.cargar(pantallaTamano);

        this.recuadro1.cargar(pantallaTamano);
        logo1.setLocation((pantallaTamano.width / 2) - (logo1.getWidth() / 2), 100);
        this.logo1.cargar();
    }

    public boolean aplicacion_corriendo(String aplicacion) {

        Integer ll_conteo = 0;

        try {

            String line;
            Process p = Runtime.getRuntime().exec("tasklist /nh /fo csv");//.exe");//  /fo csv /nh");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

            while ((line = input.readLine()) != null) {
                if (!line.trim().equals("")) {

                    // System.out.println(line);
                    // keep only the process name
                    line = line.substring(1);

                    if ((line.substring(0, line.indexOf("\"")).toString()).equals(aplicacion)) {

                        ll_conteo++;

                        if (ll_conteo > 1) {

                            return true;
                        }
                    }
                }
            }
        } catch (Exception err) {

            err.printStackTrace();
        }

        return false;
    }

    public boolean valida_vigencia() {

        Date fechaActual;
        Calendar fecha = Calendar.getInstance();
        String Sfecha;
        fechaActual = fecha.getTime();
        dias_vigencia = Integer.parseInt(properties.getProperty("dias_vigencia"));

        Sfecha = properties.getProperty("fecha_instalacion");

        if (!Sfecha.equals("")) {

            try {

                fecha_instalacion = formatoDateTime.parse(Sfecha);

            } catch (ParseException ex) {

                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        Sfecha = properties.getProperty("fecha_limite");

        if (!Sfecha.equals("")) {
            try {

                fecha_limite = formatoDateTime.parse(Sfecha);

            } catch (ParseException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (fecha_instalacion == null || fecha_limite == null) {

            Calendar calendar = Calendar.getInstance();

            fecha_instalacion = new Date();

            calendar.setTime(fecha_instalacion);

            calendar.add(Calendar.DAY_OF_YEAR, dias_vigencia);

            fecha_limite = calendar.getTime();

            formatoDateTime.format(fecha_instalacion);

            properties.setProperty("fecha_instalacion", formatoDateTime.format(fecha_instalacion));
            properties.setProperty("fecha_limite", formatoDateTime.format(fecha_limite));

            FileOutputStream fos;
            try {

                File file = new File(file_properties);
                fos = new FileOutputStream(file);

                properties.store(fos, null);

                properties.clear();
                fos.close();

            } catch (FileNotFoundException ex) {

                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {

                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }

            return true;
        }

        System.out.println("         fecha_actual: " + formatoDateTime.format(fechaActual));
        System.out.println("fecha_instalacion vpp: " + formatoDateTime.format(fecha_instalacion));
        System.out.println("     fecha_limite vpp: " + formatoDateTime.format(fecha_limite));

        if (fechaActual.after(fecha_instalacion) && fechaActual.before(fecha_limite)) {

            System.out.println("Esta en vigencia");
            return true;
        }

        System.out.println("Se Termino la vigencia");
        return false;
    }

    /**
     * @return the manejadorBD
     */
    public static ManejadorBD getManejadorBD() {
        return manejadorBD;
    }

    /**
     * @param aManejadorBD the manejadorBD to set
     */
    public static void setManejadorBD(ManejadorBD aManejadorBD) {
        manejadorBD = aManejadorBD;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        usuario = new abstractt.TextField();
        btn_aceptar = new abstractt.Boton();
        btn_registrarte = new abstractt.Boton();
        etiqueta1 = new abstractt.Etiqueta();
        contraseña = new abstractt.PasswordField();
        etiqueta3 = new abstractt.Etiqueta();
        btn_salir = new abstractt.Boton();
        logo1 = new abstractt.Logo();
        recuadro1 = new abstractt.RecuadroLogo();
        fondo1 = new abstractt.fondo();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Acceso al Sistema...");
        setUndecorated(true);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(238, 217, 170));
        jPanel1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jPanel1ComponentResized(evt);
            }
        });
        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        usuario.setText("admin");
        usuario.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usuarioActionPerformed(evt);
            }
        });
        jPanel2.add(usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, 200, 30));

        btn_aceptar.setText("Aceptar");
        btn_aceptar.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        btn_aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_aceptarActionPerformed(evt);
            }
        });
        jPanel2.add(btn_aceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 170, 150, 30));

        btn_registrarte.setText("Registrarse");
        btn_registrarte.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        btn_registrarte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_registrarteActionPerformed(evt);
            }
        });
        jPanel2.add(btn_registrarte, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 170, 150, 30));

        etiqueta1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiqueta1.setText("Usuario");
        etiqueta1.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        jPanel2.add(etiqueta1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 100, 30));

        contraseña.setText("admin");
        contraseña.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        contraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contraseñaActionPerformed(evt);
            }
        });
        jPanel2.add(contraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 100, 200, 30));

        etiqueta3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiqueta3.setText("Contraseña");
        etiqueta3.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        jPanel2.add(etiqueta3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 100, 30));

        jPanel1.add(jPanel2);
        jPanel2.setBounds(150, 130, 400, 200);

        btn_salir.setText("Salir");
        btn_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salirActionPerformed(evt);
            }
        });
        jPanel1.add(btn_salir);
        btn_salir.setBounds(1100, 680, 150, 30);

        logo1.setText("logo1");
        jPanel1.add(logo1);
        logo1.setBounds(40, 180, 300, 130);

        recuadro1.setText("recuadro1");
        jPanel1.add(recuadro1);
        recuadro1.setBounds(40, 20, 450, 200);

        fondo1.setBackground(new java.awt.Color(204, 0, 204));
        fondo1.setForeground(new java.awt.Color(51, 0, 204));
        jPanel1.add(fondo1);
        fondo1.setBounds(0, 210, 390, 100);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void botonAceptar() {
        Log("accediendo al Sistema");

        acceder();
    }

    private void jPanel1ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel1ComponentResized


    }//GEN-LAST:event_jPanel1ComponentResized

    private void btn_aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_aceptarActionPerformed
        botonAceptar();
    }//GEN-LAST:event_btn_aceptarActionPerformed

    private void btn_registrarteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_registrarteActionPerformed
        registrarse();

    }//GEN-LAST:event_btn_registrarteActionPerformed

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        arrangeObjects();
    }//GEN-LAST:event_formComponentResized

    private void usuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usuarioActionPerformed
        acceder();
    }//GEN-LAST:event_usuarioActionPerformed

    private void contraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contraseñaActionPerformed
        acceder();
    }//GEN-LAST:event_contraseñaActionPerformed

    private void btn_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btn_salirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        /*
         try {
         for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
         if ("Windows".equals(info.getName())) {
         javax.swing.UIManager.setLookAndFeel(info.getClassName());
         break;
         }
         }
         } catch (ClassNotFoundException ex) {
         java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
         } catch (InstantiationException ex) {
         java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
         } catch (IllegalAccessException ex) {
         java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
         } catch (javax.swing.UnsupportedLookAndFeelException ex) {
         java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
         }
         */
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Login dialog = null;
                try {
                    try {
                        dialog = new Login(new javax.swing.JFrame(), true);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InstantiationException ex) {
                        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    private void entrar() {
        String n = usuario.getText();
        String pw = contraseña.getText();
//        String nombre = "identals_test";
//        String sContraseña = "89egos0317";
        String nombre = "root";
        String sContraseña = "root";

        manejadorBD = new ManejadorBD(muestraSQL);

        try {
//            manejadorBD.conectar("com.mysql.jdbc.Driver", "jdbc:mysql://gkconsulting.mx:3306/" + database, nombre, sContraseña);
            manejadorBD.conectar("com.mysql.jdbc.Driver", "jdbc:mysql://127.0.0.1:3306/" + database, nombre, sContraseña);
        } catch (ClassNotFoundException ex) {
            error = true;
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            error = true;
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            error = true;
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {

            error = true;
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (error) {
            accesos++;
            JOptionPane.showMessageDialog(this, "Su usuario o contraseña no Corresponden");
        } else {
            usuario_activo = new Usuario();
            usuario_activo.cargarUsuario(n, pw);
        }
    }

    private void registrarse() {
        entrar();
        if (usuario_activo.id_usuario == null && usuario_activo.password == null) {
            JOptionPane.showMessageDialog(this, "Su usuario o contraseña no Corresponden");
            return;
        }
        if (crearUsuario != null) {
            crearUsuario.dispose();
        }
        crearUsuario = new CrearUsuario(this);
        crearUsuario.setVisible(true);

    }

    private void acceder() {
        entrar();
        if (usuario_activo.id_usuario == null && usuario_activo.password == null) {
            JOptionPane.showMessageDialog(this, "Su usuario o contraseña no Corresponden");
            return;
        }
        selecionarRancho = new SeleccionarRancho();
        this.dispose();

        selecionarRancho.setVisible(true);

        if (accesos == 3) {

            System.exit(0);
        }

        error = false;
    }

    private boolean error = false;
    private int accesos;
    public static Usuario usuario_activo;
    public static String gs_mensaje;
    private CrearUsuario crearUsuario;
    private SeleccionarRancho selecionarRancho;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private abstractt.Boton btn_aceptar;
    private abstractt.Boton btn_registrarte;
    private abstractt.Boton btn_salir;
    private abstractt.PasswordField contraseña;
    private abstractt.Etiqueta etiqueta1;
    private abstractt.Etiqueta etiqueta3;
    private abstractt.fondo fondo1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private abstractt.Logo logo1;
    private abstractt.RecuadroLogo recuadro1;
    private abstractt.TextField usuario;
    // End of variables declaration//GEN-END:variables

}
