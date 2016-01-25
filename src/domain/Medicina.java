/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import abstractt.Table;
import static gui.Desktop.manejadorBD;
import static gui.Desktop.rancho;
import static gui.Splash.formatoDateTime;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Developer GAGS
 */
public class Medicina {

    public String id_medicina;
    public Integer codigo;
    public String nombre;
    public Double costo;
    public UnidadMedida unidadMedida;
    //public Integer id_unidad;
    public Double presentacion;
    public Double costo_unitario;
    public String status;
    public Date ultima_compra;
    public Double costo_promedio;
    public Double ultimo_costo;
    public Integer existencia;

    public Medicina() {

        id_medicina = "";
        codigo = 0;
        nombre = "";
        costo = 0.00;
        unidadMedida = new UnidadMedida();
        presentacion = 0.00;
        costo_unitario = 0.00;
        status = "";
    }

    public void cargarPorId(String id_medicina) {

        manejadorBD.consulta(""
                + "SELECT m.id_medicina,                m.codigo,\n"
                + "       m.nombre,                     costo,\n"
                + "       m.id_unidad,                  m.presentacion,\n"
                + "       COALESCE(m.costo_unitario,0.0),  m.status,\n"
                + "       COALESCE(rm.existencia,0),       COALESCE(rm.ultima_compra,'1900-01-01 00:00:00'),\n"
                + "       COALESCE(rm.costo_promedio,0.0), COALESCE(rm.ultimo_costo,0.0)\n "
                + "FROM   medicina m LEFT OUTER JOIN rancho_medicina rm ON \n"
                + "         m.id_medicina = rm.id_medicina \n"
                + "       AND rm.id_rancho = '" + rancho.id_rancho + "' \n"
                + "WHERE  m.id_medicina = '" + id_medicina + "'");

        if (manejadorBD.getRowCount() > 0) {

            asignarValores();
        }
    }

    public void cargarPorNombre(String nombre) {

        manejadorBD.consulta(""
                + "SELECT m.id_medicina,                   m.codigo,\n"
                + "       m.nombre,                        costo,\n"
                + "       m.id_unidad,                     m.presentacion,\n"
                + "       COALESCE(m.costo_unitario,0.0),  m.status,\n"
                + "       COALESCE(rm.existencia,0),       COALESCE(rm.ultima_compra,'1900-01-01 00:00:00'),\n"
                + "       COALESCE(rm.costo_promedio,0.0), COALESCE(rm.ultimo_costo,0.0)\n "
                + "FROM     medicina m LEFT OUTER JOIN rancho_medicina rm ON \n"
                + "         m.id_medicina = rm.id_medicina \n"
                + "         AND rm.id_rancho = '" + rancho.id_rancho + "' \n"
                + "WHERE    m.nombre = '" + nombre + "'");

        if (manejadorBD.getRowCount() > 0) {

            asignarValores();
        }
    }

    public void asignarValores() {

        Date fecha = null;
        String id_unidad;

        id_medicina = manejadorBD.getValorString(0, 0);
        codigo = manejadorBD.getValorInt(0, 1);
        nombre = manejadorBD.getValorString(0, 2);
        costo = manejadorBD.getValorDouble(0, 3);
        id_unidad = (String) manejadorBD.getValorString(0, 4);
        presentacion = manejadorBD.getValorDouble(0, 5);
        costo_unitario = manejadorBD.getValorDouble(0, 6);
        status = manejadorBD.getValorString(0, 7);
        existencia = manejadorBD.getValorInt(0, 8);

        try {

            ultima_compra = formatoDateTime.parse(manejadorBD.getValorString(0, 9));
        } catch (ParseException ex) {

            Logger.getLogger(Animal.class.getName()).log(Level.SEVERE, null, ex);
        }

        costo_promedio = manejadorBD.getValorDouble(0, 10);
        ultimo_costo = manejadorBD.getValorDouble(0, 11);

        unidadMedida.cargarPorId(id_unidad);
    }

