package gui;

import abstractt.Table;
import domain.Corral;
import domain.Excel;
import domain.Grafica;
import static gui.Desktop.manejadorBD;
import static gui.Desktop.rancho;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.jfree.chart.JFreeChart;

public class CorralesCerrados extends javax.swing.JFrame {

    public CorralesCerrados(java.awt.Frame parent) {
        //super(parent, modal);
        this.parent = parent;
        initComponents();

        ListSelectionModel lsm = this.t_corral.getSelectionModel();

        lsm.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                try {
                    selectCorral();
                } catch (ParseException ex) {
                    Logger.getLogger(CorralesCerrados.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        Image i = null;
        i = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/logo tru-test.png"));
        setIconImage(i);

        this.setTitle(this.getTitle() + " " + rancho.descripcion);
        setLocationRelativeTo(null);
        fondo1.cargar(this.getSize());
        cargarCorralesCerrados();

        t_pesos = new Table();

        t_pesos.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Title 1", "Title 2"}
        ));

        String titulos2[] = {"Fecha", "Peso (kg)"};

        t_pesos.setTitulos(titulos2);
        t_pesos.cambiarTitulos();
        t_pesos.setFormato(new int[]{3, 1});

        g = new Grafica();

        Grafica = g.createChart(g.createDatasetPesos(t_pesos));

    }

    public void graficar() {

        if (t_pesos != null && t_pesos.getRowCount() > 0) {
            Grafica = g.createChart(g.createDatasetPesos(t_pesos));
        }
    }

    private void cargarPesajesCorral() {

        this.t_pesos.limpiarTabla();

        if (!corral.id_corral.equals("")) {

            corral.cargarHistoricoPesos(this.t_pesos);

            graficar();
        }
    }

    private void selectCorral() throws ParseException {
        Integer fila = t_corral.getSelectedRow();
        corral = new Corral();
        corral.cargarCorralCerrado(t_corral.getValueAt(fila, 1).toString());
        asignarValoresCorral();
        cargarPesajesCorral();
    }

    private void asignarValoresCorral() {
        t_alimentoIngresado.setText(corral.alimento_ingresado.toString());
        t_totalKilos.setText(corral.total_kilos.toString());
        t_gananciaKilos.setText(corral.ganancia_promedio.toString());
        jtf_numAnimales.setText(corral.numero_anmales.toString());
        t_pesoMaximo.setText(corral.peso_maximo.toString());
        t_pesoMinimo.setText(corral.peso_minimo.toString());
        t_pesoPromedio.setText(corral.peso_promedio.toString());
        jta_Observaciones.setText(corral.observaciones);
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
        t_totalKilos = new abstractt.TextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        t_gananciaKilos = new abstractt.TextField();
        jtf_numAnimales = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        t_pesoMinimo = new abstractt.TextField();
        t_pesoMaximo = new abstractt.TextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        t_pesoPromedio = new abstractt.TextField();
        jta_Observaciones = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        t_corral = new abstractt.Table();
        jPanel2 = new javax.swing.JPanel();
        boton1 = new abstractt.Boton();
        fondo1 = new abstractt.fondo();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 500));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        etiqueta2.setBackground(new java.awt.Color(95, 84, 88));
        etiqueta2.setForeground(new java.awt.Color(230, 225, 195));
        etiqueta2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiqueta2.setText("Corrales Cerrados");
        etiqueta2.setFont(new java.awt.Font("Trebuchet", 1, 36)); // NOI18N
        etiqueta2.setOpaque(true);
        jPanel1.add(etiqueta2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 60));

        jLabel17.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(95, 84, 88));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Alimento Ingresado:");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 120, 22));

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
        jPanel1.add(t_alimentoIngresado, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 90, 100, 22));

        t_totalKilos.setText("0.00");
        t_totalKilos.setFocusable(false);
        jPanel1.add(t_totalKilos, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 120, 100, 22));

        jLabel18.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(95, 84, 88));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("Total Kilos:");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 110, 22));

        jLabel19.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(95, 84, 88));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("Ganancia Kilos:");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 110, 22));

        t_gananciaKilos.setText("0.00");
        t_gananciaKilos.setFocusable(false);
        jPanel1.add(t_gananciaKilos, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, 100, 22));

        jtf_numAnimales.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jtf_numAnimales.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtf_numAnimales.setFocusable(false);
        jPanel1.add(jtf_numAnimales, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 180, 100, -1));

        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(95, 84, 88));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("No. de Animales:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 110, 22));

        jLabel20.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(95, 84, 88));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Peso Minimo:");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 110, 22));

        t_pesoMinimo.setText("0.00");
        t_pesoMinimo.setFocusable(false);
        jPanel1.add(t_pesoMinimo, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 210, 100, 22));

        t_pesoMaximo.setText("0.00");
        t_pesoMaximo.setFocusable(false);
        jPanel1.add(t_pesoMaximo, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 240, 100, 22));

        jLabel21.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(95, 84, 88));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("Peso Maximo:");
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 110, 22));

        jLabel22.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(95, 84, 88));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("Peso Promedio:");
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 110, 22));

        t_pesoPromedio.setText("0.00");
        t_pesoPromedio.setFocusable(false);
        jPanel1.add(t_pesoPromedio, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 270, 100, 22));

        jta_Observaciones.setColumns(20);
        jta_Observaciones.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jta_Observaciones.setLineWrap(true);
        jta_Observaciones.setRows(3);
        jta_Observaciones.setToolTipText("");
        jta_Observaciones.setWrapStyleWord(true);
        jta_Observaciones.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(jta_Observaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, 250, 70));

        jLabel6.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(95, 84, 88));
        jLabel6.setText("Observaciones:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 93, 22));

        t_corral.setForeground(new java.awt.Color(230, 225, 190));
        t_corral.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        jScrollPane1.setViewportView(t_corral);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 90, -1, 280));

        jPanel2.setOpaque(false);

        boton1.setText("Generar Reporte");
        boton1.setPreferredSize(new java.awt.Dimension(130, 30));
        boton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton1ActionPerformed(evt);
            }
        });
        jPanel2.add(boton1);

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 380, 450, 40));

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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void t_alimentoIngresadoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_alimentoIngresadoFocusLost

