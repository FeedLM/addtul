/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import abstractt.Calendar;
import abstractt.Table;
import domain.Animal;
import domain.Corral;
import static domain.Corral.cargarCorrales;
import domain.Excel;
import domain.Grafica;
import domain.ParametrosSP;
import domain.Raza;
import static domain.Raza.cargarRazasTodas;
import domain.SR232;
import static domain.Seguridad.pideContraseña;
import domain.Sexo;
import static gui.Desktop.manejadorBD;
import static gui.Desktop.rancho;
import static gui.Login.gs_mensaje;
import static gui.Splash.formatoDate;
import static gui.Splash.formatoDateTime;
import static gui.Splash.formatoDateTime_1;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import static org.apache.poi.hssf.usermodel.HeaderFooter.date;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

/**
 *
 * @author Gilberto Adan González Silva
 */
public class AdministracionGrupos extends javax.swing.JFrame { // {

    private String id_animal;
    private SR232 stick;
    private domain.Grafica g;

    /**
     * Creates new form AdministracionGrupos
     */
    public AdministracionGrupos(Desktop aparent) {
//         super(aparent, true);
        this.parent = aparent;
        initComponents();
        setLocationRelativeTo(null);

//        setClosable(true);
//        this.pack();
//        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/resources/logo tru-test.png")));
        cargarPuertos();

        cargarStick();

        corralSelector.valor_nuevo = true;
        razaSelector.valor_nuevo = true;

        Frame F = JOptionPane.getFrameForComponent(this);
        reporteEntradas = new ReporteEntradas(F);

        String titulos[] = {
            "Id Animal", "Arete Visual", "Arete Electronico",
            "Proveedor", "Fecha de Compra", "Arete Siniiga",
            "Arete Campaña", "Sexo", "Ingreso al Corral",
            "Numero de Lote", "No. Compra", "Peso Actual",
            "Peso de Compra"};

        t_animales.setTitulos(titulos);
        t_animales.cambiarTitulos();

        t_animales.setFormato(new int[]{
            Table.letra, Table.letra, Table.letra,
            Table.letra, Table.fecha, Table.letra,
            Table.letra, Table.letra, Table.fecha,
            Table.numero_double, Table.letra, Table.numero_double,
            Table.numero_double});

        t_animales.ocultarcolumna(0);

        corralActivo = true;

        //   graficar();
        ListSelectionModel lsm = this.t_animales.getSelectionModel();

        lsm.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                selectAnimal();
            }
        });

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

        jPanel1 = new ChartPanel(Grafica);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 216, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 186, Short.MAX_VALUE)
        );

        panelGrafica.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, panelGrafica.getWidth(), panelGrafica.getHeight()));

        cargarDatosIniciales();

        graficar();

