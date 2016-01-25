/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import abstractt.Table;
import static domain.Movimiento.crearTablaMedicinas;
import static gui.Desktop.manejadorBD;
import static gui.Desktop.rancho;
import static gui.Login.gs_mensaje;
import static gui.Splash.formatoDate;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Developer GAGS
 */
public class Raza {

    public String id_raza;
    public String descripcion;

    public Raza() {
        id_raza = "";
        descripcion = "";
    }

    public void cargarPorDescripcion(String sDescripcion) {

        manejadorBD.consulta(
                "SELECT	 id_raza, "
                + "       descripcion "
                + "FROM  raza "
                + "WHERE descripcion = '" + sDescripcion + "'");

        if (manejadorBD.getRowCount() > 0) {

            asignarValores();
        } else {
            id_raza = "";
            descripcion = "";
        }
    }

    public void cargarPorId(String sId_raza) {

        manejadorBD.consulta(
                "SELECT	 id_raza, descripcion "
                + "FROM  raza "
                + "WHERE id_raza = '" + sId_raza + "'");

        if (manejadorBD.getRowCount() > 0) {

            asignarValores();
        } else {
            id_raza = "";
            descripcion = "";
        }
    }

    private void asignarValores() {

        id_raza = manejadorBD.getValorString(0, 0);
        descripcion = manejadorBD.getValorString(0, 1);
    }

    public static ArrayList cargarRazasTodas() {

        ArrayList array = new ArrayList();

        array.add("");
        manejadorBD.consulta(""
                + "SELECT descripcion "
                + "FROM raza "
                + "ORDER BY descripcion");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }
        return array;
    }

    public static ArrayList cargarRazasSeleccionar() {

        ArrayList array = new ArrayList();

        array.add("");
        manejadorBD.consulta(""
                + "SELECT descripcion \n"
                + "FROM   raza \n"
                + "WHERE  seleccionar = 'S'\n"
                + "AND    status = 'A'\n"
                + "ORDER BY descripcion");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }
        return array;
    }

    public static void cargarRazas(Table tabla) {

        tabla = crearTablaRazas(tabla);

        //tabla = 
        String consulta = ""
                + "SELECT id_raza, descripcion, seleccionar\n"
                + "FROM   raza \n"
                + "WHERE  seleccionar = 'S'\n"
                + "AND    status      = 'A'";

        manejadorBD.consulta(consulta);

      //  if (manejadorBD.getRowCount() > 0) {
        manejadorBD.asignarTable(tabla);
        //}

       // tabla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

        int[] tamaños = new int[3];

        tamaños[0] = 100; //Idraza
        tamaños[1] = 150;  //descripcion
        tamaños[2] = 100; //seleccionar
        //  tamaños[3] = 100; //itemstatus

        tabla.tamañoColumna(tamaños);

        tabla.agregarItemStatus();

        tabla.ocultarcolumna(0);
        tabla.ocultarcolumna(2);
        
    }

    public static Table crearTablaRazas(Table tabla) {
      //  Table tabla = new Table();
        
         if (tabla == null) {
         tabla = new Table();
         }
        
        String titulos[] = {
            "Id Raza", "Descripción", "Seleccionar"};

        tabla.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                titulos
        ));

        tabla.setTitulos(titulos);
        // tabla.cambiarTitulos();
        tabla.setFormato(new int[]{
            Table.letra, Table.letra, Table.letra});

        return tabla;
    }

    public boolean crear() {

        manejadorBD.parametrosSP = new ParametrosSP();
        manejadorBD.parametrosSP.agregarParametro(descripcion, "varRaza", "STRING", "IN");

        if (manejadorBD.ejecutarSP("{ call agregarRaza(?) }") == 0) {

            //  cargarPorDescripcion(descripcion);
            return true;
        }

        return false;
    }

    public boolean actualizar() {

        manejadorBD.parametrosSP = new ParametrosSP();
        manejadorBD.parametrosSP.agregarParametro(id_raza, "varIdRaza", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(descripcion, "varRaza", "STRING", "IN");

        if (manejadorBD.ejecutarSP("{ call actualizarRaza(?,?) }") == 0) {

            //cargarPorDescripcion(descripcion);
            return true;
        }

        return false;
    }

    public boolean eliminar() {
        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(id_raza, "varIdRaza", "STRING", "IN");

        if (manejadorBD.ejecutarSP("{ call eliminarRaza(?) }") == 0) {

            return true;
        } else {

            return false;
        }
    }

}
