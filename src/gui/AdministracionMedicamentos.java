/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.Medicina;
import static domain.Medicina.leerMedicina;
import static domain.Medicina.leerMedicinaCodigo;
import domain.ParametrosSP;
import domain.Tratamiento;
import static domain.UnidadMedida.cargarUnidades;
import static gui.Desktop.manejadorBD;
import static gui.Desktop.rancho;
import static gui.Login.gs_mensaje;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Developer GAGS
 */
public class AdministracionMedicamentos extends javax.swing.JFrame {

    /**
     * Creates new form AdministracionMedicamentos
     */
    public AdministracionMedicamentos(Desktop parent) {
        this.parent = parent;
        initComponents();
        setLocationRelativeTo(null);

        cargarMedicinas();
        t_medicinas.centrar();

        this.tf_Codigo.textFieldSoloNumeros();
        unidadSelector.addArray(cargarUnidades());

        ListSelectionModel lsm = this.t_medicinas.getSelectionModel();
        lsm.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                seleccionaMedicina();
            }
        });

        this.setTitle(this.getTitle() + " " + rancho.descripcion);
        tratamiento = new Tratamiento();
        cargarMedicinasTratamiento();
        this.tf_DosisTratamiento.textFieldDouble();
        unidadSelectorTratamiento.addArray(cargarUnidades());
