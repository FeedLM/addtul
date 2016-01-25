/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import abstractt.Table;
import static gui.Desktop.manejadorBD;
import static gui.Desktop.rancho;
import static gui.Splash.formatoDate;
import static gui.Splash.formatoDateTime;
import static gui.Splash.formatoDate;
import static gui.Splash.formatoDateTime_1;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Developer GAGS
 */
public class Corral {

    public String id_corral;
    public String nombre;
    public String localizacion;
    public Integer numero_anmales;
    public Sexo sexo;
    public Raza raza;
    public String status;
    public Double total_kilos;
    public Double peso_minimo;
    public Double peso_maximo;
    public Double peso_promedio;
    public Double alimento_ingresado;
    public Double peso_ganancia;
    public String observaciones;
    public Double total_kilos_inicial;
    public Double total_costo_medicina;
    
    public Double ganancia_precio_carne;
    public Double costo_alimento;
    public Double utilidad_s_gastos;
    public Date fecha_cierre;
    public Integer dias_corral;
    public Double total_costo_flete;
    public Date fecha_inicio;
    public Double conversion_alimenticia;
    public Double ganancia_promedio;
    public Double promedio_alimento;
    public Double merma;
    public Double medicina_promedio;

    //public CorralDatos corralDatos;
    public Corral() {
        sexo = new Sexo();
        raza = new Raza();
        //corralDatos = new CorralDatos();
        id_corral = "";
    }

    public void cargarHistoricoPesos(Table tabla) {

        tabla = crearHistoricoPesos(tabla);

        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(rancho.id_rancho, "varIdRancho", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_corral, "varIdCorral", "STRING", "IN");

        manejadorBD.ejecutarSP("{ call corralPesajesGrafica(?,?) }");

        try {

            manejadorBD.setConsultaSP();
            manejadorBD.asignarTable(tabla);
        } catch (SQLException ex) {

            Logger.getLogger(Corral.class.getName()).log(Level.SEVERE, null, ex);
        }

        //SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
        //tabla.setModel(manejadorBD.modelo);
    }

