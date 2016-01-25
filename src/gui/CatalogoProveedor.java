/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.Ciudad;
import domain.Estado;
import domain.Pais;
import domain.Proveedor;
import domain.ProveedorSelector;
import static gui.Desktop.manejadorBD;
import static gui.Desktop.rancho;
import static gui.Login.gs_mensaje;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

/**
 *
 * @author sperez
 */
public class CatalogoProveedor extends javax.swing.JDialog {

    /**
     * Creates new form CatalogoRaza
     */
    public CatalogoProveedor(java.awt.Frame parent) {
        //super(parent, modal);
        this.parent = parent;
        initComponents();
        cargarProveedor();

        ListSelectionModel lsm = this.tableProveedor.getSelectionModel();

        lsm.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                selectProveedor();
            }
        });
        /*
         tableProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
         @Override
         public void mouseClicked(java.awt.event.MouseEvent evt) {
         Integer row = tableProveedor.rowAtPoint(evt.getPoint());
         Integer col = tableProveedor.columnAtPoint(evt.getPoint());
         if (row >= 0 && col >= 0) {
         click(row, col);
         }
         }
         });
         */
        /*
         this.tableProveedor.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent evt) {
         table1KeyPressed(evt);
         }
         });
         */

        paisSelector1.cargar();
        estadoSelector1.cargar();

        Image i = null;
        i = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/logo tru-test.png"));
        setIconImage(i);

        this.setTitle(this.getTitle() + " " + rancho.descripcion);
        setLocationRelativeTo(null);
        fondo1.cargar(this.getSize());
        id_proveedor.setVisible(false);
    }

    private void table1KeyPressed(KeyEvent evt) {

        if (this.tableProveedor.isEditing()) {
            this.tableProveedor.getCellEditor().stopCellEditing();
        }
        cambiaStatus();
    }

    public void cambiaStatus() {

        int fila = this.tableProveedor.getSelectedRow();
        int itemStatus;
        if (fila >= 0) {

            itemStatus = Integer.parseInt(tableProveedor.getValueAt(fila, tableProveedor.colItemStatus).toString());

            switch (itemStatus) {

                case 0:
                    tableProveedor.setValueAt(2, fila, tableProveedor.colItemStatus);
                    break;
                case 1:
                    tableProveedor.setValueAt(1, fila, tableProveedor.colItemStatus);
                    break;
                case 2:
                    tableProveedor.setValueAt(2, fila, tableProveedor.colItemStatus);
                    break;
            }
        }
    }

    private void grabar2() {

        Integer filas = 0;
        filas = this.tableProveedor.getRowCount();
        // Proveedor proveedor;
        boolean error = false;
        int itemStatus;
        ProveedorSelector proveedorSelector;
        proveedorSelector = new ProveedorSelector();

        tableProveedor.acceptText();

        for (int i = 0; i < filas; i++) {

            itemStatus = Integer.parseInt(tableProveedor.getValueAt(i, tableProveedor.colItemStatus).toString());

            switch (itemStatus) {

                case 0:
                    //No hay cambio
                    break;
                case 1:
                    //Nuevo
                    proveedor = new Proveedor();
                    proveedor.descripcion = tableProveedor.getValueAt(i, 1).toString();
                    proveedor.pais.cargarPorDescripcion(tableProveedor.getValueAt(i, Proveedor.colPais).toString());
                    proveedor.estado.cargarPorDescripcion(tableProveedor.getValueAt(i, Proveedor.colEstado).toString());
                    proveedor.ciudad.cargarPorDescripcion(tableProveedor.getValueAt(i, Proveedor.colCiudad).toString());
                    proveedor.direccion = tableProveedor.getValueAt(i, Proveedor.colDireccion).toString();
                    proveedor.email = tableProveedor.getValueAt(i, Proveedor.colEmail).toString();
                    proveedor.telefono = tableProveedor.getValueAt(i, Proveedor.colTelefono).toString();

                    if (proveedor.crear()) {
                        //cambiar no hay cambios de status
                        tableProveedor.setValueAt(0, i, tableProveedor.colItemStatus);
                    } else {
                        error = true;
                        JOptionPane.showMessageDialog(this, "Error al Crear el Proveedor\n" + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
                    }

                    break;
                case 2:
                    //Cambios
                    proveedor = new Proveedor();
                    proveedor.cargarPorId(tableProveedor.getValueAt(i, 0).toString());
                    proveedor.descripcion = tableProveedor.getValueAt(i, 1).toString();
                    proveedor.pais.cargarPorDescripcion(tableProveedor.getValueAt(i, Proveedor.colPais).toString());
                    proveedor.estado.cargarPorDescripcion(tableProveedor.getValueAt(i, Proveedor.colEstado).toString());
                    proveedor.ciudad.cargarPorDescripcion(tableProveedor.getValueAt(i, Proveedor.colCiudad).toString());
                    proveedor.direccion = tableProveedor.getValueAt(i, Proveedor.colDireccion).toString();
                    proveedor.email = tableProveedor.getValueAt(i, Proveedor.colEmail).toString();
                    proveedor.telefono = tableProveedor.getValueAt(i, Proveedor.colTelefono).toString();

                    if (proveedor.actualizar()) {
                        //cambiar no hay cambios de status
                        tableProveedor.setValueAt(0, i, tableProveedor.colItemStatus);
                    } else {
                        error = true;
                        JOptionPane.showMessageDialog(this, "Error al Actualizar el ¨Proveedor\n" + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
                    }

                    break;
            }
        }

        if (!error) {

            JOptionPane.showMessageDialog(this, "Se Guardaron los Cambios Correctamente", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    Proveedor proveedor;
    Estado estado;
    Pais pais;
    Ciudad ciudad;

    private void selectProveedor() {

        if (cargando_tabla) {
            return;
        }

        System.out.println("CLICK");

        Integer fila = tableProveedor.getSelectedRow();

        proveedor = new Proveedor();

        proveedor.cargarPorId(tableProveedor.getValueAt(fila, 0).toString());

        System.out.println("proveedor " + proveedor.descripcion);

        asignarValoresProveedor();

        /*
         proveedor = new Proveedor();
         estado = new Estado();
         Estado estadoNvo;
         Pais paisNvo;
         Ciudad ciudadNvo;

         Integer fila = tableProveedor.getSelectedRow();
         Integer columna = tableProveedor.getSelectedColumn();

         if (fila >= 0 && tableProveedor.getValueAt(fila, 0) != null) {

         proveedor.cargarPorId(tableProveedor.getValueAt(fila, 0).toString());
         }

         if (columna == Proveedor.colEstado) {

         estado = new Estado();
         estadoNvo = new Estado();
         estado.cargarPorDescripcion(tableProveedor.getValueAt(fila, Proveedor.colEstado).toString());

         estadoNvo = estado.mostrarDialogoEstado();

         if (!estado.descripcion.equals(estadoNvo.descripcion)) {
         tableProveedor.setValueAt(estadoNvo.descripcion, fila, Proveedor.colEstado);

         cambiaStatus();
         }
         }

         if (columna == Proveedor.colPais) {

         pais = new Pais();
         paisNvo = new Pais();
         pais.cargarPorDescripcion(tableProveedor.getValueAt(fila, Proveedor.colPais).toString());

         paisNvo = pais.mostrarDialogoPais();

         if (!pais.descripcion.equals(paisNvo.descripcion)) {
         tableProveedor.setValueAt(paisNvo.descripcion, fila, Proveedor.colPais);

         cambiaStatus();
         }
         }

         if (columna == Proveedor.colCiudad) {

         ciudad = new Ciudad();
         ciudadNvo = new Ciudad();
         estado = new Estado();
         estado.cargarPorDescripcion(tableProveedor.getValueAt(fila, Proveedor.colEstado).toString());
         ciudad.cargarPorDescripcion(tableProveedor.getValueAt(fila, Proveedor.colCiudad).toString());

         ciudadNvo = ciudad.mostrarDialogoCiudad(estado);

         if (!ciudad.descripcion.equals(ciudadNvo.descripcion)) {

         tableProveedor.setValueAt(ciudadNvo.descripcion, fila, Proveedor.colCiudad);
         cambiaStatus();
         }
         }
        
         */
    }

    private void asignarValoresProveedor() {

        id_proveedor.setText(proveedor.id_proveedor);
        descripcion.setText(proveedor.descripcion);

        CB_FisicaMoral.setSelectedIndex(0);

        if (proveedor.p_fisica_moral.equals("F")) {

            CB_FisicaMoral.setSelectedItem("Fisica");
        } else {
            if (proveedor.p_fisica_moral.equals("M")) {

                CB_FisicaMoral.setSelectedItem("Moral");
            }
        }

        TF_email.setText(proveedor.email);
        TF_Telefono.setText(proveedor.telefono);
        paisSelector1.setPais(proveedor.pais);
        estadoSelector1.setEstado(proveedor.estado);
        ciudadSelector1.setCiudad(proveedor.estado, proveedor.ciudad);
        TF_Direccion.setText(proveedor.direccion);
    }

    private void recuperarValoresProveedor() {

        proveedor.id_proveedor = id_proveedor.getText();
        proveedor.descripcion = descripcion.getText();
        proveedor.p_fisica_moral = "";

        if (CB_FisicaMoral.getSelectedItem().equals("Fisica")) {
            proveedor.p_fisica_moral = "F";
        } else {
            if (CB_FisicaMoral.getSelectedItem().equals("Moral")) {
                proveedor.p_fisica_moral = "M";
            }
        }
        proveedor.email = TF_email.getText();
        proveedor.telefono = TF_Telefono.getText();
        proveedor.pais = paisSelector1.getPais();
        proveedor.estado = estadoSelector1.getEstado();

        proveedor.ciudad = ciudadSelector1.getCiudad();
        proveedor.direccion = TF_Direccion.getText();

    }

    public void click(Integer fila, Integer col) {
        /*
         System.out.println("CLICK 2");

         proveedor = new Proveedor();

         proveedor.cargarPorId(tableProveedor.getValueAt(fila, 0).toString());

         System.out.println("proveedor " + proveedor.descripcion);

         asignarValoresProveedor();
         */
        /*
         Estado estadoNvo;
         Pais paisNvo;
         Ciudad ciudadNvo;

         if (col == Proveedor.colEstado) {

         estado = new Estado();
         estadoNvo = new Estado();
         estado.cargarPorDescripcion(tableProveedor.getValueAt(fila, col).toString());

         estadoNvo = estado.mostrarDialogoEstado();

         if (!estado.descripcion.equals(estadoNvo.descripcion)) {
         tableProveedor.setValueAt(estadoNvo.descripcion, fila, col);

         cambiaStatus();
         }
         }

         if (col == Proveedor.colPais) {

         pais = new Pais();
         paisNvo = new Pais();
         pais.cargarPorDescripcion(tableProveedor.getValueAt(fila, Proveedor.colPais).toString());

         paisNvo = pais.mostrarDialogoPais();

         if (!pais.descripcion.equals(paisNvo.descripcion)) {
         tableProveedor.setValueAt(paisNvo.descripcion, fila, Proveedor.colPais);

         cambiaStatus();
         }
         }

         if (col == Proveedor.colCiudad) {

         ciudad = new Ciudad();
         ciudadNvo = new Ciudad();
         estado = new Estado();
         estado.cargarPorDescripcion(tableProveedor.getValueAt(fila, Proveedor.colEstado).toString());
         ciudad.cargarPorDescripcion(tableProveedor.getValueAt(fila, Proveedor.colCiudad).toString());

         ciudadNvo = ciudad.mostrarDialogoCiudad(estado);

         if (!ciudad.descripcion.equals(ciudadNvo.descripcion)) {

         tableProveedor.setValueAt(ciudadNvo.descripcion, fila, Proveedor.colCiudad);
         cambiaStatus();
         }
         }
        
         */
    }

    public boolean cargando_tabla;

    public void cargarProveedor() {

        cargando_tabla = true;
        proveedor = new Proveedor();
        asignarValoresProveedor();
        Proveedor.cargarProveedores(tableProveedor);
        cargando_tabla = false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableProveedor = new abstractt.Table();
        id_proveedor = new abstractt.TextField();
        descripcion = new abstractt.TextField();
        jLabel4 = new javax.swing.JLabel();
        CB_FisicaMoral = new abstractt.ComboBox();
        jLabel5 = new javax.swing.JLabel();
        TF_email = new abstractt.TextField();
        jLabel6 = new javax.swing.JLabel();
        TF_Telefono = new abstractt.TextField();
        jLabel7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        paisSelector1 = new domain.PaisSelector();
        jLabel3 = new javax.swing.JLabel();
        estadoSelector1 = new domain.EstadoSelector();
        jLabel8 = new javax.swing.JLabel();
        ciudadSelector1 = new domain.CiudadSelector();
        jLabel9 = new javax.swing.JLabel();
        TF_Direccion = new abstractt.TextField();
        jLabel10 = new javax.swing.JLabel();
        btn_Nuevo = new abstractt.Boton();
        btn_Guardar = new abstractt.Boton();
        btn_Eliminar = new abstractt.Boton();
        etiqueta1 = new abstractt.Etiqueta();
        fondo1 = new abstractt.fondo();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Catalogo de Proveedores");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 0, 102));
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableProveedor.setForeground(new java.awt.Color(230, 225, 195));
        tableProveedor.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tableProveedor);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 880, 240));

        id_proveedor.setEditable(false);
        id_proveedor.setOpaque(false);
        jPanel1.add(id_proveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 260, -1));
        jPanel1.add(descripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 250, -1));

        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(95, 84, 88));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Telefono:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 100, 20));

        CB_FisicaMoral.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Fisica", "Moral" }));
        jPanel1.add(CB_FisicaMoral, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, -1, -1));

        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(95, 84, 88));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Nombre:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 100, 20));
        jPanel1.add(TF_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 250, -1));

        jLabel6.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(95, 84, 88));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Persona Fisica/Moral:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, -1, 20));
        jPanel1.add(TF_Telefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 100, -1));

        jLabel7.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(95, 84, 88));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("e-mail:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 100, 20));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Dirección"));
        jPanel3.setOpaque(false);
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(paisSelector1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 240, -1));

        jLabel3.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(95, 84, 88));
        jLabel3.setText("Dirección");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 60, 20));

        estadoSelector1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estadoSelector1ActionPerformed(evt);
            }
        });
        jPanel3.add(estadoSelector1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 240, -1));

        jLabel8.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(95, 84, 88));
        jLabel8.setText("Pais:");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 60, 20));
        jPanel3.add(ciudadSelector1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 240, -1));

        jLabel9.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(95, 84, 88));
        jLabel9.setText("Estado:");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 60, 20));
        jPanel3.add(TF_Direccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, 400, -1));

        jLabel10.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(95, 84, 88));
        jLabel10.setText("Ciudad:");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 60, 20));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, 500, 140));

        btn_Nuevo.setText("Nuevo");
        btn_Nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_NuevoActionPerformed(evt);
            }
        });
        jPanel1.add(btn_Nuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 100, 30));

        btn_Guardar.setText("Guardar");
        btn_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_GuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_Guardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 450, 100, 30));

        btn_Eliminar.setText("Eliminar");
        btn_Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_Eliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 450, 100, 30));

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, 900, 500));

        etiqueta1.setBackground(new java.awt.Color(95, 84, 88));
        etiqueta1.setForeground(new java.awt.Color(230, 225, 195));
        etiqueta1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiqueta1.setText("Proveedores");
        etiqueta1.setFont(new java.awt.Font("Trebuchet", 1, 36)); // NOI18N
        etiqueta1.setOpaque(true);
        jPanel2.add(etiqueta1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 60));

        fondo1.setText("fondo1");
        jPanel2.add(fondo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void nuevo() {

        id_proveedor.setText("");
        descripcion.setText("");
        CB_FisicaMoral.setSelectedIndex(0);
        TF_email.setText("");
        TF_Telefono.setText("");
        paisSelector1.cargar();
        estadoSelector1.cargar();
        TF_Direccion.setText("");

    }

    private void grabar() {

        recuperarValoresProveedor();

        if (proveedor.id_proveedor.equals("")) {
            //Nuevo

            if (proveedor.crear()) {
                JOptionPane.showMessageDialog(this, "Se Creo el Proveedor Correctamente", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
                cargarProveedor();
            } else {

                JOptionPane.showMessageDialog(this, "Error al Crear el Proveedor\n" + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
            }

        } else {
            //Editar

            if (proveedor.actualizar()) {
                JOptionPane.showMessageDialog(this, "Se Actualizo el Proveedor Correctamente", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
                cargarProveedor();
            } else {

                JOptionPane.showMessageDialog(this, "Error al Actualizar el ¨Proveedor\n" + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    private void estadoSelector1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estadoSelector1ActionPerformed

        estado = estadoSelector1.getEstado();
        ciudadSelector1.cargar(estado);

    }//GEN-LAST:event_estadoSelector1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
//        parent.setVisible(true);
    }//GEN-LAST:event_formWindowClosing

    private void btn_NuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_NuevoActionPerformed
        nuevo();
    }//GEN-LAST:event_btn_NuevoActionPerformed

    private void btn_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_GuardarActionPerformed
        grabar();
    }//GEN-LAST:event_btn_GuardarActionPerformed

    private void btn_EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EliminarActionPerformed
        int opcion;
        Integer fila = tableProveedor.getSelectedRow();

        if (fila >= 0) {

            opcion = JOptionPane.showConfirmDialog(this, "¿Desea Borrar el Registro Seleccionado?", gs_mensaje, JOptionPane.YES_NO_OPTION);

            if (opcion != 0) {

                return;
            }

            if (!tieneID(fila)) {

                tableProveedor.eliminarFila(fila);
            } else {
//Borra de BD
                if (proveedor.eliminar()) {

                    JOptionPane.showMessageDialog(this, "Se Elimino el Proveedor Correctamente", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);

                    this.cargarProveedor();

                } else {

                    JOptionPane.showMessageDialog(this, "Error al Eliminar el Proveedor\n" + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_btn_EliminarActionPerformed

    public boolean tieneID(Integer fila) {

        String id_proveedor;
        System.out.println("fila " + fila);

        if (tableProveedor.getValueAt(fila, 0) == null) {
            return false;
        }

        id_proveedor = tableProveedor.getValueAt(fila, 0).toString();

        if (id_proveedor == null) {
            return false;
        }

        if (id_proveedor.equals("")) {
            return false;
        }

        return true;
    }
    java.awt.Frame parent;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private abstractt.ComboBox CB_FisicaMoral;
    private abstractt.TextField TF_Direccion;
    private abstractt.TextField TF_Telefono;
    private abstractt.TextField TF_email;
    private abstractt.Boton btn_Eliminar;
    private abstractt.Boton btn_Guardar;
    private abstractt.Boton btn_Nuevo;
    private domain.CiudadSelector ciudadSelector1;
    private abstractt.TextField descripcion;
    private domain.EstadoSelector estadoSelector1;
    private abstractt.Etiqueta etiqueta1;
    private abstractt.fondo fondo1;
    private abstractt.TextField id_proveedor;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private domain.PaisSelector paisSelector1;
    private abstractt.Table tableProveedor;
    // End of variables declaration//GEN-END:variables
}
