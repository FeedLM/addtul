package gui;

import domain.Animal;
import domain.Cria;
import static domain.Cria.leerCrias;
import domain.Excel;
import domain.SR232;
import static gui.Desktop.rancho;
import static gui.Desktop.manejadorBD;
import static gui.Login.gs_mensaje;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Developer GAGS
 */
public class PartosAbortos extends javax.swing.JFrame {

    /**
     * Creates new form MedicinasAnimal
     */
    public PartosAbortos(Desktop parent) {
        this.parent = parent;
//        super(Aparent, modal);
        initComponents();
        setLocationRelativeTo(null);
        animal = new Animal();

//        parent = Aparent;
        animalSelector1.cargararete_visualshembrasEmparejadas();
        HembrasEparejadasSelector.cargararete_visualshembrasEmparejadas();

        // cargarParto();
        this.razaSelector1.cargarSeleccionar();
        this.sexoSelector.cargar();

        cargarPuertos();
        /*
         stick = new SR232(puertoStick, 6, parent, 2);
         stick.setEID(tf_Eid);   //  tf_Eid::tf_numeroPedido

         stick.start();
         */
        cargarStick();

        // cria = new Cria();
        ListSelectionModel lsm = this.t_Parto.getSelectionModel();
        lsm.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                seleccionaCria();
            }
        });

        this.setTitle(this.getTitle() + " " + rancho.descripcion);
        fondo1.cargar(jPanel1.getSize());
        fondo2.cargar(jPanel5.getSize());
        tf_Peso.textFieldDouble();

        tipoPartoSelector.cargar();
        tipoPartoSelector1.cargar();

        tipoAbortoSelector1.cargar();
    }

    public void cargarStick() {

        stick = new SR232(puertoStick, 1, parent, 6);

        if (!stick.puertoDisponible()) {

            JOptionPane.showMessageDialog(this, "No se pudo conectar al puerto serie " + puertoStick + "\n las opciones de entrada estaran deshabilitadas ", gs_mensaje, JOptionPane.ERROR_MESSAGE);
            return;
        }
        stick.start();
    }

    private void seleccionaCria() {

        Integer fila;
        fila = this.t_Parto.getSelectedRow();

        if (fila >= 0) {

            cria.cargarPorIdCria(Integer.parseInt(t_Parto.getValueAt(fila, 1).toString()));

            this.tf_Eid.setText(cria.arete);
            this.sexoSelector.setSelectedItem(cria.sexo.descripcion);
            this.razaSelector1.setSelectedItem(cria.raza.descripcion);
            this.selectorFecha.setFecha(cria.fecha_nacimiento);

        }
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
    private SR232 stick;

    public void setEid(String eid) {

        tf_Eid.setText(eid);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        t_Parto = new abstractt.Table();
        jPanel3 = new javax.swing.JPanel();
        selectorFecha = new gui.SelectorFecha();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        razaSelector1 = new domain.RazaSelector();
        tf_Eid = new abstractt.TextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tf_Peso = new abstractt.TextField();
        tipoPartoSelector = new domain.TipoPartoSelector();
        sexoSelector = new domain.SexoSelector();
        jLabel1 = new javax.swing.JLabel();
        animalSelector1 = new domain.AnimalSelector();
        etiqueta1 = new abstractt.Etiqueta();
        jPanel2 = new javax.swing.JPanel();
        btn_Agregar = new abstractt.Boton();
        btn_Eliminar = new abstractt.Boton();
        fondo1 = new abstractt.fondo();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        HembrasEparejadasSelector = new domain.AnimalSelector();
        etiqueta2 = new abstractt.Etiqueta();
        jPanel7 = new javax.swing.JPanel();
        btn_Agregar1 = new abstractt.Boton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        selectorFecha1 = new gui.SelectorFecha();
        tipoAbortoSelector1 = new domain.TipoAbortoSelector();
        tipoPartoSelector1 = new domain.TipoPartoSelector();
        fondo2 = new abstractt.fondo();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Captura de Partos");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(520, 750));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        t_Parto.setForeground(new java.awt.Color(230, 225, 195));
        t_Parto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        jScrollPane2.setViewportView(t_Parto);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 500, 200));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cria", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Trebuchet MS", 0, 12))); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        selectorFecha.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fecha Nacimiento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Trebuchet MS", 0, 12))); // NOI18N
        jPanel3.add(selectorFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 300, -1));

        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(95, 84, 88));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Arete");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 50, 20));

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(95, 84, 88));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Sexo");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 50, 20));

        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(95, 84, 88));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Tipo de Parto");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 100, 20));
        jPanel3.add(razaSelector1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, 160, -1));
        jPanel3.add(tf_Eid, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, 160, -1));

        jLabel6.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(95, 84, 88));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Raza");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 50, 20));

        jLabel7.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(95, 84, 88));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Peso");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, 50, 20));

        tf_Peso.setText("0.00");
        jPanel3.add(tf_Peso, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, 160, -1));
        jPanel3.add(tipoPartoSelector, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 140, 160, -1));
        jPanel3.add(sexoSelector, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 160, -1));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 130, 350, 290));

        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(95, 84, 88));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Arete Visual(Madre):");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 130, 20));

        animalSelector1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                animalSelector1ActionPerformed(evt);
            }
        });
        jPanel1.add(animalSelector1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 100, 150, 20));

        etiqueta1.setBackground(new java.awt.Color(97, 84, 88));
        etiqueta1.setForeground(new java.awt.Color(230, 225, 195));
        etiqueta1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiqueta1.setText("P a r t o s");
        etiqueta1.setFont(new java.awt.Font("Trebuchet", 1, 48)); // NOI18N
        etiqueta1.setOpaque(true);
        jPanel1.add(etiqueta1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 520, 80));

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_Agregar.setText("Agregar");
        btn_Agregar.setFont(new java.awt.Font("Trebuchet", 1, 12)); // NOI18N
        btn_Agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AgregarActionPerformed(evt);
            }
        });
        jPanel2.add(btn_Agregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 100, 30));

        btn_Eliminar.setText("Eliminar");
        btn_Eliminar.setFont(new java.awt.Font("Trebuchet", 1, 12)); // NOI18N
        btn_Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EliminarActionPerformed(evt);
            }
        });
        jPanel2.add(btn_Eliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 20, 100, 30));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 630, 520, 70));

        fondo1.setText("fondo1");
        jPanel1.add(fondo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jTabbedPane1.addTab("P a r t o s", jPanel1);
        jPanel1.getAccessibleContext().setAccessibleName("");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setPreferredSize(new java.awt.Dimension(520, 750));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(95, 84, 88));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Arete Visual(Madre):");
        jPanel5.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, 130, 20));

        HembrasEparejadasSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HembrasEparejadasSelectorActionPerformed(evt);
            }
        });
        jPanel5.add(HembrasEparejadasSelector, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 180, 160, 20));

        etiqueta2.setBackground(new java.awt.Color(97, 84, 88));
        etiqueta2.setForeground(new java.awt.Color(230, 225, 195));
        etiqueta2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiqueta2.setText("A b o r t o s");
        etiqueta2.setFont(new java.awt.Font("Trebuchet", 1, 48)); // NOI18N
        etiqueta2.setOpaque(true);
        jPanel5.add(etiqueta2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 520, 80));

        jPanel7.setOpaque(false);
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_Agregar1.setText("Agregar");
        btn_Agregar1.setFont(new java.awt.Font("Trebuchet", 1, 12)); // NOI18N
        btn_Agregar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Agregar1ActionPerformed(evt);
            }
        });
        jPanel7.add(btn_Agregar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 100, 30));

        jPanel5.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 400, 400, 70));

        jLabel8.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(95, 84, 88));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Tipo de Parto:");
        jPanel5.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 220, 140, 20));

        jLabel9.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(95, 84, 88));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Tipo de Aborto:");
        jPanel5.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 260, 110, 20));

        selectorFecha1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fecha", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Trebuchet MS", 0, 12))); // NOI18N
        jPanel5.add(selectorFecha1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 300, 300, -1));
        jPanel5.add(tipoAbortoSelector1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 260, 160, -1));
        jPanel5.add(tipoPartoSelector1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 220, 160, -1));

        fondo2.setText("fondo1");
        jPanel5.add(fondo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jTabbedPane1.addTab("A b o r t o s", jPanel5);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 520, 720));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cargaNuevosDatosCria() {

        cria.arete = tf_Eid.getText();
        cria.fecha_nacimiento = selectorFecha.getFecha();
        cria.madre = animal;
        cria.raza.cargarPorDescripcion(razaSelector1.getSelectedItem().toString());
        cria.sexo = sexoSelector.getSexo();
        cria.peso = tf_Peso.getDouble();
        cria.tipo_parto = this.tipoPartoSelector.getTipoParto();
    }

    private void agregarAborto() {

        Animal animal;
        animal = new Animal();
        Date fecha;

        fecha = this.selectorFecha1.getFecha();
        animal = HembrasEparejadasSelector.getAnimal();

        if (animal.agregarAborto(fecha)) {

            JOptionPane.showMessageDialog(this, "Se agrego el registro de Aborto Correctamente", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
        } else {

            JOptionPane.showMessageDialog(this, "Error al agregar el registro de Aborto\n" + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarParto() {

        //  Cria cria = new Cria(); 
        if (!cria.id_cria.equals("")) {

            int opcion;
            opcion = JOptionPane.showConfirmDialog(this, "Desea Actualizar datos de la Cria?", gs_mensaje, JOptionPane.YES_NO_OPTION);

            if (opcion == 0) {

                cargaNuevosDatosCria();

                if (cria.actualizar()) {

                    JOptionPane.showMessageDialog(this, "Se actualizo la Cria Correctamente", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
                } else {

                    JOptionPane.showMessageDialog(this, "Error al actualizar la cria\n" + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
                }

            } else {
                return;
            }
        } else {

            cargaNuevosDatosCria();

            if (cria.grabar()) {

                JOptionPane.showMessageDialog(this, "Se agrego la Cria en el animal Correctamente", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
            } else {

                JOptionPane.showMessageDialog(this, "Error al agregar la cria en el animal\n" + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
            }
        }
        cargarParto();
    }

    public void cargarParto() {

        String id_animal;

        id_animal = this.animalSelector1.getSelectedItem().toString();

      //  if (!id_animal.equals("")) {

            animal.cargarPorAreteVisual(id_animal, "A");

        //}
        leerCrias(t_Parto, animal);
        cria = new Cria();

    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

        stick.setSeguir(false);
    }//GEN-LAST:event_formWindowClosing

    private void animalSelector1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_animalSelector1ActionPerformed
        cargarParto();
    }//GEN-LAST:event_animalSelector1ActionPerformed

    private void btn_AgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AgregarActionPerformed
        agregarParto();
    }//GEN-LAST:event_btn_AgregarActionPerformed

    private void btn_EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EliminarActionPerformed
        eliminar();
    }//GEN-LAST:event_btn_EliminarActionPerformed

    private void HembrasEparejadasSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HembrasEparejadasSelectorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HembrasEparejadasSelectorActionPerformed

    private void btn_Agregar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Agregar1ActionPerformed
        agregarAborto();
    }//GEN-LAST:event_btn_Agregar1ActionPerformed

    private void eliminar() {

        int opcion;
        //Integer fila;
        //  Integer id_cria;
        if (cria.id_cria.equals("")) {

            JOptionPane.showMessageDialog(this, "No ha seleccionado la cria", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        opcion = JOptionPane.showConfirmDialog(this, "Desea Eliminar la Cria?", gs_mensaje, JOptionPane.YES_NO_OPTION);

        if (opcion != 0) {

            return;
        }

        if (cria.eliminar()) {

            JOptionPane.showMessageDialog(this, "Se elimino la cria del animal Correctamente", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
        } else {

            JOptionPane.showMessageDialog(this, "Error al eliminar la cria del animal\n" + manejadorBD.errorSQL, gs_mensaje, JOptionPane.ERROR_MESSAGE);
        }

        cargarParto();
        //  }
    }

    private void reporte() {

        Excel excel = new Excel();
        //     excel.reporteMedicinasAnimal(t_Parto, animal, costo);
    }

    private Date fecha;
    private Animal animal;
    Desktop parent;
    private Cria cria;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private domain.AnimalSelector HembrasEparejadasSelector;
    private domain.AnimalSelector animalSelector1;
    private abstractt.Boton btn_Agregar;
    private abstractt.Boton btn_Agregar1;
    private abstractt.Boton btn_Eliminar;
    private javax.swing.ButtonGroup buttonGroup1;
    private abstractt.Etiqueta etiqueta1;
    private abstractt.Etiqueta etiqueta2;
    private abstractt.fondo fondo1;
    private abstractt.fondo fondo2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private domain.RazaSelector razaSelector1;
    private gui.SelectorFecha selectorFecha;
    private gui.SelectorFecha selectorFecha1;
    private domain.SexoSelector sexoSelector;
    private abstractt.Table t_Parto;
    private abstractt.TextField tf_Eid;
    private abstractt.TextField tf_Peso;
    private domain.TipoAbortoSelector tipoAbortoSelector1;
    private domain.TipoPartoSelector tipoPartoSelector;
    private domain.TipoPartoSelector tipoPartoSelector1;
    // End of variables declaration//GEN-END:variables
}