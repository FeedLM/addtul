/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.Ciudad;
import domain.Estado;
import domain.Pais;
import domain.Cliente;
import domain.ClienteSelector;
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
public class CatalogoCliente extends javax.swing.JDialog {

    /**
     * Creates new form CatalogoRaza
     */
    public CatalogoCliente(java.awt.Frame parent) {
        //super(parent, modal);
        this.parent = parent;
        initComponents();
        cargarCliente();

        ListSelectionModel lsm = this.tableCliente.getSelectionModel();

        lsm.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                selectCliente();
            }
        });

        paisSelector1.cargar();
        estadoSelector1.cargar();

        Image i = null;
        i = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/logo tru-test.png"));
        setIconImage(i);

        this.setTitle(this.getTitle() + " " + rancho.descripcion);
        setLocationRelativeTo(null);
        fondo1.cargar(this.getSize());
        id_cliente.setVisible(false);
        
    }

    private void table1KeyPressed(KeyEvent evt) {

        if (this.tableCliente.isEditing()) {
            this.tableCliente.getCellEditor().stopCellEditing();
        }
        cambiaStatus();
    }

    public void cambiaStatus() {

        int fila = this.tableCliente.getSelectedRow();
        int itemStatus;
        if (fila >= 0) {

            itemStatus = Integer.parseInt(tableCliente.getValueAt(fila, tableCliente.colItemStatus).toString());

            switch (itemStatus) {

                case 0:
                    tableCliente.setValueAt(2, fila, tableCliente.colItemStatus);
                    break;
                case 1:
                    tableCliente.setValueAt(1, fila, tableCliente.colItemStatus);
                    break;
                case 2:
                    tableCliente.setValueAt(2, fila, tableCliente.colItemStatus);
                    break;
            }
        }
    }

    private void grabar2() {

        Integer filas = 0;
        filas = this.tableCliente.getRowCount();
        // Cliente cliente;
        boolean error = false;
        int itemStatus;
        ClienteSelector clienteSelector;
        clienteSelector = new ClienteSelector();

        tableCliente.acceptText();

        for (int i = 0; i < filas; i++) {

            itemStatus = Integer.parseInt(tableCliente.getValueAt(i, tableCliente.colItemStatus).toString());

            switch (itemStatus) {

                case 0:
                    //No hay cambio
                    break;
                case 1:
                    //Nuevo
                    cliente = new Cliente();
                    cliente.descripcion = tableCliente.getValueAt(i, 1).toString();
                    cliente.pais.cargarPorDescripcion(tableCliente.getValueAt(i, Cliente.colPais).toString());
                    cliente.estado.cargarPorDescripcion(tableCliente.getValueAt(i, Cliente.colEstado).toString());
                    cliente.ciudad.cargarPorDescripcion(tableCliente.getValueAt(i, Cliente.colCiudad).toString());
                    cliente.direccion = tableCliente.getValueAt(i, Cliente.colDireccion).toString();
                    cliente.email = tableCliente.getValueAt(i, Cliente.colEmail).toString();
                    cliente.telefono = tableCliente.getValueAt(i, Cliente.colTelefono).toString();

                    if (cliente.crear()) {
                        //cambiar no hay cambios de status
                        tableCliente.setValueAt(0, i, tableCliente.colItemStatus);
                    } else {
                        error = true;
                        JOptionPane.showMessageDialog(this, "Error al Crear el Cliente\n" + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
                    }

                    break;
                case 2:
                    //Cambios
                    cliente = new Cliente();
                    cliente.cargarPorId(tableCliente.getValueAt(i, 0).toString());
                    cliente.descripcion = tableCliente.getValueAt(i, 1).toString();
                    cliente.pais.cargarPorDescripcion(tableCliente.getValueAt(i, Cliente.colPais).toString());
                    cliente.estado.cargarPorDescripcion(tableCliente.getValueAt(i, Cliente.colEstado).toString());
                    cliente.ciudad.cargarPorDescripcion(tableCliente.getValueAt(i, Cliente.colCiudad).toString());
                    cliente.direccion = tableCliente.getValueAt(i, Cliente.colDireccion).toString();
                    cliente.email = tableCliente.getValueAt(i, Cliente.colEmail).toString();
                    cliente.telefono = tableCliente.getValueAt(i, Cliente.colTelefono).toString();

                    if (cliente.actualizar()) {
                        //cambiar no hay cambios de status
                        tableCliente.setValueAt(0, i, tableCliente.colItemStatus);
                    } else {
                        error = true;
                        JOptionPane.showMessageDialog(this, "Error al Actualizar el ¨Cliente\n" + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
                    }

                    break;
            }
        }

        if (!error) {

            JOptionPane.showMessageDialog(this, "Se Guardaron los Cambios Correctamente", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    Cliente cliente;
    Estado estado;
    Pais pais;
    Ciudad ciudad;

    private void selectCliente() {

        if (cargando_tabla) {
            return;
        }

        System.out.println("CLICK");

        Integer fila = tableCliente.getSelectedRow();

        cliente = new Cliente();

        cliente.cargarPorId(tableCliente.getValueAt(fila, 0).toString());

        System.out.println("cliente " + cliente.descripcion);

        asignarValoresCliente();

    }

    private void asignarValoresCliente() {

        id_cliente.setText(cliente.id_cliente);
        descripcion.setText(cliente.descripcion);

        CB_FisicaMoral.setSelectedIndex(0);

        if (cliente.p_fisica_moral.equals("F")) {

            CB_FisicaMoral.setSelectedItem("Fisica");
        } else {
            if (cliente.p_fisica_moral.equals("M")) {

                CB_FisicaMoral.setSelectedItem("Moral");
            }
        }

        TF_email.setText(cliente.email);
        TF_Telefono.setText(cliente.telefono);
        paisSelector1.setPais(cliente.pais);
        estadoSelector1.setEstado(cliente.estado);
        ciudadSelector1.setCiudad(cliente.estado, cliente.ciudad);
        TF_Direccion.setText(cliente.direccion);
    }

    private void recuperarValoresCliente() {

        cliente.id_cliente = id_cliente.getText();
        cliente.descripcion = descripcion.getText();
        cliente.p_fisica_moral = "";

        if (CB_FisicaMoral.getSelectedItem().equals("Fisica")) {
            cliente.p_fisica_moral = "F";
        } else {
            if (CB_FisicaMoral.getSelectedItem().equals("Moral")) {
                cliente.p_fisica_moral = "M";
            }
        }
        cliente.email = TF_email.getText();
        cliente.telefono = TF_Telefono.getText();
        cliente.pais = paisSelector1.getPais();
        cliente.estado = estadoSelector1.getEstado();

        cliente.ciudad = ciudadSelector1.getCiudad();
        cliente.direccion = TF_Direccion.getText();

    }

    public boolean cargando_tabla;

    public void cargarCliente() {

        cargando_tabla = true;
        cliente = new Cliente();
        asignarValoresCliente();
        Cliente.cargarClientes(tableCliente);
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableCliente = new abstractt.Table();
        btn_Nuevo = new abstractt.Boton();
        btn_Grabar = new abstractt.Boton();
        btn_Eliminar = new abstractt.Boton();
        id_cliente = new abstractt.TextField();
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

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableCliente.setForeground(new java.awt.Color(230, 225, 195));
        tableCliente.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tableCliente);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 880, 240));

        btn_Nuevo.setText("Nuevo");
        btn_Nuevo.setToolTipText("");
        btn_Nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_NuevoActionPerformed(evt);
            }
        });
        jPanel1.add(btn_Nuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, 80, 30));

        btn_Grabar.setText("Guardar");
        btn_Grabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_GrabarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_Grabar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 450, 80, 30));

        btn_Eliminar.setText("Eliminar");
        btn_Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_Eliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 450, 80, 30));

        id_cliente.setEditable(false);
        jPanel1.add(id_cliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 260, -1));
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

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 900, 500));

        etiqueta1.setBackground(new java.awt.Color(95, 84, 88));
        etiqueta1.setForeground(new java.awt.Color(230, 225, 195));
        etiqueta1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiqueta1.setText("Clientes");
        etiqueta1.setFont(new java.awt.Font("Trebuchet", 1, 36)); // NOI18N
        etiqueta1.setOpaque(true);
        getContentPane().add(etiqueta1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 60));

        fondo1.setText("fondo1");
        getContentPane().add(fondo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void nuevo() {

        id_cliente.setText("");
        descripcion.setText("");
        CB_FisicaMoral.setSelectedIndex(0);
        TF_email.setText("");
        TF_Telefono.setText("");
        paisSelector1.cargar();
        estadoSelector1.cargar();
        TF_Direccion.setText("");

    }

    private void grabar() {

        recuperarValoresCliente();

        if (cliente.id_cliente.equals("")) {
            //Nuevo

            if (cliente.crear()) {
                JOptionPane.showMessageDialog(this, "Se Creo el Cliente Correctamente", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
                cargarCliente();
            } else {

                JOptionPane.showMessageDialog(this, "Error al Crear el Cliente\n" + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
            }

        } else {
            //Editar

            if (cliente.actualizar()) {
                JOptionPane.showMessageDialog(this, "Se Actualizo el Cliente Correctamente", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
                cargarCliente();
            } else {

                JOptionPane.showMessageDialog(this, "Error al Actualizar el ¨Cliente\n" + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    private void estadoSelector1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estadoSelector1ActionPerformed

        estado = estadoSelector1.getEstado();
        ciudadSelector1.cargar(estado);

    }//GEN-LAST:event_estadoSelector1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        parent.setVisible(true);
    }//GEN-LAST:event_formWindowClosing

    private void btn_NuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_NuevoActionPerformed
        nuevo();
    }//GEN-LAST:event_btn_NuevoActionPerformed

    private void btn_GrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_GrabarActionPerformed
        grabar();
    }//GEN-LAST:event_btn_GrabarActionPerformed

    private void btn_EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EliminarActionPerformed
        int opcion;
        Integer fila = tableCliente.getSelectedRow();

        if (fila >= 0) {

            opcion = JOptionPane.showConfirmDialog(this, "¿Desea Borrar el Registro Seleccionado?", gs_mensaje, JOptionPane.YES_NO_OPTION);

            if (opcion != 0) {

                return;
            }

            if (!tieneID(fila)) {

                tableCliente.eliminarFila(fila);
            } else {
//Borra de BD
                if (cliente.eliminar()) {

                    JOptionPane.showMessageDialog(this, "Se Elimino el Cliente Correctamente", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);

                    this.cargarCliente();

                } else {

                    JOptionPane.showMessageDialog(this, "Error al Eliminar el Cliente\n" + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_btn_EliminarActionPerformed

    public boolean tieneID(Integer fila) {

        String id_cliente;
        System.out.println("fila " + fila);

        if (tableCliente.getValueAt(fila, 0) == null) {
            return false;
        }

        id_cliente = tableCliente.getValueAt(fila, 0).toString();

        if (id_cliente == null) {
            return false;
        }

        if (id_cliente.equals("")) {
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
    private abstractt.Boton btn_Grabar;
    private abstractt.Boton btn_Nuevo;
    private domain.CiudadSelector ciudadSelector1;
    private abstractt.TextField descripcion;
    private domain.EstadoSelector estadoSelector1;
    private abstractt.Etiqueta etiqueta1;
    private abstractt.fondo fondo1;
    private abstractt.TextField id_cliente;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private domain.PaisSelector paisSelector1;
    private abstractt.Table tableCliente;
    // End of variables declaration//GEN-END:variables
}
