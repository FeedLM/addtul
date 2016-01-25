/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.Animal;
import domain.Corral;
import static domain.Corral.cargarCorrales;
import static domain.Corral.corralIdNombre;
import static domain.CorralAnimal.cargarAnimalesCorral;
import static domain.CorralAnimal.cargarAnimalesCorral;
import static domain.CorralAnimal.cargarAnimalesCorral;
import domain.Parametro;
import domain.ParametrosSP;
import domain.Rancho;
import domain.SR232;
import static gui.Desktop.rancho;
import static gui.Desktop.manejadorBD;
import static gui.Login.gs_mensaje;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import static java.sql.Types.NULL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Gilberto Adan González Silva
 */
public class Traslados extends javax.swing.JInternalFrame{

    private SR232 stick;
    private String puertoStick;
    private Desktop parent;

    /**
     * Creates new form Grupos
     */
    public Traslados(Desktop parent) {
        this.parent = parent;
        initComponents();
        setClosable(true);
        this.pack();
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/resources/logo tru-test.png")));

        ListSelectionModel lsm = JL_Corrales.getSelectionModel();

        lsm.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {

                selectCorral();
            }
        });

        cargarCorralDestino();
        cargarCorralesList();

        animales2 = new ArrayList();
        id_corral_destino = "";

        DefaultListCellRenderer cellRenderer = (DefaultListCellRenderer) JL_Corrales.getCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        cellRenderer = (DefaultListCellRenderer) JL_Animales.getCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        cellRenderer = (DefaultListCellRenderer) JL_CorralDestino.getCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        cargarPuertos();

        stick = new SR232(puertoStick, 1, parent, 4);
        stick.setEID(tf_Eid);   //  tf_Eid::tf_numeroPedido

        stick.start();

        this.setTitle(this.getTitle() + " " + rancho.descripcion);
        ranchoSelector1.cargar();
        ranchoSelector1.setSelectedItem(rancho.descripcion);
        
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension tamanoPantalla = tk.getScreenSize();
        setResizable(false);
        fondo1.cargar(tamanoPantalla);
    }

    Corral corralP;

    public void setEid(String recibido) {
        Corral corral = null;
        Animal animal;
        animal = new Animal();

        corral = new Corral();

        animal.cargarPorEid(recibido);

        if (animal.id_animal != null) {

            //System.out.println("Animal "+animal.toString());
            corral.cargarPorAnimal(animal);

            if (corralP == null) {
                corralP = corral;
            } else {

                if (!corralP.id_corral.equals(corral.id_corral)) {

                    JOptionPane.showMessageDialog(this, "No se permite mezclar animales de diferente corral origen\n\n"
                            + "Corral Origen: " + corralP.nombre + "\n\n"
                            + "Animal: " + animal.arete_visual + " de Corral: " + corral.nombre, gs_mensaje, JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            for (int i = 0; i < JL_Corrales.getModel().getSize(); i++) {

                // System.out.println("Corral: " + JL_Corrales.getModel().getElementAt(i).toString());
                if (JL_Corrales.getModel().getElementAt(i).toString().equals(corral.nombre)) {

                    JL_Corrales.setSelectedIndex(i);
                    System.out.println("Corral Encontrado");

                    for (int j = 0; j < JL_Animales.getModel().getSize(); j++) {

                        if (JL_Animales.getModel().getElementAt(j).toString().equals(animal.arete_visual)) {

                            JL_Animales.setSelectedIndex(j);
                            agregarAnimales();
                            break;
                        }
                    }

                    break;

                }
            }
        }
    }

    public void cargarPuertos() {

        manejadorBD.consulta(
                "SELECT puerto_baston "
                + "FROM configuracion ");

        if (manejadorBD.getRowCount() > 0) {

            puertoStick = manejadorBD.getValorString(0, 0);
        }
    }

    private void cargarCorralDestino() {
        /*
         cargandoCorralDestinos = true;

         corralDestinoSelector.removeAllItems();

         corralDestinoSelector.addArray(cargarCorrales());
         cargandoCorralDestinos = false;
         */

    }

    private void selectCorral() {

        String ls_corral;
        ls_corral = JL_Corrales.getSelectedValue().toString();

        id_corral_origen = corralIdNombre(ls_corral);
//      System.out.println("corral 1");
        animales1 = cargarAnimalesCorral(ls_corral,rancho.id_rancho);
//      System.out.println("corral 2");
        animales1.remove("");
        llenarCooral();
    }

    private void llenarCooral() {

        DefaultListModel listModel = new DefaultListModel();

        for (int i = 0; i < animales1.size(); i++) {

            listModel.addElement(animales1.get(i).toString());
        }

        JL_Animales.setModel(listModel);
    }

    private void cargarCorralesList() {

        ArrayList a = cargarCorrales();
        DefaultListModel listModel = new DefaultListModel();

        for (int i = 0; i < a.size(); i++) {

            if (!a.get(i).toString().equals("")) {

                listModel.addElement(a.get(i).toString());
            }
        }

        JL_Corrales.setModel(listModel);
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
        tf_Eid = new abstractt.TextField();
        etiqueta1 = new abstractt.Etiqueta();
        pn_animales = new javax.swing.JPanel();
        etiqueta4 = new abstractt.Etiqueta();
        jScrollPane3 = new javax.swing.JScrollPane();
        JL_Animales = new javax.swing.JList();
        jScrollPane1 = new javax.swing.JScrollPane();
        JL_Corrales = new javax.swing.JList();
        etiqueta5 = new abstractt.Etiqueta();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 50), new java.awt.Dimension(0, 50), new java.awt.Dimension(32767, 50));
        pn_botones = new javax.swing.JPanel();
        btn_Agregar = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        btn_quitar = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 50), new java.awt.Dimension(0, 50), new java.awt.Dimension(32767, 50));
        btn_Actualizar = new javax.swing.JButton();
        pn_destino = new javax.swing.JPanel();
        etiqueta2 = new abstractt.Etiqueta();
        ranchoSelector1 = new domain.RanchoSelector();
        corralSelector1 = new domain.CorralSelector();
        etiqueta3 = new abstractt.Etiqueta();
        jScrollPane4 = new javax.swing.JScrollPane();
        JL_CorralDestino = new javax.swing.JList();
        fondo1 = new abstractt.fondo();

        setTitle("Grupos");

        jPanel1.setBackground(new java.awt.Color(238, 217, 170));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tf_Eid.setText("tf_Eid");
        jPanel1.add(tf_Eid, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        etiqueta1.setBackground(new java.awt.Color(95, 84, 88));
        etiqueta1.setForeground(new java.awt.Color(230, 225, 195));
        etiqueta1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiqueta1.setText("Traslados");
        etiqueta1.setFont(new java.awt.Font("Trebuchet", 1, 48)); // NOI18N
        etiqueta1.setOpaque(true);
        jPanel1.add(etiqueta1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1235, 80));

        pn_animales.setOpaque(false);
        pn_animales.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        etiqueta4.setText("Corral/Grupos: ");
        pn_animales.add(etiqueta4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jScrollPane3.setOpaque(false);

        JL_Animales.setBackground(new java.awt.Color(95, 84, 88));
        JL_Animales.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        JL_Animales.setForeground(new java.awt.Color(230, 225, 195));
        JL_Animales.setSelectionBackground(new java.awt.Color(195, 25, 25));
        jScrollPane3.setViewportView(JL_Animales);

        pn_animales.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 50, 300, 400));

        jScrollPane1.setOpaque(false);

        JL_Corrales.setBackground(new java.awt.Color(95, 84, 88));
        JL_Corrales.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        JL_Corrales.setForeground(new java.awt.Color(230, 225, 195));
        JL_Corrales.setSelectionBackground(new java.awt.Color(195, 25, 25));
        jScrollPane1.setViewportView(JL_Corrales);

        pn_animales.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 300, 400));

        etiqueta5.setText("Animales: ");
        pn_animales.add(etiqueta5, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 0, -1, -1));
        pn_animales.add(filler3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 50, 50));

        jPanel1.add(pn_animales, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, -1));

        pn_botones.setOpaque(false);
        pn_botones.setLayout(new javax.swing.BoxLayout(pn_botones, javax.swing.BoxLayout.Y_AXIS));

        btn_Agregar.setBackground(new java.awt.Color(64, 36, 4));
        btn_Agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/arrow_right.png"))); // NOI18N
        btn_Agregar.setContentAreaFilled(false);
        btn_Agregar.setOpaque(true);
        btn_Agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AgregarActionPerformed(evt);
            }
        });
        pn_botones.add(btn_Agregar);
        pn_botones.add(filler2);

        btn_quitar.setBackground(new java.awt.Color(64, 36, 4));
        btn_quitar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/arrow_left.png"))); // NOI18N
        btn_quitar.setContentAreaFilled(false);
        btn_quitar.setOpaque(true);
        btn_quitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_quitarActionPerformed(evt);
            }
        });
        pn_botones.add(btn_quitar);
        pn_botones.add(filler1);

        btn_Actualizar.setBackground(new java.awt.Color(64, 36, 4));
        btn_Actualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/arrow_refresh.png"))); // NOI18N
        btn_Actualizar.setContentAreaFilled(false);
        btn_Actualizar.setOpaque(true);
        btn_Actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ActualizarActionPerformed(evt);
            }
        });
        pn_botones.add(btn_Actualizar);

        jPanel1.add(pn_botones, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 250, -1, -1));

        pn_destino.setOpaque(false);
        pn_destino.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        etiqueta2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiqueta2.setText("Rancho de Destino: ");
        pn_destino.add(etiqueta2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 150, 20));

        ranchoSelector1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ranchoSelector1ActionPerformed(evt);
            }
        });
        pn_destino.add(ranchoSelector1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 200, 20));
        pn_destino.add(corralSelector1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 200, 20));

        etiqueta3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiqueta3.setText("Corral de Destino: ");
        pn_destino.add(etiqueta3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 150, 20));

        jScrollPane4.setOpaque(false);

        JL_CorralDestino.setBackground(new java.awt.Color(95, 84, 88));
        JL_CorralDestino.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        JL_CorralDestino.setForeground(new java.awt.Color(230, 225, 195));
        JL_CorralDestino.setSelectionBackground(new java.awt.Color(195, 25, 25));
        jScrollPane4.setViewportView(JL_CorralDestino);

        pn_destino.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, 300, 300));

        jPanel1.add(pn_destino, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 130, -1, -1));

        fondo1.setText("fondo1");
        jPanel1.add(fondo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_AgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AgregarActionPerformed
        agregarAnimales();

    }//GEN-LAST:event_btn_AgregarActionPerformed

    private void agregarAnimales(){
        Object tagId[] = JL_Animales.getSelectedValues();
        for (int i = 0; i < tagId.length; i++) {
            agregarAnimal(tagId[i].toString());
        }
    }
    
    private void agregarAnimal(String tag) {
        animal = new Animal();

        String tagId;
        boolean existe = false;

        //esarete_visual
        tagId = tag;

        animal.cargarPorAreteVisual(tagId,"A");

        Corral corral;
        corral = new Corral();

        corral.cargarPorAnimal(animal);

        if (corralP == null) {
            corralP = corral;
        } else {

            if (!corralP.id_corral.equals(corral.id_corral)) {

                JOptionPane.showMessageDialog(this, "No se permite mezclar animales de diferente corral origen\n\n"
                        + "Corral Origen: " + corralP.nombre + "\n\n"
                        + "Animal: " + animal.arete_visual + " de Corral: " + corral.nombre, gs_mensaje, JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // System.out.println(animal.toString());
        for (int i = 0; i < animales2.size(); i++) {

            if (animales2.get(i).equals(tagId)) {
                existe = true;
            }
        }
        if (!existe) {

            animales2.add(tagId);
        }

        llenarCorralDestino();

    }

    private Animal animal;

    private void btn_quitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_quitarActionPerformed

        String tagId;

        tagId = JL_CorralDestino.getSelectedValue().toString();

        animales2.remove(tagId);

        llenarCorralDestino();
    }//GEN-LAST:event_btn_quitarActionPerformed

    private void llenarCorralDestino() {

        DefaultListModel listModel = new DefaultListModel();

        for (int i = 0; i < animales2.size(); i++) {

            listModel.addElement(animales2.get(i).toString());
        }

        JL_CorralDestino.setModel(listModel);
    }

    private void btn_ActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ActualizarActionPerformed
        generarTraspaso();
    }//GEN-LAST:event_btn_ActualizarActionPerformed

    private void generarTraspaso() {

        String id_movimiento = "0";
        String arete_visual = "";

        Corral corralDestino;
        corralDestino = new Corral();
        corralDestino.cargarPorNombre(this.corralSelector1.getSelectedItem().toString(), ranchoTraspaso);

        if (corralDestino.id_corral.equals("")) {

            JOptionPane.showMessageDialog(this, "No ha seleccionado el Corral de Destino", gs_mensaje, JOptionPane.ERROR_MESSAGE);
            return;
        }

        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Date fecha = new Date();

        //  Obtener el Concepto del traspaso de entrada
        //  Insertar el movimiento
        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(rancho.id_rancho, "varIdRancho", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(rancho.conceptoSalidaTraspaso, "varConceptoTrasSalida", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_movimiento, "varIdMovimiento", "STRING", "OUT");
        manejadorBD.parametrosSP.agregarParametro(formatoDelTexto.format(fecha), "varFecha", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(ranchoTraspaso.id_rancho, "varIdRanchoDestino", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_corral_origen, "varIdCorralOrigen", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(corralDestino.id_corral, "varIdCorralDestino", "STRING", "IN");

        if (manejadorBD.ejecutarSP("{ call movimientoTraspasoSalida(?,?,?,?,?,?,?) }") == 0) {

            // id_con_tras_sal = manejadorBD.parametrosSP.get(1).getValor();
            id_movimiento = manejadorBD.parametrosSP.get(2).getValor();
            System.out.println("id Movimiento de Traspaso " + id_movimiento);
            System.out.println("id Concepto de Traspaso " + rancho.conceptoSalidaTraspaso);
            // JOptionPane.showMessageDialog(this, "Se Creo el corral Correctamente", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
        } else {

            JOptionPane.showMessageDialog(this, "Error al generar el movimiento de traspaso\n" + manejadorBD.errorSQL,
                    gs_mensaje, JOptionPane.ERROR_MESSAGE);
            //   manejadorBD.rollback();
            return;
        }

        //  Recorrer animales a traspasar
        for (int i = 0; i < animales2.size(); i++) {

            arete_visual = animales2.get(i).toString();

            animal.cargarPorAreteVisual(arete_visual,"A");
            //  Obtener el siguiente id detalle
            //  Insertar el detalle
            manejadorBD.parametrosSP = new ParametrosSP();

            manejadorBD.parametrosSP.agregarParametro(rancho.id_rancho, "varIdRancho", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(rancho.conceptoSalidaTraspaso, "varConceptoMovimiento", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(id_movimiento, "varIdMovimiento", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(animal.id_animal, "varIdAnimal", "STRING", "IN");

            if (manejadorBD.ejecutarSP("{ call insertarDetalleMovimiento(?,?,?,?) }") != 0) {

                JOptionPane.showMessageDialog(this, "Error al insertar el animal [" + arete_visual + "] en el traspaso", gs_mensaje, JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        //  Duplicar el movimiento con traspaso de entrada
        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(rancho.id_rancho, "varIdRancho", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_movimiento, "varIdMovimientoOrigen", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(rancho.conceptoSalidaTraspaso, "varConceptoOrigen", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(ranchoTraspaso.id_rancho, "varIdRanchoDestino", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(ranchoTraspaso.conceptoEntradaTraspaso, "varConceptoDestino", "STRING", "IN");

        if (manejadorBD.ejecutarSP("{ call duplicaMovimiento(?,?,?,?,?) }") == 0) {

            JOptionPane.showMessageDialog(this, "Se genero el traspaso correctamente ", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);

            //corralDestinoSelector.removeAllItems();
            //corralDestinoSelector.addArray(cargarCorrales());
            this.cargarCorralDestino();

            selectCorral();
            animales2 = new ArrayList();
            llenarCorralDestino();
            id_corral_destino = "";
            corralP = null;
        } else {
            JOptionPane.showMessageDialog(this, "Error al generar el movimiento de traspaso de entrada\n" + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
            //    manejadorBD.rollback();
        }

    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.stick.setSeguir(false);
        parent.setVisible(true);
    }//GEN-LAST:event_formWindowClosing

    private void ranchoSelector1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ranchoSelector1ActionPerformed

        ranchoTraspaso = new Rancho();

        ranchoTraspaso.cargarPorDescripcion(this.ranchoSelector1.getSelectedItem().toString());

        Corral.cargarCorralesRancho(ranchoTraspaso);

        this.corralSelector1.cargarPorRancho(ranchoTraspaso);

    }//GEN-LAST:event_ranchoSelector1ActionPerformed

    private boolean cargandoCorralDestinos;

    private void ObtenerCorralDestino() {

        String ls_corral;

        ls_corral = this.corralSelector1.getSelectedItem().toString();

        id_corral_destino = corralIdNombre(ls_corral);
    }

    private String id_corral_origen, id_corral_destino;
    private ArrayList animales1, animales2;
    Rancho ranchoTraspaso;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList JL_Animales;
    private javax.swing.JList JL_CorralDestino;
    private javax.swing.JList JL_Corrales;
    private javax.swing.JButton btn_Actualizar;
    private javax.swing.JButton btn_Agregar;
    private javax.swing.JButton btn_quitar;
    private domain.CorralSelector corralSelector1;
    private abstractt.Etiqueta etiqueta1;
    private abstractt.Etiqueta etiqueta2;
    private abstractt.Etiqueta etiqueta3;
    private abstractt.Etiqueta etiqueta4;
    private abstractt.Etiqueta etiqueta5;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private abstractt.fondo fondo1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPanel pn_animales;
    private javax.swing.JPanel pn_botones;
    private javax.swing.JPanel pn_destino;
    private domain.RanchoSelector ranchoSelector1;
    private abstractt.TextField tf_Eid;
    // End of variables declaration//GEN-END:variables
}