//        Image i = null;
//        i = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/logo tru-test.png"));
//        setIconImage(i);
        this.t_alimentoIngresado.textFieldDouble();
        this.t_pesoMaximo.textFieldDouble();
        this.t_pesoMinimo.textFieldDouble();
        this.t_pesoPromedio.textFieldDouble();
        this.t_totalKilos.textFieldDouble();

        this.setTitle(this.getTitle() + " " + rancho.descripcion);

        cargarComponentes();

    }

    public void cargarStick() {

        stick = new SR232(puertoStick, 3, parent, 1);

        if (!stick.puertoDisponible()) {

            JOptionPane.showMessageDialog(this, "No se pudo conectar al puerto serie " + puertoStick + "\n las opciones de entrada estaran deshabilitadas ", gs_mensaje, JOptionPane.ERROR_MESSAGE);
            return;
        }
        stick.start();
    }

    public void cargarDatosIniciales() {
        System.out.println("Entrada a metodo------");
        cargandoDatos = true;
        corralSelector.addArray(cargarCorrales());
        sexoSelector1.cargarTodos();

        razaSelector.addArray(cargarRazasTodas());
        jtf_localizacion.setText("");
        this.jta_Observaciones.setText("");
        cargandoDatos = false;

        this.t_animales.limpiarTabla();
        this.t_pesos.limpiarTabla();
        t_animales.ocultarcolumna(0);
        this.graficar();
    }

    public void cargarPuertos() {

        manejadorBD.consulta(
                "SELECT puerto_baston, puerto_bascula "
                + "FROM configuracion ");

        if (manejadorBD.getRowCount() > 0) {

            puertoStick = manejadorBD.getValorString(0, 0);
            puertoBascula = manejadorBD.getValorString(0, 1);
        }
    }

    String puertoStick, puertoBascula;

    private void selectAnimal() {

        Integer fila = t_animales.getSelectedRow();

        if (fila >= 0) {

            id_animal = t_animales.getValueAt(fila, 0).toString();

//            leerPesos(t_pesos, id_animal);
            btn_detalles.setEnabled(true);

        } else {
            btn_detalles.setEnabled(false);
        }

        //graficar();
    }

    public void graficar() {

        if (t_pesos != null && t_pesos.getRowCount() > 0) {

            Grafica = g.createChart(g.createDatasetPesos(t_pesos));
            jPanel1.setChart(Grafica);
        }
    }

    JFreeChart Grafica;
    ChartPanel jPanel1;
    Table t_pesos;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        etiqueta1 = new abstractt.Etiqueta();
        datosPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jtf_localizacion = new javax.swing.JTextField();
        jtf_numAnimales = new javax.swing.JTextField();
        corralSelector = new abstractt.ComboBox();
        razaSelector = new abstractt.ComboBox();
        tf_Eid = new abstractt.TextField();
        tf_PesoActual = new abstractt.TextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        t_pesoPromedio = new abstractt.TextField();
        t_alimentoIngresado = new abstractt.TextField();
        t_totalKilos = new abstractt.TextField();
        t_pesoMinimo = new abstractt.TextField();
        t_pesoMaximo = new abstractt.TextField();
        t_gananciaKilos = new abstractt.TextField();
        jLabel16 = new javax.swing.JLabel();
        sexoSelector1 = new domain.SexoSelector();
        jLabel6 = new javax.swing.JLabel();
        jta_Observaciones = new javax.swing.JTextArea();
        jLabel17 = new javax.swing.JLabel();
        panelGrafica = new javax.swing.JPanel();
        botonesHoriz = new javax.swing.JPanel();
        btn_cierreCorral = new abstractt.Boton();
        btn_historico = new abstractt.Boton();
        btn_reporteCorral = new abstractt.Boton();
        btn_reporteEntradas = new abstractt.Boton();
        btn_detalles = new abstractt.Boton();
        botonesVert = new javax.swing.JPanel();
        btn_Crear = new abstractt.Boton();
        btn_Actualizar = new abstractt.Boton();
        btn_Eliminar = new abstractt.Boton();
        btn_Animal = new abstractt.Boton();
        btn_busqueda = new abstractt.Boton();
        jScrollPane1 = new javax.swing.JScrollPane();
        t_animales = new abstractt.Table();
        btn_IngresoAlimento = new abstractt.Boton();
        etiqueta2 = new abstractt.Etiqueta();
        fondo1 = new abstractt.fondo();

        setTitle("Administración de Grupos");

        jPanel6.setBackground(new java.awt.Color(238, 217, 170));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        etiqueta1.setBackground(new java.awt.Color(95, 84, 88));
        etiqueta1.setForeground(new java.awt.Color(230, 225, 195));
        etiqueta1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiqueta1.setText("Administracion de Grupos");
        etiqueta1.setFont(new java.awt.Font("Trebuchet MS", 1, 48)); // NOI18N
        etiqueta1.setOpaque(true);
        jPanel6.add(etiqueta1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1310, 80));

        datosPanel.setBackground(new java.awt.Color(255, 255, 255));
        datosPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        datosPanel.setOpaque(false);
        datosPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(95, 84, 88));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Corral:");
        datosPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 80, 22));

        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(95, 84, 88));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Localización:");
        datosPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 80, 22));

        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(95, 84, 88));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("No. de Animales:");
        datosPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 100, 110, 22));

        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(95, 84, 88));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Sexo:");
        datosPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 80, 22));

        jtf_localizacion.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jtf_localizacion.setForeground(new java.awt.Color(64, 37, 4));
        jtf_localizacion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        datosPanel.add(jtf_localizacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, 140, -1));

        jtf_numAnimales.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jtf_numAnimales.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtf_numAnimales.setFocusable(false);
        datosPanel.add(jtf_numAnimales, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 100, 100, -1));

        corralSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                corralSelectorActionPerformed(evt);
            }
        });
        datosPanel.add(corralSelector, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 140, 22));

        razaSelector.setEditable(false);
        razaSelector.setEnabled(false);
        razaSelector.setFocusable(false);
        razaSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                razaSelectorActionPerformed(evt);
            }
        });
        datosPanel.add(razaSelector, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 140, -1));

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
        datosPanel.add(tf_Eid, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        tf_PesoActual.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tf_PesoActualFocusGained(evt);
            }
        });
        datosPanel.add(tf_PesoActual, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        jLabel11.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(95, 84, 88));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Total Kilos Ganado:");
        datosPanel.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, 110, 22));

        jLabel12.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(95, 84, 88));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Peso Minimo:");
        datosPanel.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 130, 110, 22));

        jLabel13.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(95, 84, 88));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Peso Maximo:");
        datosPanel.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 160, 110, 22));

        jLabel14.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(95, 84, 88));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Peso Promedio:");
        datosPanel.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 190, 110, 22));

        jLabel15.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(95, 84, 88));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Alimento Ingresado:");
        datosPanel.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 120, 22));

        t_pesoPromedio.setText("0.00");
        t_pesoPromedio.setFocusable(false);
        datosPanel.add(t_pesoPromedio, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 190, 100, 22));

        t_alimentoIngresado.setText("0.00");
        t_alimentoIngresado.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        t_alimentoIngresado.setFocusable(false);
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
        datosPanel.add(t_alimentoIngresado, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, 100, 22));

        t_totalKilos.setText("0.00");
        t_totalKilos.setFocusable(false);
        datosPanel.add(t_totalKilos, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 40, 100, 22));

        t_pesoMinimo.setText("0.00");
        t_pesoMinimo.setFocusable(false);
        datosPanel.add(t_pesoMinimo, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 130, 100, 22));

        t_pesoMaximo.setText("0.00");
        t_pesoMaximo.setFocusable(false);
        datosPanel.add(t_pesoMaximo, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 160, 100, 22));

        t_gananciaKilos.setText("0.00");
        t_gananciaKilos.setFocusable(false);
        datosPanel.add(t_gananciaKilos, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 70, 100, 22));

        jLabel16.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(95, 84, 88));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Ganancia Kilos:");
        datosPanel.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 70, 110, 22));

        sexoSelector1.setEditable(false);
        sexoSelector1.setFocusable(false);
        sexoSelector1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sexoSelector1ActionPerformed(evt);
            }
        });
        datosPanel.add(sexoSelector1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 140, -1));

        jLabel6.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(95, 84, 88));
        jLabel6.setText("Observaciones:");
        datosPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 93, 22));

        jta_Observaciones.setColumns(20);
        jta_Observaciones.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jta_Observaciones.setLineWrap(true);
        jta_Observaciones.setRows(3);
        jta_Observaciones.setToolTipText("");
        jta_Observaciones.setWrapStyleWord(true);
        jta_Observaciones.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        datosPanel.add(jta_Observaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 250, 70));

        jLabel17.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(95, 84, 88));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Raza:");
        datosPanel.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 80, 22));

        jPanel6.add(datosPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, 600, 230));

        panelGrafica.setBackground(new java.awt.Color(0, 102, 102));
        panelGrafica.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel6.add(panelGrafica, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 100, 400, 220));

        botonesHoriz.setOpaque(false);

        btn_cierreCorral.setText("Cierre");
        btn_cierreCorral.setPreferredSize(new java.awt.Dimension(69, 45));
        btn_cierreCorral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cierreCorralActionPerformed(evt);
            }
        });
        botonesHoriz.add(btn_cierreCorral);

        btn_historico.setText("Historico de Corrales");
        btn_historico.setFont(new java.awt.Font("Trebuchet", 0, 10)); // NOI18N
        btn_historico.setPreferredSize(new java.awt.Dimension(130, 45));
        btn_historico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_historicoActionPerformed(evt);
            }
        });
        botonesHoriz.add(btn_historico);

        btn_reporteCorral.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icono reporte corral.png"))); // NOI18N
        btn_reporteCorral.setToolTipText("Reporte de Corral");
        btn_reporteCorral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_reporteCorralActionPerformed(evt);
            }
        });
        botonesHoriz.add(btn_reporteCorral);

        btn_reporteEntradas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icono reporte de entradas.png"))); // NOI18N
        btn_reporteEntradas.setToolTipText("Reporte de Animales ingresados");
        btn_reporteEntradas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_reporteEntradasActionPerformed(evt);
            }
        });
        botonesHoriz.add(btn_reporteEntradas);

        btn_detalles.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icono detalles.jpg"))); // NOI18N
        btn_detalles.setToolTipText("Especificaciones del Animal");
        btn_detalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_detallesActionPerformed(evt);
            }
        });
        botonesHoriz.add(btn_detalles);

        jPanel6.add(botonesHoriz, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 330, 440, -1));

        botonesVert.setOpaque(false);
        botonesVert.setPreferredSize(new java.awt.Dimension(80, 300));
        botonesVert.setLayout(new javax.swing.BoxLayout(botonesVert, javax.swing.BoxLayout.Y_AXIS));

        btn_Crear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icono crear.png"))); // NOI18N
        btn_Crear.setToolTipText("Crear un Corral");
        btn_Crear.setMaximumSize(new java.awt.Dimension(70, 50));
        btn_Crear.setMinimumSize(new java.awt.Dimension(70, 50));
        btn_Crear.setPreferredSize(new java.awt.Dimension(70, 50));
        btn_Crear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CrearActionPerformed(evt);
            }
        });
        botonesVert.add(btn_Crear);

        btn_Actualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icono actualizar.png"))); // NOI18N
        btn_Actualizar.setToolTipText("Actualizar el Corral");
        btn_Actualizar.setMaximumSize(new java.awt.Dimension(70, 50));
        btn_Actualizar.setMinimumSize(new java.awt.Dimension(70, 50));
        btn_Actualizar.setPreferredSize(new java.awt.Dimension(70, 50));
        btn_Actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ActualizarActionPerformed(evt);
            }
        });
        botonesVert.add(btn_Actualizar);

        btn_Eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icono eliminar.png"))); // NOI18N
        btn_Eliminar.setToolTipText("Borrar el Corral");
        btn_Eliminar.setMaximumSize(new java.awt.Dimension(70, 50));
        btn_Eliminar.setMinimumSize(new java.awt.Dimension(70, 50));
        btn_Eliminar.setPreferredSize(new java.awt.Dimension(70, 50));
        btn_Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EliminarActionPerformed(evt);
            }
        });
        botonesVert.add(btn_Eliminar);

        btn_Animal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icono animal.png"))); // NOI18N
        btn_Animal.setToolTipText("Borrar Animal");
        btn_Animal.setMaximumSize(new java.awt.Dimension(70, 50));
        btn_Animal.setMinimumSize(new java.awt.Dimension(70, 50));
        btn_Animal.setPreferredSize(new java.awt.Dimension(70, 50));
        btn_Animal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AnimalActionPerformed(evt);
            }
        });
        botonesVert.add(btn_Animal);

        btn_busqueda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icono buscar.png"))); // NOI18N
        btn_busqueda.setToolTipText("Búsqueda de animales");
        btn_busqueda.setMaximumSize(new java.awt.Dimension(60, 50));
        btn_busqueda.setPreferredSize(new java.awt.Dimension(70, 50));
        btn_busqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_busquedaActionPerformed(evt);
            }
        });
        botonesVert.add(btn_busqueda);

        jPanel6.add(botonesVert, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 90, 60, 250));

        t_animales.setForeground(new java.awt.Color(230, 225, 195));
        t_animales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8 ", "Title 9", "Title 10", "Title 11", "Title 12", "Title 13"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        t_animales.setGridColor(new java.awt.Color(95, 84, 88));
        jScrollPane1.setViewportView(t_animales);
        if (t_animales.getColumnModel().getColumnCount() > 0) {
            t_animales.getColumnModel().getColumn(0).setResizable(false);
            t_animales.getColumnModel().getColumn(1).setResizable(false);
            t_animales.getColumnModel().getColumn(2).setResizable(false);
            t_animales.getColumnModel().getColumn(3).setResizable(false);
            t_animales.getColumnModel().getColumn(4).setResizable(false);
            t_animales.getColumnModel().getColumn(5).setResizable(false);
            t_animales.getColumnModel().getColumn(6).setResizable(false);
            t_animales.getColumnModel().getColumn(7).setResizable(false);
            t_animales.getColumnModel().getColumn(8).setResizable(false);
            t_animales.getColumnModel().getColumn(9).setResizable(false);
            t_animales.getColumnModel().getColumn(10).setResizable(false);
            t_animales.getColumnModel().getColumn(11).setResizable(false);
            t_animales.getColumnModel().getColumn(12).setResizable(false);
        }

        jPanel6.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 385, 1310, 300));

        btn_IngresoAlimento.setText("Ingreso Alimento");
        btn_IngresoAlimento.setFont(new java.awt.Font("Trebuchet", 0, 10)); // NOI18N
        btn_IngresoAlimento.setPreferredSize(new java.awt.Dimension(130, 45));
        btn_IngresoAlimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_IngresoAlimentoActionPerformed(evt);
            }
        });
        jPanel6.add(btn_IngresoAlimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 350, 120, 23));

        etiqueta2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiqueta2.setText("Listado de animales");
        etiqueta2.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jPanel6.add(etiqueta2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, 790, 40));

        fondo1.setText("fondo1");
        jPanel6.add(fondo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void corralSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_corralSelectorActionPerformed
       
        if (cargandoDatos) {

            return;
        }
        cargarAnimalesCorral();
        cargarPesajesCorral();
    }//GEN-LAST:event_corralSelectorActionPerformed

    private Corral corral;

    private void creaRaza() {

        //String raza;
        //raza = razaSelector.getSelectedItem().toString();
        //Raza Nueva
        if (corral.raza.id_raza.equals("") && !corral.raza.descripcion.equals("")) {

            manejadorBD.parametrosSP = new ParametrosSP();

            manejadorBD.parametrosSP.agregarParametro(corral.raza.descripcion, "varRaza", "STRING", "IN");

            if (manejadorBD.ejecutarSP("{ call agregarRaza(?) }") == 0) {

                /*
                 manejadorBD.consulta(""
                 + "SELECT id_raza "
                 + "FROM   raza "
                 + "WHERE  descripcion = '" + raza + "'");

                 if (manejadorBD.getRowCount() > 0) {

                 id_raza = manejadorBD.getValorInt(0, 0);
                 }
                 */
            } else {

                JOptionPane.showMessageDialog(this, "Error al Agregar la Nueva Raza\n" + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    private void cargarAnimalesCorral() {
        
        if (!corralActivo) {

            return;
        }

        corral = new Corral();

        String nombre = corralSelector.getSelectedItem().toString();

        corral.cargarPorNombre(nombre);

        if (!corral.id_corral.equals("")) {

            this.btn_Crear.setEnabled(false);
            this.btn_Actualizar.setEnabled(true);
            this.btn_Eliminar.setEnabled(true);

            t_totalKilos.setDouble(corral.total_kilos);
            t_pesoMinimo.setDouble(corral.peso_minimo);
            t_pesoMaximo.setDouble(corral.peso_maximo);
            t_pesoPromedio.setDouble(corral.peso_promedio);
            t_alimentoIngresado.setDouble(corral.alimento_ingresado);
            t_gananciaKilos.setDouble(corral.peso_ganancia);

            jtf_localizacion.setText(corral.localizacion);
            jtf_numAnimales.setText(corral.numero_anmales.toString());

            razaSelector.setSelectedItem(corral.raza.descripcion);
            sexoSelector1.setSelectedItem(corral.sexo.descripcion);
            this.jta_Observaciones.setText(corral.observaciones);

            manejadorBD.consulta(""
                    + "SELECT animal.id_animal,         animal.arete_visual,\n"
                    + "       animal.arete_electronico, p.descripcion,\n"
                    + "       animal.fecha_ingreso,     animal.arete_siniiga,\n"
                    + "       animal.arete_campaña,     sexo.descripcion,\n"
                    + "       animal.fecha_compra,      animal.numero_lote,\n"
                    + "       animal.compra,            animal.peso_actual,\n"
                    + "       animal.peso_compra\n"
                    + "FROM   corral, corral_animal,\n"
                    + "       ( animal LEFT OUTER JOIN proveedor p ON animal.id_proveedor = p.id_proveedor )\n"
                    + "        LEFT OUTER JOIN sexo ON  animal.id_sexo  = sexo.id_sexo\n"
                    + "WHERE corral.id_corral    = corral_animal.id_corral\n"
                    + "AND   animal.id_animal    = corral_animal.id_animal\n"
                    + "AND   animal.status = 'A'\n"
                    + "AND   corral.id_corral    = '" + corral.id_corral + "'");

            manejadorBD.asignarTable(t_animales);

            if (manejadorBD.getRowCount() > 0) {

                this.t_animales.setRowSelectionInterval(0, 0);
                selectAnimal();
                this.btn_Animal.setEnabled(true);
                btn_reporteCorral.setEnabled(true);
            } else {
                this.btn_Animal.setEnabled(false);
                btn_reporteCorral.setEnabled(false);
                this.t_pesos.limpiarTabla();
                graficar();
            }
            t_animales.ocultarcolumna(0);

        } else {
            this.btn_Crear.setEnabled(true);
            this.btn_Actualizar.setEnabled(false);
            this.btn_Eliminar.setEnabled(false);
            this.btn_Animal.setEnabled(false);
            btn_reporteCorral.setEnabled(false);
            limpiarVariables();
        }
        if (t_pesos.getRowCount() > 0) {

            this.btn_detalles.setEnabled(true);
        } else {
            this.btn_detalles.setEnabled(false);
        }
    }

    private void cargarPesajesCorral() {

        this.t_pesos.limpiarTabla();

        if (!corral.id_corral.equals("")) {

            corral.cargarHistoricoPesos(this.t_pesos);

            graficar();
        }
    }


    private void razaSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_razaSelectorActionPerformed

        if (cargandoDatos) {

            return;
        }
    }//GEN-LAST:event_razaSelectorActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed

    }//GEN-LAST:event_formWindowClosed

    private void tf_EidFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tf_EidFocusLost

        if (true) {

            return;
        }

        String Eid;
        Double peso;

        Eid = tf_Eid.getText();
        //System.out.println("Eid: "+Eid);
        if (Eid.equals("")) {

            return;
        }
        /*
         this.stick.setSeguir(false);
         this.stick.cerrarPuerto();
         */
        peso = this.tf_PesoActual.getDouble();

        if (parent.especificacionesAnimal != null) {

            parent.especificacionesAnimal.dispose();
        }
        parent.especificacionesAnimal = new EspecificacionesAnimal(parent);

        parent.especificacionesAnimal.setPeso(peso);
        parent.especificacionesAnimal.setEid(Eid);

        parent.especificacionesAnimal.setVisible(true);

        this.tf_PesoActual.setDouble(new Double(0.0));
        this.tf_Eid.setText("");

    }//GEN-LAST:event_tf_EidFocusLost

    private void tf_PesoActualFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tf_PesoActualFocusGained
    }//GEN-LAST:event_tf_PesoActualFocusGained

    private void tf_EidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_EidActionPerformed

    }//GEN-LAST:event_tf_EidActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.stick.setSeguir(false);

    }//GEN-LAST:event_formWindowClosing

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus

