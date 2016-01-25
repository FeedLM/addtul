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
import static gui.Login.gs_mensaje;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Developer GAGS
 */
public class Tratamiento {

    public String id_tratamiento;
    public String codigo;
    public String nombre;
    public String status;
    public LinkedList<Medicina> medicinas;

    public Tratamiento() {
        id_tratamiento = "";
        codigo = "";
        nombre = "";
        status = "";
        medicinas = null;
    }

    public void cargarPorNombre(String nombre) {

        manejadorBD.consulta(""
                + "SELECT   id_tratamiento,   codigo, nombre, status, fecha \n"
                + "FROM     tratamiento                                     \n"
                + "WHERE    status  =   'A'                                 \n"
                + "AND      tratamiento.nombre = '" + nombre + "'");

        if (manejadorBD.getRowCount() > 0) {

            asignarValores();
        }
    }

    public void cargarPorCodigo(String codigo) {

        manejadorBD.consulta(""
                + "SELECT   id_tratamiento,   codigo, nombre, status, fecha \n"
                + "FROM     tratamiento                                     \n"
                + "WHERE    status  =   'A'                                 \n"
                + "AND      tratamiento.codigo = '" + codigo + "'");

        if (manejadorBD.getRowCount() > 0) {

            asignarValores();
        }
    }

    public void asignarValores() {

        Date fecha = null;

        id_tratamiento = manejadorBD.getValorString(0, 0);
        codigo = manejadorBD.getValorString(0, 1);
        nombre = manejadorBD.getValorString(0, 2);
        status = manejadorBD.getValorString(0, 3);

        try {

            fecha = formatoDateTime.parse(manejadorBD.getValorString(0, 4));
        } catch (ParseException ex) {

            Logger.getLogger(Animal.class.getName()).log(Level.SEVERE, null, ex);
        }

        cargarMedicinasTratamiento();
    }