    public static ArrayList cargarCodigoMedicinas() {

        ArrayList array = new ArrayList();

        array.add("");
        manejadorBD.consulta(""
                + "SELECT codigo "
                + "FROM medicina "
                + "WHERE status =   'S' "
                + "ORDER BY codigo");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }
        return array;
    }

    public static ArrayList cargarMedicinas() {

        ArrayList array = new ArrayList();

        array.add("");
        manejadorBD.consulta(""
                + "SELECT nombre "
                + "FROM medicina "
                + "WHERE status =   'S' "
                + "ORDER BY nombre");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }
        return array;
    }

    public static ArrayList cargarMedicinasAnimal(Integer id_animal) {

        ArrayList array = new ArrayList();

        array.add("");
        manejadorBD.consulta(""
                + "SELECT nombre "
                + "FROM medicina "
                + "WHERE status =   'S' "
                + "ORDER BY nombre");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }

        return array;
    }

    public static void leerMedicinaAnimal(Table tabla, Animal animal) {

        crearTablaMedicinaAnimal(tabla);
/*
        manejadorBD.consulta(""
                + "SELECT   ma.id_medicina_animal, m.codigo,                   m.nombre,\n"
                + "         um.descripcion,             cast(fecha as Date),\n"
                + "         round(m.costo_unitario,2),  round(ma.dosis,2),\n"
                + "         round(round(m.costo_unitario,2) * ma.dosis,2)  "
                + "FROM     medicina_animal ma, medicina m, unidades_de_medida um "
                + "WHERE    ma.id_medicina  =   m.id_medicina "
                + "AND      m.id_unidad     =   um.id_unidad "
                + "AND      ma.id_rancho    =   '" + rancho.id_rancho + "' "
                + "AND      ma.id_animal    =   '" + animal.id_animal + "' ");
  */      
        manejadorBD.consulta(""
                + "SELECT   ma.id_medicina_animal, m.codigo,                   m.nombre,\n"
                + "         um.descripcion,             cast(fecha as Date),\n"
                + "         round(ma.dosis,2),          round(ma.costo,2),  \n"
                + "         round(round(ma.costo,2) * ma.dosis,2)  "
                + "FROM     medicina_animal ma, medicina m, unidades_de_medida um "
                + "WHERE    ma.id_medicina  =   m.id_medicina "
                + "AND      m.id_unidad     =   um.id_unidad "
                + "AND      ma.id_rancho    =   '" + rancho.id_rancho + "' "
                + "AND      ma.id_animal    =   '" + animal.id_animal + "' ");
        

        if (manejadorBD.getRowCount() > 0) {

            manejadorBD.asignarTable(tabla);
        }

        tabla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);

        int[] tamaños = new int[8];
        tamaños[0] = 10;//id_medicina aniaml
        tamaños[1] = 80;//id Codigo
        tamaños[2] = 200;//nombre
        tamaños[3] = 50;//uniad de medida
        tamaños[4] = 80;//Fecha Salida
        tamaños[5] = 100; //costo unitario
        tamaños[6] = 100;//dosis
        tamaños[7] = 100;//importe

        tabla.tamañoColumna(tamaños);

        tabla.ocultarcolumna(0);
        //  tabla.ocultarcolumna(2);
    }

    public static void crearTablaMedicinaAnimal(Table tabla) {

        if (tabla == null) {
            tabla = new Table();
        }

        String titulos[] = {"id medicina animal", "Codigo", "Medicinas", "U.M.", "Fecha", "Dosis", "Costo Unitario",  "Importe"};

        tabla.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                titulos
        ));

        tabla.setTitulos(titulos);
        tabla.cambiarTitulos();
        tabla.setFormato(new int[]{0, 4, 0, 0, 3, 4, 4, 4});
    }

    public static void leerMedicina(Table tabla) {

        crearTablaMedicina(tabla);

        manejadorBD.consulta(""
                + "SELECT   m.id_medicina,              m.codigo,\n"
                + "         ifnull(m.nombre,'') nombre, um.descripcion, \n"
                + "         COALESCE(rm.existencia,0) existencia,\n"
                + "         COALESCE(rm.costo_promedio,0.0) costo_promedio, \n"
                + "         COALESCE(rm.ultimo_costo,0.0) ultimo_costo, "
                + "         COALESCE(rm.ultima_compra,'1900-01-01 00:00:00') ultima_compra \n"
                //+ "         costo,                      m.presentacion, \n"
                //+ "         m.costo_unitario,           m.id_unidad  \n"
                + "FROM     medicina m LEFT OUTER JOIN rancho_medicina rm ON\n"
                + "             m.id_medicina = rm.id_medicina \n"
                + "         AND rm.id_rancho = '" + rancho.id_rancho + "', \n"
                + "         unidades_de_medida um\n"
                + "WHERE    m.id_unidad =   um.id_unidad \n"
                + "AND      m.status  =   'S'");

        if (manejadorBD.getRowCount() > 0) {

            manejadorBD.asignarTable(tabla);
        }

        tabla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);

        int[] tamaños = new int[8];
        tamaños[0] = 10;//id_medicina aniaml
        tamaños[1] = 80;//id Codigo
        tamaños[2] = 200;//nombre
        tamaños[3] = 50;//Undad de medida
        tamaños[4] = 80;//Existencia
        tamaños[5] = 100; //costo promedio
        tamaños[6] = 100;//ultimo_costo
        tamaños[7] = 100;//ultima_compra

        tabla.tamañoColumna(tamaños);

        tabla.ocultarcolumna(0);
        //  tabla.ocultarcolumna(2);
    }

    public static void crearTablaMedicina(Table tabla) {

        if (tabla == null) {
            tabla = new Table();
        }

        String titulos[] = {
            "id_medicina", "Codigo", "Nombre", "Unidad de Medida",
            "Existencia", "Costo Promedio", "Ultimo Costo",
            "Ultima Compra"};

        tabla.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                titulos
        ));

        tabla.setTitulos(titulos);
        tabla.cambiarTitulos();

        tabla.setFormato(new int[]{
            Table.letra, Table.letra, Table.letra,
            Table.numero_entero, Table.numero_double, Table.numero_double,
            Table.fecha});

    }

    public static Integer MedicinaCodigo(String medicina) {

        manejadorBD.consulta(""
                + "SELECT codigo "
                + "FROM medicina "
                + "WHERE nombre =   =   '" + medicina + "'");

        if (manejadorBD.getRowCount() > 0) {
            return manejadorBD.getValorInt(0, 0);
        }
        return 0;
    }

    public static String CodigoMedicina(String codigo) {

        manejadorBD.consulta(""
                + "SELECT nombre "
                + "FROM medicina "
                + "WHERE codigo =   =   " + codigo);

        if (manejadorBD.getRowCount() > 0) {
            return manejadorBD.getValorString(0, 0);
        }
        return "";
    }

    public static boolean leerMedicinaCodigo(String codigo) {

        manejadorBD.consulta(""
                + "SELECT m.codigo, m.nombre, "
                + "       m.costo, u.descripcion "
                + "FROM medicina m,  unidades_de_medida u "
                + "WHERE m.id_unidad = u.id_unidad "
                + "AND m.status = 'S'"
                + "AND m.codigo =   " + codigo);

        if (manejadorBD.getRowCount() > 0) {

            return true;
        }
        return false;
    }
}
