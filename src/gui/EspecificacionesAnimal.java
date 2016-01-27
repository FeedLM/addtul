    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import static abstractt.ClaseAbstracta.dialogoConfirmacionSiNo;
import domain.Animal;
import domain.Corral;
import static domain.Corral.cargarCorrales;
import domain.Excel;
import static domain.Movimiento.leerPesos;
import domain.ParametrosSP;
import domain.Recepcion;
import domain.SR232;
import static gui.Desktop.rancho;
import static gui.Desktop.manejadorBD;
import static gui.Login.gs_mensaje;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

/**
 *
 * @author Developer GAGS
 */
public class EspecificacionesAnimal extends javax.swing.JDialog {//JDialog { //

    private SR232 stick;
    private SR232 scale;
    private domain.Grafica g;
    private boolean consulta;

    /**
     * Creates new form EspecificacionesAnimal
     *
     * @param aparent
     */
    public EspecificacionesAnimal(Desktop parent) {
        this.parent = parent;
        initComponents();
        setLocationRelativeTo(null);
        cargarPuertos();

        tf_PesoActual.textFieldDouble();
        tf_Temperatura.textFieldDouble();

        corralSelector.addArray(cargarCorrales());
        sexoSelector1.cargar();
        animalDetalle = new Animal();

        String titulos[] = {"Fecha", "Peso (kg)"};

        t_pesos.setTitulos(titulos);
        t_pesos.cambiarTitulos();
        t_pesos.setFormato(new int[]{3, 1});

        g = new domain.Grafica();

        Grafica = g.createChart(g.createDatasetPesos(t_pesos));

        jP_Grafica = new ChartPanel(Grafica);

        jP_Grafica.setBackground(new java.awt.Color(255, 255, 204));
        jP_Grafica.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jP_GraficaLayout = new javax.swing.GroupLayout(jP_Grafica);
        jP_Grafica.setLayout(jP_GraficaLayout);
        jP_GraficaLayout.setHorizontalGroup(
                jP_GraficaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE)
        );
        jP_GraficaLayout.setVerticalGroup(
                jP_GraficaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 368, Short.MAX_VALUE)
        );

        panelGrafica.add(jP_Grafica, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, panelGrafica.getWidth(), panelGrafica.getHeight()));

        graficar();

        fecha_peso = Calendar.getInstance();

        JDc_FechaCompra.setDate(Calendar.getInstance().getTime());
        JDc_FechaIngreso.setDate(Calendar.getInstance().getTime());

        Image i = null;
        i = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/logo tru-test.png"));
        setIconImage(i);