    private void cargarMedicinasTratamiento() {

        medicinas = new LinkedList<Medicina>();
        Medicina medicina;

        manejadorBD.consulta(""
                + "SELECT id_medicina                         \n"
                + "FROM medicina_tratamiento                  \n"
                + "WHERE id_tratamiento = '" + id_tratamiento + "'\n");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            medicina = new Medicina();
            medicina.cargarPorId(manejadorBD.getValueAt(i, 0).toString());
            medicinas.add(medicina);
        }
    }

    public static ArrayList cargarCodigoTratamiento() {

        ArrayList array = new ArrayList();

        array.add("");
        manejadorBD.consulta(""
                + "SELECT codigo "
                + "FROM tratamiento "
                + "WHERE status =   'A' "
                + "ORDER BY codigo");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }
        return array;
    }

    public static ArrayList cargarTratamientos() {

        ArrayList array = new ArrayList();

        array.add("");
        manejadorBD.consulta(""
                + "SELECT nombre "
                + "FROM tratamiento "
                + "WHERE status =   'A' "
                + "ORDER BY nombre");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }
        return array;
    }
    /*
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
     */

    public static void leerMedicinaTratamiento(Table tabla, Tratamiento tratamiento) {

        crearTablaMedicinaTratamiento(tabla);

        manejadorBD.consulta(""
                + "SELECT mt.id_tratamiento, mt.id_medicina, m.codigo,	         \n"
                + "       m.nombre,          u.descripcion,  rm.costo_promedio,   \n"
                + "       mt.dosis,          round(mt.dosis * rm.costo_promedio,2) \n"
                + "FROM   tratamiento t,medicina_tratamiento mt,                \n"
                + "       medicina m,   unidades_de_medida u, rancho_medicina rm \n"
                + "WHERE  t.id_tratamiento = mt.id_tratamiento                   \n"
                + "AND    rm.id_medicina = m.id_medicina  \n"
                + "AND	  mt.id_medicina   = m.id_medicina                       \n"
                + "AND	  m.id_unidad	   = u.id_unidad                         \n"
                + "AND    t.id_tratamiento = '" + tratamiento.id_tratamiento + "' \n"
                + "AND    rm.id_rancho = '" + rancho.id_rancho + "' ");

        if (manejadorBD.getRowCount() > 0) {

            manejadorBD.asignarTable(tabla);
        }

        tabla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);

        int[] tamaños = new int[8];
        tamaños[0] = 10;//Id tratamiento
        tamaños[1] = 10;//Id_medicina
        tamaños[2] = 80;//Codigo
        tamaños[3] = 150;//Nombre
        tamaños[4] = 50; //Unidad de Medida
        tamaños[5] = 100;//Costo unitario
        tamaños[6] = 100;//Dosis
        tamaños[7] = 100;//Importe

        tabla.tamañoColumna(tamaños);

        tabla.ocultarcolumna(0);
        tabla.ocultarcolumna(1);
    }

    public static void crearTablaMedicinaTratamiento(Table tabla) {

        if (tabla == null) {
            tabla = new Table();
        }

        String titulos[] = {"id_tratamiento", "id_medicina", "Codigo", "Nombre", "UM.", "Costo Unitario", "Dosis", "Importe"};

        tabla.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                titulos
        ));

        tabla.setTitulos(titulos);
        tabla.cambiarTitulos();
        tabla.setFormato(new int[]{
            Table.letra, Table.letra, Table.numero_entero,
            Table.letra, Table.letra, Table.numero_double,
            Table.numero_double, Table.numero_double});
    }

    public boolean grabar() {

        System.out.println(toString());

        manejadorBD.parametrosSP = new ParametrosSP();
        manejadorBD.parametrosSP.agregarParametro(codigo.toString(), "varCodigo", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(nombre, "varNombre", "STRING", "IN");

        if (manejadorBD.ejecutarSP("{ call agregarTratamiento(?,?) }") == 0) {

            return true;
        }

        return false;
    }

    public boolean actualizar() {

        System.out.println(toString());

        manejadorBD.parametrosSP = new ParametrosSP();
        manejadorBD.parametrosSP.agregarParametro(id_tratamiento, "varIdTratamiento", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(codigo, "varCodigo", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(nombre, "varNombre", "STRING", "IN");

        if (manejadorBD.ejecutarSP("{ call actualizarTratamiento(?,?,?) }") == 0) {

            return true;
        }

        return false;
    }

    public boolean eliminar() {

        System.out.println(toString());

        manejadorBD.parametrosSP = new ParametrosSP();
        manejadorBD.parametrosSP.agregarParametro(id_tratamiento, "varIdTratamiento", "STRING", "IN");

        if (manejadorBD.ejecutarSP("{ call eliminarTratamiento(?) }") == 0) {

            return true;
        }

        return false;
    }

    public boolean agregarMedicina(Medicina medicina, Double dosis) {

        System.out.println(toString());

        manejadorBD.parametrosSP = new ParametrosSP();
        manejadorBD.parametrosSP.agregarParametro(id_tratamiento, "varIdTratamiento", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(medicina.id_medicina, "varIdMedicina", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(dosis.toString(), "varDosis", "DOUBLE", "IN");

        if (manejadorBD.ejecutarSP("{ call agregarMedicinaTratamiento(?,?,?) }") == 0) {

            return true;
        }

        return false;
    }

    public boolean eliminarMedicina(Medicina medicina) {

        System.out.println(toString());

        manejadorBD.parametrosSP = new ParametrosSP();
        manejadorBD.parametrosSP.agregarParametro(id_tratamiento, "varIdTratamiento", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(medicina.id_medicina, "varIdMedicina", "STRING", "IN");

        if (manejadorBD.ejecutarSP("{ call eliminarMedicinaTratamiento(?,?) }") == 0) {

            return true;
        }

        return false;
    }

    public boolean agregarTratamientoAnimal(Animal animal, Double dosis, Date fecha) {

        System.out.println(toString());

        manejadorBD.parametrosSP = new ParametrosSP();
        manejadorBD.parametrosSP.agregarParametro(rancho.id_rancho, "varIdRancho", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_tratamiento, "varIdTratamiento", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(animal.id_animal, "varIdAnimal", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(dosis.toString(), "varDosisTratamiento", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(fecha), "varFecha", "STRING", "IN");

        if (manejadorBD.ejecutarSP("{ call agregarTratamientoAnimal(?,?,?,?,?) }") == 0) {

            return true;
        }

        return false;
    }

    public String toString() {

        String salida;

        salida = "{id_tratamiento='" + id_tratamiento
                + "', codigo='" + codigo + "'"
                + ", nombre='" + nombre + "'"
                + ", status='" + status + "'}";

        return salida;
    }
}
