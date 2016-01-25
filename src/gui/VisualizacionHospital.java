/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.Animal;
import static domain.Corral.corralIdNombre;
import domain.Excel;
import domain.Movimiento;
import domain.ParametrosSP;
import static gui.Desktop.manejadorBD;
import static gui.Desktop.rancho;
import static gui.Login.gs_mensaje;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Home
 */
public class VisualizacionHospital extends javax.swing.JInternalFrame {

    /**
     * Creates new form VisualizacionHospitals
     */
    public VisualizacionHospital(Desktop parent) {
        this.parent = parent;
//        super(parent, modal);
        initComponents();
//        setLocationRelativeTo(null);
//        Image i = null;
//        i = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/logo tru-test.png"));
//        setIconImage(i);
        setClosable(true);
        this.pack();
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/resources/logo tru-test.png")));
        cargarInicio();
        ListSelectionModel lsm = this.t_animalesHospital.getSelectionModel();
        lsm.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                selectAnimal();
            }
        });

        corralSelector1.cargar();

        this.setTitle(this.getTitle() + " " + rancho.descripcion);
        animal_detalles = new Animal();
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension TamanoPantalla = tk.getScreenSize();
        fondo1.cargar(TamanoPantalla);
        this.setResizable(false);
    }

    private void cargarInicio() {

        cargarAnimalesHospital();
        cargarHistorico();
        this.btn_salidaHospital.setEnabled(false);
    }

    private void selectAnimal() {

        Integer fila = t_animalesHospital.getSelectedRow();

        this.btn_salidaHospital.setEnabled(false);

        if (fila >= 0) {

            id_animal = t_animalesHospital.getValueAt(fila, 0).toString();
            this.btn_salidaHospital.setEnabled(true);
            this.btn_detalles.setEnabled(true);
            animal_detalles.cargarPorId(this.t_animalesHospital.getValueAt(fila, 0).toString());
        } 

    }

    public void cargarAnimalesHospital() {

        Movimiento.cargarAnimalesHospital(t_animalesHospital);

    }

    public void cargarHistorico() {
        Movimiento.cargarHistoricoHospital(t_HistoricoHospital);
        /*
         int[] tamaños = new int[6];
         tamaños[0] = 80;//id Animal
         tamaños[1] = 110;//Fecha Salida
         tamaños[2] = 110;//Fecha Salida
         tamaños[3] = 110;//Dias en Hospital
         tamaños[4] = 120; //Causa de Entrada
         tamaños[5] = 120;//Observaciones

         t_HistoricoHospital.tamañoColumna(tamaños);
         */
    }

    public void reporteAimalesHospital() {

        Excel excel = new Excel();

        excel.reporteAnimalesHospital(t_animalesHospital);

    }

    public void reporteHistorico() {

        Excel excel = new Excel();

        excel.reporteHistoricoHospital(t_HistoricoHospital);

    }

    private void ObtenerCorralDestino() {

        String ls_corral;

        ls_corral = this.corralSelector1.getSelectedItem().toString();

        id_corral_destino = corralIdNombre(ls_corral);
    }

    private void generarSalida() {

        String id_movimiento = "0";
        String id_con_tras_sal = "0";
        String id_con_tras_ent = "0";
        String id_corral_hospital = "0";
        //  String id_animal = "0";

        if (id_corral_destino.equals("")) {
            JOptionPane.showMessageDialog(this, "No ha seleccionado el Corral de Destino", gs_mensaje, JOptionPane.ERROR_MESSAGE);
            return;
        }
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Date fecha = calendar1.getDate();

        //  Obtener el Concepto del traspaso de entrada
        manejadorBD.consulta(""
                + "SELECT con_traspaso_entrada, con_traspaso_salida, id_corral_hospital "
                + "FROM rancho "
                + "WHERE id_rancho = '" + rancho.id_rancho + "'");

        if (manejadorBD.getRowCount() > 0) {

            id_con_tras_ent = manejadorBD.getValorString(0, 0);
            id_con_tras_sal = manejadorBD.getValorString(0, 1);
            id_corral_hospital = manejadorBD.getValorString(0, 2);
        }
        //  Insertar el movimiento
        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(rancho.id_rancho, "varIdRancho", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_con_tras_sal, "varConceptoTrasSalida", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_movimiento, "varIdMovimiento", "STRING", "OUT");
        manejadorBD.parametrosSP.agregarParametro(formatoDelTexto.format(fecha), "varFecha", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(rancho.id_rancho, "varIdRanchoDestino", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_corral_hospital, "varIdCorralOrigen", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_corral_destino, "varIdCorralDestino", "STRING", "IN");

        if (manejadorBD.ejecutarSP("{ call generarTraspasoSalida(?,?,?,?,?,?,?) }") == 0) {

            // id_con_tras_sal = manejadorBD.parametrosSP.get(1).getValor();
            id_movimiento = manejadorBD.parametrosSP.get(2).getValor();
            System.out.println("id Movimiento de Traspaso " + id_movimiento);
            System.out.println("id Concepto de Traspaso " + id_con_tras_sal);
            // JOptionPane.showMessageDialog(this, "Se Creo el corral Correctamente", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
        } else {

            JOptionPane.showMessageDialog(this, "Error al generar el movimiento de traspaso", gs_mensaje, JOptionPane.ERROR_MESSAGE);
            //   manejadorBD.rollback();
            return;
        }

        //  Insertar el detalle
        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(rancho.id_rancho, "varIdRancho", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_con_tras_sal, "varConceptoMovimiento", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_movimiento, "varIdMovimiento", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_animal, "varIdAnimal", "STRING", "IN");

        if (manejadorBD.ejecutarSP("{ call insertarDetalleMovimiento(?,?,?,?) }") != 0) {

            JOptionPane.showMessageDialog(this, "Error al insertar el animal [" + id_animal + "] en el traspaso\n " + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
            return;
        }

        //  Duplicar el movimiento con traspaso de entrada
        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(rancho.id_rancho, "varIdRancho", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_movimiento, "varIdMovimientoOrigen", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_con_tras_sal, "varConceptoOrigen", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(rancho.id_rancho, "varIdRanchoDestino", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_con_tras_ent, "varConceptoDestino", "STRING", "IN");

        if (manejadorBD.ejecutarSP("{ call duplicaMovimiento(?,?,?,?,? ) }") == 0) {

            JOptionPane.showMessageDialog(this, "Se genero el Reintegro del Animal correctamente ", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);

            this.cargarInicio();
        } else {
            JOptionPane.showMessageDialog(this, "Error al generar el Reintegro del Animal\n" + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
            //    manejadorBD.rollback();
        }
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
        etq_visualización_Hospital = new abstractt.Etiqueta();
        jPanel2 = new javax.swing.JPanel();
        etiqueta1 = new abstractt.Etiqueta();
        jScrollPane1 = new javax.swing.JScrollPane();
        t_animalesHospital = new abstractt.Table();
        jPanel6 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        etiqueta2 = new abstractt.Etiqueta();
        calendar1 = new abstractt.Calendar();
        jPanel4 = new javax.swing.JPanel();
        etiqueta3 = new abstractt.Etiqueta();
        corralSelector1 = new domain.CorralSelector();
        jPanel5 = new javax.swing.JPanel();
        btn_salidaHospital = new abstractt.Boton();
        btn_detalles = new abstractt.Boton();
        jPanel7 = new javax.swing.JPanel();
        etiqueta4 = new abstractt.Etiqueta();
        jScrollPane2 = new javax.swing.JScrollPane();
        t_HistoricoHospital = new abstractt.Table();
        jPanel8 = new javax.swing.JPanel();
        tbn_reporte1 = new abstractt.Boton();
        tbn_reporte = new abstractt.Boton();
        fondo1 = new abstractt.fondo();

        setTitle("Visualización Hospital");
        setPreferredSize(new java.awt.Dimension(1020, 660));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        etq_visualización_Hospital.setBackground(new java.awt.Color(95, 84, 88));
        etq_visualización_Hospital.setForeground(new java.awt.Color(230, 225, 195));
        etq_visualización_Hospital.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etq_visualización_Hospital.setText("Visualización Hospital");
        etq_visualización_Hospital.setFont(new java.awt.Font("Trebuchet", 1, 48)); // NOI18N
        etq_visualización_Hospital.setOpaque(true);
        jPanel1.add(etq_visualización_Hospital, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1020, 79));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        etiqueta1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiqueta1.setText("Animales en el Hospital: ");
        etiqueta1.setFont(new java.awt.Font("Trebuchet", 1, 18)); // NOI18N
        jPanel2.add(etiqueta1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 0, -1, -1));

        t_animalesHospital.setForeground(new java.awt.Color(230, 225, 195));
        t_animalesHospital.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(t_animalesHospital);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 1000, 150));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 85, 1000, -1));

        jPanel6.setOpaque(false);
        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 100, 5));

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.Y_AXIS));

        etiqueta2.setText("Fecha de reintegro: ");
        etiqueta2.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jPanel3.add(etiqueta2);

        calendar1.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jPanel3.add(calendar1);

        jPanel6.add(jPanel3);

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.Y_AXIS));

        etiqueta3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiqueta3.setText("Corral de Destino: ");
        etiqueta3.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jPanel4.add(etiqueta3);

        corralSelector1.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        corralSelector1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                corralSelector1ActionPerformed(evt);
            }
        });
        jPanel4.add(corralSelector1);

        jPanel6.add(jPanel4);

        jPanel5.setOpaque(false);
        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));

        btn_salidaHospital.setText("Salida Hospital");
        btn_salidaHospital.setFont(new java.awt.Font("Trebuchet", 1, 12)); // NOI18N
        btn_salidaHospital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salidaHospitalActionPerformed(evt);
            }
        });
        jPanel5.add(btn_salidaHospital);

        jPanel6.add(jPanel5);

        btn_detalles.setText("Detalles");
        btn_detalles.setPreferredSize(new java.awt.Dimension(117, 25));
        btn_detalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_detallesActionPerformed(evt);
            }
        });
        jPanel6.add(btn_detalles);

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 1000, -1));

        jPanel7.setOpaque(false);
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        etiqueta4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiqueta4.setText("Historico de Animales:");
        etiqueta4.setFont(new java.awt.Font("Trebuchet", 1, 18)); // NOI18N
        jPanel7.add(etiqueta4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, -1));

        t_HistoricoHospital.setForeground(new java.awt.Color(230, 225, 195));
        t_HistoricoHospital.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(t_HistoricoHospital);

        jPanel7.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 1000, 150));

        jPanel8.setOpaque(false);
        jPanel8.setPreferredSize(new java.awt.Dimension(494, 90));
        jPanel8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 180, 20));

        tbn_reporte1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/animales en hospital ICONO.png"))); // NOI18N
        tbn_reporte1.setToolTipText("Reporte de Hospital");
        tbn_reporte1.setPreferredSize(new java.awt.Dimension(55, 55));
        tbn_reporte1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbn_reporte1ActionPerformed(evt);
            }
        });
        jPanel8.add(tbn_reporte1);

        tbn_reporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/historial ICONO.png"))); // NOI18N
        tbn_reporte.setToolTipText("Reporte de Historial");
        tbn_reporte.setPreferredSize(new java.awt.Dimension(55, 55));
        tbn_reporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbn_reporteActionPerformed(evt);
            }
        });
        jPanel8.add(tbn_reporte);

        jPanel7.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 1000, 100));

        getContentPane().add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 1000, 275));

        fondo1.setText("fondo1");
        getContentPane().add(fondo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbn_reporte1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbn_reporte1ActionPerformed

        reporteAimalesHospital();

    }//GEN-LAST:event_tbn_reporte1ActionPerformed

    private void tbn_reporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbn_reporteActionPerformed

        reporteHistorico();

    }//GEN-LAST:event_tbn_reporteActionPerformed

    private void corralSelector1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_corralSelector1ActionPerformed

        ObtenerCorralDestino();

    }//GEN-LAST:event_corralSelector1ActionPerformed

    private void btn_salidaHospitalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salidaHospitalActionPerformed

        generarSalida();

    }//GEN-LAST:event_btn_salidaHospitalActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        parent.setVisible(true);
    }//GEN-LAST:event_formWindowClosing

    private void btn_detallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_detallesActionPerformed
        if (especificacionesAnimal != null) {

            especificacionesAnimal.dispose();
        }

        especificacionesAnimal = new EspecificacionesAnimal(parent);
        especificacionesAnimal.setConsulta(true);

        if (!animal_detalles.id_animal.equals("")) {

            especificacionesAnimal.setId_animal(animal_detalles.id_animal);
        }

        especificacionesAnimal.setVisible(true);
    }//GEN-LAST:event_btn_detallesActionPerformed

    Desktop parent;
    private String id_corral_destino;
    private String id_animal;
    private Animal animal_detalles;
    EspecificacionesAnimal especificacionesAnimal;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private abstractt.Boton btn_detalles;
    private abstractt.Boton btn_salidaHospital;
    private abstractt.Calendar calendar1;
    private domain.CorralSelector corralSelector1;
    private abstractt.Etiqueta etiqueta1;
    private abstractt.Etiqueta etiqueta2;
    private abstractt.Etiqueta etiqueta3;
    private abstractt.Etiqueta etiqueta4;
    private abstractt.Etiqueta etq_visualización_Hospital;
    private abstractt.fondo fondo1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private abstractt.Table t_HistoricoHospital;
    private abstractt.Table t_animalesHospital;
    private abstractt.Boton tbn_reporte;
    private abstractt.Boton tbn_reporte1;
    // End of variables declaration//GEN-END:variables
}