//        ListSelectionModel lsm = this.t_medicinasTratamientos.getSelectionModel();
//        lsm.addListSelectionListener(new ListSelectionListener() {
//            public void valueChanged(ListSelectionEvent e) {
//                seleccionaMedicinaTratamiento();
//            }
//        });
        tratamientoSelectorCodigo.valor_nuevo = true;
        tratamientoSelectorNombre.valor_nuevo = true;

        tratamientoSelectorCodigo.cargar2();
        tratamientoSelectorNombre.cargar();
        medicinaSelector.cargar();
        cargarComponentes();

    }

    private void limpiarMedicina() {
        tf_Codigo.setText("");
        tf_Nombre.setText("");
        this.unidadSelector.setSelectedItem("");
    }

    private void seleccionaMedicina() {

        Integer fila = t_medicinas.getSelectedRow();

        if (fila >= 0) {

            medicina = new Medicina();

            medicina.cargarPorId(t_medicinas.getValueAt(fila, 0).toString());
            /*
             id_medicina = t_medicinas.getValueAt(fila, 0).toString();
             codigo = Integer.parseInt(t_medicinas.getValueAt(fila, 1).toString());
             nombre = t_medicinas.getValueAt(fila, 2).toString();
             unidad = t_medicinas.getValueAt(fila, 3).toString();
             costo = Double.parseDouble(t_medicinas.getValueAt(fila, 4).toString());
             presentacion = Double.parseDouble(t_medicinas.getValueAt(fila, 5).toString());
             costo_unitario = Double.parseDouble(t_medicinas.getValueAt(fila, 6).toString());
             id_unidad = t_medicinas.getValueAt(fila, 7).toString();
             */
            tf_Codigo.setText(medicina.codigo.toString());
            tf_Nombre.setText(medicina.nombre);
            this.unidadSelector.setSelectedItem(medicina.unidadMedida.descripcion);
            this.codigoOriginal = medicina.codigo;
            this.tf_Existencias.setText(medicina.existencia.toString());
            this.tf_CostoPromedio.setDouble(medicina.costo_promedio);
            this.tf_UltimoCosto.setDouble(medicina.ultimo_costo);
            UltimaCompra.setDate(medicina.ultima_compra);
            cambioCodigo = false;

        } else {

        }
    }

    private void cargarMedicinas() {

        leerMedicina(t_medicinas);

    }
    /*Limpiar componentes de Tratamiento*/

    private void limpiarTratamiento() {

        this.tf_CostoTratamiento.setText("$ 0.00");
        this.tf_DosisTratamiento.setText("0.0");
        this.unidadSelectorTratamiento.setSelectedItem("");
    }


    /*Selecciona medicina Tratamiento*/
    private void seleccionaMedicinaTratamiento() {

        Integer fila = t_medicinasTratamientos.getSelectedRow();

        if (fila >= 0) {

            medicinaTratamiento.cargarPorId(t_medicinasTratamientos.getValueAt(fila, 1).toString());
        }
    }

    /*Cargar las medicinas del tratamiento*/
    private void cargarMedicinasTratamiento() {

        Tratamiento.leerMedicinaTratamiento(t_medicinasTratamientos, tratamiento);
        
        
    }

    /*Validar Código en tratamiento*/
    private boolean validarCodigoTratamiento() {

        String Scodigo = "";//tf_Codigo.getText();

        if (!Scodigo.equals("")) {

            if (leerMedicinaCodigo(Scodigo)) {

                JOptionPane.showMessageDialog(this, "El codigo que intenta guardar ya existe", gs_mensaje, JOptionPane.ERROR_MESSAGE);

                //        tf_Codigo.requestFocus();
                return false;

            } else {

                codigoTratamiento = Integer.parseInt(Scodigo);
                this.btn_agregarTratamiento.setEnabled(true);
            }
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        pn_medicamentos = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lbl_unidades = new javax.swing.JLabel();
        lbl_nombre = new javax.swing.JLabel();
        lbl_codigo = new javax.swing.JLabel();
        unidadSelector = new abstractt.ComboBox();
        tf_Nombre = new abstractt.TextField();
        tf_Codigo = new abstractt.TextField();
        etiqueta3 = new abstractt.Etiqueta();
        etiqueta4 = new abstractt.Etiqueta();
        tf_Existencias = new abstractt.TextField();
        tf_CostoPromedio = new abstractt.TextFieldMoneda();
        etiqueta5 = new abstractt.Etiqueta();
        tf_UltimoCosto = new abstractt.TextFieldMoneda();
        etiqueta6 = new abstractt.Etiqueta();
        UltimaCompra = new abstractt.Calendar();
        jScrollPane2 = new javax.swing.JScrollPane();
        t_medicinas = new abstractt.Table();
        btn_agregar = new abstractt.Boton();
        btn_eliminar = new abstractt.Boton();
        btn_guardar = new abstractt.Boton();
        btn_aplicacionMasiva = new abstractt.Boton();
        etiqueta2 = new abstractt.Etiqueta();
        btn_comprar = new abstractt.Boton();
        fondo1 = new abstractt.fondo();
        pn_tratamientos = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        medicinaSelector = new domain.MedicinaSelector();
        tratamientoSelectorNombre = new domain.TratamientoSelector();
        tratamientoSelectorCodigo = new domain.TratamientoSelector();
        tf_DosisTratamiento = new abstractt.TextField();
        tf_CostoTratamiento = new abstractt.TextFieldMoneda();
        unidadSelectorTratamiento = new abstractt.ComboBox();
        btn_agregarMedicamento = new abstractt.Boton();
        btn_eliminarMedicamento = new abstractt.Boton();
        btn_agregarTratamiento = new abstractt.Boton();
        btn_actualizar = new abstractt.Boton();
        btn_eliminarTratamiento = new abstractt.Boton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        t_medicinasTratamientos = new abstractt.Table();
        etiqueta1 = new abstractt.Etiqueta();
        fondo2 = new abstractt.fondo();
        fondo3 = new abstractt.fondo();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Administracion de Medicamentos");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(230, 225, 195));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.setBackground(new java.awt.Color(64, 37, 4));
        jTabbedPane1.setForeground(new java.awt.Color(64, 37, 4));
        jTabbedPane1.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1000, 526));
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        pn_medicamentos.setMinimumSize(new java.awt.Dimension(985, 580));
        pn_medicamentos.setOpaque(false);
        pn_medicamentos.setPreferredSize(new java.awt.Dimension(985, 580));
        pn_medicamentos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_unidades.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        lbl_unidades.setForeground(new java.awt.Color(95, 84, 88));
        lbl_unidades.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_unidades.setText("Unidad de Medida:");
        lbl_unidades.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel1.add(lbl_unidades, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 120, 20));

        lbl_nombre.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        lbl_nombre.setForeground(new java.awt.Color(95, 84, 88));
        lbl_nombre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_nombre.setText("Nombre:");
        lbl_nombre.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel1.add(lbl_nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 120, 20));

        lbl_codigo.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        lbl_codigo.setForeground(new java.awt.Color(95, 84, 88));
        lbl_codigo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_codigo.setText("Codigo:");
        lbl_codigo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel1.add(lbl_codigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 20));

        unidadSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unidadSelectorActionPerformed(evt);
            }
        });
        jPanel1.add(unidadSelector, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, 200, 20));

        tf_Nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_NombreActionPerformed(evt);
            }
        });
        jPanel1.add(tf_Nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 200, 20));

        tf_Codigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tf_CodigoFocusLost(evt);
            }
        });
        tf_Codigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_CodigoActionPerformed(evt);
            }
        });
        jPanel1.add(tf_Codigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, 200, 20));

        etiqueta3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiqueta3.setText("Existencia:");
        jPanel1.add(etiqueta3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 120, 20));

        etiqueta4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiqueta4.setText("Costo Promedio:");
        jPanel1.add(etiqueta4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 120, 20));

        tf_Existencias.setEditable(false);
        tf_Existencias.setEnabled(false);
        jPanel1.add(tf_Existencias, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 200, 20));

        tf_CostoPromedio.setEditable(false);
        tf_CostoPromedio.setEnabled(false);
        jPanel1.add(tf_CostoPromedio, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, 200, 20));

        etiqueta5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiqueta5.setText("Ultimo Compra:");
        jPanel1.add(etiqueta5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 120, 20));

        tf_UltimoCosto.setEditable(false);
        tf_UltimoCosto.setEnabled(false);
        jPanel1.add(tf_UltimoCosto, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 150, 200, 20));

        etiqueta6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiqueta6.setText("Ultimo Costo:");
        jPanel1.add(etiqueta6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 120, 20));

        UltimaCompra.setEnabled(false);
        jPanel1.add(UltimaCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 180, 200, -1));

        pn_medicamentos.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 100, 370, 210));

        t_medicinas.setForeground(new java.awt.Color(230, 225, 195));
        t_medicinas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        t_medicinas.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jScrollPane2.setViewportView(t_medicinas);
        if (t_medicinas.getColumnModel().getColumnCount() > 0) {
            t_medicinas.getColumnModel().getColumn(0).setResizable(false);
            t_medicinas.getColumnModel().getColumn(1).setResizable(false);
            t_medicinas.getColumnModel().getColumn(2).setResizable(false);
            t_medicinas.getColumnModel().getColumn(3).setResizable(false);
            t_medicinas.getColumnModel().getColumn(4).setResizable(false);
            t_medicinas.getColumnModel().getColumn(5).setResizable(false);
        }

        pn_medicamentos.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 975, 200));

        btn_agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/botón agregar.png"))); // NOI18N
        btn_agregar.setToolTipText("Agregar el medicamento");
        btn_agregar.setFont(new java.awt.Font("Castellar", 1, 12)); // NOI18N
        btn_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarActionPerformed(evt);
            }
        });
        pn_medicamentos.add(btn_agregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 310, -1, -1));

        btn_eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/botón eliminar.png"))); // NOI18N
        btn_eliminar.setToolTipText("Borrar el medicamento");
        btn_eliminar.setFont(new java.awt.Font("Castellar", 1, 12)); // NOI18N
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });
        pn_medicamentos.add(btn_eliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 310, -1, -1));

        btn_guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/botón gaurdar.png"))); // NOI18N
        btn_guardar.setToolTipText("Guardar las actualizaciones del medicamento");
        btn_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarActionPerformed(evt);
            }
        });
        pn_medicamentos.add(btn_guardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(815, 310, -1, -1));

        btn_aplicacionMasiva.setText("Aplicación Masiva de Medicamentos");
        btn_aplicacionMasiva.setToolTipText("Administra medicamentos a Animales por grupos");
        btn_aplicacionMasiva.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        btn_aplicacionMasiva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_aplicacionMasivaActionPerformed(evt);
            }
        });
        pn_medicamentos.add(btn_aplicacionMasiva, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, -1, 30));

        etiqueta2.setBackground(new java.awt.Color(95, 84, 88));
        etiqueta2.setForeground(new java.awt.Color(230, 225, 195));
        etiqueta2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiqueta2.setText("Medicamentos");
        etiqueta2.setFont(new java.awt.Font("Trebuchet", 1, 48)); // NOI18N
        etiqueta2.setOpaque(true);
        pn_medicamentos.add(etiqueta2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 60));

        btn_comprar.setText("Compra de Medicamentos");
        btn_comprar.setToolTipText("Realizar compras de medicamentos");
        btn_comprar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_comprarActionPerformed(evt);
            }
        });
        pn_medicamentos.add(btn_comprar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 330, 230, 30));

        fondo1.setText("fondo1");
        pn_medicamentos.add(fondo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-5, -5, -1, -1));

        jTabbedPane1.addTab(" M e d i c a m e n t o s ", pn_medicamentos);

        pn_tratamientos.setBackground(new java.awt.Color(255, 255, 255));
        pn_tratamientos.setOpaque(false);
        pn_tratamientos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(95, 84, 88));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Codigo:");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 22));

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(95, 84, 88));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Medicamento:");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 100, 22));

        jLabel3.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(95, 84, 88));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Dosis");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 100, 22));

        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(95, 84, 88));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Unidades");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 100, 22));

        jLabel8.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(95, 84, 88));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Costo:");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 100, 22));

        jLabel9.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(95, 84, 88));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Nombre:");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 100, 22));

        medicinaSelector.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        medicinaSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                medicinaSelectorActionPerformed(evt);
            }
        });
        jPanel3.add(medicinaSelector, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, 200, 20));

        tratamientoSelectorNombre.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        tratamientoSelectorNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tratamientoSelectorNombreActionPerformed(evt);
            }
        });
        jPanel3.add(tratamientoSelectorNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 200, 20));

        tratamientoSelectorCodigo.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        tratamientoSelectorCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tratamientoSelectorCodigoActionPerformed(evt);
            }
        });
        jPanel3.add(tratamientoSelectorCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, 200, 20));

        tf_DosisTratamiento.setText("0.0");
        tf_DosisTratamiento.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        tf_DosisTratamiento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tf_DosisTratamientoFocusLost(evt);
            }
        });
        jPanel3.add(tf_DosisTratamiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, 200, 20));

        tf_CostoTratamiento.setEditable(false);
        tf_CostoTratamiento.setEnabled(false);
        tf_CostoTratamiento.setFocusable(false);
        tf_CostoTratamiento.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        tf_CostoTratamiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_CostoTratamientoActionPerformed(evt);
            }
        });
        jPanel3.add(tf_CostoTratamiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 150, 200, 20));

        unidadSelectorTratamiento.setEnabled(false);
        unidadSelectorTratamiento.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jPanel3.add(unidadSelectorTratamiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 200, 20));

        btn_agregarMedicamento.setText("Agregar Medicamento");
        btn_agregarMedicamento.setToolTipText("Agrega el medicamento a la lista del Tratamiento");
        btn_agregarMedicamento.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        btn_agregarMedicamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarMedicamentoActionPerformed(evt);
            }
        });
        jPanel3.add(btn_agregarMedicamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 50, -1, -1));

        btn_eliminarMedicamento.setText("Eliminar Medicamento");
        btn_eliminarMedicamento.setToolTipText("Elimina el medicamento del Tratamiento");
        btn_eliminarMedicamento.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        btn_eliminarMedicamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarMedicamentoActionPerformed(evt);
            }
        });
        jPanel3.add(btn_eliminarMedicamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 100, -1, -1));

        btn_agregarTratamiento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/botón agregar.png"))); // NOI18N
        btn_agregarTratamiento.setToolTipText("Crear un Tratamiento");
        btn_agregarTratamiento.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        btn_agregarTratamiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarTratamientoActionPerformed(evt);
            }
        });
        jPanel3.add(btn_agregarTratamiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 205, -1, -1));

        btn_actualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/botón gaurdar.png"))); // NOI18N
        btn_actualizar.setToolTipText("Guardar las actualizaciones del tratamiento");
        btn_actualizar.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        btn_actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizarActionPerformed(evt);
            }
        });
        jPanel3.add(btn_actualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(565, 205, -1, -1));

        btn_eliminarTratamiento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/botón eliminar.png"))); // NOI18N
        btn_eliminarTratamiento.setToolTipText("Borrar el Tratamiento");
        btn_eliminarTratamiento.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        btn_eliminarTratamiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarTratamientoActionPerformed(evt);
            }
        });
        jPanel3.add(btn_eliminarTratamiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 205, -1, -1));

        pn_tratamientos.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 100, -1, -1));

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        t_medicinasTratamientos.setForeground(new java.awt.Color(230, 225, 195));
        t_medicinasTratamientos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(t_medicinasTratamientos);

        jPanel4.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 975, 200));

        pn_tratamientos.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 380, -1, -1));

        etiqueta1.setBackground(new java.awt.Color(95, 84, 88));
        etiqueta1.setForeground(new java.awt.Color(230, 225, 195));
        etiqueta1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiqueta1.setText("Tratamientos");
        etiqueta1.setFont(new java.awt.Font("Trebuchet", 1, 48)); // NOI18N
        etiqueta1.setOpaque(true);
        pn_tratamientos.add(etiqueta1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 60));

        fondo2.setText("fondo1");
        pn_tratamientos.add(fondo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-5, -5, -1, -1));

        jTabbedPane1.addTab(" T r a t a m i e n t o s ", pn_tratamientos);

        jPanel5.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 650));

        fondo3.setText("fondo3");
        jPanel5.add(fondo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_eliminarTratamientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarTratamientoActionPerformed
        System.out.println(tratamiento.toString());

        if (tratamiento.eliminar()) {

            cargarMedicinasTratamiento();

            JOptionPane.showMessageDialog(this, "Se Elimino el Tratamiento Correctamente", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);

        } else {

            JOptionPane.showMessageDialog(this, "Error al Eliminar el Tratamiento\n" + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
        }

        cargandoTratamientoSelector = true;
        tratamientoSelectorCodigo.cargar2();
        tratamientoSelectorNombre.cargar();
        cargandoTratamientoSelector = false;
    }//GEN-LAST:event_btn_eliminarTratamientoActionPerformed

    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed

        tratamiento.id_tratamiento = id_tratamiento;
        tratamiento.codigo = this.tratamientoSelectorCodigo.getSelectedItem().toString();
        tratamiento.nombre = this.tratamientoSelectorNombre.getSelectedItem().toString();

        if (tratamiento.actualizar()) {

            cargarMedicinasTratamiento();

            JOptionPane.showMessageDialog(this, "Se Actualizo el Tratamiento Correctamente", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);

        } else {

            JOptionPane.showMessageDialog(this, "Error al Actualizar el Tratamiento\n" + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
        }

        cargandoTratamientoSelector = true;
        tratamientoSelectorCodigo.cargar2();
        tratamientoSelectorNombre.cargar();
        cargandoTratamientoSelector = false;
    }//GEN-LAST:event_btn_actualizarActionPerformed

    private void btn_agregarTratamientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarTratamientoActionPerformed
        if (tratamientoSelectorCodigo.getText().length() != 0 && tratamientoSelectorNombre.getText().length() != 0) {

            tratamiento.codigo = this.tratamientoSelectorCodigo.getSelectedItem().toString();
            tratamiento.nombre = this.tratamientoSelectorNombre.getSelectedItem().toString();

            if (tratamiento.grabar()) {

                cargarMedicinasTratamiento();

                JOptionPane.showMessageDialog(this, "Se Agrego el Tratamiento Correctamente", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
                limpiarTratamiento();
            } else {

                JOptionPane.showMessageDialog(this, "Error al crear el Tratamiento\n" + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
            }
            cargandoTratamientoSelector = true;
            tratamientoSelectorCodigo.cargar2();
            tratamientoSelectorNombre.cargar();
            cargandoTratamientoSelector = false;
        } else {
            JOptionPane.showMessageDialog(this, "Error al ingresar el nombre o código del tratamiento", gs_mensaje, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_agregarTratamientoActionPerformed

    private void btn_eliminarMedicamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarMedicamentoActionPerformed

        if (tratamiento.eliminarMedicina(medicinaTratamiento)) {

            JOptionPane.showMessageDialog(this, "Se Elimino la Medicina al Tratamiento Correctamente", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);

            this.cargarMedicinasTratamiento();
        } else {

            JOptionPane.showMessageDialog(this, "Error al Eliminar la Medicina al Tratamiento\n" + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_eliminarMedicamentoActionPerformed

    private void btn_agregarMedicamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarMedicamentoActionPerformed

        Double dosis;
        dosis = this.tf_DosisTratamiento.getDouble();
        if (dosis > 0) {
            if (tratamiento.agregarMedicina(medicinaTratamiento, dosis)) {

                JOptionPane.showMessageDialog(this, "Se Agrego la Medicina al Tratamiento Correctamente", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);

                this.cargarMedicinasTratamiento();
            } else {

                JOptionPane.showMessageDialog(this, "Error al Agregar la Medicina al Tratamiento\n" + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btn_agregarMedicamentoActionPerformed

    private void tf_CostoTratamientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_CostoTratamientoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_CostoTratamientoActionPerformed

    private void tratamientoSelectorCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tratamientoSelectorCodigoActionPerformed

        if (!cargandoTratamientoSelector) {

            cargandoTratamientoSelector = true;
            tratamiento = this.tratamientoSelectorCodigo.getTratamientoCodigo();

            if (!tratamiento.id_tratamiento.equals("")) {

                this.tratamientoSelectorNombre.setSelectedItem(tratamiento.nombre);
                id_tratamiento = tratamiento.id_tratamiento;
            }

            this.medicinaSelector.setSelectedItem("");
            cargarMedicinasTratamiento();

            cargandoTratamientoSelector = false;
        }
    }//GEN-LAST:event_tratamientoSelectorCodigoActionPerformed

    private void tratamientoSelectorNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tratamientoSelectorNombreActionPerformed

        if (!cargandoTratamientoSelector) {

            cargandoTratamientoSelector = true;
            tratamiento = this.tratamientoSelectorNombre.getTratamientoNombre();
            if (!tratamiento.id_tratamiento.equals("")) {

                this.tratamientoSelectorCodigo.setSelectedItem(tratamiento.codigo);
                id_tratamiento = tratamiento.id_tratamiento;
            }
            medicinaSelector.setSelectedItem("");

            cargarMedicinasTratamiento();
            cargandoTratamientoSelector = false;
        }
    }//GEN-LAST:event_tratamientoSelectorNombreActionPerformed

    private void medicinaSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_medicinaSelectorActionPerformed

        medicinaTratamiento = medicinaSelector.getMedicnaNombre();
        unidadSelectorTratamiento.setSelectedItem(medicinaTratamiento.unidadMedida.descripcion);
        colocarCosto();
    }//GEN-LAST:event_medicinaSelectorActionPerformed

    private void btn_aplicacionMasivaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_aplicacionMasivaActionPerformed
        if(medicinaAnimalGrupo != null){
            medicinaAnimalGrupo.dispose();
        }
        medicinaAnimalGrupo = new MedicinasAnimalGrupo(parent);
        medicinaAnimalGrupo.setVisible(true);
    }//GEN-LAST:event_btn_aplicacionMasivaActionPerformed

    private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarActionPerformed

        if (cambioCodigo) {
            if (!this.validarCodigo()) {
                return;
            }
        }

        codigo = Integer.parseInt(tf_Codigo.getText());
        nombre = tf_Nombre.getText();
        //        costo_unitario = this.tf_CostoUnitario.obtenerValor();

        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(id_medicina, "varId_medicina", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(codigo.toString(), "varCodigo", "INT", "IN");
        manejadorBD.parametrosSP.agregarParametro(nombre, "varNombre", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro("0.0", "varCosto", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_unidad, "varId_unidad", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro("0", "varPresentacion", "DOUBLE", "IN");

        if (manejadorBD.ejecutarSP("{ call actualizarMedicina(?,?,?,?,?,?) }") == 0) {

            JOptionPane.showMessageDialog(this, "Se guardó el Medicamento Correctamente", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
            cargarMedicinas();
        } else {

            JOptionPane.showMessageDialog(this, "Error al guardar el Medicamento", gs_mensaje, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_guardarActionPerformed

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(medicina.id_medicina, "varId_medicina", "STRING", "IN");

        if (manejadorBD.ejecutarSP("{ call eliminarMedicina(?) }") == 0) {

            JOptionPane.showMessageDialog(this, "Se Elimino el Medicamento Correctamente", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
            cargarMedicinas();
        } else {

            JOptionPane.showMessageDialog(this, "Error al Eliminar el Medicamento\n" + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed

        if (!validarCodigo()) {
            return;
        }

        if (tf_Codigo.getText().length() != 0 && tf_Nombre.getText().length() != 0) {
            codigo = Integer.parseInt(tf_Codigo.getText());
            nombre = tf_Nombre.getText();
            //  costo_unitario = this.tf_CostoUnitario.obtenerValor();

            if (id_unidad.equals("")) {

                JOptionPane.showMessageDialog(this, "No ha seleccionado unidad de medida", gs_mensaje, JOptionPane.ERROR_MESSAGE);
            } else {

                manejadorBD.parametrosSP = new ParametrosSP();
                manejadorBD.parametrosSP.agregarParametro(codigo.toString(), "varCodigo", "INT", "IN");
                manejadorBD.parametrosSP.agregarParametro(nombre, "varNombre", "STRING", "IN");
                manejadorBD.parametrosSP.agregarParametro("0.0", "varCosto", "DOUBLE", "IN");
                manejadorBD.parametrosSP.agregarParametro(id_unidad.toString(), "varId_unidad", "INT", "IN");
                manejadorBD.parametrosSP.agregarParametro("0", "varPresentacion", "DOUBLE", "IN");

                if (manejadorBD.ejecutarSP("{ call agregarMedicina(?,?,?,?,?) }") == 0) {

                    cargarMedicinas();
                    JOptionPane.showMessageDialog(this, "Se Agrego el Medicamento Correctamente", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
                    limpiarMedicina();

                } else {

                    JOptionPane.showMessageDialog(this, "Error al crear el Medicamento\n" + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "El nombre o el código de la medicina es incorrecto\n" + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_agregarActionPerformed

    private void tf_CodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_CodigoActionPerformed

        if (!this.tf_Codigo.getText().equals(this.codigoOriginal)) {
            cambioCodigo = true;
        } else {
            cambioCodigo = false;
        }
    }//GEN-LAST:event_tf_CodigoActionPerformed

    private void tf_CodigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tf_CodigoFocusLost

        if (!this.tf_Codigo.getText().equals(this.codigoOriginal)) {

            cambioCodigo = true;
        } else {

            cambioCodigo = false;
        }
    }//GEN-LAST:event_tf_CodigoFocusLost

    private void tf_NombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_NombreActionPerformed

        nombre = tf_Nombre.getText();
    }//GEN-LAST:event_tf_NombreActionPerformed

    private void unidadSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unidadSelectorActionPerformed

        unidad = unidadSelector.getSelectedItem().toString();

        if (!unidad.equals("")) {

            manejadorBD.consulta(""
                    + "SELECT   id_unidad "
                    + "FROM     unidades_de_medida "
                    + "WHERE    descripcion = \"" + unidad + "\"");

            if (manejadorBD.getRowCount() > 0) {

                id_unidad = manejadorBD.getValorString(0, 0);
            }
        } else {

            id_unidad = "";
        }
    }//GEN-LAST:event_unidadSelectorActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
//        parent.setVisible(true);
    }//GEN-LAST:event_formWindowClosing

    private void btn_comprarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_comprarActionPerformed
        
        compras = new Compras(parent);
        compras.setVisible(true);
    }//GEN-LAST:event_btn_comprarActionPerformed

    private void tf_DosisTratamientoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tf_DosisTratamientoFocusLost
        colocarCosto();
    }//GEN-LAST:event_tf_DosisTratamientoFocusLost

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
       
        //cambio de tab
        System.out.println("Tab: " + jTabbedPane1.getSelectedIndex());
        
        if ( jTabbedPane1.getSelectedIndex() == 1 ){
            medicinaSelector.cargar();
        }
    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void colocarCosto() {
        try {
            costoTratamiento = medicinaTratamiento.costo_promedio * this.tf_DosisTratamiento.getDouble();
            presentacionTratamiento = this.tf_DosisTratamiento.getDouble();
            tf_CostoTratamiento.setDouble(costoTratamiento);
        } catch (Exception x) {
            tf_CostoTratamiento.setDouble(0.0);
        }
    }

    private boolean validarCodigo() {

        String Scodigo = tf_Codigo.getText();

        if (!Scodigo.equals("")) {

            if (leerMedicinaCodigo(Scodigo)) {

                JOptionPane.showMessageDialog(this, "El codigo que intenta guardar ya existe", gs_mensaje, JOptionPane.ERROR_MESSAGE);
                tf_Codigo.requestFocus();
                return false;

            } else {

                codigo = Integer.parseInt(Scodigo);
                this.btn_agregar.setEnabled(true);
            }
        }

        return true;
    }

    private void cargarComponentes() {
        Dimension fond1 = new Dimension(pn_medicamentos.getWidth() + 10, pn_medicamentos.getHeight() + 10);
        Dimension fond2 = new Dimension(pn_tratamientos.getWidth() + 10, pn_tratamientos.getHeight() + 10);
        fondo1.cargar(fond1);
        fondo2.cargar(fond2);
        fondo3.cargar(getSize());
        setResizable(false);

    }

    Desktop parent;
    private Integer codigo;
    private String nombre;
    private Double costo;
    private Double presentacion;
    private Double costo_unitario;
    private String id_unidad;
    private String unidad;
    private String id_medicina;
    private Integer codigoOriginal;
    private boolean cambioCodigo;
    private Integer codigoTratamiento;
    private Double costoTratamiento;
    private Double presentacionTratamiento;
    private boolean cargandoTratamientoSelector = false;
    private Tratamiento tratamiento;
    private String id_tratamiento;
    private Medicina medicinaTratamiento;
    private MedicinasAnimalGrupo medicinaAnimalGrupo;
    private Compras compras;
    private Medicina medicina;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private abstractt.Calendar UltimaCompra;
    private abstractt.Boton btn_actualizar;
    private abstractt.Boton btn_agregar;
    private abstractt.Boton btn_agregarMedicamento;
    private abstractt.Boton btn_agregarTratamiento;
    private abstractt.Boton btn_aplicacionMasiva;
    private abstractt.Boton btn_comprar;
    private abstractt.Boton btn_eliminar;
    private abstractt.Boton btn_eliminarMedicamento;
    private abstractt.Boton btn_eliminarTratamiento;
    private abstractt.Boton btn_guardar;
    private abstractt.Etiqueta etiqueta1;
    private abstractt.Etiqueta etiqueta2;
    private abstractt.Etiqueta etiqueta3;
    private abstractt.Etiqueta etiqueta4;
    private abstractt.Etiqueta etiqueta5;
    private abstractt.Etiqueta etiqueta6;
    private abstractt.fondo fondo1;
    private abstractt.fondo fondo2;
    private abstractt.fondo fondo3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbl_codigo;
    private javax.swing.JLabel lbl_nombre;
    private javax.swing.JLabel lbl_unidades;
    private domain.MedicinaSelector medicinaSelector;
    private javax.swing.JPanel pn_medicamentos;
    private javax.swing.JPanel pn_tratamientos;
    private abstractt.Table t_medicinas;
    private abstractt.Table t_medicinasTratamientos;
    private abstractt.TextField tf_Codigo;
    private abstractt.TextFieldMoneda tf_CostoPromedio;
    private abstractt.TextFieldMoneda tf_CostoTratamiento;
    private abstractt.TextField tf_DosisTratamiento;
    private abstractt.TextField tf_Existencias;
    private abstractt.TextField tf_Nombre;
    private abstractt.TextFieldMoneda tf_UltimoCosto;
    private domain.TratamientoSelector tratamientoSelectorCodigo;
    private domain.TratamientoSelector tratamientoSelectorNombre;
    private abstractt.ComboBox unidadSelector;
    private abstractt.ComboBox unidadSelectorTratamiento;
    // End of variables declaration//GEN-END:variables
}