//        alimento_ingresado = this.t_alimentoIngresado.getDouble();
    }//GEN-LAST:event_t_alimentoIngresadoFocusLost

    private void t_alimentoIngresadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_alimentoIngresadoActionPerformed

//        corral.alimento_ingresado = this.t_alimentoIngresado.getDouble();
    }//GEN-LAST:event_t_alimentoIngresadoActionPerformed

    private void boton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton1ActionPerformed
        generarReporte();

    }//GEN-LAST:event_boton1ActionPerformed

    private void generarReporte() {
        Excel excel = new Excel();
        excel.reporteCorralCerrado(corral, Grafica);
    }

    private void cargarCorralesCerrados() {
        String titulos[] = {
            "Id Rancho", "id Corral", "Nombre",
            "Localización", "Observaciones", "Costo en Medicinas"};

        t_corral.setTitulos(titulos);
        t_corral.cambiarTitulos();

        manejadorBD.consulta(""
                + "SELECT \n"
                + "    id_rancho, id_corral, nombre, localizacion, observaciones, total_costo_medicina\n"
                + "FROM\n"
                + "    corral\n"
                + "WHERE\n"
                + "    status = 'C'"
                + "     AND id_rancho = '" + rancho.id_rancho + "'");
        manejadorBD.asignarTable(t_corral);
        t_corral.ocultarcolumna(0);
        t_corral.ocultarcolumna(1);
    }

    Corral corral;
    Frame parent;
    private domain.Grafica g;
    private Table t_pesos;
    JFreeChart Grafica;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private abstractt.Boton boton1;
    private abstractt.Etiqueta etiqueta2;
    private abstractt.fondo fondo1;
    private domain.Grafica grafica1;
    private domain.Grafica grafica2;
    private domain.Grafica grafica3;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jta_Observaciones;
    private javax.swing.JTextField jtf_numAnimales;
    private abstractt.TextField t_alimentoIngresado;
    private abstractt.Table t_corral;
    private abstractt.TextField t_gananciaKilos;
    private abstractt.TextField t_pesoMaximo;
    private abstractt.TextField t_pesoMinimo;
    private abstractt.TextField t_pesoPromedio;
    private abstractt.TextField t_totalKilos;
    // End of variables declaration//GEN-END:variables
}
