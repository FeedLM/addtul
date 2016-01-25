/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import abstractt.Table;
import static gui.Splash.formatoDate;
import static gui.Desktop.rancho;
import static gui.Desktop.manejadorBD;
import java.util.Date;

/**
 *
 * @author Developer GAGS
 */
public class Movimiento {

    public static Table cargarMedicinas(Table tabla, Integer tipo, Date fecha_ini, Date fecha_fin, Animal animal, Corral corral) {

        tabla = crearTablaMedicinas(tabla);

        String consulta = ""
                + "SELECT   a.id_animal, a.arete_visual, a.arete_electronico, m.codigo, m.nombre, \n"
                + "         DATE_FORMAT(ma.fecha, '%Y/%m/%d') fecha, c.nombre, \n"
                + "         round(ma.dosis,2) dosis , round(rm.costo_promedio,2) costo, round(ma.dosis * rm.costo_promedio ,2) importe \n"
                + "FROM     medicina_animal ma, animal a, medicina m, \n"
                + "         corral_animal ca, corral c, rancho_medicina rm\n"
                + "WHERE    ma.id_animal   = a.id_animal \n"
                + "AND      ma.id_medicina = m.id_medicina \n"
                + "AND      ca.id_rancho   = ma.id_rancho \n"
                + "AND      ca.id_animal   = ma.id_animal \n"
                + "AND      ma.id_rancho   = c.id_rancho \n"
                + "AND      c.id_corral    = ca.id_corral \n"
                + "AND     rm.id_medicina 	= 	m.id_medicina\n"
                + "AND      a.status           =   'A' \n"
                + "AND      ma.id_rancho     = '" + rancho.id_rancho + "'\n"
                + "AND 	rm.id_rancho 	=  '" + rancho.id_rancho + "'\n"
                + "AND      c.id_corral        =   '" + corral.id_corral + "'";

        switch (tipo) {
            case 1://fecha
                consulta += "AND    ma.fecha between '" + formatoDate.format(fecha_ini) + " 00:00' "
                        + "AND  '" + formatoDate.format(fecha_ini) + " 23:59' ";
                break;
            case 2://Entre fechas
                consulta += "AND    ma.fecha between '" + formatoDate.format(fecha_ini) + " 00:00' "
                        + "AND  '" + formatoDate.format(fecha_fin) + " 23:59' ";
                break;
            case 3:// arete_visual
                consulta += "AND a.arete_visual = '" + animal.arete_visual + "' ";
        }

        consulta += "ORDER BY a.arete_visual";

        manejadorBD.consulta(consulta);

        if (manejadorBD.getRowCount() > 0) {

            manejadorBD.asignarTable(tabla);
        }

        tabla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);

        int[] tamaños = new int[9];

        tamaños[0] = 100; //IdAnimal
        tamaños[1] = 80;  //Tag
        tamaños[2] = 100; //Codigo
        tamaños[3] = 120;  //Medina
        tamaños[4] = 80;  //Fecha
        tamaños[5] = 120;  //Corral
        tamaños[6] = 80; //Dosis
        tamaños[7] = 80; //Costo
        tamaños[8] = 80; //Importe

        tabla.tamañoColumna(tamaños);
        tabla.ocultarcolumna(0);

