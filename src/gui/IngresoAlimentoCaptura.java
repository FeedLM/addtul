package gui;

import domain.Corral;
import domain.IngresoAlimento;
import static gui.Desktop.manejadorBD;
import static gui.Desktop.rancho;
import static gui.Login.gs_mensaje;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JOptionPane;

public class IngresoAlimentoCaptura extends javax.swing.JFrame {

    public IngresoAlimentoCaptura(java.awt.Frame parent, String numero_lote, Corral corral) {
        //super(parent, modal);
        this.parent = parent;
        initComponents();
        setLocationRelativeTo(null);
        this.setResizable(false);
        Image i = null;
        i = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/logo tru-test.png"));
        setIconImage(i);

        this.setTitle(this.getTitle() + " " + rancho.descripcion);
        setLocationRelativeTo(null);
        fondo1.cargar(this.getSize());

        this.numero_lote = numero_lote;
        this.corral = corral;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grafica1 = new domain.Grafica();
        grafica2 = new domain.Grafica();
        grafica3 = new domain.Grafica();
        jPanel1 = new javax.swing.JPanel();
        etiqueta2 = new abstractt.Etiqueta();
        jLabel17 = new javax.swing.JLabel();
        t_alimentoIngresado = new abstractt.TextField();
        t_costoTotal = new abstractt.TextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        t_costoUnitario = new abstractt.TextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        btn_agregarAlimento = new abstractt.Boton();
        c_fecha = new abstractt.Calendar();
        tf_carro = new abstractt.TextField();
        fondo1 = new abstractt.fondo();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        etiqueta2.setBackground(new java.awt.Color(95, 84, 88));
        etiqueta2.setForeground(new java.awt.Color(230, 225, 195));
        etiqueta2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiqueta2.setText("Ingreso de Alimento");
        etiqueta2.setFont(new java.awt.Font("Trebuchet", 1, 36)); // NOI18N
        etiqueta2.setOpaque(true);
        jPanel1.add(etiqueta2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, 60));

        jLabel17.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(95, 84, 88));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Total Alimento:");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, 120, 22));

        t_alimentoIngresado.setText("0.00");
        t_alimentoIngresado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                t_alimentoIngresadoFocusLost(evt);
            }
        });
        t_alimentoIngresado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_alimentoIngresadoActionPerformed(evt);
            }
        });
        jPanel1.add(t_alimentoIngresado, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 90, 100, 22));

        t_costoTotal.setEditable(false);
        t_costoTotal.setText("0.00");
        t_costoTotal.setFocusable(false);
        jPanel1.add(t_costoTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 150, 100, 22));

        jLabel18.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(95, 84, 88));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("Fecha:");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 180, 110, 22));

        jLabel19.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(95, 84, 88));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("Costo Unitario:");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 120, 110, 22));

        t_costoUnitario.setText("0.00");
        t_costoUnitario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                t_costoUnitarioFocusLost(evt);
            }
        });
        t_costoUnitario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_costoUnitarioActionPerformed(evt);
            }
        });
        jPanel1.add(t_costoUnitario, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 120, 100, 22));

        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(95, 84, 88));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Costo Total:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, 110, 22));

        jLabel20.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(95, 84, 88));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Carro:");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 210, 110, 22));

        btn_agregarAlimento.setText("Agregar");
        btn_agregarAlimento.setPreferredSize(new java.awt.Dimension(130, 30));
        btn_agregarAlimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarAlimentoActionPerformed(evt);
            }
        });
        jPanel1.add(btn_agregarAlimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 280, -1, 30));
        jPanel1.add(c_fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 180, 100, -1));
        jPanel1.add(tf_carro, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 210, 100, -1));

        fondo1.setText("fondo1");
        jPanel1.add(fondo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void t_alimentoIngresadoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_alimentoIngresadoFocusLost

//        alimento_ingresado = this.t_alimentoIngresado.getDouble();
    }//GEN-LAST:event_t_alimentoIngresadoFocusLost

    private void t_alimentoIngresadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_alimentoIngresadoActionPerformed
        actualizarTotal();
//        corral.alimento_ingresado = this.t_alimentoIngresado.getDouble();
    }//GEN-LAST:event_t_alimentoIngresadoActionPerformed

    private void btn_agregarAlimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarAlimentoActionPerformed

        agregar_alimento();
    }//GEN-LAST:event_btn_agregarAlimentoActionPerformed

    private void agregar_alimento() {

        ingresoAlimento = new IngresoAlimento();
        ingresoAlimento.carro = tf_carro.getText();
        ingresoAlimento.corral = corral;
        ingresoAlimento.costo_total = this.t_costoTotal.getDouble();
        ingresoAlimento.costo_unitario = this.t_costoUnitario.getDouble();
        ingresoAlimento.fecha = this.c_fecha.getDate();
        ingresoAlimento.numero_lote = this.numero_lote;
        ingresoAlimento.total_alimento = this.t_alimentoIngresado.getDouble();

        if (ingresoAlimento.grabar()) {

            JOptionPane.showMessageDialog(this, "Se Agrego el Alimento correctamente", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
        } else {

            JOptionPane.showMessageDialog(this, "Error al Agregar el Alimento " + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
        }
        this.dispose();
    }

    private void t_costoUnitarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_costoUnitarioActionPerformed

        actualizarTotal();
    }//GEN-LAST:event_t_costoUnitarioActionPerformed

    private void t_costoUnitarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_costoUnitarioFocusLost
        actualizarTotal();
    }//GEN-LAST:event_t_costoUnitarioFocusLost

    private void actualizarTotal() {

        Double alimento;
        Double costo_unitario;
        Double costo_total;

        alimento = t_alimentoIngresado.getDouble();
        costo_unitario = t_costoUnitario.getDouble();
        costo_total = alimento * costo_unitario;

        this.t_costoTotal.setDouble(costo_total);
    }

    Corral corral;
    String numero_lote;
    Frame parent;
    IngresoAlimento ingresoAlimento;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private abstractt.Boton btn_agregarAlimento;
    private abstractt.Calendar c_fecha;
    private abstractt.Etiqueta etiqueta2;
    private abstractt.fondo fondo1;
    private domain.Grafica grafica1;
    private domain.Grafica grafica2;
    private domain.Grafica grafica3;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private abstractt.TextField t_alimentoIngresado;
    private abstractt.TextField t_costoTotal;
    private abstractt.TextField t_costoUnitario;
    private abstractt.TextField tf_carro;
    // End of variables declaration//GEN-END:variables
}
