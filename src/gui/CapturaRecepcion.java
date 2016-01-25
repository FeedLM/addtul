/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.Corral;
import domain.Estado;
import domain.ParametrosSP;
import domain.Proveedor;
import domain.Recepcion;
import static domain.Seguridad.pideContraseña;
import static gui.Desktop.manejadorBD;
import static gui.Desktop.rancho;
import static gui.Login.gs_mensaje;
import static gui.Splash.formatoDateTime;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import javax.swing.JOptionPane;

/**
 *
 * @author Marco
 */
public class CapturaRecepcion extends javax.swing.JFrame {

    /**
     * Creates new form Captura_Recepcion
     */
    public CapturaRecepcion() {
        initComponents();
        String titulos[] = {"id_recepcion", "Folio", "Proveedor",
            "Fecha Compra", "# Animales", "Peso Origen", "Peso Recepción", "Lote",
            "Kg Merma", "% Merma", "Devoluciones", "Causa"};
        proveedorSelector1.cargar();
        estadoSelector1.cargar();
        tf_limiteMerma.textFieldDouble();
        tf_numeroAnimales.textFieldSoloNumeros();
        tf_pesoOrigen.textFieldDouble();
        t_recepcion.setTitulos(titulos);
        t_recepcion.cambiarTitulos();
        cargarTabla();

        t_recepcion.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    ingresaAlimento();
                }
            }
        });

        setLocationRelativeTo(null);
        this.setTitle(this.getTitle() + " " + rancho.descripcion);
        this.setResizable(false);
        fondo1.cargar(this.getSize());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_ = new javax.swing.JPanel();
        etiqueta1 = new abstractt.Etiqueta();
        jScrollPane1 = new javax.swing.JScrollPane();
        t_recepcion = new abstractt.Table();
        btn_limpiar = new abstractt.Boton();
        btn_guardar = new abstractt.Boton();
        btn_ingresoAlimento = new abstractt.Boton();
        jPanel2 = new javax.swing.JPanel();
        etiqueta2 = new abstractt.Etiqueta();
        proveedorSelector1 = new domain.ProveedorSelector();
        etiqueta3 = new abstractt.Etiqueta();
        estadoSelector1 = new domain.EstadoSelector();
        tf_folio = new abstractt.TextField();
        etiqueta4 = new abstractt.Etiqueta();
        tf_numeroAnimales = new abstractt.TextField();
        etiqueta6 = new abstractt.Etiqueta();
        tf_numeroLote = new abstractt.TextField();
        etiqueta12 = new abstractt.Etiqueta();
        calendar1 = new abstractt.Calendar();
        etiqueta5 = new abstractt.Etiqueta();
        jPanel1 = new javax.swing.JPanel();
        etiqueta8 = new abstractt.Etiqueta();
        etiqueta7 = new abstractt.Etiqueta();
        etiqueta11 = new abstractt.Etiqueta();
        etiqueta9 = new abstractt.Etiqueta();
        etiqueta10 = new abstractt.Etiqueta();
        tf_pesoOrigen = new abstractt.TextField();
        tf_limiteMerma = new abstractt.TextField();
        tf_pesoRecepcion = new abstractt.TextField();
        tf_costoFlete = new abstractt.TextFieldMoneda();
        calendar2 = new abstractt.Calendar();
        fondo1 = new abstractt.fondo();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        btn_.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        etiqueta1.setBackground(new java.awt.Color(95, 84, 88));
        etiqueta1.setForeground(new java.awt.Color(230, 225, 195));
        etiqueta1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiqueta1.setText("Recepción");
        etiqueta1.setFont(new java.awt.Font("Trebuchet", 1, 36)); // NOI18N
        etiqueta1.setOpaque(true);
        btn_.add(etiqueta1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 60));

        t_recepcion.setForeground(new java.awt.Color(230, 225, 95));
        t_recepcion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10", "Title 11", "Title 12"
            }
        ));
        jScrollPane1.setViewportView(t_recepcion);

        btn_.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 960, 300));

        btn_limpiar.setText("Limpiar");
        btn_limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_limpiarActionPerformed(evt);
            }
        });
        btn_.add(btn_limpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 80, 100, -1));

        btn_guardar.setText("Guardar");
        btn_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarActionPerformed(evt);
            }
        });
        btn_.add(btn_guardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 110, 100, -1));

        btn_ingresoAlimento.setText("Ingreso de Alimento");
        btn_ingresoAlimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ingresoAlimentoActionPerformed(evt);
            }
        });
        btn_.add(btn_ingresoAlimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 160, -1, 30));

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        etiqueta2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiqueta2.setText("Proveedor");
        jPanel2.add(etiqueta2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 120, 20));
        jPanel2.add(proveedorSelector1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 0, -1, 20));

        etiqueta3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiqueta3.setText("Origen de Compra");
        jPanel2.add(etiqueta3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 120, 20));
        jPanel2.add(estadoSelector1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, 130, -1));
        jPanel2.add(tf_folio, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 60, 130, 20));

        etiqueta4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiqueta4.setText("Factura de Compra");
        jPanel2.add(etiqueta4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 120, 20));
        jPanel2.add(tf_numeroAnimales, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 90, 130, 20));

        etiqueta6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiqueta6.setText("Número de Animales");
        jPanel2.add(etiqueta6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, 120, -1));

        tf_numeroLote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_numeroLoteActionPerformed(evt);
            }
        });
        jPanel2.add(tf_numeroLote, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 120, 130, 20));

        etiqueta12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiqueta12.setText("Lote ");
        jPanel2.add(etiqueta12, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, -1, 20));
        jPanel2.add(calendar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, 120, -1));

        etiqueta5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiqueta5.setText("Fecha de Compra");
        jPanel2.add(etiqueta5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, 120, 20));

        btn_.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, 340, -1));

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        etiqueta8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiqueta8.setText("Limite de Merma");
        jPanel1.add(etiqueta8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 120, 20));

        etiqueta7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiqueta7.setText("Peso de Origen");
        jPanel1.add(etiqueta7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 20));

        etiqueta11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiqueta11.setText("Peso Recepción");
        jPanel1.add(etiqueta11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 120, 20));

        etiqueta9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiqueta9.setText("Costo de Flete");
        jPanel1.add(etiqueta9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 120, 20));

        etiqueta10.setText("Fecha de Recepción");
        jPanel1.add(etiqueta10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 120, 20));
        jPanel1.add(tf_pesoOrigen, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 0, 130, -1));

        tf_limiteMerma.setToolTipText("Porcentaje asignado de merma al lote de compra");
        jPanel1.add(tf_limiteMerma, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 130, -1));
        jPanel1.add(tf_pesoRecepcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, 130, 20));
        jPanel1.add(tf_costoFlete, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 130, 20));
        jPanel1.add(calendar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 120, 20));

        btn_.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 70, -1, -1));

        fondo1.setText("fondo1");
        btn_.add(fondo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(btn_, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void cargarTabla() {
        manejadorBD.consulta(""
                + "SELECT \n"
                + "    id_recepcion,\n"
                + "    folio,\n"
                + "    p.descripcion,\n"
                + "    Fecha_compra,\n"
                + "    Animales,\n"
                + "    Peso_Origen,\n"
                + "    Peso_Recepcion,\n"
                + "    Numero_lote, \n"
                + "    Merma,\n"
                + "    porcentaje_Merma,\n"
                + "    Devoluciones,\n"
                + "    Causa_devolucion\n"
                + "FROM\n"
                + "    recepcion r,\n"
                + "    proveedor p\n"
                + "WHERE\n"
                + "    p.id_proveedor = r.id_proveedor;");
        manejadorBD.asignarTable(t_recepcion);
        t_recepcion.ocultarcolumna(0);

    }

    private void btn_limpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limpiarActionPerformed
        limpiar();
    }//GEN-LAST:event_btn_limpiarActionPerformed

    private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarActionPerformed

        guardar();

    }//GEN-LAST:event_btn_guardarActionPerformed

    private void tf_numeroLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_numeroLoteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_numeroLoteActionPerformed

    private void btn_ingresoAlimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ingresoAlimentoActionPerformed
        if (alimentoLote != null) {
            alimentoLote.dispose();
        }
        alimentoLote = new AlimentoLotes("");
        alimentoLote.setVisible(true);
    }//GEN-LAST:event_btn_ingresoAlimentoActionPerformed

    public void guardar() {

        if (tf_folio.equals("") || tf_numeroAnimales.equals("") || this.tf_numeroLote.equals("")) {

            JOptionPane.showMessageDialog(this, "Verifique que los campos se llenaron correctamente.", gs_mensaje, JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!pideContraseña()) {

            return;
        }
        recepcion = new Recepcion();
        proveedor = new Proveedor();
        estado = new Estado();

        proveedor.cargarPorDescripcion(this.proveedorSelector1.getText());
        recepcion.proveedor = proveedor;
        estado.cargarPorDescripcion(this.estadoSelector1.getText());
        recepcion.origen = estado;
        recepcion.folio = this.tf_folio.getText();
        recepcion.fecha_compra = this.calendar1.getDate();
        recepcion.fecha_recepcion = this.calendar2.getDate();
        recepcion.animales = this.tf_numeroAnimales.getInt();
        recepcion.peso_origen = this.tf_pesoOrigen.getDouble();
        recepcion.peso_recepcion = this.tf_pesoRecepcion.getDouble();
        recepcion.limite_merma = this.tf_limiteMerma.getDouble();
        recepcion.numero_lote = this.tf_numeroLote.getText();
        recepcion.costo_flete = this.tf_costoFlete.obtenerValor();

        //Verificando el porcentaje de merma aceptada
        if (((recepcion.peso_origen - recepcion.peso_recepcion) * 100) / recepcion.peso_origen > recepcion.limite_merma) {
            int opcion = JOptionPane.showConfirmDialog(this, "El límite de merma ha sido excedido, ¿Desea ajustar el peso de compra?"
                    + "", gs_mensaje, JOptionPane.YES_NO_OPTION);
            if (opcion == 0) {
                recepcion.peso_origen = recepcion.peso_recepcion + ((recepcion.limite_merma * recepcion.peso_recepcion) / 100);
            }
        }

        manejadorBD.parametrosSP = new ParametrosSP();
        manejadorBD.parametrosSP.agregarParametro(recepcion.proveedor.id_proveedor, "varIdProveedor", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(recepcion.origen.id_estado, "varIdOrigen", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(recepcion.folio, "varFolio", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(recepcion.fecha_compra), "varFechaCompra", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(recepcion.fecha_recepcion), "varFechaRecepcion", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(recepcion.animales.toString(), "varAnimales", "INT", "IN");
        manejadorBD.parametrosSP.agregarParametro(recepcion.peso_origen.toString(), "varPesoOrigen", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(recepcion.limite_merma.toString(), "varLimiteMerma", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(recepcion.merma.toString(), "varMerma", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(recepcion.porcentaje_merma.toString(), "varPorcentajeMerma", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(recepcion.peso_recepcion.toString(), "varPesoRecepcion", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(recepcion.numero_lote, "varNumeroLote", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(recepcion.costo_flete.toString(), "varCostoFlete", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(recepcion.devoluciones.toString(), "varDevoluciones", "INT", "IN");
        manejadorBD.parametrosSP.agregarParametro(recepcion.causa_devolucion, "varCausaDevolucion", "STRING", "IN");

        if (manejadorBD.ejecutarSP("{ call agregarRecepcion(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }") == 0) {

            JOptionPane.showMessageDialog(this, "La compra se ha agregado correctamente", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
            cargarTabla();
            limpiar();
        } else {

            JOptionPane.showMessageDialog(this, "Ocurrió un error con los campos. " + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ingresaAlimento() {
        Integer fila = t_recepcion.getSelectedRow();
        if (fila >= 0) {
            lote = t_recepcion.getValueAt(fila, 7).toString();
            if (alimentoLotes != null) {
                alimentoLotes.dispose();
            }
            alimentoLotes = new AlimentoLotes(lote);
            alimentoLotes.setVisible(true);

        }

    }

    private void limpiar() {

        this.tf_costoFlete.setText("$ 0.00");
        this.tf_folio.setText("");
        this.tf_limiteMerma.setText("0.00");
        this.tf_numeroAnimales.setText("0");
        this.tf_numeroLote.setText("");
        this.estadoSelector1.setSelectedItem("");
        this.proveedorSelector1.setSelectedItem("");
        this.tf_pesoOrigen.setText("0.00");
        this.tf_pesoRecepcion.setText("0.00");
        this.calendar1.setDate(Calendar.getInstance().getTime());
        this.calendar2.setDate(Calendar.getInstance().getTime());
    }

    String lote;
    Estado estado;
    Proveedor proveedor;
    Recepcion recepcion;
    AlimentoLotes alimentoLotes;
    AlimentoLotes alimentoLote;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btn_;
    private abstractt.Boton btn_guardar;
    private abstractt.Boton btn_ingresoAlimento;
    private abstractt.Boton btn_limpiar;
    private abstractt.Calendar calendar1;
    private abstractt.Calendar calendar2;
    private domain.EstadoSelector estadoSelector1;
    private abstractt.Etiqueta etiqueta1;
    private abstractt.Etiqueta etiqueta10;
    private abstractt.Etiqueta etiqueta11;
    private abstractt.Etiqueta etiqueta12;
    private abstractt.Etiqueta etiqueta2;
    private abstractt.Etiqueta etiqueta3;
    private abstractt.Etiqueta etiqueta4;
    private abstractt.Etiqueta etiqueta5;
    private abstractt.Etiqueta etiqueta6;
    private abstractt.Etiqueta etiqueta7;
    private abstractt.Etiqueta etiqueta8;
    private abstractt.Etiqueta etiqueta9;
    private abstractt.fondo fondo1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private domain.ProveedorSelector proveedorSelector1;
    private abstractt.Table t_recepcion;
    private abstractt.TextFieldMoneda tf_costoFlete;
    private abstractt.TextField tf_folio;
    private abstractt.TextField tf_limiteMerma;
    private abstractt.TextField tf_numeroAnimales;
    private abstractt.TextField tf_numeroLote;
    private abstractt.TextField tf_pesoOrigen;
    private abstractt.TextField tf_pesoRecepcion;
    // End of variables declaration//GEN-END:variables

}