    private static Table crearHistoricoPesos(Table tabla) {

        if (tabla == null) {

            tabla = new Table();
        }

        String titulos[] = {"Fecha", "Peso (kg)"};

        tabla.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                titulos
        ));

        tabla.setTitulos(titulos);
        tabla.cambiarTitulos();
        tabla.setFormato(new int[]{
            Table.fecha, Table.numero_double});

        int[] tamaños = new int[2];

        tamaños[0] = 100;//id Animal
        tamaños[1] = 100;//arete Visual        

        tabla.tamañoColumna(tamaños);

        return tabla;
    }

    public void cargarPorId(String id_corral) {

        manejadorBD.consulta(""
                + "SELECT   id_rancho,                          id_corral,\n"
                + "         nombre,                             localizacion,\n"
                + "         num_animales,                       IFNULL(id_sexo,''),\n"
                + "         IFNULL(id_raza,''),                 status,\n"
                + "         COALESCE(total_kilos,0.0),          COALESCE(peso_minimo,0.0), \n"
                + "         COALESCE(peso_maximo,0.0),          COALESCE(peso_promedio,0.0),\n"
                + "         COALESCE(alimento_ingresado,0.0),   COALESCE(peso_ganancia,0.0), \n"
                + "         IFNULL(observaciones,''),           COALESCE(total_kilos_iniciales,0.0),\n"
                + "         COALESCE(total_costo_medicina,0.0)\n"
                + "FROM     corral \n"
                + "WHERE    status = 'S' \n"
                + "AND      id_corral = '" + id_corral + "' \n "
                + "AND      id_rancho = '" + rancho.id_rancho + "'");

        if (manejadorBD.getRowCount() > 0) {
            asignarValores();
        } else {
            id_corral = "";
        }
    }

    public void cargarPorNombre(String nombre) {

        manejadorBD.consulta(""
                + "SELECT   id_rancho,                          id_corral,\n"
                + "         nombre,                             localizacion,\n"
                + "         COALESCE(num_animales,0),                       IFNULL(id_sexo,''),\n"
                + "         IFNULL(id_raza,''),                 status,\n"
                + "         COALESCE(total_kilos,0.0),          COALESCE(peso_minimo,0.0), \n"
                + "         COALESCE(peso_maximo,0.0),          COALESCE(peso_promedio,0.0),\n"
                + "         COALESCE(alimento_ingresado,0.0),   COALESCE(peso_ganancia,0.0), \n"
                + "         IFNULL(observaciones,''),           COALESCE(total_kilos_iniciales,0.0),\n"
                + "         COALESCE(total_costo_medicina,0.0)\n"
                + "FROM     corral "
                + "WHERE    status = 'S' "
                + "AND      nombre = '" + nombre + "' \n "
                + "AND      id_rancho = '" + rancho.id_rancho + "'");

        if (manejadorBD.getRowCount() > 0) {
            asignarValores();
        } else {
            id_corral = "";
        }
    }

    public void cargarPorNombre(String nombre, Rancho aRancho) {

        manejadorBD.consulta(""
                + "SELECT   id_rancho,                          id_corral,\n"
                + "         nombre,                             localizacion,\n"
                + "         num_animales,                       IFNULL(id_sexo,''),\n"
                + "         IFNULL(id_raza,''),                 status,\n"
                + "         COALESCE(total_kilos,0.0),          COALESCE(peso_minimo,0.0), \n"
                + "         COALESCE(peso_maximo,0.0),          COALESCE(peso_promedio,0.0),\n"
                + "         COALESCE(alimento_ingresado,0.0),   COALESCE(peso_ganancia,0.0), \n"
                + "         IFNULL(observaciones,''),           COALESCE(total_kilos_iniciales,0.0),\n"
                + "         COALESCE(total_costo_medicina,0.0)\n"
                + "FROM     corral "
                + "WHERE    status = 'S' "
                + "AND      nombre = '" + nombre + "' \n "
                + "AND      id_rancho = '" + aRancho.id_rancho + "'");

        if (manejadorBD.getRowCount() > 0) {
            asignarValores();
        } else {
            id_corral = "";
        }
    }

    public void cargarPorAnimal(Animal animal) {

        manejadorBD.consulta(""
                + "SELECT   corral.id_rancho,                   corral.id_corral,\n"
                + "         nombre,                             localizacion,\n"
                + "         num_animales,                       IFNULL(id_sexo,''),\n"
                + "         IFNULL(id_raza,''),                 status,\n"
                + "         COALESCE(total_kilos,0.0),          COALESCE(peso_minimo,0.0), \n"
                + "         COALESCE(peso_maximo,0.0),          COALESCE(peso_promedio,0.0),\n"
                + "         COALESCE(alimento_ingresado,0.0),   COALESCE(peso_ganancia,0.0), \n"
                + "         IFNULL(observaciones,''),           COALESCE(total_kilos_iniciales,0.0),\n"
                + "         COALESCE(total_costo_medicina,0.0)\n"
                + "FROM     corral, corral_animal "
                + "WHERE    status = 'S' "
                + "AND      corral.id_corral = corral_animal.id_corral "
                + "AND      corral_animal.id_animal = '" + animal.id_animal + "'\n "
                + "AND      corral.id_rancho = '" + rancho.id_rancho + "'");

        if (manejadorBD.getRowCount() > 0) {
            asignarValores();
        } else {
            id_corral = "";
        }
    }

    private void asignarValores() {

        String id_sexo;

        String id_raza;

        //id_rancho = manejadorBD.getValorString(0, 0);
        id_corral = manejadorBD.getValorString(0, 1);
        nombre = manejadorBD.getValorString(0, 2);
        localizacion = manejadorBD.getValorString(0, 3);
        numero_anmales = manejadorBD.getValorInt(0, 4);
        id_sexo = manejadorBD.getValorString(0, 5);
        id_raza = manejadorBD.getValorString(0, 6);
        status = manejadorBD.getValorString(0, 7);

        total_kilos = manejadorBD.getValorDouble(0, 8);
        peso_minimo = manejadorBD.getValorDouble(0, 9);
        peso_maximo = manejadorBD.getValorDouble(0, 10);
        peso_promedio = manejadorBD.getValorDouble(0, 11);
        alimento_ingresado = manejadorBD.getValorDouble(0, 12);
        peso_ganancia = manejadorBD.getValorDouble(0, 13);
        observaciones = manejadorBD.getValorString(0, 14);
        total_kilos_inicial = manejadorBD.getValorDouble(0, 15);
        total_costo_medicina = manejadorBD.getValorDouble(0, 16);

        sexo.cargarPorId(id_sexo);

        raza.cargarPorId(id_raza);
        // corralDatos.cargarPorId(id_corral);

        calculosTotales();
    }

    private void asignarValores2() throws ParseException {

        String id_sexo;

        String id_raza;

        //id_rancho = manejadorBD.getValorString(0, 0);
        id_corral = manejadorBD.getValorString(0, 1);
        nombre = manejadorBD.getValorString(0, 2);
        localizacion = manejadorBD.getValorString(0, 3);
        numero_anmales = manejadorBD.getValorInt(0, 4);
        id_sexo = manejadorBD.getValorString(0, 5);
        id_raza = manejadorBD.getValorString(0, 6);
        status = manejadorBD.getValorString(0, 7);

        total_kilos = manejadorBD.getValorDouble(0, 8);
        peso_minimo = manejadorBD.getValorDouble(0, 9);
        peso_maximo = manejadorBD.getValorDouble(0, 10);
        peso_promedio = manejadorBD.getValorDouble(0, 11);
        alimento_ingresado = manejadorBD.getValorDouble(0, 12);
        ganancia_promedio = manejadorBD.getValorDouble(0, 13);
        observaciones = manejadorBD.getValorString(0, 14);
        total_kilos_inicial = manejadorBD.getValorDouble(0, 15);
        total_costo_medicina = manejadorBD.getValorDouble(0, 16);
        fecha_cierre = formatoDateTime_1.parse(manejadorBD.getValorString(0, 17));
        dias_corral = manejadorBD.getValorInt(0, 18);
        total_costo_flete = manejadorBD.getValorDouble(0, 19);
        fecha_inicio = formatoDateTime_1.parse(manejadorBD.getValorString(0, 20));
        conversion_alimenticia = manejadorBD.getValorDouble(0, 21);
        promedio_alimento = manejadorBD.getValorDouble(0, 22);
        merma = manejadorBD.getValorDouble(0, 23);
        medicina_promedio = manejadorBD.getValorDouble(0, 24);
        sexo.cargarPorId(id_sexo);

        raza.cargarPorId(id_raza);
        // corralDatos.cargarPorId(id_corral);

//        calculosTotales();
    }

    private void calculosTotales() {

        Configuracion configuracion;
        configuracion = new Configuracion();
        configuracion.cargarConfiguracion();

        ganancia_precio_carne = configuracion.precio_carne * peso_ganancia;
        costo_alimento = configuracion.costo_alimento * alimento_ingresado;
        utilidad_s_gastos = ganancia_precio_carne - (costo_alimento + total_costo_medicina);
    }

    public static ArrayList cargarCorrales() {

        ArrayList array = new ArrayList();

        //  corralSelector.removeAllItems();
        array.add("");
        manejadorBD.consulta(""
                + "SELECT   nombre\n "
                + "FROM     corral\n "
                + "WHERE    status = 'S'\n "
                + "AND      id_rancho = '" + rancho.id_rancho + "' "
                + "ORDER BY nombre");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }

        return array;
    }

    public static ArrayList cargarCorralesRancho(Rancho aRancho) {

        ArrayList array = new ArrayList();

        //  corralSelector.removeAllItems();
        array.add("");
        manejadorBD.consulta(""
                + "SELECT   nombre\n"
                + "FROM     corral\n"
                + "WHERE    status = 'S' \n "
                + "AND      id_rancho = '" + aRancho.id_rancho + "'\n"
                + "ORDER BY nombre");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }

        return array;
    }

    public static String corral(Integer id_corral) {

        manejadorBD.consulta(""
                + "SELECT   nombre\n"
                + "FROM     corral\n"
                + "WHERE    id_corral = '" + id_corral + "' \n "
                + "AND      id_rancho = '" + rancho.id_rancho + "'");

        if (manejadorBD.getColumnCount() > 0) {

            return manejadorBD.getValorString(0, 0);
        }

        return "";
    }

    public static String corralIdNombre(String aNombre) {

        String id_corral;

        manejadorBD.consulta(""
                + "SELECT   id_corral\n"
                + "FROM     corral \n"
                + "WHERE    nombre    = '" + aNombre + "' \n "
                + "AND      id_rancho = '" + rancho.id_rancho + "'");

        if (manejadorBD.getRowCount() > 0) {

            id_corral = manejadorBD.getValorString(0, 0);
        } else {

            id_corral = "";
        }

        return id_corral;
    }

    public boolean grabar() {

        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(rancho.id_rancho, "varId_Rancho", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(nombre, "varNombre", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(localizacion, "varLocalizacion", "STRING", "IN");
        //  manejadorBD.parametrosSP.agregarParametro(raza.id_raza, "varId_raza", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(alimento_ingresado.toString(), "varAlimentoIngresado", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(observaciones, "varObservaciones", "STRING", "IN");

        if (manejadorBD.ejecutarSP("{ call agregarCorral(?,?,?,?,?) }") == 0) {
            return true;
        }
        return false;
    }

    public boolean actualizar() {

        manejadorBD.parametrosSP = new ParametrosSP();
        manejadorBD.parametrosSP.agregarParametro(rancho.id_rancho, "varIdRancho", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_corral, "varIdCorral", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(nombre, "varNombre", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(localizacion, "varLocalizacion", "STRING", "IN");
        //    manejadorBD.parametrosSP.agregarParametro(estado.id_estado, "varId_estado", "STRING", "IN");
        //    manejadorBD.parametrosSP.agregarParametro(pais.id_pais, "varId_pais", "STRING", "IN");
        //   manejadorBD.parametrosSP.agregarParametro(raza.id_raza, "varId_raza", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(alimento_ingresado.toString(), "varAlimentoIngresado", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(observaciones, "varObservaciones", "STRING", "IN");

        if (manejadorBD.ejecutarSP("{ call actualizarCorral(?,?,?,?,?,?) }") == 0) {
            /*
             manejadorBD.parametrosSP = new ParametrosSP();
             manejadorBD.parametrosSP.agregarParametro(id_corral, "varIdCorral", "STRING", "IN");

             if (manejadorBD.ejecutarSP("{ call animalesPorCorral(?) }") == 0) {

             return true;
             }

             return false;*/
            return true;
        }
        return false;
    }

    public boolean eliminar() {

        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(rancho.id_rancho, "varIdRancho", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_corral, "varIdCorral", "STRING", "IN");

        if (manejadorBD.ejecutarSP("{ call eliminarCorral(?,?) }") == 0) {
            return true;
        }
        return false;
    }

    public void cargarCorralCerrado(String id_corral) throws ParseException {

        manejadorBD.consulta(""
                + "SELECT   id_rancho,                          id_corral,\n"
                + "         nombre,                             localizacion,\n"
                + "         num_animales,                       IFNULL(id_sexo,''),\n"
                + "         IFNULL(id_raza,''),                 status,\n"
                + "         COALESCE(total_kilos,0.0),          COALESCE(peso_minimo,0.0), \n"
                + "         COALESCE(peso_maximo,0.0),          COALESCE(peso_promedio,0.0),\n"
                + "         COALESCE(alimento_ingresado,0.0),   COALESCE(ganancia_promedio,0.0), \n"
                + "         IFNULL(observaciones,''),           COALESCE(total_kilos_iniciales,0.0),\n"
                + "         COALESCE(total_costo_medicina,0.0), fecha_cierre, \n"
                + "         COALESCE(dias_corral,0),            COALESCE(total_costo_flete, 0.0), \n"
                + "         fecha_inicio,                       COALESCE(conversion_alimenticia, 0.0), \n"
                + "         COALESCE(promedio_alimento,0.0),                  COALESCE(merma, 0.0), \n"
                + "         COALESCE(medicina_promedio, 0.0) \n"
                + "FROM     corral \n"
                + "WHERE    status = 'C' \n"
                + "AND      id_corral = '" + id_corral + "' \n "
                + "AND      id_rancho = '" + rancho.id_rancho + "'");

        if (manejadorBD.getRowCount() > 0) {
            asignarValores2();
        } else {
            id_corral = "";
        }

    }
}
