/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import abstractt.Table;
import static gui.Desktop.manejadorBD;
import static gui.Desktop.rancho;
import java.util.ArrayList;

/**
 *
 * @author Developer GAGS
 */
public class Rancho {

    public String id_rancho;
    public String descripcion;
    public String conceptoEntradaTraspaso;
    public String conceptoSalidaTraspaso;
    public String conceptoSalida;
    public String conceptoMuerte;
    public String conceptoPesaje;
    public Corral corralHospital;
    public String actividad;
    public Estado estado;
    public Ciudad ciudad;

    public Rancho() {

        id_rancho = "";
        descripcion = "";
        conceptoEntradaTraspaso = "";
        conceptoSalidaTraspaso = "";
        conceptoSalida = "";
        conceptoMuerte = "";
        conceptoPesaje = "";
        //   corralHospital = new Corral();
        estado = new Estado();
        ciudad = new Ciudad();
    }
    /*
     public void cargarUnico() {

     manejadorBD.consulta(
     "SELECT	 id_rancho "
     + "FROM  rancho ");

     if (manejadorBD.getRowCount() > 0) {

     asignarValores();
     }
     }
     */

    public void cargarPorDescripcion(String sDescripcion) {

        manejadorBD.consulta(
                "SELECT  id_rancho,            descripcion,        \n"
                + "      con_traspaso_entrada, con_traspaso_salida,\n "
                + "      con_salida,           con_muerte,         \n"
                + "      con_pesaje,           id_corral_hospital, \n"
                + "      actividad,            id_estado,          \n"
                + "      id_ciudad  \n"
                + "FROM  rancho                                    \n"
                + "WHERE descripcion = '" + sDescripcion + "'");

        if (manejadorBD.getRowCount() > 0) {

            asignarValores();
        }
    }

    public void cargarPorId(String sId_rancho) {

        manejadorBD.consulta(
                "SELECT  id_rancho,            descripcion,        \n"
                + "      con_traspaso_entrada, con_traspaso_salida,\n "
                + "      con_salida,           con_muerte,         \n"
                + "      con_pesaje,           id_corral_hospital, \n"
                + "      actividad,            id_estado,          \n"
                + "      id_ciudad  \n"
                + "FROM  rancho                                    \n"
                + "WHERE id_rancho = '" + sId_rancho + "'");

        if (manejadorBD.getRowCount() > 0) {

            asignarValores();
        }
    }

    private void asignarValores() {

        String id_estado;
        String id_ciudad;
        String id_corral;

        corralHospital = new Corral();

        id_rancho = manejadorBD.getValorString(0, 0);
        descripcion = manejadorBD.getValorString(0, 1);
        conceptoEntradaTraspaso = manejadorBD.getValorString(0, 2);
        conceptoSalidaTraspaso = manejadorBD.getValorString(0, 3);
        conceptoSalida = manejadorBD.getValorString(0, 4);
        conceptoMuerte = manejadorBD.getValorString(0, 5);
        conceptoPesaje = manejadorBD.getValorString(0, 6);
        id_corral = manejadorBD.getValorString(0, 7);
        actividad = manejadorBD.getValorString(0, 8);
        id_estado = manejadorBD.getValorString(0, 9);
        id_ciudad = manejadorBD.getValorString(0, 10);
        
        corralHospital.cargarPorId(id_corral);
        estado.cargarPorId(id_estado);
        ciudad.cargarPorId(id_ciudad, estado);
    }

    public static ArrayList cargarRanchos() {

        ArrayList array = new ArrayList();

        //array.add("");
        manejadorBD.consulta(""
                + "SELECT descripcion \n"
                + "FROM rancho \n"
                + "WHERE status = 'A' \n"
                + "ORDER BY descripcion \n");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }
        return array;
    }

    public static Table leerRanchos(Table tabla) {

        tabla = crearTablaRancho(tabla);

        manejadorBD.consulta(""
                + "SELECT   r.id_rancho,    r.descripcion,      \n"
                + "         e.descripcion,  descripcion_ciudad  \n"
                + "FROM     rancho r, estado e, ciudad c        \n"
                + "WHERE    r.id_estado =   e.id_estado         \n"
                + "AND      r.id_ciudad =   c.id_ciudad         \n"
                + "AND      r.id_estado	=   c.id_estado         \n"
                + "AND      status      =   'A'");

        if (manejadorBD.getRowCount() > 0) {

            manejadorBD.asignarTable(tabla);
        }

        tabla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);

        tabla.ocultarcolumna(0);

        return tabla;
    }

    private static Table crearTablaRancho(Table tabla) {

        if (tabla == null) {

            tabla = new Table();
        }

        String titulos[] = {"Id Rancho", "Nombre", "Estado", "Ciudad"};

        tabla.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                titulos));

        tabla.setTitulos(titulos);
        tabla.cambiarTitulos();
        tabla.setFormato(new int[]{
            tabla.letra, tabla.letra,
            tabla.letra, tabla.letra});

        int[] tamaños = new int[4];
        tamaños[0] = 80;//id Madre
        tamaños[1] = 200;//id_cria
        tamaños[2] = 200;//id_cria
        tamaños[3] = 200;//id_cria

        tabla.tamañoColumna(tamaños);

        return tabla;
    }
}