//        this.pack();
//        this.setClosable(true);
//        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/resources/logo tru-test.png")));

        this.semental.cargarTagsIdsSementales();
        this.proveedorSelector1.cargar();

        this.setTitle(this.getTitle() + " " + rancho.descripcion);
        this.razaSelector1.valor_nuevo = true;
        this.proveedorSelector1.valor_nuevo = true;
        this.tf_ganancia.setEditable(false);

        razaSelector1.cargarSeleccionar();
        loteSelector1.cargar();
        animalSelectorMadre.cargararete_visuals_2();
        animalSelectorPadre.cargararete_visuals_2();
        cargarComponentes();
    }

    public void graficar() {

        Grafica = g.createChart(g.createDatasetPesos(t_pesos));
        jP_Grafica.setChart(Grafica);
    }

    private void cargarPuertos() {

        manejadorBD.consulta(
                "SELECT puerto_baston, puerto_bascula "
                + "FROM configuracion ");

        if (manejadorBD.getRowCount() > 0) {

            this.puertoStick = manejadorBD.getValorString(0, 0);
            this.puertoBascula = manejadorBD.getValorString(0, 1);
        }
    }

    public void cargarStick() {

        //System.out.println("Inicio Puerto");
        stick = new SR232(puertoStick, 3, parent, 1);
        //  stick.setEID(tf_Eid);
        //  stick.setTf_Peso(tf_PesoActual);

        if (!stick.puertoDisponible()) {

            JOptionPane.showMessageDialog(this, "No se pudo conectar al puerto serie " + puertoStick + "\n las opciones de entrada estaran deshabilitadas ", gs_mensaje, JOptionPane.ERROR_MESSAGE);
            return;
        }
        stick.start();

        automatico = true;
    }

    boolean automatico = false;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        pn_Formulario = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        tf_PesoActual = new abstractt.TextField();
        tf_Eid = new abstractt.TextField();
        tf_AreteSiniiga = new abstractt.TextField();
        tf_AreteCampaña = new abstractt.TextField();
        tf_AreteVisual = new abstractt.TextField();
        JDc_FechaIngreso = new abstractt.Calendar();
        tf_Temperatura = new abstractt.TextField();
        tf_PesoCompra = new abstractt.TextField();
        jrb_esSemental = new javax.swing.JRadioButton();
        semental = new domain.AnimalSelector();
        tf_Compra = new javax.swing.JTextField();
        JDc_FechaCompra = new abstractt.Calendar();
        sexoSelector1 = new domain.SexoSelector();
        proveedorSelector1 = new domain.ProveedorSelector();
        corralSelector = new abstractt.ComboBox();
        razaSelector1 = new domain.RazaSelector();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        animalSelectorMadre = new domain.AnimalSelector();
        animalSelectorPadre = new domain.AnimalSelector();
        jrb_esVientre = new javax.swing.JRadioButton();
        loteSelector1 = new domain.LoteSelector();
        JDc_fechaRecepcion = new abstractt.Calendar();
        etiqueta2 = new abstractt.Etiqueta();
        etiqueta3 = new abstractt.Etiqueta();
        tf_pesoRecepcion = new abstractt.TextField();
        pn_Grafica_Tabla = new javax.swing.JPanel();
        panelGrafica = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        t_pesos = new abstractt.Table();
        pn_botonesVerticales = new javax.swing.JPanel();
        btn_agregar = new abstractt.Boton();
        btn_actualizar = new abstractt.Boton();
        btn_reporte = new abstractt.Boton();
        btn_medicinas = new abstractt.Boton();
        btn_Hospital = new abstractt.Boton();
        btn_pesoManual = new abstractt.Boton();
        btn_BajasMuerte = new abstractt.Boton();
        jPanel2 = new javax.swing.JPanel();
        tf_consumo = new abstractt.TextField();
        pn_Peso = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        tf_pesoBascula = new abstractt.TextField();
        btn_quitarPeso = new javax.swing.JButton();
        btn_capturarPeso = new javax.swing.JButton();
        etiqueta1 = new abstractt.Etiqueta();
        jPanel1 = new javax.swing.JPanel();
        tf_ganancia = new abstractt.TextField();
        jPanel4 = new javax.swing.JPanel();
        tf_merma = new abstractt.TextField();
        fondo1 = new abstractt.fondo();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Especificaciones del Animal");
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pn_Formulario.setBackground(new java.awt.Color(255, 255, 255));
        pn_Formulario.setOpaque(false);
        pn_Formulario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(95, 84, 88));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Arete Visual (IDV):");
        pn_Formulario.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 120, 20));

        jLabel3.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(95, 84, 88));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Arete Electronico (IDE):");
        pn_Formulario.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 360, 160, 20));

        jLabel7.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(95, 84, 88));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Arete Siniiga:");
        pn_Formulario.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 390, 120, 20));

        jLabel8.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(95, 84, 88));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Arete Campaña:");
        pn_Formulario.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 420, 120, 20));

        jLabel14.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(95, 84, 88));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Padre:");
        jLabel14.setFocusable(false);
        pn_Formulario.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 660, 120, 20));

        jLabel15.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(95, 84, 88));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Peso Actual (Kg):");
        pn_Formulario.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 450, 120, 20));

        jLabel6.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(95, 84, 88));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Fecha de Ingreso:");
        pn_Formulario.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 120, 20));

        jLabel19.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("Temperatura:");
        jLabel19.setFocusable(false);
        pn_Formulario.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 600, 110, 20));

        jLabel16.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(95, 84, 88));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Raza:");
        pn_Formulario.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 120, 20));

        jLabel18.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(95, 84, 88));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("Peso de Compra:");
        pn_Formulario.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 120, 20));

        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(95, 84, 88));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Corral:");
        pn_Formulario.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 120, 20));

        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(95, 84, 88));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Proveedor:");
        pn_Formulario.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 120, 20));

        jLabel10.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(95, 84, 88));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Sexo:");
        pn_Formulario.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 120, 20));

        jLabel12.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(95, 84, 88));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Numero de Lote:");
        pn_Formulario.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 120, 20));

        jLabel21.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(95, 84, 88));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("Folio de Compra:");
        pn_Formulario.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 120, 20));

        jLabel11.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(95, 84, 88));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Fecha de Compra:");
        pn_Formulario.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 120, 20));

        tf_PesoActual.setText("0.00");
        tf_PesoActual.setFocusable(false);
        tf_PesoActual.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        pn_Formulario.add(tf_PesoActual, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 450, 160, 20));

        tf_Eid.setFocusable(false);
        tf_Eid.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        tf_Eid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tf_EidFocusLost(evt);
            }
        });
        tf_Eid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_EidActionPerformed(evt);
            }
        });
        pn_Formulario.add(tf_Eid, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 360, 160, 20));

        tf_AreteSiniiga.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        pn_Formulario.add(tf_AreteSiniiga, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 390, 160, 20));

        tf_AreteCampaña.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        pn_Formulario.add(tf_AreteCampaña, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 420, 160, 20));

        tf_AreteVisual.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        tf_AreteVisual.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tf_AreteVisualFocusLost(evt);
            }
        });
        tf_AreteVisual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_AreteVisualActionPerformed(evt);
            }
        });
        pn_Formulario.add(tf_AreteVisual, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 330, 160, 20));

        JDc_FechaIngreso.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        pn_Formulario.add(JDc_FechaIngreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 240, 160, 20));

        tf_Temperatura.setText("0.00");
        tf_Temperatura.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        pn_Formulario.add(tf_Temperatura, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 600, 160, 20));

        tf_PesoCompra.setText("0.00");
        tf_PesoCompra.setFocusable(false);
        tf_PesoCompra.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        pn_Formulario.add(tf_PesoCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 150, 160, 20));

        jrb_esSemental.setBackground(new java.awt.Color(255, 255, 255));
        jrb_esSemental.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jrb_esSemental.setForeground(new java.awt.Color(95, 84, 88));
        jrb_esSemental.setText("Es Semental:                   ");
        jrb_esSemental.setEnabled(false);
        jrb_esSemental.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jrb_esSemental.setOpaque(false);
        pn_Formulario.add(jrb_esSemental, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 540, 220, 20));
        jrb_esSemental.getAccessibleContext().setAccessibleName("Es Semental:                  ");

        semental.setEnabled(false);
        semental.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        pn_Formulario.add(semental, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 570, 160, 20));

        tf_Compra.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        tf_Compra.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pn_Formulario.add(tf_Compra, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, 160, 20));

        JDc_FechaCompra.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        pn_Formulario.add(JDc_FechaCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 60, 160, 20));

        sexoSelector1.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        sexoSelector1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sexoSelector1ActionPerformed(evt);
            }
        });
        pn_Formulario.add(sexoSelector1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 300, 160, -1));

        proveedorSelector1.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        pn_Formulario.add(proveedorSelector1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 160, 20));

        corralSelector.setFocusable(false);
        corralSelector.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        corralSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                corralSelectorActionPerformed(evt);
            }
        });
        pn_Formulario.add(corralSelector, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 160, 20));

        razaSelector1.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        pn_Formulario.add(razaSelector1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 270, 160, -1));

        jLabel20.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(95, 84, 88));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Semental:");
        jLabel20.setFocusable(false);
        pn_Formulario.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 570, 120, 20));

        jLabel22.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(95, 84, 88));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("Madre:");
        jLabel22.setFocusable(false);
        pn_Formulario.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 630, 120, 20));

        animalSelectorMadre.setEnabled(false);
        pn_Formulario.add(animalSelectorMadre, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 630, 160, 20));

        animalSelectorPadre.setEnabled(false);
        pn_Formulario.add(animalSelectorPadre, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 660, 160, 20));

        jrb_esVientre.setBackground(new java.awt.Color(255, 255, 255));
        jrb_esVientre.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jrb_esVientre.setForeground(new java.awt.Color(95, 84, 88));
        jrb_esVientre.setText("Es Vientre:                   ");
        jrb_esVientre.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jrb_esVientre.setOpaque(false);
        pn_Formulario.add(jrb_esVientre, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 600, 210, 20));

        loteSelector1.setEditable(false);
        loteSelector1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loteSelector1ActionPerformed(evt);
            }
        });
        pn_Formulario.add(loteSelector1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 120, 160, -1));
        pn_Formulario.add(JDc_fechaRecepcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 180, 160, 20));

        etiqueta2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiqueta2.setText("Fecha de Recepción: ");
        pn_Formulario.add(etiqueta2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 140, 20));

        etiqueta3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiqueta3.setText("Peso de Recepción: ");
        pn_Formulario.add(etiqueta3, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 210, 130, -1));

        tf_pesoRecepcion.setText("0.00");
        pn_Formulario.add(tf_pesoRecepcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 210, 160, 20));

        jPanel3.add(pn_Formulario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 320, 490));

        pn_Grafica_Tabla.setBackground(new java.awt.Color(255, 255, 255));
        pn_Grafica_Tabla.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pn_Grafica_Tabla.setOpaque(false);
        pn_Grafica_Tabla.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelGrafica.setBackground(new java.awt.Color(0, 51, 51));
        panelGrafica.setForeground(new java.awt.Color(0, 51, 51));
        panelGrafica.setOpaque(false);
        panelGrafica.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        pn_Grafica_Tabla.add(panelGrafica, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, 300));

        jScrollPane2.setOpaque(false);

        t_pesos.setForeground(new java.awt.Color(230, 225, 195));
        t_pesos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        t_pesos.setGridColor(new java.awt.Color(204, 204, 204));
        jScrollPane2.setViewportView(t_pesos);

        pn_Grafica_Tabla.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 450, 300));

        jPanel3.add(pn_Grafica_Tabla, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 80, -1, -1));

        pn_botonesVerticales.setBackground(new java.awt.Color(255, 255, 255));
        pn_botonesVerticales.setOpaque(false);
        pn_botonesVerticales.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_agregar.setText("Agregar");
        btn_agregar.setEnabled(false);
        btn_agregar.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        btn_agregar.setMaximumSize(new java.awt.Dimension(150, 30));
        btn_agregar.setMinimumSize(new java.awt.Dimension(150, 30));
        btn_agregar.setPreferredSize(new java.awt.Dimension(200, 30));
        btn_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarActionPerformed(evt);
            }
        });
        pn_botonesVerticales.add(btn_agregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 150, -1));

        btn_actualizar.setText("Actualizar");
        btn_actualizar.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        btn_actualizar.setMaximumSize(new java.awt.Dimension(150, 30));
        btn_actualizar.setMinimumSize(new java.awt.Dimension(150, 30));
        btn_actualizar.setPreferredSize(new java.awt.Dimension(200, 30));
        btn_actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizarActionPerformed(evt);
            }
        });
        pn_botonesVerticales.add(btn_actualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 150, -1));

        btn_reporte.setText("Reporte");
        btn_reporte.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        btn_reporte.setMaximumSize(new java.awt.Dimension(150, 30));
        btn_reporte.setMinimumSize(new java.awt.Dimension(150, 30));
        btn_reporte.setPreferredSize(new java.awt.Dimension(200, 30));
        btn_reporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_reporteActionPerformed(evt);
            }
        });
        pn_botonesVerticales.add(btn_reporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 150, -1));

        btn_medicinas.setText("Medicinas");
        btn_medicinas.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        btn_medicinas.setMaximumSize(new java.awt.Dimension(150, 30));
        btn_medicinas.setMinimumSize(new java.awt.Dimension(150, 30));
        btn_medicinas.setPreferredSize(new java.awt.Dimension(200, 30));
        btn_medicinas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_medicinasActionPerformed(evt);
            }
        });
        pn_botonesVerticales.add(btn_medicinas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 150, -1));

        btn_Hospital.setText("Hospital");
        btn_Hospital.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        btn_Hospital.setMaximumSize(new java.awt.Dimension(150, 30));
        btn_Hospital.setMinimumSize(new java.awt.Dimension(150, 30));
        btn_Hospital.setPreferredSize(new java.awt.Dimension(200, 30));
        btn_Hospital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_HospitalActionPerformed(evt);
            }
        });
        pn_botonesVerticales.add(btn_Hospital, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 150, -1));

        btn_pesoManual.setText("Peso manual");
        btn_pesoManual.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        btn_pesoManual.setMaximumSize(new java.awt.Dimension(150, 30));
        btn_pesoManual.setMinimumSize(new java.awt.Dimension(150, 30));
        btn_pesoManual.setPreferredSize(new java.awt.Dimension(200, 30));
        btn_pesoManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pesoManualActionPerformed(evt);
            }
        });
        pn_botonesVerticales.add(btn_pesoManual, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 150, -1));

        btn_BajasMuerte.setText("Muertes");
        btn_BajasMuerte.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        btn_BajasMuerte.setMaximumSize(new java.awt.Dimension(150, 30));
        btn_BajasMuerte.setMinimumSize(new java.awt.Dimension(150, 30));
        btn_BajasMuerte.setPreferredSize(new java.awt.Dimension(200, 30));
        btn_BajasMuerte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BajasMuerteActionPerformed(evt);
            }
        });
        pn_botonesVerticales.add(btn_BajasMuerte, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 150, -1));

        jPanel3.add(pn_botonesVerticales, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 80, -1, 280));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Consumo Promedio de Alimento"));
        jPanel2.setOpaque(false);

        tf_consumo.setEditable(false);
        tf_consumo.setBackground(new java.awt.Color(255, 255, 204));
        tf_consumo.setText("0.0");
        tf_consumo.setFont(new java.awt.Font("Trebuchet", 0, 48)); // NOI18N
        tf_consumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_consumoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tf_consumo, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tf_consumo, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 470, 180, 100));

        pn_Peso.setBackground(new java.awt.Color(255, 255, 255));
        pn_Peso.setOpaque(false);
        pn_Peso.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel9.setOpaque(false);
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Kg.");
        jPanel9.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, 20, 10));

        tf_pesoBascula.setBorder(null);
        tf_pesoBascula.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tf_pesoBascula.setText("0.00");
        tf_pesoBascula.setFocusable(false);
        tf_pesoBascula.setFont(new java.awt.Font("Trebuchet MS", 0, 60)); // NOI18N
        jPanel9.add(tf_pesoBascula, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 210, 50));

        pn_Peso.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 260, 70));

        btn_quitarPeso.setBackground(new java.awt.Color(64, 37, 4));
        btn_quitarPeso.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        btn_quitarPeso.setForeground(new java.awt.Color(230, 225, 190));
        btn_quitarPeso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/cancel.png"))); // NOI18N
        btn_quitarPeso.setText("Cancelar");
        btn_quitarPeso.setContentAreaFilled(false);
        btn_quitarPeso.setIconTextGap(15);
        btn_quitarPeso.setOpaque(true);
        btn_quitarPeso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_quitarPesoActionPerformed(evt);
            }
        });
        pn_Peso.add(btn_quitarPeso, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 80, -1, 30));

        btn_capturarPeso.setBackground(new java.awt.Color(64, 37, 4));
        btn_capturarPeso.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        btn_capturarPeso.setForeground(new java.awt.Color(230, 225, 190));
        btn_capturarPeso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/accept_button.png"))); // NOI18N
        btn_capturarPeso.setText("Agregar");
        btn_capturarPeso.setContentAreaFilled(false);
        btn_capturarPeso.setIconTextGap(15);
        btn_capturarPeso.setOpaque(true);
        btn_capturarPeso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_capturarPesoActionPerformed(evt);
            }
        });
        pn_Peso.add(btn_capturarPeso, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, -1, 30));

        jPanel3.add(pn_Peso, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 570, -1, 130));

        etiqueta1.setBackground(new java.awt.Color(95, 84, 88));
        etiqueta1.setForeground(new java.awt.Color(230, 225, 195));
        etiqueta1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiqueta1.setText("Especificaciones del animal");
        etiqueta1.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        etiqueta1.setOpaque(true);
        jPanel3.add(etiqueta1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 60));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ganancia de Peso Diaria", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Trebuchet MS", 0, 12))); // NOI18N
        jPanel1.setOpaque(false);

        tf_ganancia.setEditable(false);
        tf_ganancia.setBackground(new java.awt.Color(255, 255, 204));
        tf_ganancia.setText("0.0");
        tf_ganancia.setToolTipText("El peso promedio ganado por día");
        tf_ganancia.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tf_ganancia.setEnabled(false);
        tf_ganancia.setFont(new java.awt.Font("Trebuchet", 0, 60)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 168, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(tf_ganancia, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 87, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(tf_ganancia, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jPanel3.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 570, 180, 110));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Porcentaje de Merma"));
        jPanel4.setOpaque(false);

        tf_merma.setEditable(false);
        tf_merma.setBackground(new java.awt.Color(255, 255, 204));
        tf_merma.setText("0.0");
        tf_merma.setFont(new java.awt.Font("Trebuchet", 0, 48)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tf_merma, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tf_merma, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 360, 180, 100));

        fondo1.setText("fondo1");
        jPanel3.add(fondo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-5, -5, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void corralSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_corralSelectorActionPerformed
        cargarDatosCorral();
    }//GEN-LAST:event_corralSelectorActionPerformed

    private void cargarDatosCorral() {

        String nombreCorral;

        corral = new Corral();

        if (corralSelector.vacio()) {

            return;
        }

        nombreCorral = corralSelector.getSelectedItem().toString();

        corral.cargarPorNombre(nombreCorral);

    }

    private void btn_capturarPesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_capturarPesoActionPerformed

        tf_PesoActual.setDouble(tf_pesoBascula.getDouble());
        if (!validacionCodigo) {
            agregar_peso();
        }

    }//GEN-LAST:event_btn_capturarPesoActionPerformed

    private void tf_EidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_EidActionPerformed
        leerEid();
    }//GEN-LAST:event_tf_EidActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
    }//GEN-LAST:event_formWindowClosed

    private void tf_EidFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tf_EidFocusLost
        this.leerEid();        // TODO add your handling code here:
    }//GEN-LAST:event_tf_EidFocusLost

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (automatico) {
            this.stick.setSeguir(false);
        }
    }//GEN-LAST:event_formWindowClosing

    private void btn_quitarPesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_quitarPesoActionPerformed

        this.tf_pesoBascula.setDouble(new Double(0.0));
    }//GEN-LAST:event_btn_quitarPesoActionPerformed

    private void tf_AreteVisualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_AreteVisualActionPerformed
        validarCodigo();
    }//GEN-LAST:event_tf_AreteVisualActionPerformed

    private void tf_AreteVisualFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tf_AreteVisualFocusLost

        validarCodigo();
    }//GEN-LAST:event_tf_AreteVisualFocusLost

    private void btn_BajasMuerteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BajasMuerteActionPerformed
        // TODO add your handling code here:
        if (animalDetalle != null) {
            if (bajasMuerte != null) {
                bajasMuerte.dispose();
            }
            bajasMuerte = new BajasMuerte(parent, animalDetalle);
            bajasMuerte.setVisible(true);
        }
    }//GEN-LAST:event_btn_BajasMuerteActionPerformed

    private void btn_pesoManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pesoManualActionPerformed
        // TODO add your handling code here:
        if (pesoManual != null) {
            pesoManual.dispose();
        }
        pesoManual = new PesoManual(this, tf_pesoBascula);
        pesoManual.setVisible(true);
    }//GEN-LAST:event_btn_pesoManualActionPerformed

    private void btn_HospitalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_HospitalActionPerformed
        // TODO add your handling code here:
        if (animalDetalle != null) {
            if (hospital != null) {
                hospital.dispose();
            }
            hospital = new Hospital(animalDetalle.id_animal);
            hospital.setVisible(true);
        }

    }//GEN-LAST:event_btn_HospitalActionPerformed

    private void btn_medicinasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_medicinasActionPerformed
        // TODO add your handling code here:
        if (animalDetalle != null) {
            if (medicinasAnimal != null) {
                medicinasAnimal.dispose();
            }
            medicinasAnimal = new MedicinasAnimal(animalDetalle);
            medicinasAnimal.setVisible(true);
        }
    }//GEN-LAST:event_btn_medicinasActionPerformed

    private void btn_reporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reporteActionPerformed
        // TODO add your handling code here:
        Excel excel = new Excel();
        excel.reporteAnimalGrafica(t_pesos, Grafica, animalDetalle);

    }//GEN-LAST:event_btn_reporteActionPerformed

    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed
        // TODO add your handling code here:
        this.actualizar_animal();
    }//GEN-LAST:event_btn_actualizarActionPerformed

    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed
        // TODO add your handling code here:
        this.agregar_animal();
    }//GEN-LAST:event_btn_agregarActionPerformed

    private void sexoSelector1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sexoSelector1ActionPerformed

        if (sexoSelector1.getSelectedItem().equals("Macho")) {

            semental.setEnabled(false);
            jrb_esSemental.setEnabled(true);
            jrb_esVientre.setEnabled(false);
            jrb_esVientre.setSelected(false);
        }
        if (sexoSelector1.getSelectedItem().equals("Hembra")) {

            semental.setEnabled(true);
            jrb_esSemental.setEnabled(false);
            jrb_esSemental.setSelected(false);
            this.jrb_esVientre.setEnabled(true);
        }
    }//GEN-LAST:event_sexoSelector1ActionPerformed

    private void loteSelector1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loteSelector1ActionPerformed
        this.proveedorSelector1.setFocusable(false);
        this.JDc_FechaCompra.setFocusable(false);
        this.JDc_fechaRecepcion.setFocusable(false);
        this.tf_Compra.setFocusable(false);
        this.tf_PesoCompra.setFocusable(false);
        this.tf_pesoRecepcion.setFocusable(false);
        recepcion = new Recepcion();
        recepcion.cargarPorLote(loteSelector1.getSelectedItem().toString());
        proveedorSelector1.setSelectedItem(recepcion.proveedor.descripcion);
        tf_Compra.setText(recepcion.folio);
        Double d = recepcion.peso_origen / recepcion.animales;
        if (d > 0) {
            tf_PesoCompra.setText(d.toString());
        }
        d = recepcion.peso_recepcion / recepcion.animales;
        if (d > 0) {
            tf_pesoRecepcion.setText(d.toString());
        }
        JDc_fechaRecepcion.setDate(recepcion.fecha_recepcion);
        JDc_FechaCompra.setDate(recepcion.fecha_compra);
        ((JLabel) loteSelector1.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
    }//GEN-LAST:event_loteSelector1ActionPerformed

    private void tf_consumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_consumoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_consumoActionPerformed

    private boolean validacionCodigo;

    private void validarCodigo() {

        if (!validacionCodigo) {

            validacionCodigo = true;

            String tagId = tf_AreteVisual.getText();

            if (!tagId.equals("")) {

                if (animalDetalle.arete_visual.equals(tagId)) {

                    btn_BajasMuerte.setEnabled(true);
                    btn_Hospital.setEnabled(true);
                    if (nuevo) {
                        btn_agregar.setEnabled(true);
                    } else {
                        btn_actualizar.setEnabled(true);
                    }
                    btn_capturarPeso.setEnabled(true);
                    btn_medicinas.setEnabled(true);
                    btn_pesoManual.setEnabled(true);
                    btn_quitarPeso.setEnabled(true);
                    btn_reporte.setEnabled(true);
                    return;
                }

                Animal animal2 = new Animal();

                animal2.cargarPorAreteVisualTodos(tagId);

                if (!animal2.id_animal.equals("")) {

                    JOptionPane.showMessageDialog(this, "El código IDV que intenta guardar ya existe", gs_mensaje, JOptionPane.ERROR_MESSAGE);

                    btn_BajasMuerte.setEnabled(false);
                    btn_Hospital.setEnabled(false);
                    btn_actualizar.setEnabled(false);
                    btn_agregar.setEnabled(false);
                    btn_capturarPeso.setEnabled(false);
                    btn_medicinas.setEnabled(false);
                    btn_pesoManual.setEnabled(false);
                    btn_quitarPeso.setEnabled(false);
                    btn_reporte.setEnabled(false);
                    tf_AreteVisual.requestFocus();

                } else {

                    btn_BajasMuerte.setEnabled(true);
                    btn_Hospital.setEnabled(true);

                    if (nuevo) {

                        btn_agregar.setEnabled(true);
                    } else {

                        btn_actualizar.setEnabled(true);
                    }

                    btn_capturarPeso.setEnabled(true);
                    btn_medicinas.setEnabled(true);
                    btn_pesoManual.setEnabled(true);
                    btn_quitarPeso.setEnabled(true);
                    btn_reporte.setEnabled(true);
                }
            }
            validacionCodigo = false;
        }
    }

    private void agregar_peso() {

//        Calendar fecha_actual = Calendar.getInstance();
        String tag_id;
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//        fecha_peso.set(fecha_peso.getTime().getYear() + 1900,
//                fecha_peso.getTime().getMonth(),
//                fecha_peso.getTime().getDate(),
//                fecha_actual.getTime().getHours(),
//                fecha_actual.getTime().getMinutes(),
//                fecha_actual.getTime().getSeconds());
        tag_id = tf_AreteVisual.getText();
        tf_PesoActual.setDouble(this.tf_pesoBascula.getDouble());

        if (this.tf_pesoBascula.getDouble().equals(0.0)) {
            JOptionPane.showMessageDialog(this, "El peso debe ser mayor que cero", gs_mensaje, JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (animalDetalle.id_animal.equals("")) {
            agregar_animal();
            return;
        }

        // id_animal = Integer.parseInt(tag_id);
        animalDetalle.peso_actual = tf_PesoActual.getDouble();
        animalDetalle.temperatura = tf_Temperatura.getDouble();

        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(rancho.id_rancho, "varIdRancho", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(animalDetalle.id_animal, "varIdAnimal", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(formatoDelTexto.format(fecha_peso.getTime()), "varFecha", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(animalDetalle.peso_actual.toString(), "varPeso", "DOUBLE", "IN");

        if (manejadorBD.ejecutarSP("{ call movimientoPeso(?,?,?,?) }") == 0) {

            JOptionPane.showMessageDialog(this, "Se agrego el peso Correctamente", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
            btn_agregar.setEnabled(false);
        } else {

            JOptionPane.showMessageDialog(this, "Error al agregar el peso del animal\n" + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
        }
        leerEid();
        leerAreteVisual();
    }

    private void actualizar_animal() {

        Animal semental;

        semental = new Animal();

        animalDetalle.arete_visual = tf_AreteVisual.getText();
        animalDetalle.fecha_ingreso = JDc_FechaIngreso.getCalendar().getTime();
        animalDetalle.arete_siniiga = tf_AreteSiniiga.getText();
        animalDetalle.arete_campaña = tf_AreteCampaña.getText();
        animalDetalle.fecha_compra = JDc_FechaCompra.getCalendar().getTime();
        animalDetalle.numero_lote = loteSelector1.getSelectedItem().toString();
        animalDetalle.compra = tf_Compra.getText();

        animalDetalle.proveedor.cargarPorDescripcion(proveedorSelector1.getSelectedItem().toString());
        animalDetalle.proveedor.descripcion = proveedorSelector1.getSelectedItem().toString();

        animalDetalle.peso_actual = Double.parseDouble(tf_PesoActual.getText());
        animalDetalle.temperatura = Double.parseDouble(tf_Temperatura.getText());
        animalDetalle.peso_compra = Double.parseDouble(tf_PesoCompra.getText());
        animalDetalle.corral.cargarPorNombre(corralSelector.getSelectedItem().toString());

        if (this.jrb_esSemental.isSelected()) {

            animalDetalle.es_semental = "S";
        } else {

            if (this.semental.getItemCount() > 0) {
                if (!this.semental.getSelectedItem().toString().equals("")) {

                    semental.cargarPorAreteVisual(this.semental.getSelectedItem().toString(), "A");

                    animalDetalle.semental = semental;
                }
            }
        }

        if (this.jrb_esVientre.isSelected()) {

            animalDetalle.es_vientre = "S";
        } else {
            animalDetalle.es_vientre = "N";
        }

        animalDetalle.sexo.cargarPorDescripcion(this.sexoSelector1.getSelectedItem().toString());

        animalDetalle.raza.cargarPorDescripcion(razaSelector1.getSelectedItem().toString());
        animalDetalle.raza.descripcion = razaSelector1.getSelectedItem().toString();

        if (animalDetalle.corral.id_corral.equals("")) {
            JOptionPane.showMessageDialog(this, "No ha seleccionado un corral valido", gs_mensaje, JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (animalDetalle.actualizar()) {

            JOptionPane.showMessageDialog(this, "Se actualizó el animal Correctamente", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
            btn_agregar.setEnabled(false);
            btn_actualizar.setEnabled(true);
        } else {

            JOptionPane.showMessageDialog(this, "Error al actualizar el animal\n" + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
        }

        leerEid();
    }

    private void agregar_animal() {

        animalDetalle.arete_visual = tf_AreteVisual.getText().trim();
        animalDetalle.arete_electronico = tf_Eid.getText();
        animalDetalle.fecha_ingreso = JDc_FechaIngreso.getCalendar().getTime();
        animalDetalle.arete_siniiga = tf_AreteSiniiga.getText();
        animalDetalle.arete_campaña = tf_AreteCampaña.getText();
        animalDetalle.fecha_compra = JDc_FechaCompra.getCalendar().getTime();
        animalDetalle.numero_lote = loteSelector1.getSelectedItem().toString();
        animalDetalle.compra = tf_Compra.getText();
        // animalDetalle.proveedor.cargarPorDescripcion(proveedorSelector1.getSelectedItem().toString());
        animalDetalle.proveedor.cargarPorDescripcion(proveedorSelector1.getSelectedItem().toString());
        animalDetalle.proveedor.descripcion = proveedorSelector1.getSelectedItem().toString();

        animalDetalle.peso_actual = Double.parseDouble(tf_pesoBascula.getText());
        animalDetalle.temperatura = Double.parseDouble(tf_Temperatura.getText());
        animalDetalle.peso_compra = Double.parseDouble(tf_PesoCompra.getDouble().toString());
        animalDetalle.corral.cargarPorNombre(corralSelector.getSelectedItem().toString());
        animalDetalle.fecha_recepcion = JDc_fechaRecepcion.getCalendar().getTime();
        animalDetalle.peso_recepcion = Double.parseDouble(tf_pesoRecepcion.getDouble().toString());

        if (animalDetalle.peso_actual.equals(0.0)) {

            JOptionPane.showMessageDialog(this, "Debe de agregar el peso", gs_mensaje, JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (animalDetalle.arete_visual.equals("")) {

            JOptionPane.showMessageDialog(this, "Debe de agregar el arete visual", gs_mensaje, JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (this.jrb_esSemental.isSelected()) {

            animalDetalle.es_semental = "S";
        }

        animalDetalle.sexo.cargarPorDescripcion(this.sexoSelector1.getSelectedItem().toString());

        animalDetalle.raza.cargarPorDescripcion(razaSelector1.getSelectedItem().toString());
        animalDetalle.raza.descripcion = razaSelector1.getSelectedItem().toString();

        if (this.jrb_esVientre.isSelected()) {

            animalDetalle.es_vientre = "S";
        } else {
            animalDetalle.es_vientre = "N";
        }

        if (animalDetalle.corral.id_corral.equals("")) {
            JOptionPane.showMessageDialog(this, "No ha seleccionado un corral valido", gs_mensaje, JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (animalDetalle.grabar()) {

            JOptionPane.showMessageDialog(this, "Se agrego el animal Correctamente", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
            btn_agregar.setEnabled(false);
            btn_actualizar.setEnabled(true);
            this.leerEid();
        } else {

            JOptionPane.showMessageDialog(this, "Error al agregar el animal\n" + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
        }
    }

    public void leerEid() {

        String eid;
        eid = tf_Eid.getText();

        if (eid.length() == 0) {
            leerAreteVisual();
            return;
        }

        //Cargar Valores para animal Anterior
        if (!animalDetalle.id_animal.equals("")) {

            tf_AreteVisual.setText("");
            corralSelector.setSelectedItem(animalDetalle.corral.nombre);

            JDc_FechaIngreso.setDate(animalDetalle.fecha_ingreso);
            tf_AreteSiniiga.setText("");
            tf_AreteCampaña.setText("");

            JDc_FechaCompra.setDate(animalDetalle.fecha_compra);
            loteSelector1.setSelectedItem(animalDetalle.numero_lote);
            tf_Compra.setText(animalDetalle.compra);
            proveedorSelector1.setSelectedItem(animalDetalle.proveedor.descripcion);
            tf_PesoActual.setText("0.0");
            tf_PesoCompra.setText("0.0");
            sexoSelector1.setSelectedItem(animalDetalle.sexo.descripcion);
            razaSelector1.setSelectedItem(animalDetalle.raza.descripcion);
        }

        if (!consulta) {

            animalDetalle = new Animal();
            animalDetalle.cargarPorEid(eid);
        }

        if (!animalDetalle.id_animal.equals("")) {

            tf_AreteVisual.setText(animalDetalle.arete_visual);
            corralSelector.setSelectedItem(animalDetalle.corral.nombre);
            JDc_FechaIngreso.setDate(animalDetalle.fecha_ingreso);
            tf_AreteSiniiga.setText(animalDetalle.arete_siniiga);
            tf_AreteCampaña.setText(animalDetalle.arete_campaña);
            JDc_FechaCompra.setDate(animalDetalle.fecha_compra);
            loteSelector1.addItem(animalDetalle.numero_lote);
            loteSelector1.setSelectedItem(animalDetalle.numero_lote);
            loteSelector1.setEnabled(false);
            ((JLabel) loteSelector1.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
            JDc_fechaRecepcion.setDate(animalDetalle.fecha_recepcion);
            tf_pesoRecepcion.setText(animalDetalle.peso_recepcion.toString());
            tf_Compra.setText(animalDetalle.compra);
            proveedorSelector1.setSelectedItem(animalDetalle.proveedor.descripcion);
            tf_PesoActual.setText(animalDetalle.peso_actual.toString());
            tf_Temperatura.setText(animalDetalle.temperatura.toString());
            tf_PesoCompra.setText(animalDetalle.peso_compra.toString());
            sexoSelector1.setSelectedItem(animalDetalle.sexo.descripcion);
            razaSelector1.setSelectedItem(animalDetalle.raza.descripcion);
            leerPesos(t_pesos, animalDetalle.id_animal);
            graficar();
            valoresPromedio();
            corralSelector.setEnabled(false);

            this.animalSelectorMadre.setAnimal(animalDetalle.genealogia.madre);
            this.animalSelectorPadre.setAnimal(animalDetalle.genealogia.padre);

            this.jrb_esSemental.setEnabled(false);
            this.semental.setEnabled(false);

            if (animalDetalle.sexo.descripcion.equals("Macho")) {//es Macho

                this.jrb_esSemental.setEnabled(true);
                this.jrb_esVientre.setEnabled(false);

                if (animalDetalle.es_semental.equals("S")) {

                    this.jrb_esSemental.setSelected(true);
                }

            } else {

                this.semental.setEnabled(true);
                this.jrb_esVientre.setEnabled(true);

                if (animalDetalle.semental != null) {

                    this.semental.setSelectedItem(animalDetalle.semental.arete_visual);

                } else {

                    this.semental.setSelectedItem("");
                }
            }

            if (animalDetalle.es_vientre.equals("S")) {

                this.jrb_esVientre.setSelected(true);
            }

        } else {

            //buscar el EId en todos los ranchos
            animalDetalle.cargarPorEidTodosRanchos(eid);

            if (animalDetalle.id_animal.equals("")) {

                if (dialogoConfirmacionSiNo(this, "¿El animal no existe desea ingresarlo al sistema?", gs_mensaje, 1) == 0) {

                    nuevo = true;
                    btn_agregar.setEnabled(true);
                    btn_actualizar.setEnabled(false);
                    corralSelector.setEnabled(true);
                    leerPesos(t_pesos, animalDetalle.id_animal);
                    graficar();

                    calculaPorcentaje();

                } else {

                    nuevo = false;
                    return;
                }
            } else {

                JOptionPane.showMessageDialog(this, "El Chip Elecronico ya esta capturado en otro rancho/finca", gs_mensaje, JOptionPane.ERROR_MESSAGE);
                nuevo = false;
                return;
            }
        }

        setVisible(true);
    }

    public void leerAreteVisual() {

        String areteVisual;
        areteVisual = this.tf_AreteVisual.getText();

        if (areteVisual.length() == 0) {

            return;
        }

        //Cargar Valores para animal Anterior
        if (!animalDetalle.id_animal.equals("")) {

            tf_AreteVisual.setText("");
            corralSelector.setSelectedItem(animalDetalle.corral.nombre);

            JDc_FechaIngreso.setDate(animalDetalle.fecha_ingreso);
            tf_AreteSiniiga.setText("");
            tf_AreteCampaña.setText("");

            JDc_FechaCompra.setDate(animalDetalle.fecha_compra);
            loteSelector1.setSelectedItem(animalDetalle.numero_lote);
            tf_Compra.setText(animalDetalle.compra);
            proveedorSelector1.setSelectedItem(animalDetalle.proveedor.descripcion);
            tf_PesoActual.setText("0.0");
            tf_PesoCompra.setText("0.0");
            sexoSelector1.setSelectedItem(animalDetalle.sexo.descripcion);
            razaSelector1.setSelectedItem(animalDetalle.raza.descripcion);
        }

        if (!consulta) {

            animalDetalle = new Animal();
            animalDetalle.cargarPorAreteVisual(areteVisual, "A");
        }

        if (!animalDetalle.id_animal.equals("")) {

            tf_AreteVisual.setText(animalDetalle.arete_visual);
            corralSelector.setSelectedItem(animalDetalle.corral.nombre);
            JDc_FechaIngreso.setDate(animalDetalle.fecha_ingreso);
            tf_AreteSiniiga.setText(animalDetalle.arete_siniiga);
            tf_AreteCampaña.setText(animalDetalle.arete_campaña);
            JDc_FechaCompra.setDate(animalDetalle.fecha_compra);
            JDc_fechaRecepcion.setDate(animalDetalle.fecha_recepcion);
            loteSelector1.addItem(animalDetalle.numero_lote);
            loteSelector1.setSelectedItem(animalDetalle.numero_lote);
            loteSelector1.setEnabled(false);
            ((JLabel) loteSelector1.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
            tf_pesoRecepcion.setText(animalDetalle.peso_recepcion.toString());
            tf_Compra.setText(animalDetalle.compra);
            proveedorSelector1.setSelectedItem(animalDetalle.proveedor.descripcion);
            tf_PesoActual.setText(animalDetalle.peso_actual.toString());
            tf_Temperatura.setText(animalDetalle.temperatura.toString());
            tf_PesoCompra.setText(animalDetalle.peso_compra.toString());
            sexoSelector1.setSelectedItem(animalDetalle.sexo.descripcion);
            razaSelector1.setSelectedItem(animalDetalle.raza.descripcion);
            leerPesos(t_pesos, animalDetalle.id_animal);
            valoresPromedio();
            graficar();
            corralSelector.setEnabled(false);

            this.jrb_esSemental.setEnabled(false);
            this.semental.setEnabled(false);

            this.animalSelectorMadre.setAnimal(animalDetalle.genealogia.madre);
            this.animalSelectorPadre.setAnimal(animalDetalle.genealogia.padre);

            if (animalDetalle.sexo.descripcion.equals("Macho")) {//es Macho

                this.jrb_esSemental.setEnabled(true);
                this.jrb_esVientre.setEnabled(false);

                if (animalDetalle.es_semental.equals("S")) {

                    this.jrb_esSemental.setSelected(true);
                }
            } else {
                this.semental.setEnabled(true);
                this.jrb_esVientre.setEnabled(true);

                if (animalDetalle.semental != null) {

                    this.semental.setSelectedItem(animalDetalle.semental.arete_visual);
                } else {

                    this.semental.setSelectedItem("");
                }
            }

            if (animalDetalle.es_vientre.equals("S")) {

                this.jrb_esVientre.setSelected(true);
            }
        } else {

            if (dialogoConfirmacionSiNo(this,"Este IDE no existe. \n"
                    + "¿Desea ingresarlo al sistema como nuevo animal o emparejar con un animal sin IDE?", gs_mensaje, 1) == 0) {

                nuevo = true;
                btn_agregar.setEnabled(true);
                btn_actualizar.setEnabled(false);
                corralSelector.setEnabled(true);
                leerPesos(t_pesos, animalDetalle.id_animal);
                graficar();

                calculaPorcentaje();

            } else {

                nuevo = false;
                return;
            }
            /*
             } else {

             JOptionPane.showMessageDialog(this, "El Chip Elecronico ya esta capturado en otro rancho/finca", gs_mensaje, JOptionPane.ERROR_MESSAGE);
             nuevo = false;
             return;
             }
             */
        }

        setVisible(true);
    }

    private void calculaPorcentaje() {

        Double peso, porcentaje;
        peso = tf_pesoBascula.getDouble();
        tf_PesoCompra.setDouble(peso);
    }

    /**
     * @param id_animal the id_animal to set
     */
    public void setId_animal(String id_animal) {

        animalDetalle = new Animal();
        //id_animal = id_animal;
        animalDetalle.cargarPorId(id_animal);
        //String l_eid;
        //l_eid = EEid(id_animal);
        if (animalDetalle.arete_electronico.length() == 0) {

            this.tf_AreteVisual.setText(animalDetalle.arete_visual);
            leerAreteVisual();
        } else {

            this.tf_Eid.setText(animalDetalle.arete_electronico);
            leerEid();
        }
    }

    public void setEid(String AEid) {

        corralSelector.addArray(cargarCorrales());
        this.tf_Eid.setText(AEid);
        leerEid();
    }

    public void setPeso(Double aPeso) {

        tf_pesoBascula.setText(aPeso.toString());
    }

    public void setAretevisual(String aAreteVisual) {

        this.tf_AreteVisual.setText(aAreteVisual);
        leerAreteVisual();
    }

    private void cargarComponentes() {

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension pantallaTamano = tk.getScreenSize();
//        setSize(pantallaTamano); /*Dar tamaño máximo a la pantalla*/
        fondo1.cargar(pantallaTamano);
//        jPanel1.setLocation((pantallaTamano.width / 2) - (jPanel1.getWidth() / 2), (pantallaTamano.height / 2) - (jPanel1.getHeight() / 2));
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false); /*Boton de minimizar*/

    }

    public Calendar fecha_peso;
    public static Animal animalDetalle;
    public boolean nuevo;
    private PesoManual pesoManual;
    private MedicinasAnimal medicinasAnimal;
    private BajasMuerte bajasMuerte;
    private RegistroEmpadre registroEmpadre;
    private HistorialPartos historialPartos;
    private Hospital hospital;
    private String puertoStick;
    private String puertoBascula;
    private JFreeChart Grafica;
    private ChartPanel jP_Grafica;
    Desktop parent;
    private Corral corral;
    Recepcion recepcion;
    private Date fecha_reg_csv;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private abstractt.Calendar JDc_FechaCompra;
    private abstractt.Calendar JDc_FechaIngreso;
    private abstractt.Calendar JDc_fechaRecepcion;
    private domain.AnimalSelector animalSelectorMadre;
    private domain.AnimalSelector animalSelectorPadre;
    private abstractt.Boton btn_BajasMuerte;
    private abstractt.Boton btn_Hospital;
    private abstractt.Boton btn_actualizar;
    private abstractt.Boton btn_agregar;
    private javax.swing.JButton btn_capturarPeso;
    private abstractt.Boton btn_medicinas;
    private abstractt.Boton btn_pesoManual;
    private javax.swing.JButton btn_quitarPeso;
    private abstractt.Boton btn_reporte;
    private abstractt.ComboBox corralSelector;
    private abstractt.Etiqueta etiqueta1;
    private abstractt.Etiqueta etiqueta2;
    private abstractt.Etiqueta etiqueta3;
    private abstractt.fondo fondo1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton jrb_esSemental;
    private javax.swing.JRadioButton jrb_esVientre;
    private domain.LoteSelector loteSelector1;
    private javax.swing.JPanel panelGrafica;
    private javax.swing.JPanel pn_Formulario;
    private javax.swing.JPanel pn_Grafica_Tabla;
    private javax.swing.JPanel pn_Peso;
    private javax.swing.JPanel pn_botonesVerticales;
    private domain.ProveedorSelector proveedorSelector1;
    private domain.RazaSelector razaSelector1;
    private domain.AnimalSelector semental;
    private domain.SexoSelector sexoSelector1;
    private abstractt.Table t_pesos;
    private abstractt.TextField tf_AreteCampaña;
    private abstractt.TextField tf_AreteSiniiga;
    private abstractt.TextField tf_AreteVisual;
    private javax.swing.JTextField tf_Compra;
    private abstractt.TextField tf_Eid;
    private abstractt.TextField tf_PesoActual;
    private abstractt.TextField tf_PesoCompra;
    private abstractt.TextField tf_Temperatura;
    private abstractt.TextField tf_consumo;
    private abstractt.TextField tf_ganancia;
    private abstractt.TextField tf_merma;
    private abstractt.TextField tf_pesoBascula;
    private abstractt.TextField tf_pesoRecepcion;
    // End of variables declaration//GEN-END:variables

    /**
     * @param consulta the consulta to set
     */
    public void setConsulta(boolean consulta) {

        this.consulta = consulta;
    }

    public void setFechaRegCsv(Date fecha_reg_csv) {

        this.fecha_reg_csv = fecha_reg_csv;
        this.JDc_FechaIngreso.setDate(fecha_reg_csv);
    }

    private void valoresPromedio() {
        tf_ganancia.setText(animalDetalle.ganancia_promedio.toString());
        this.tf_consumo.setText(animalDetalle.promedio_alimentacion.toString());
        this.tf_merma.setText(animalDetalle.porcentaje_merma.toString());
    }
}