        //    JTableHeader header = tabla.getTableHeader();
        //  header.setBackground(java.awt.Color.BLUE);
        //  header.setForeground(java.awt.Color.white);
        return tabla;
    }

    public static Table cargarTratamientos(Table tabla, Integer tipo, Date fecha_ini, Date fecha_fin, Animal animal, Corral corral) {

        tabla = crearTablaMedicinas(tabla);

        String consulta = ""
                + "SELECT DISTINCT(A.id_animal) AS 'Id Animal', A.arete_visual AS 'Arete Visual', "
                + " T.codigo AS 'Código', T.nombre AS 'Tratamiento', MA.fecha AS 'Fecha', "
                + " C.nombre AS 'Nombre del Corral' "
                + " FROM tratamiento AS T, medicina_tratamiento AS MT, medicina AS M, medicina_animal AS MA, "
                + "animal AS A, corral_animal AS CA, corral AS C "
                + "WHERE A.id_animal = MA.id_animal "
                + "AND A.id_animal = CA.id_animal "
                + "AND CA.id_corral = C.id_corral "
                + "AND MA.id_medicina = M.id_medicina "
                + "AND MT.id_medicina = M.id_medicina "
                + "AND MT.id_tratamiento = T.id_tratamiento"
                + "AND A.status = 'A' "
                + "AND ma.id_rancho     = '" + rancho.id_rancho + "' \n "
                + "AND      c.id_corral        =   '" + corral.id_corral + "' \n";

        switch (tipo) {
            case 1://fecha
                consulta += "AND    ma.fecha between '" + formatoDate.format(fecha_ini) + " 00:00' "
                        + "AND  '" + formatoDate.format(fecha_ini) + " 23:59' ";
                break;
            case 2://Entre fechas
                consulta += "AND    ma.fecha between '" + formatoDate.format(fecha_ini) + " 00:00' "
                        + "AND  '" + formatoDate.format(fecha_fin) + " 23:59' ";
                break;
            case 3:// arete_visual
                consulta += "AND a.arete_visual = '" + animal.arete_visual + "' ";
        }

        consulta += "ORDER BY ma.fecha";

        manejadorBD.consulta(consulta);

        if (manejadorBD.getRowCount() > 0) {

            manejadorBD.asignarTable(tabla);
        }

        tabla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);

        int[] tamaños = new int[9];

        tamaños[0] = 100; //IdAnimal
        tamaños[1] = 80;  //Tag
        tamaños[2] = 100; //Codigo
        tamaños[3] = 120;  //Medina
        tamaños[4] = 80;  //Fecha
        tamaños[5] = 120;  //Corral
        tamaños[6] = 80; //Dosis
        tamaños[7] = 80; //Costo
        tamaños[8] = 80; //Importe

        tabla.tamañoColumna(tamaños);
        tabla.ocultarcolumna(0);

        //    JTableHeader header = tabla.getTableHeader();
        //  header.setBackground(java.awt.Color.BLUE);
        //  header.setForeground(java.awt.Color.white);
        return tabla;
    }

    public static Table crearTablaMedicinas(Table tabla) {

        if (tabla == null) {
            tabla = new Table();
        }

        String titulos[] = {
            "Id Animal", "Arete Visual", "Arete Electronico", "Codigo", "Medicina", "Fecha",
            "Corral", "Dosis", "Costo", "Importe"};

        tabla.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                titulos
        ));

        tabla.setTitulos(titulos);
        tabla.cambiarTitulos();
        tabla.setFormato(new int[]{0, 0, 0, 0, 3, 0, 3, 1, 1, 1});

        return tabla;
    }

    public static Table crearTablaTratamientos(Table tabla) {

        if (tabla == null) {
            tabla = new Table();
        }

        String titulos[] = {
            "Id Animal", "Arete Visual", "Codigo", "Tratamiento", "Fecha",
            "Corral"};

        tabla.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                titulos
        ));

        tabla.setTitulos(titulos);
        tabla.cambiarTitulos();
        tabla.setFormato(new int[]{0, 0, 0, 0, 3, 0});

        return tabla;
    }

    public static Table cargarPesos(Table tabla, Integer tipo, Date fecha_ini, Date fecha_fin, Animal animal, Corral corral) {

        tabla = crearTablaPesos(tabla);

        String consulta = ""
                + "SELECT   a.id_animal, a.arete_visual, COALESCE(a.arete_electronico,''), \n"
                + "         DATE_FORMAT(m.fecha, '%Y/%m/%d') fecha, \n"
                + "         round(m.peso,2) peso, c.nombre \n"
                + "FROM     movimiento m, detalle_movimiento d, animal a, \n"
                + "         rancho r, corral c, corral_animal ca \n"
                + "WHERE    m.id_rancho     =   d.id_rancho \n"
                + "AND      ca.id_animal    =   a.id_animal \n"
                + "AND      ca.id_corral    =   c.id_corral \n"
                + "AND      m.id_concepto   =   d.id_concepto \n"
                + "AND      m.id_movimiento =   d.id_movimiento \n"
                + "AND      d.id_animal     =   a.id_animal \n"
                + "AND      d.id_rancho     =   ca.id_rancho \n"
                + "AND      d.id_animal     =   ca.id_animal \n"
                + "AND      ca.id_rancho    =   c.id_rancho \n"
                + "AND      ca.id_corral    =   c.id_corral \n"
                + "AND      m.id_rancho     =   r.id_rancho \n"
                + "AND      m.id_concepto   =   r.con_pesaje \n"
                + "AND      a.status        =   'A' "
                + "AND   m.id_rancho     = '" + rancho.id_rancho + "' "
                + "AND c.id_corral = '" + corral.id_corral + "' ";

        switch (tipo) {
            case 1://fecha
                consulta += "AND    m.fecha between '" + formatoDate.format(fecha_ini) + " 00:00' "
                        + "AND  '" + formatoDate.format(fecha_ini) + " 23:59' ";
                break;
            case 2://Entre fechas
                consulta += "AND    m.fecha between '" + formatoDate.format(fecha_ini) + " 00:00' "
                        + "AND  '" + formatoDate.format(fecha_fin) + " 23:59' ";
                break;
            case 3:// arete_visual
                consulta += "AND a.arete_visual = '" + animal.arete_visual + "' ";
        }
        consulta += "ORDER BY m.fecha";

        manejadorBD.consulta(consulta);

        if (manejadorBD.getRowCount() > 0) {

            manejadorBD.asignarTable(tabla);
        }

        tabla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);

        int[] tamaños = new int[6];
        tamaños[0] = 100;//id Animal
        tamaños[1] = 80;//Tag
        tamaños[2] = 100;//Eid
        tamaños[3] = 80;//fecha
        tamaños[4] = 80; //peso
        tamaños[5] = 120; //Corral
        tabla.tamañoColumna(tamaños);

        tabla.ocultarcolumna(0);
        return tabla;
    }

    public static Table crearTablaPesos(Table tabla) {

        if (tabla == null) {
            tabla = new Table();
        }

        String titulos[] = {
            "Id Animal", "Arete Visual", "Eid", "Fecha", "Peso(Kg)",
            "Corral"};

        tabla.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                titulos
        ));

        tabla.setTitulos(titulos);
        tabla.cambiarTitulos();
        tabla.setFormato(new int[]{0, 0, 0, 3, 1, 0});

        return tabla;
    }

    public static Table cargarMuertes(Table tabla, Integer tipo, Date fecha_ini, Date fecha_fin, Animal animal) {

        tabla = crearTablaMuertes(tabla);

        String consulta = ""
                + "SELECT d.id_animal, a.arete_visual, DATE_FORMAT(m.fecha, '%d/%m/%Y') fecha, m.necropcia, m.dx_muerte, "
                + "       datediff(CURRENT_DATE, m.fecha) dias_muerte,	etapa_reproductiva "
                + "FROM   movimiento m, detalle_movimiento d, rancho r, animal a "
                + "WHERE  m.id_rancho     = r.id_rancho "
                + "AND    m.id_concepto   = r.con_muerte "
                + "AND    m.id_rancho     = d.id_rancho "
                + "AND    m.id_concepto   = d.id_concepto "
                + "AND    m.id_movimiento = d.id_movimiento "
                + "AND    d.id_animal     = a.id_animal "
                + "AND    m.id_rancho     = '" + rancho.id_rancho + "' ";

        switch (tipo) {
            case 1://fecha
                consulta += "AND    m.fecha between '" + formatoDate.format(fecha_ini) + " 00:00' "
                        + "AND  '" + formatoDate.format(fecha_ini) + " 23:59' ";
                break;
            case 2://Entre fechas
                consulta += "AND    m.fecha between '" + formatoDate.format(fecha_ini) + " 00:00' "
                        + "AND  '" + formatoDate.format(fecha_fin) + " 23:59' ";
                break;
            case 3:// arete_visual
                consulta += "AND a.arete_visual = '" + animal.arete_visual + "'";
        }

        manejadorBD.consulta(consulta);

        if (manejadorBD.getRowCount() > 0) {

            manejadorBD.asignarTable(tabla);
        }

        tabla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);

        int[] tamaños = new int[7];
        tamaños[0] = 100;//id Animal
        tamaños[1] = 100;//Arete Electronico
        tamaños[2] = 120;//fecha
        tamaños[3] = 140;//necropcia
        tamaños[4] = 120; //dxMuerte
        tamaños[5] = 120;//dias Muerte
        tamaños[6] = 120;//etapa Reproductiva
        tabla.tamañoColumna(tamaños);

        tabla.ocultarcolumna(0);
        return tabla;
    }

    public static Table crearTablaMuertes(Table tabla) {

        if (tabla == null) {
            tabla = new Table();
        }

        String titulos[] = {
            "Id Animal", "Arete Visual", "Fecha de Muerte", "Necropcia", "Dx de Muerte",
            "Dias Muerte", "Etapa Reproductiva"};

        tabla.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                titulos
        ));

        tabla.setTitulos(titulos);
        tabla.cambiarTitulos();
        tabla.setFormato(new int[]{0, 0, 0, 0, 0, 0, 0});

        return tabla;
    }

    /**
     *
     * @param tabla
     */
    public static Table cargarTraspasos(Table tabla, Integer tipo, Date fecha_ini, Date fecha_fin, Corral corral) {

        tabla = crearTablaTraspasos(tabla);
        // SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");

        String consulta = ""
                + "SELECT   a.id_animal,         a.arete_visual, \n"
                + "         a.arete_electronico, DATE_FORMAT(m.fecha, '%d/%m/%Y') fecha,\n"
                + "         origen.nombre,       destino.nombre \n"
                + "FROM   movimiento m, rancho r, detalle_movimiento d, animal a, corral origen, corral destino \n"
                + "WHERE  m.id_rancho     = d.id_rancho \n"
                + "AND    m.id_concepto   = d.id_concepto \n"
                + "AND    m.id_movimiento = d.id_movimiento \n"
                + "AND    d.id_animal     = a.id_animal \n"
                + "AND    m.id_rancho     = r.id_rancho \n"
                + "AND    (   m.id_concepto    = r.con_traspaso_salida \n"
                + "        OR m.id_concepto = r.con_traspaso_entrada ) \n"
                + "AND    m.id_corral_origen  = origen.id_corral \n"
                + "AND    m.id_corral_destino = destino.id_corral \n"
                + "AND   a.status = 'A' \n"
                + "AND    m.id_corral_origen  <> r.id_corral_hospital \n"
                + "AND    m.id_corral_destino <> r.id_corral_hospital \n"
                + "AND    m.id_rancho     = '" + rancho.id_rancho + "' \n"
                + "AND (origen.nombre = '" + corral.nombre + "' \n"
                + " OR destino.nombre = '" + corral.nombre + "') \n";

        switch (tipo) {
            case 1://Todo
                break;
            case 2://Hoy
                consulta += "AND    m.fecha between '" + formatoDate.format(fecha_ini) + " 00:00' "
                        + "AND  '" + formatoDate.format(fecha_fin) + " 23:59' ";
                break;
            case 3://otra fecha
                consulta += "AND    m.fecha between '" + formatoDate.format(fecha_ini) + " 00:00' "
                        + "AND  '" + formatoDate.format(fecha_fin) + " 23:59' ";
                break;
            case 4://entre fechas
                consulta += "AND    m.fecha between '" + formatoDate.format(fecha_ini) + " 00:00' "
                        + "AND  '" + formatoDate.format(fecha_fin) + " 23:59' ";
                break;
        }

        consulta += "ORDER BY fecha";

        manejadorBD.consulta(consulta);

        if (manejadorBD.getRowCount() > 0) {

            manejadorBD.asignarTable(tabla);
        }

        tabla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);

        int[] tamaños = new int[6];
        tamaños[0] = 100;//id Animal
        tamaños[1] = 100;//Arete Visual
        tamaños[2] = 100;//Arete Electronico
        tamaños[3] = 140;//Fecha Salida
        tamaños[4] = 120; //Grupo Origen
        tamaños[5] = 120;//Grupo Destino

        tabla.tamañoColumna(tamaños);

        tabla.ocultarcolumna(0);
        return tabla;
    }

    public static Table crearTablaTraspasos(Table tabla) {

        if (tabla == null) {
            tabla = new Table();
        }

        String titulos[] = {
            "Id Animal", "Arete Visual", "Arete Electronico", "Fecha de Movimiento", "Grupo de Origen",
            "Grupo Destino"};

        tabla.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                titulos
        ));

        tabla.setTitulos(titulos);
        tabla.cambiarTitulos();
        tabla.setFormato(new int[]{0, 0, 0, 0, 0, 0});

        return tabla;

    }

    public static void cargarHistoricoHospital(Table tabla) {

        crearTablaHistoricoHospital(tabla);

        manejadorBD.consulta(""
                + "SELECT A1.id_animal, arete_visual, "
                + "       DATE_FORMAT(fechaEntrada, '%d/%m/%Y') fechaEntrada, "
                + "       COALESCE(DATE_FORMAT(fechaSalida, '%d/%m/%Y'), '00/00/0000') fechaSalida, "//(DATE_FORMAT(fechaSalida, '%d/%m/%Y') fechaSalida, "
                + "       datediff(COALESCE(fechaSalida, CURRENT_DATE), fechaEntrada) dias,  causa_entrada, observacion "
                + "FROM   (SELECT d.id_animal,     m.fecha fechaEntrada, "
                + "               fn_fechaSalidaHospital(d.id_animal, m.fecha) fechaSalida, "
                + "               m.causa_entrada, m.observacion "
                + "        FROM   movimiento m, rancho r, detalle_movimiento d "
                + "        WHERE  (     m.id_rancho         = r.id_rancho "
                + "                AND  m.id_concepto       = r.con_traspaso_entrada  "
                + "                AND  m.id_corral_destino = r.id_corral_hospital )"
                + "        AND    (     m.id_rancho     = d.id_rancho "
                + "                AND m.id_concepto   = d.id_concepto "
                + "                AND m.id_movimiento = d.id_movimiento )"
                + "        AND   m.id_rancho     = '" + rancho.id_rancho + "' ) A1, animal "
                + "WHERE A1.id_animal = animal.id_animal "
                + "AND   animal.status = 'A' ");

        if (manejadorBD.getRowCount() > 0) {
            manejadorBD.asignarTable(tabla);
        }
        tabla.ocultarcolumna(0);
    }

    public static void crearTablaHistoricoHospital(Table tabla) {

        if (tabla == null) {
            tabla = new Table();
        }

        String titulos[] = {
            "Id Animal", "Arete Visual", "Fecha de Entrada", "Fecha de Salida", "Dias en Hospital",
            "Causa de Entrada", "Observaciones"};

        tabla.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                titulos
        ));

        tabla.setTitulos(titulos);
        tabla.cambiarTitulos();
        tabla.setFormato(new int[]{0, 0, 0, 0, 0, 0, 0});

        int[] tamaños = new int[7];

        tamaños[0] = 80;//id Animal
        tamaños[1] = 110;//Arete Visual
        tamaños[2] = 110;//Fecha Salida
        tamaños[3] = 110;//Fecha Salida
        tamaños[4] = 110;//Dias en Hospital
        tamaños[5] = 120; //Causa de Entrada
        tamaños[6] = 120;//Observaciones

        tabla.tamañoColumna(tamaños);

    }

    public static Table cargarAnimalesHospital(Table tabla) {

        crearTablaAnimalesHospital(tabla);
        /*
         manejadorBD.consulta(""
         + "SELECT id_animal,"
         + "       DATE_FORMAT(fechaEntrada, '%d/%m/%Y') fechaEntrada, "
         + "       datediff(CURRENT_DATE, fechaEntrada) dias,  causa_entrada, observacion "
         + "FROM   (SELECT d.id_animal,     m.fecha fechaEntrada, "
         + "               fn_fechaSalidaHospital(d.id_animal, m.fecha) fechaSalida, "
         + "               m.causa_entrada, m.observacion "
         + "        FROM   movimiento m, rancho r, detalle_movimiento d "
         + "        WHERE  (     m.id_rancho         = r.id_rancho "
         + "                AND  m.id_concepto       = r.con_traspaso_entrada  "
         + "                AND  m.id_corral_destino = r.id_corral_hospital )"
         + "        AND    (     m.id_rancho     = d.id_rancho "
         + "                AND m.id_concepto   = d.id_concepto "
         + "                AND m.id_movimiento = d.id_movimiento )) A1 "
         + "WHERE   fechaSalida is null");
         */
        manejadorBD.consulta(""
                + "select a.id_animal, a.arete_visual, DATE_FORMAT(m.fecha, '%d/%m/%Y') fecha, "
                + "       dateDiff(current_date, m.fecha) dias, "
                + "       m.causa_entrada, m.observacion "
                + "from animal a, corral_animal ca, rancho r, movimiento_animal ma, movimiento m "
                + "where a.id_animal = ca.id_animal "
                + "and   r.id_corral_hospital = ca.id_corral "
                + "and  ma.id_rancho = r.id_rancho "
                + "and  ma.id_concepto = r.con_traspaso_entrada "
                + "and  ma.id_animal   = a.id_animal "
                + "and  ma.id_rancho   = m.id_rancho "
                + "and  ma.id_concepto  = m.id_concepto "
                + "and  ma.id_movimiento = m.id_movimiento "
                + "AND   a.status = 'A' \n"
                + "AND   m.id_rancho     = '" + rancho.id_rancho + "' ");

        if (manejadorBD.getRowCount() > 0) {
            manejadorBD.asignarTable(tabla);
            //tabla.setModel(manejadorBD.modelo);
        }
        tabla.ocultarcolumna(0);
        return tabla;
    }

    public static void crearTablaAnimalesHospital(Table tabla) {

        if (tabla == null) {
            tabla = new Table();
        }

        String titulos[] = {
            "Id Animal", "Arete Visual", "Fecha de Entrada", "Dias en Hospital",
            "Causa de Entrada", "Observaciones"};

        tabla.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                titulos
        ));

        tabla.setTitulos(titulos);
        tabla.cambiarTitulos();
        tabla.setFormato(new int[]{0, 0, 0, 0, 0, 0});

        int[] tamaños = new int[6];

        tamaños[0] = 80;//id Animal
        tamaños[1] = 110;//Arete Visual
        tamaños[2] = 110;//Fecha Salida
        tamaños[3] = 110;//Dias en Hospital
        tamaños[4] = 120; //Causa de Entrada
        tamaños[5] = 120;//Observaciones

        tabla.tamañoColumna(tamaños);

    }

    public static Table cargarMovimientosEntrada(Date fecha) {

        Table tabla = crearTablaEntrada();

        //  SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
        manejadorBD.consulta(""
                + "SELECT a.arete_visual,                                                 a.arete_electronico,\n"
                + "       c.nombre as corral,                                             COALESCE(p.descripcion,''),\n"
                + "       COALESCE(DATE_FORMAT(fecha_ingreso, '%d/%m/%Y'), '01/01/1900'), a.arete_siniiga,\n"
                + "       a.arete_campaña,                                                COALESCE(s.descripcion,''),\n"
                + "       COALESCE(DATE_FORMAT(fecha_compra, '%d/%m/%Y'), '01/01/1900'),  a.numero_lote,\n"
                + "       a.compra,                                                       round(a.peso_actual,2),\n"
                + "       round(a.temperatura,2)\n"
                + "FROM   animal a LEFT OUTER JOIN proveedor p ON a.id_proveedor = p.id_proveedor\n"
                + "                LEFT OUTER JOIN sexo s      ON a.id_sexo      = s.id_sexo,\n"
                + "       corral c,   corral_animal ca\n"
                + "WHERE  a.id_animal = ca.id_animal \n"
                + "AND    c.id_corral = ca.id_corral \n"
                + "AND    a.status    =   'A' \n"
                + "AND    fecha_ingreso between '" + formatoDate.format(fecha) + " 00:00' \n"
                + "AND    '" + formatoDate.format(fecha) + " 23:59' \n"
                + "AND    c.id_rancho     = '" + rancho.id_rancho + "' ");

        if (manejadorBD.getRowCount() > 0) {
            manejadorBD.asignarTable(tabla);
            //tabla.setModel(manejadorBD.modelo);
        }

        return tabla;
    }

    public static Table crearTablaEntrada() {

        Table t_Salidas;

        t_Salidas = new Table();

        String titulos[] = {
            "Arete Visual", "Arete Electronico", "Corral",
            "Proveedor", "Fecha de Ingreso", "Arete Siniiga",
            "Arete Campaña", "Sexo", "Fecha de Compra",
            "Numero de Lote", "Compra", "Peso Actual (kg)",
            "Temperatura"};

        t_Salidas.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                titulos
        ));

        t_Salidas.setTitulos(titulos);
        t_Salidas.cambiarTitulos();
        t_Salidas.setFormato(new int[]{
            Table.letra, Table.letra, Table.letra,
            Table.letra, Table.fecha, Table.letra,
            Table.letra, Table.letra, Table.fecha,
            Table.letra, Table.letra, Table.numero_double,
            Table.numero_double});

        return t_Salidas;
    }

    public static void cargarMovimientosSalida(Table tabla, Integer tipo, Date fecha_ini, Date fecha_fin, Animal animal) {

        tabla = crearTablaSalidas(tabla);
        String consulta;

        /*
         * 2014-10-18 11:47 
         * cast Date a la fecha
         * para la ventana de Salida de Ganado
         */
        consulta = ""
                + "SELECT a.id_animal,  a.arete_visual,		 a.arete_electronico,a.arete_siniiga,\n"
                + " DATE_FORMAT(m.fecha, '%d/%m/%Y'), COALESCE(c.descripcion,'') as clase, m.numero_pedido,\n"
                //  + "m.fecha, c.descripcion as clase, m.numero_pedido, "
                + "co.nombre as corral,	round(a.peso_actual, 2)\n"
                + "FROM movimiento m LEFT OUTER JOIN clase_movimiento c\n"
                + "ON m.id_clase_movimiento = c.id_clase_movimiento,\n"
                + "detalle_movimiento d, rancho r, animal a, corral_animal ca , corral co\n"
                + "WHERE m.id_rancho	 = r.id_rancho\n"
                + "AND 	 m.id_concepto	 = r.con_salida\n"
                + "AND	 m.id_rancho	 = d.id_rancho\n"
                + "AND	 m.id_movimiento = d.id_movimiento\n"
                + "AND	 m.id_concepto	 = d.id_concepto\n"
                + "AND	 d.id_animal	 = a.id_animal\n"
                + "AND	 d.id_animal	 = ca.id_animal\n"
                + "AND	 ca.id_corral	 = co.id_corral\n"
                + "AND   a.status   =   'V'\n"
                + "AND   m.id_rancho     = '" + rancho.id_rancho + "' \n";

        switch (tipo) {
            case 1://fecha
                consulta += "AND    m.fecha between '" + formatoDate.format(fecha_ini) + " 00:00' "
                        + "AND  '" + formatoDate.format(fecha_ini) + " 23:59' ";
                break;
            case 2://Entre fechas
                consulta += "AND    m.fecha between '" + formatoDate.format(fecha_ini) + " 00:00' "
                        + "AND  '" + formatoDate.format(fecha_fin) + " 23:59' ";
                break;
            case 3:// arete_visual
                consulta += "AND a.arete_visual = '" + animal.arete_visual + "'";
                break;
            case 4:
                consulta += "AND a.arete_electronico = '" + animal.arete_electronico + "' \n"
                        + "AND  a.fecha_ingreso BETWEEN '" + formatoDate.format(fecha_ini) + " 00:00' "
                        + "AND  '" + formatoDate.format(fecha_ini) + " 23:59' ";
        }

        manejadorBD.consulta(consulta);

        if (manejadorBD.getRowCount() > 0) {
            manejadorBD.asignarTable(tabla);
            //tabla.setModel(manejadorBD.modelo);
        }

        tabla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

        tabla.ocultarcolumna(0);
        //return tabla;
    }

    public static Table cargarMovimientosSalida(Date fecha) {

        Table tabla = null;
        tabla = crearTablaSalidas(tabla);

        //   SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
        manejadorBD.consulta(""
                + "SELECT   a.arete_visual,                                a.arete_electronico,  \n"
                + "         a.arete_siniiga,                        m.fecha,\n"
                + "         COALESCE(c.descripcion,'') as clase,    m.numero_pedido,\n"
                + "         co.nombre as corral,                    a.peso_actual \n"
                + "FROM     movimiento m LEFT OUTER JOIN clase_movimiento c \n"
                + "             ON m.id_clase_movimiento = c.id_clase_movimiento, \n"
                + "         detalle_movimiento d, rancho r, animal a, corral_animal ca , corral co \n"
                + "WHERE m.id_rancho        =   r.id_rancho \n"
                + "AND 	 m.id_concepto      =   r.con_salida \n"
                + "AND	 m.id_rancho        =   d.id_rancho \n"
                + "AND	 m.id_movimiento    =   d.id_movimiento \n"
                + "AND	 m.id_concepto      =   d.id_concepto \n"
                + "AND	 d.id_animal        =   a.id_animal \n"
                + "AND	 d.id_animal        =   ca.id_animal \n"
                + "AND	 ca.id_corral       =   co.id_corral \n"
                + "AND   a.status           =   'A' \n"
                + "AND   m.fecha between '" + formatoDate.format(fecha) + " 00:00' \n"
                + "AND  '" + formatoDate.format(fecha) + " 23:59' \n"
                + "AND   m.id_rancho     = '" + rancho.id_rancho + "' \n");

        if (manejadorBD.getRowCount() > 0) {
            manejadorBD.asignarTable(tabla);
            //tabla.setModel(manejadorBD.modelo);
        }

        return tabla;
    }

    private static Table crearTablaSalidas(Table tabla) {

        if (tabla == null) {
            tabla = new Table();
        }

        String titulos[] = {"Id Animal", "Arete Visual", "Arete Electronico", "Arete Siniiga", "Fecha de Salida",
            "Clase de Movimiento", "Numero de Pedido", "Grupo de Origen",
            "Peso (kg)"};

        tabla.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                titulos
        ));

        tabla.setTitulos(titulos);
        tabla.cambiarTitulos();
        tabla.setFormato(new int[]{1, 4, 0, 0, 3, 0, 0, 0, 1});

        int[] tamaños = new int[9];

        tamaños[0] = 80;//id Animal
        tamaños[1] = 110;//arete Visual
        tamaños[2] = 110;//Fecha Salida
        tamaños[3] = 110;//Dias en Hospital
        tamaños[4] = 120; //Causa de Entrada
        tamaños[5] = 180;//Observaciones
        tamaños[6] = 120;//Observaciones
        tamaños[7] = 120;//Observaciones
        tamaños[8] = 120;//Observaciones

        tabla.tamañoColumna(tamaños);

        return tabla;
    }

    public static void leerPesos(Table tabla, String id_animal) {

        manejadorBD.consulta(""
                + "SELECT DATE_FORMAT(fecha, '%Y-%m-%d %T') fecha, Round(peso,2) "
                + "FROM   movimiento M, detalle_movimiento D, rancho R "
                + "WHERE  (    M.id_rancho		=   D.id_rancho "
                + "	   AND M.id_movimiento =   D.id_movimiento "
                + "	   AND M.id_concepto	=   D.id_concepto ) "
                + "AND	  (    M.id_concepto	=   R.con_pesaje "
                + "	   AND M.id_rancho     =   R.id_rancho	) "
                //   + "AND      M.id_rancho	= '" + rancho.id_rancho + "' "
                + "AND      D.id_animal	= '" + id_animal + "' "
                + "ORDER BY fecha DESC");

        if (manejadorBD.getRowCount() > 0) {

            manejadorBD.asignarTable(tabla);
            //tabla.setModel(manejadorBD.modelo);
        } else {
            tabla.limpiarTabla();
        }
    }

}
