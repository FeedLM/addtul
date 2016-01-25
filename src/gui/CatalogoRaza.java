/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.Raza;
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

/**
 *
 * @author sperez
 */
public class CatalogoRaza extends javax.swing.JDialog {

    /**
     * Creates new form CatalogoRaza
     */
    public CatalogoRaza(java.awt.Frame parent) {
        //super(parent);
        this.parent = parent;
        initComponents();
        cargarRaza();

        ListSelectionModel lsm = this.tableRaza.getSelectionModel();

        lsm.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                selectRaza();
            }
        });

        this.tableRaza.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                table1KeyPressed(evt);
            }
        });

        Image i = null;
        i = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/logo tru-test.png"));
        setIconImage(i);

        this.setTitle(this.getTitle() + " " + rancho.descripcion);
        setLocationRelativeTo(null);
        fondo1.cargar(this.getSize());
    }

    private void table1KeyPressed(KeyEvent evt) {
        if (this.tableRaza.isEditing()) {
            this.tableRaza.getCellEditor().stopCellEditing();
        }
        cambiaStatus();
    }

    public void cambiaStatus() {

        int fila = this.tableRaza.getSelectedRow();
        int itemStatus;
        if (fila >= 0) {

            itemStatus = Integer.parseInt(tableRaza.getValueAt(fila, tableRaza.colItemStatus).toString());

            switch (itemStatus) {

                case 0:
                    tableRaza.setValueAt(2, fila, tableRaza.colItemStatus);
                    break;
                case 1:
                    tableRaza.setValueAt(1, fila, tableRaza.colItemStatus);
                    break;
                case 2:
                    tableRaza.setValueAt(2, fila, tableRaza.colItemStatus);
                    break;
            }
        }
    }

    private void grabar() {

        Integer filas = 0;
        filas = this.tableRaza.getRowCount();
        Raza raza;
        boolean error = false;
        int itemStatus;

        tableRaza.acceptText();

        for (int i = 0; i < filas; i++) {

            itemStatus = Integer.parseInt(tableRaza.getValueAt(i, tableRaza.colItemStatus).toString());

            switch (itemStatus) {

                case 0:
                    //No hay cambio
                    break;
                case 1:
                    //Nuevo
                    raza = new Raza();
                    raza.descripcion = tableRaza.getValueAt(i, 1).toString();
                    System.out.println("Nuevo " + raza.descripcion);
                    if (raza.crear()) {
                        //cambiar no hay cambios de status
                        tableRaza.setValueAt(0, i, tableRaza.colItemStatus);
                    } else {
                        error = true;
                        JOptionPane.showMessageDialog(this, "Error al Crear la Raza\n" + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
                    }

                    break;
                case 2:
                    //Cambios
                    raza = new Raza();
                    raza.cargarPorId(tableRaza.getValueAt(i, 0).toString());
                    System.out.print("modificando: " + raza.descripcion);
                    raza.descripcion = tableRaza.getValueAt(i, 1).toString();
                    System.out.println("a " + raza.descripcion);
                    if (raza.actualizar()) {
                        //cambiar no hay cambios de status
                        tableRaza.setValueAt(0, i, tableRaza.colItemStatus);
                    } else {
                        error = true;
                        JOptionPane.showMessageDialog(this, "Error al Actualizar la Raza\n" + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
                    }

                    break;
            }
        }

        if (!error) {

            JOptionPane.showMessageDialog(this, "Se Guardaron los Cambios Correctamente", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    Raza raza;

    private void selectRaza() {

        raza = new Raza();

        Integer fila = tableRaza.getSelectedRow();

        if (fila >= 0 && tableRaza.getValueAt(fila, 0) != null) {

            raza.cargarPorId(tableRaza.getValueAt(fila, 0).toString());
        }
    }

    public void cargarRaza() {

        Raza.cargarRazas(tableRaza);
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
        tableRaza = new abstractt.Table();
        etiqueta1 = new abstractt.Etiqueta();
        btn_nuevo = new abstractt.Boton();
        btn_guardar = new abstractt.Boton();
        btn_Eliminar = new abstractt.Boton();
        fondo1 = new abstractt.fondo();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Catalogo de Razas");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        tableRaza.setForeground(new java.awt.Color(230, 225, 195));
        tableRaza.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tableRaza);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(50, 100, 700, 300);

        etiqueta1.setBackground(new java.awt.Color(95, 84, 88));
        etiqueta1.setForeground(new java.awt.Color(230, 225, 195));
        etiqueta1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiqueta1.setText("R a z a s ");
        etiqueta1.setFont(new java.awt.Font("Trebuchet", 1, 36)); // NOI18N
        etiqueta1.setOpaque(true);
        jPanel1.add(etiqueta1);
        etiqueta1.setBounds(0, 0, 800, 60);

        btn_nuevo.setText("Nuevo");
        btn_nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevoActionPerformed(evt);
            }
        });
        jPanel1.add(btn_nuevo);
        btn_nuevo.setBounds(10, 450, 100, 30);

        btn_guardar.setText("Guardar");
        btn_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_guardar);
        btn_guardar.setBounds(130, 450, 100, 30);

        btn_Eliminar.setText("Eliminar");
        btn_Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_Eliminar);
        btn_Eliminar.setBounds(250, 450, 100, 30);

        fondo1.setText("fondo1");
        jPanel1.add(fondo1);
        fondo1.setBounds(0, 0, 34, 14);

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        parent.setVisible(true);
    }//GEN-LAST:event_formWindowClosing

    private void btn_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevoActionPerformed

        this.tableRaza.agregarFila(jScrollPane1);
        this.tableRaza.transferFocus();
        Integer fila = tableRaza.getSelectedRow();
        tableRaza.setValueAt(1, fila, tableRaza.colItemStatus);
    }//GEN-LAST:event_btn_nuevoActionPerformed

    private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarActionPerformed
        grabar();
    }//GEN-LAST:event_btn_guardarActionPerformed

    private void btn_EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EliminarActionPerformed
        int opcion;
        Integer fila = tableRaza.getSelectedRow();

        if (fila >= 0) {

            opcion = JOptionPane.showConfirmDialog(this, "¿Desea Borrar el Registro Seleccionado?", gs_mensaje, JOptionPane.YES_NO_OPTION);

            if (opcion != 0) {

                return;
            }

            if (!tieneID(fila)) {

                tableRaza.eliminarFila(fila);
            } else {
//Borra de BD
                if (raza.eliminar()) {

                    JOptionPane.showMessageDialog(this, "Se Elimino la Raza Correctamente", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);

                    this.cargarRaza();

                } else {

                    JOptionPane.showMessageDialog(this, "Error al Eliminar la Raza\n" + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_btn_EliminarActionPerformed

    public boolean tieneID(Integer fila) {

        String id_raza;
        System.out.println("fila " + fila);

        if (tableRaza.getValueAt(fila, 0) == null) {
            return false;
        }

        id_raza = tableRaza.getValueAt(fila, 0).toString();

        if (id_raza == null) {
            return false;
        }

        if (id_raza.equals("")) {
            return false;
        }

        return true;
    }
    java.awt.Frame parent;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private abstractt.Boton btn_Eliminar;
    private abstractt.Boton btn_guardar;
    private abstractt.Boton btn_nuevo;
    private abstractt.Etiqueta etiqueta1;
    private abstractt.fondo fondo1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private abstractt.Table tableRaza;
    // End of variables declaration//GEN-END:variables
}