//        this.status.setText(" Listo...");
        if (cargandoDatos) {

            return;
        }

        cargarAnimalesCorral();
    }//GEN-LAST:event_formWindowGainedFocus

    private void formWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowLostFocus
    }//GEN-LAST:event_formWindowLostFocus

    private void btn_DatosCorral() {
        CapturaDatosCorral datosCorral;
        Corral corral = new Corral();

        corral.cargarPorNombre(this.corralSelector.getSelectedItem().toString());
        datosCorral = new CapturaDatosCorral(corral);
        datosCorral.setVisible(true);
    }

    private void t_alimentoIngresadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_alimentoIngresadoActionPerformed

        alimento_ingresado = this.t_alimentoIngresado.getDouble();
    }//GEN-LAST:event_t_alimentoIngresadoActionPerformed

    private void t_alimentoIngresadoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_alimentoIngresadoFocusLost

        alimento_ingresado = this.t_alimentoIngresado.getDouble();
    }//GEN-LAST:event_t_alimentoIngresadoFocusLost

    private void btn_detallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_detallesActionPerformed
        // TODO add your handling code here:

        if (especificacionesAnimal != null) {

            especificacionesAnimal.dispose();
        }

        especificacionesAnimal = new EspecificacionesAnimal(parent);

        if (!id_animal.equals("")) {

            especificacionesAnimal.setId_animal(id_animal);
        }

        especificacionesAnimal.setVisible(true);

    }//GEN-LAST:event_btn_detallesActionPerformed

    private void btn_EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EliminarActionPerformed

        int opcion;

        opcion = JOptionPane.showConfirmDialog(this, "Desea Borrar el Corral?", gs_mensaje, JOptionPane.YES_NO_OPTION);

        if (opcion == 0) {

            if (!pideContraseña()) {

                return;
            }

            if (manejadorBD.actualizacion(
                    "UPDATE corral "
                    + "SET status = 'N' "
                    + "WHERE id_corral = '" + corral.id_corral + "'") == 0) {

                JOptionPane.showMessageDialog(this, "Se elimino el corral correctamente", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
                cargarDatosIniciales();
                corralActivo = false;
                cargarCorrales();
                corralActivo = true;
            } else {

                JOptionPane.showMessageDialog(this, "Error al eliminar el corral " + manejadorBD.errorSQL, gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_btn_EliminarActionPerformed

    private void btn_busquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_busquedaActionPerformed
        if (busquedaAnimal != null) {
            busquedaAnimal.dispose();
        }
        busquedaAnimal = new BusquedaAnimal(parent);
//        this.dispose();
        busquedaAnimal.setVisible(true);
    }//GEN-LAST:event_btn_busquedaActionPerformed

    private void btn_CrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CrearActionPerformed

        corral.nombre = corralSelector.getSelectedItem().toString();

        if (corral.nombre.equals("")) {

            return;
        }

        if (!pideContraseña()) {

            return;
        }
        corral.localizacion = jtf_localizacion.getText();
        corral.nombre = corralSelector.getSelectedItem().toString();
        corral.alimento_ingresado = this.t_alimentoIngresado.getDouble();
        corral.observaciones = this.jta_Observaciones.getText();

        if (corral.id_corral.equals("")) {

            corral.localizacion = jtf_localizacion.getText();

            if (corral.grabar()) {

                JOptionPane.showMessageDialog(this, "Se Creo el corral Correctamente", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
            } else {

                JOptionPane.showMessageDialog(this, "Error al crear el corral", gs_mensaje, JOptionPane.ERROR_MESSAGE);
            }

            cargarDatosIniciales();
            
            corralActivo = false;
            cargarCorrales();
            corralActivo = true;
            corralSelector.setSelectedItem(corral.nombre);
        } else {

        }

    }//GEN-LAST:event_btn_CrearActionPerformed

    private void btn_ActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ActualizarActionPerformed

        if (!pideContraseña()) {

            return;
        }

        corral.localizacion = jtf_localizacion.getText();
        corral.nombre = corralSelector.getSelectedItem().toString();
        corral.alimento_ingresado = alimento_ingresado;
        if (alimento_ingresado == null) {
            corral.alimento_ingresado = this.t_alimentoIngresado.getDouble();
        }

        corral.observaciones = this.jta_Observaciones.getText();

        if (corral.actualizar()) {

            JOptionPane.showMessageDialog(this, "Se actualizo el corral Correctamente", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);

        } else {

            JOptionPane.showMessageDialog(this, "Error al actualizar el corral", gs_mensaje, JOptionPane.ERROR_MESSAGE);
        }

        corralActivo = false;
        cargarCorrales();
        corralActivo = true;
        cargandoDatos = true;
        razaSelector.addArray(cargarRazasTodas());
        cargandoDatos = false;
        corralSelector.setSelectedItem(corral.nombre);

    }//GEN-LAST:event_btn_ActualizarActionPerformed

    private void btn_AnimalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AnimalActionPerformed

        int opcion;

        Animal animalSeleccionado;

        Integer fila = t_animales.getSelectedRow();

        if (fila >= 0) {

            animalSeleccionado = new Animal();
            animalSeleccionado.cargarPorId(id_animal);
        } else {
            return;
        }

        opcion = JOptionPane.showConfirmDialog(this, "¿Desea Borrar el Animal?", gs_mensaje, JOptionPane.YES_NO_OPTION);

        if (opcion != 0) {

            return;
        }

        if (!pideContraseña()) {

            return;
        }

        manejadorBD.parametrosSP = new ParametrosSP();
        manejadorBD.parametrosSP.agregarParametro(animalSeleccionado.id_animal, "varIdAnimal", "STRING", "IN");

        if (manejadorBD.ejecutarSP("{ call eliminarAnimal(?) }") == 0) {

            JOptionPane.showMessageDialog(this, "Se elimino el animal correctamente", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
            this.cargarAnimalesCorral();
        } else {

            JOptionPane.showMessageDialog(this, "Error al eliminar el animal", gs_mensaje, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_AnimalActionPerformed

    private void btn_reporteEntradasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reporteEntradasActionPerformed

        if (reporteEntradas != null) {
            reporteEntradas.dispose();
        }

        reporteEntradas.setVisible(true);

    }//GEN-LAST:event_btn_reporteEntradasActionPerformed

    private void btn_reporteCorralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reporteCorralActionPerformed
        reporteCorral();
    }//GEN-LAST:event_btn_reporteCorralActionPerformed

    private void reporteCorral() {
        Excel excel;
        excel = new Excel();
        excel.reporteCorral(corral, Grafica);

    }
    private void btn_cierreCorralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cierreCorralActionPerformed

        int opcion;
        opcion = JOptionPane.showConfirmDialog(this, "¿Desea hacer 'Cierre de Corral'?", gs_mensaje, JOptionPane.YES_NO_OPTION);
        if (opcion == 0) {
            if (!pideContraseña()) {
                return;
            }
            cargarAnimalesCorral();
            cargarPesajesCorral();
            for (int i = 0; i < t_animales.getRowCount(); i++) {
                manejadorBD.parametrosSP = new ParametrosSP();
                manejadorBD.parametrosSP.agregarParametro(rancho.id_rancho, "varIdRancho", "STRING", "IN");
                manejadorBD.parametrosSP.agregarParametro(t_animales.getValueAt(i, 0).toString(), "varIdAnimal", "STRING", "IN");
                manejadorBD.parametrosSP.agregarParametro(formatoDateTime_1.format(new Date()), "varFechaSalida", "STRING", "IN");
                manejadorBD.parametrosSP.agregarParametro("2", "varIdClaseMovimiento", "STRING", "IN");
                manejadorBD.parametrosSP.agregarParametro("", "varNumeroPedido", "STRING", "IN");
                manejadorBD.parametrosSP.agregarParametro("", "varIdCliente", "STRING", "IN");
                manejadorBD.parametrosSP.agregarParametro(t_animales.getValueAt(i, 11).toString(), "varPesoActual", "DOUBLE", "IN");
                if (manejadorBD.ejecutarSP("{ call movimientoSalida(?,?,?,?,?,?,?) }") != 0) {
                    return;
                }
            }
            manejadorBD.parametrosSP = new ParametrosSP();
            manejadorBD.parametrosSP.agregarParametro(corral.id_corral, "varIdCorral", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(rancho.id_rancho, "varIdRancho", "STRING", "IN");

            if (manejadorBD.ejecutarSP("{ call cierreCorral(?,?) }") == 0) {
                JOptionPane.showMessageDialog(this, "Se cerro el corral correctamente, puede encontrar el corral en Historico de Corrales.", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
                this.cargarAnimalesCorral();
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Ocurrio un error al cerrar el corral", gs_mensaje, JOptionPane.ERROR_MESSAGE);
            }

        }
    }//GEN-LAST:event_btn_cierreCorralActionPerformed

    private void btn_historicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_historicoActionPerformed
        if (corralesCerrados != null) {
            corralesCerrados.dispose();
        }
        corralesCerrados = new CorralesCerrados(this);
        corralesCerrados.setVisible(true);
    }//GEN-LAST:event_btn_historicoActionPerformed

    private void sexoSelector1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sexoSelector1ActionPerformed
        sexo = this.sexoSelector1.getSexo();
        if (sexo.descripcion.length() > 0) {
            if (sexo.descripcion.equals("Mixto")) {
                cargarAnimalesCorral();
            } else {
                cargarCorralPorSexo();
            }
        }
    }//GEN-LAST:event_sexoSelector1ActionPerformed

    private void btn_IngresoAlimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_IngresoAlimentoActionPerformed

        if (corral == null || corral.id_corral.equals("")) {

            JOptionPane.showMessageDialog(this, "Seleccione un corral", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (ingresoAlimento != null) {

            ingresoAlimento.dispose();
        }

        ingresoAlimento = new IngresoAlimentoCaptura(parent, "", this.corral);

        ingresoAlimento.setVisible(true);
    }//GEN-LAST:event_btn_IngresoAlimentoActionPerformed

    private void cargarCorralPorSexo() {

        if (!corralActivo) {

            return;
        }

        corral = new Corral();
        String nombre = corralSelector.getSelectedItem().toString();
        corral.cargarPorNombre(nombre);
        if (!corral.id_corral.equals("")) {
            this.btn_Crear.setEnabled(false);
            this.btn_Actualizar.setEnabled(true);
            this.btn_Eliminar.setEnabled(true);
            t_totalKilos.setDouble(corral.total_kilos);
            t_pesoMinimo.setDouble(corral.peso_minimo);
            t_pesoMaximo.setDouble(corral.peso_maximo);
            t_pesoPromedio.setDouble(corral.peso_promedio);
            t_alimentoIngresado.setDouble(corral.alimento_ingresado);
            t_gananciaKilos.setDouble(corral.peso_ganancia);
            jtf_localizacion.setText(corral.localizacion);
            jtf_numAnimales.setText(corral.numero_anmales.toString());
            razaSelector.setSelectedItem(corral.raza.descripcion);
            sexoSelector1.setSelectedItem(sexo.descripcion);
            this.jta_Observaciones.setText(corral.observaciones);

            manejadorBD.consulta(""
                    + "SELECT animal.id_animal,         animal.arete_visual,\n"
                    + "       animal.arete_electronico, p.descripcion,\n"
                    + "       animal.fecha_ingreso,     animal.arete_siniiga,\n"
                    + "       animal.arete_campaña,     sexo.descripcion,\n"
                    + "       animal.fecha_compra,      animal.numero_lote,\n"
                    + "       animal.compra,            animal.peso_actual,\n"
                    + "       animal.temperatura\n"
                    + "FROM   corral, corral_animal,\n"
                    + "       ( animal LEFT OUTER JOIN proveedor p ON animal.id_proveedor = p.id_proveedor )\n"
                    + "        LEFT OUTER JOIN sexo ON  animal.id_sexo  = sexo.id_sexo\n"
                    + "WHERE corral.id_corral    = corral_animal.id_corral\n"
                    + "AND   animal.id_animal    = corral_animal.id_animal\n"
                    + "AND   animal.status = 'A'\n"
                    + "AND  animal.id_sexo = '" + sexo.id_sexo + "' \n"
                    + "AND   corral.id_corral    = '" + corral.id_corral + "'");

            manejadorBD.asignarTable(t_animales);

            if (manejadorBD.getRowCount() > 0) {

                this.t_animales.setRowSelectionInterval(0, 0);
                selectAnimal();
                this.btn_Animal.setEnabled(true);
                btn_reporteCorral.setEnabled(true);
            } else {
                this.btn_Animal.setEnabled(false);
                btn_reporteCorral.setEnabled(false);
                this.t_pesos.limpiarTabla();
                graficar();
            }
            t_animales.ocultarcolumna(0);

        } else {
            this.btn_Crear.setEnabled(true);
            this.btn_Actualizar.setEnabled(false);
            this.btn_Eliminar.setEnabled(false);
            this.btn_Animal.setEnabled(false);
            btn_reporteCorral.setEnabled(false);
            limpiarVariables();
        }
        if (t_pesos.getRowCount() > 0) {

            this.btn_detalles.setEnabled(true);
        } else {
            this.btn_detalles.setEnabled(false);
        }
    }

    Double alimento_ingresado;

    public void limpiarVariables() {

        id_corral = 0;
        num_animales = 0;
        id_estado = 0;
        id_pais = 0;
        id_raza = 0;

        jtf_localizacion.setText("");

        razaSelector.setSelectedIndex(0);
        sexoSelector1.setSelectedIndex(0);
        jta_Observaciones.setText("");
        jtf_numAnimales.setText("0");

        t_alimentoIngresado.setDouble(0.0);
        t_totalKilos.setDouble(0.0);
        t_gananciaKilos.setDouble(0.0);
        t_pesoMinimo.setDouble(0.0);
        t_pesoMaximo.setDouble(0.0);
        t_pesoPromedio.setDouble(0.0);

        this.t_animales.limpiarTabla();
        this.t_pesos.limpiarTabla();
        graficar();
    }

    private void cargarComponentes() {
//        plecaSuperior1.cargar();
        fondo1.cargar(getSize());
//        jPanel1.setLocation((pantallaTamano.width / 2) - (jPanel1.getWidth() / 2), (pantallaTamano.height / 2) - (jPanel1.getHeight() / 2));
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
    }

    private Integer id_corral, num_animales, id_estado, id_pais, id_raza;
    private ReporteEntradas reporteEntradas;
    private AdministracionMedicamentos administracionMedicamentos;
    public EspecificacionesAnimal especificacionesAnimal;
    private BusquedaAnimal busquedaAnimal;
    private boolean corralActivo;
    private boolean estadoActivo;
    private boolean paisActivo;
    private boolean razaActivo;
    private boolean cargandoDatos;
    Sexo sexo;
    Raza raza;
    private CargarArchivoSesion cargarArchivoSesion;
    Desktop parent;
    IngresoAlimentoCaptura ingresoAlimento;
    CorralesCerrados corralesCerrados;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel botonesHoriz;
    private javax.swing.JPanel botonesVert;
    private abstractt.Boton btn_Actualizar;
    private abstractt.Boton btn_Animal;
    private abstractt.Boton btn_Crear;
    private abstractt.Boton btn_Eliminar;
    private abstractt.Boton btn_IngresoAlimento;
    private abstractt.Boton btn_busqueda;
    private abstractt.Boton btn_cierreCorral;
    private abstractt.Boton btn_detalles;
    private abstractt.Boton btn_historico;
    private abstractt.Boton btn_reporteCorral;
    private abstractt.Boton btn_reporteEntradas;
    private abstractt.ComboBox corralSelector;
    private javax.swing.JPanel datosPanel;
    private abstractt.Etiqueta etiqueta1;
    private abstractt.Etiqueta etiqueta2;
    private abstractt.fondo fondo1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jta_Observaciones;
    private javax.swing.JTextField jtf_localizacion;
    private javax.swing.JTextField jtf_numAnimales;
    private javax.swing.JPanel panelGrafica;
    private abstractt.ComboBox razaSelector;
    private domain.SexoSelector sexoSelector1;
    private abstractt.TextField t_alimentoIngresado;
    private abstractt.Table t_animales;
    private abstractt.TextField t_gananciaKilos;
    private abstractt.TextField t_pesoMaximo;
    private abstractt.TextField t_pesoMinimo;
    private abstractt.TextField t_pesoPromedio;
    private abstractt.TextField t_totalKilos;
    private abstractt.TextField tf_Eid;
    private abstractt.TextField tf_PesoActual;
    // End of variables declaration//GEN-END:variables
}
