/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import abstractt.Table;
import static gui.Splash.formatoDateTime;
import static gui.Desktop.rancho;
import static gui.Desktop.manejadorBD;
import static gui.Login.gs_mensaje;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Developer GAGS
 */
public class Animal {

    public String id_animal;
    public String arete_visual;
    public String arete_electronico;
    public Date fecha_ingreso;
    public String arete_siniiga;
    public String arete_campaña;
    public Date fecha_compra;
    public String numero_lote;
    public String compra;
    public Proveedor proveedor;
    public Double peso_actual;
    public Double temperatura;
    public Double peso_compra;
    public Sexo sexo;
    public String status;
    public Corral corral;
    public String es_semental;
    public Animal semental;
    public Raza raza;
    public String id_registro_empadre;
    public Genealogia genealogia;
    public String es_vientre;
    public Date fecha_recepcion;
    public Double peso_recepcion;

    public Double porcentaje_merma;
    public Double costo_flete;
    public Double total_alimento;
    public Double costo_alimentacion;
    public Double promedio_alimentacion;
    public Double promedio_costo_alimento;
    public Date fecha_ultima_comida;
    public Double ganancia_promedio;

    public Animal() {

        id_animal = "";
        arete_visual = "";
        sexo = new Sexo();
        proveedor = new Proveedor();
        corral = new Corral();
        raza = new Raza();
        genealogia = new Genealogia();

    }

    public String selectInicial() {

        return "SELECT animal.id_animal,                             arete_visual, \n"
                + "    COALESCE(arete_electronico,''),               COALESCE(fecha_ingreso,'1900-01-01 00:00:00'), \n"
                + "    COALESCE(arete_siniiga,''),                   COALESCE(arete_campaña,''), \n"
                + "    COALESCE(fecha_compra,'1900-01-01 00:00:00'), COALESCE(numero_lote,''), \n"
                + "    COALESCE(compra,''),                          COALESCE(id_proveedor,''), \n"
                + "    COALESCE(peso_actual,0),                      COALESCE(temperatura,0), \n"
                + "    COALESCE(peso_compra,0),                      status, \n"
                + "    COALESCE(es_semental,'N'),                    COALESCE(id_semental,0), \n"
                + "    COALESCE(animal.id_sexo,''),                  COALESCE(id_raza,''),\n"
                + "    COALESCE(es_vientre,'N'),                     round(COALESCE(porcentaje_merma,0),2),"
                + "    COALESCE(costo_flete, 0),                  COALESCE(total_alimento, 0),"
                + "    COALESCE(costo_alimento, 0),                round(COALESCE(promedio_alimento, 0),2),"
                + "    COALESCE(promedio_costo_alimento,0),        COALESCE(fecha_ultima_comida, '1900-01-01 00:00:00'),"
                + "    round(COALESCE(ganancia_promedio, 0),2),     COALESCE(fecha_recepcion, '1900-01-01 00:00:00'), \n "
                + "     COALESCE(peso_recepcion, 0)";

    }

    public void cargarPorId(String sId_animal) {

        manejadorBD.consulta(""
                + selectInicial()
                + "FROM   animal, corral_animal \n"
                + "WHERE  animal.id_animal        = '" + sId_animal + "' \n"
                + "AND    animal.id_animal        = corral_animal.id_animal \n"
                + "AND    corral_animal.id_rancho = '" + rancho.id_rancho + "'");

        if (manejadorBD.getRowCount() > 0) {

            asignarValores();
        }
    }

    public void cargarPorEid(String sEid) {

        manejadorBD.consulta(""
                + selectInicial()
                + "FROM  animal, corral_animal  \n"
                + "WHERE animal.status = 'A' \n"
                + "AND   animal.arete_electronico    = '" + sEid + "' \n"
                + "AND      animal.id_animal    =   corral_animal.id_animal \n"
                + "AND      corral_animal.id_rancho = '" + rancho.id_rancho + "'");

        if (manejadorBD.getRowCount() > 0) {

            asignarValores();
        }
    }

    public void cargarPorAreteElectronicoStatus(String aAreteElectronico, String aStatus) {

        manejadorBD.consulta(""
                + selectInicial()
                + "FROM  animal, corral_animal  \n"
                + "WHERE animal.status = '" + aStatus + "' \n"
                + "AND   animal.arete_electronico    = '" + aAreteElectronico + "' \n"
                + "AND      animal.id_animal    =   corral_animal.id_animal \n"
                + "AND      corral_animal.id_rancho = '" + rancho.id_rancho + "'");

        if (manejadorBD.getRowCount() > 0) {

            asignarValores();
        }
    }

    public void cargarPorEidTodosRanchos(String sEid) {

        manejadorBD.consulta(""
                + selectInicial()
                + "FROM  animal, corral_animal  \n"
                + "WHERE animal.status = 'A' \n"
                + "AND   animal.arete_electronico    = '" + sEid + "' \n"
                + "AND      animal.id_animal    =   corral_animal.id_animal");

        if (manejadorBD.getRowCount() > 0) {

            asignarValores();
        }
    }

    public void cargarPorAreteVisual(String aAreteVisual, String aStatus) {

        manejadorBD.consulta(""
                + selectInicial()
                + "FROM  animal, corral_animal  \n"
                + "WHERE    animal.arete_visual  = '" + aAreteVisual + "' \n"
                + "AND      status  =   '" + aStatus + "'                    \n"
                + "AND      animal.id_animal    =   corral_animal.id_animal \n"
                + "AND      corral_animal.id_rancho = '" + rancho.id_rancho + "'");

        if (manejadorBD.getRowCount() > 0) {

            asignarValores();
        }
    }

    public void cargarPorAreteVisualTodos(String aAreteVisual) {

        manejadorBD.consulta(""
                + selectInicial()
                + "FROM  animal, corral_animal  \n"
                + "WHERE    animal.arete_visual  = '" + aAreteVisual + "' \n"
                + "AND      animal.id_animal    =   corral_animal.id_animal \n"
                + "AND      corral_animal.id_rancho = '" + rancho.id_rancho + "'");

        if (manejadorBD.getRowCount() > 0) {

            asignarValores();
        }
    }

    public void cargarPorAreteSiniiga(String sSiniiga) {

        manejadorBD.consulta(""
                + selectInicial()
                + "FROM  animal, corral_animal  \n"
                + "WHERE   animal.arete_siniiga  = '" + sSiniiga + "' \n"
                + "AMD     animal.id_animal = corral_animal.id_animal \n"
                + "AND     corral_animal.id_rancho    =   '" + rancho.id_rancho + "'");

        if (manejadorBD.getRowCount() > 0) {

            asignarValores();
        }
    }

    private void asignarValores() {

        Date fecha = null;
        String id_sexo;
        String id_proveedor;
        String id_semental;
        String id_raza;

        id_animal = manejadorBD.getValorString(0, 0);
        arete_visual = manejadorBD.getValorString(0, 1);
        arete_electronico = manejadorBD.getValorString(0, 2);

        try {

            fecha_ingreso = formatoDateTime.parse(manejadorBD.getValorString(0, 3));
        } catch (ParseException ex) {

            Logger.getLogger(Animal.class.getName()).log(Level.SEVERE, null, ex);
        }

        arete_siniiga = manejadorBD.getValorString(0, 4);
        arete_campaña = manejadorBD.getValorString(0, 5);

        try {

            fecha_compra = formatoDateTime.parse(manejadorBD.getValorString(0, 6));
        } catch (ParseException ex) {

            Logger.getLogger(Animal.class.getName()).log(Level.SEVERE, null, ex);
        }

        numero_lote = manejadorBD.getValorString(0, 7);
        compra = manejadorBD.getValorString(0, 8);
        id_proveedor = manejadorBD.getValorString(0, 9);
        peso_actual = manejadorBD.getValorDouble(0, 10);
        temperatura = manejadorBD.getValorDouble(0, 11);
        peso_compra = manejadorBD.getValorDouble(0, 12);

        status = manejadorBD.getValorString(0, 13);
        es_semental = manejadorBD.getValorString(0, 14);
        id_semental = manejadorBD.getValorString(0, 15);
        id_sexo = manejadorBD.getValorString(0, 16);
        id_raza = manejadorBD.getValorString(0, 17);
        es_vientre = manejadorBD.getValorString(0, 18);
        
        porcentaje_merma = manejadorBD.getValorDouble(0, 19);
        costo_flete = manejadorBD.getValorDouble(0, 20);
        total_alimento = manejadorBD.getValorDouble(0, 21);
        costo_alimentacion = manejadorBD.getValorDouble(0, 22);
        
        promedio_alimentacion = manejadorBD.getValorDouble(0, 23);
        promedio_costo_alimento = manejadorBD.getValorDouble(0, 24);

        try {
            fecha_ultima_comida = formatoDateTime.parse(manejadorBD.getValorString(0, 25));
        } catch (ParseException ex) {
            Logger.getLogger(Animal.class.getName()).log(Level.SEVERE, null, ex);
        }
        ganancia_promedio = manejadorBD.getValorDouble(0, 26);
        try {
            fecha_recepcion = formatoDateTime.parse(manejadorBD.getValorString(0, 27));
        } catch (ParseException ex) {
            Logger.getLogger(Animal.class.getName()).log(Level.SEVERE, null, ex);
        }
        peso_recepcion = manejadorBD.getValorDouble(0, 28);
        
        
        proveedor.cargarPorId(id_proveedor);

        if (!id_semental.equals("")) {

            semental = new Animal();
            semental.cargarPorId(id_semental);
        }

        sexo.cargarPorId(id_sexo);
        raza.cargarPorId(id_raza);
        corral.cargarPorAnimal(this);

        if (sexo.descripcion.equals("Hembra") && !id_semental.equals("")) {
            //Tiene emparejamiento
            obtenerRegistroEmpadre();
        }

        genealogia.cargarGenealogia(id_animal);

    }

    public void obtenerRegistroEmpadre() {

        manejadorBD.consulta(""
                + "SELECT id_registro_empadre\n"
                + "FROM   registro_empadre \n"
                + "WHERE  id_hembra = '" + id_animal + "'\n"
                + "AND    activo    = 'S' ");

        if (manejadorBD.getRowCount() > 0) {

            this.id_registro_empadre = manejadorBD.getValorString(0, 0);
        } else {
            this.id_registro_empadre = "0";
        }
    }

    public String toString() {

        String salida;

        salida = "{id_animal=" + id_animal
                + ", arete_visual='" + arete_visual + "'"
                + ", arete_electronico='" + arete_electronico + "'"
                //     + ", categoria(Proveedor)='" + categoria + "'"
                + ", fecha_ingreso(Fecha de Compra)=" + fecha_ingreso.toString()
                + ", arete_siniiga='" + arete_siniiga + "'"
                + ", arete_campaña(Arete Campaña)='" + arete_campaña + "'"
                //           + ", arete_metalico_numero='" + arete_metalico_numero + "'"
                //   + ", ganado_amedias(rancho)='" + ganado_amedias + "'"
                //                + ", color_arete(Sexo)=" + color.toString()
                + ", fecha_compra(Fecha de Manejo)=" + fecha_compra.toString()
                + ", numero_lote='" + numero_lote + "'"
                + ", compra='" + compra + "'"
                + ", id_proveedor = " + proveedor.toString()
                + ", peso_actual=" + peso_actual.toString()
                + ", temperatura=" + temperatura.toString()
                + ", peso_compra=" + peso_compra.toString()
                //     + ", tipo_ganado=" + tipoGanado.toString()
                //                + ", tipo_medicamento=" + tipoMedicamento.toString()
                + ", status= '" + status + "'}";

        return salida;
    }

    public static Table animalGrupo(Table tabla, Usuario usuario, String tipo) {

        tabla = crearTablaAnimal(tabla);

        manejadorBD.consulta(""
                + "SELECT animal.id_animal,                   animal.arete_visual,                        animal.arete_electronico,\n"
                + "       COALESCE(proveedor.descripcion,''), COALESCE(fecha_compra,'1900-01-01 00:00'),  animal.numero_lote,\n"
                + "       COALESCE(sexo.descripcion,''),      COALESCE(fecha_ingreso,'1900-01-01 00:00'), animal.arete_siniiga,\n"
                + "       animal.arete_campaña,               ROUND(animal.peso_actual,2),                ROUND(animal.temperatura,2),\n"
                + "       corral.nombre "
                + "FROM  animal LEFT OUTER JOIN proveedor ON animal.id_proveedor = proveedor.id_proveedor"
                + "             LEFT OUTER JOIN sexo      ON animal.id_sexo      = sexo.id_sexo,"
                + "      corral, corral_animal, animal_grupo \n"
                //+ "FROM   corral, corral_animal, animal, color_arete, proveedor,  "
                + "WHERE  corral.id_corral        = corral_animal.id_corral "
                + "AND    animal.id_animal        = corral_animal.id_animal "
                + "AND    animal_grupo.id_rancho  = '" + rancho.id_rancho + "' "
                + "AND    animal_grupo.id_usuario = '" + usuario.id_usuario + "' "
                + "AND    animal_grupo.id_animal  = animal.id_animal  "
                + "AND    animal_grupo.tipo       = '" + tipo + "' "
                + "AND    animal.status           = 'A' "
        );

        if (manejadorBD.getRowCount() > 0) {

            manejadorBD.asignarTable(tabla);
        }

        tabla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

        tabla.ocultarcolumna(0);

        return tabla;
    }

    public static Table animalEmparejamiento(Table tabla, Animal semental) {

        tabla = crearTablaAnimal(tabla);

        manejadorBD.consulta(""
                + "SELECT animal.id_animal,                          animal.arete_visual,                       \n"
                + "       animal.arete_electronico,                  COALESCE(proveedor.descripcion,''),        \n"
                + "       COALESCE(fecha_compra,'1900-01-01 00:00'), animal.numero_lote,                        \n"
                + "       COALESCE(sexo.descripcion,''),             COALESCE(fecha_ingreso,'1900-01-01 00:00'),\n"
                + "       animal.arete_siniiga,                      animal.arete_campaña,                      \n"
                + "       ROUND(animal.peso_actual,2),               ROUND(animal.temperatura,2),               \n"
                + "       corral.nombre\n"
                + "FROM  animal LEFT OUTER JOIN proveedor ON animal.id_proveedor = proveedor.id_proveedor\n"
                + "             LEFT OUTER JOIN sexo      ON animal.id_sexo      = sexo.id_sexo, \n"
                + "      corral, corral_animal \n"
                + "WHERE corral.id_corral    = corral_animal.id_corral \n"
                + "AND   animal.id_animal    = corral_animal.id_animal \n"
                + "AND   animal.status       = 'A' \n"
                + "AND   animal.id_semental     = '" + semental.id_animal + "'\n"
                + "AND   corral.id_rancho    = '" + rancho.id_rancho + "'");

        manejadorBD.asignarTable(tabla);

        tabla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

        tabla.ocultarcolumna(0);

        return tabla;
    }

    public static Table animalSementales(Table tabla) {

        tabla = crearTablaAnimal(tabla);

        manejadorBD.consulta(""
                + "SELECT animal.id_animal,                          animal.arete_visual,                       \n"
                + "       animal.arete_electronico,                  COALESCE(proveedor.descripcion,''),        \n"
                + "       COALESCE(fecha_compra,'1900-01-01 00:00'), animal.numero_lote,                        \n"
                + "       COALESCE(sexo.descripcion,''),             COALESCE(fecha_ingreso,'1900-01-01 00:00'),\n"
                + "       animal.arete_siniiga,                      animal.arete_campaña,                      \n"
                + "       ROUND(animal.peso_actual,2),               ROUND(animal.temperatura,2),               \n"
                + "       corral.nombre\n"
                + "FROM  animal LEFT OUTER JOIN proveedor ON animal.id_proveedor = proveedor.id_proveedor\n"
                + "             LEFT OUTER JOIN sexo      ON animal.id_sexo      = sexo.id_sexo, \n"
                + "      corral, corral_animal \n"
                + "WHERE corral.id_corral    = corral_animal.id_corral \n"
                + "AND   animal.id_animal    = corral_animal.id_animal \n"
                + "AND   animal.es_semental  = 'S' \n"
                + "AND   animal.status       = 'A' \n"
                + "AND   corral.id_rancho    = '" + rancho.id_rancho + "'");

        manejadorBD.asignarTable(tabla);

        tabla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

        tabla.ocultarcolumna(0);

        return tabla;
    }

    public static Table animalTodos(Table tabla) {

        tabla = crearTablaAnimal(tabla);

        manejadorBD.consulta(""
                + "SELECT animal.id_animal,                   animal.arete_visual,                        animal.arete_electronico,\n"
                + "       COALESCE(proveedor.descripcion,''), COALESCE(fecha_compra,'1900-01-01 00:00'),  animal.numero_lote,\n"
                + "       COALESCE(sexo.descripcion,''),      COALESCE(fecha_ingreso,'1900-01-01 00:00'), animal.arete_siniiga,\n"
                + "       animal.arete_campaña,               ROUND(animal.peso_actual,2),                ROUND(animal.temperatura,2),\n"
                + "       corral.nombre "
                + "FROM  animal LEFT OUTER JOIN proveedor ON animal.id_proveedor = proveedor.id_proveedor"
                + "             LEFT OUTER JOIN sexo      ON animal.id_sexo      = sexo.id_sexo,"
                + "      corral, corral_animal "
                + "WHERE corral.id_corral    = corral_animal.id_corral "
                + "AND   animal.id_animal    = corral_animal.id_animal "
                + "AND   animal.status = 'A' "
                + "AND   corral.id_rancho = '" + rancho.id_rancho + "'");

        if (manejadorBD.getRowCount() > 0) {

            manejadorBD.asignarTable(tabla);
        }

        tabla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

        tabla.ocultarcolumna(0);

        return tabla;
    }

    public static Table hembrasSinEmparejar(Table tabla) {

        tabla = crearTablaAnimal(tabla);

        manejadorBD.consulta("/*Consulta de Todas las Hembras sin Emparejar*/\n"
                + "SELECT animal.id_animal,                   animal.arete_visual,                        animal.arete_electronico,\n"
                + "       COALESCE(proveedor.descripcion,''), COALESCE(fecha_compra,'1900-01-01 00:00'),  animal.numero_lote,\n"
                + "       COALESCE(sexo.descripcion,''),      COALESCE(fecha_ingreso,'1900-01-01 00:00'), animal.arete_siniiga,\n"
                + "       animal.arete_campaña,               ROUND(animal.peso_actual,2),                ROUND(animal.temperatura,2),\n"
                + "       corral.nombre \n"
                + "FROM  animal LEFT OUTER JOIN proveedor ON animal.id_proveedor = proveedor.id_proveedor \n"
                + "             LEFT OUTER JOIN sexo      ON animal.id_sexo      = sexo.id_sexo, \n"
                + "      corral, corral_animal \n"
                + "WHERE corral.id_corral                 = corral_animal.id_corral \n"
                + "AND   animal.id_animal                 = corral_animal.id_animal \n"
                + "AND   animal.status                    = 'A' \n"
                + "AND   sexo.descripcion                 = 'Hembra'\n"
                + "AND   ( COALESCE(animal.id_semental,'0') = '0' \n"
                + "       OR animal.id_semental = '' ) \n"
                + "AND   animal.es_vientre  =   'S'\n"
                + "AND   corral.id_rancho                 = '" + rancho.id_rancho + "'\n"
                + "/*Consulta de Todas las Hembras sin Emparejar*/");

        if (manejadorBD.getRowCount() > 0) {

            manejadorBD.asignarTable(tabla);
        }

        tabla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

        tabla.ocultarcolumna(0);

        return tabla;
    }

    public static Table Animal(Integer id_animal, Table tabla) {

        tabla = crearTablaAnimal(tabla);

        manejadorBD.consulta(""
                + "SELECT animal.id_animal,                   animal.arete_visual,                        animal.arete_electronico,\n"
                + "       COALESCE(proveedor.descripcion,''), COALESCE(fecha_compra,'1900-01-01 00:00'),  animal.numero_lote,\n"
                + "       COALESCE(sexo.descripcion,''),      COALESCE(fecha_ingreso,'1900-01-01 00:00'), animal.arete_siniiga,\n"
                + "       animal.arete_campaña,               ROUND(animal.peso_actual,2),                ROUND(animal.temperatura,2),\n"
                + "       corral.nombre "
                + "FROM  animal LEFT OUTER JOIN proveedor ON animal.id_proveedor = proveedor.id_proveedor"
                + "             LEFT OUTER JOIN sexo      ON animal.id_sexo      = sexo.id_sexo,"
                + "      corral, corral_animal "
                + "WHERE corral.id_corral    = corral_animal.id_corral "
                + "AND   animal.id_animal    = corral_animal.id_animal "
                + "AND   animal.status = 'A' "
                + "AND   animal.id_animal    = '" + id_animal + "' \n"
                + "AND   corral.id_rancho = '" + rancho.id_rancho + "'");

        manejadorBD.asignarTable(tabla);

        return tabla;
    }

    private static Table crearTablaAnimal(Table tabla) {

        if (tabla == null) {

            tabla = new Table();
        }

        String titulos[] = {
            "Id Animal", "Arete Visual", "Arete Electronico",
            "Proveedor", "Fecha de Compra", "Numero de Lote",
            "Sexo", "Fecha de Ingreso", "Arete Siniiga",
            "Arete Campaña", "Peso Actual", "Temperatura",
            "Corral"};

        tabla.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                titulos));

        tabla.setTitulos(titulos);
        tabla.cambiarTitulos();
        tabla.setFormato(new int[]{
            Table.letra, Table.letra, Table.letra,
            Table.letra, Table.fecha, Table.letra,
            Table.letra, Table.fecha, Table.letra,
            Table.letra, Table.numero_double, Table.numero_double,
            Table.letra});

        int[] tamaños = new int[13];
        tamaños[0] = 0;//id Animal
        tamaños[1] = 80;//Arete Visual
        tamaños[2] = 120;//Arete Electronico
        tamaños[3] = 120;//Proveedor
        tamaños[4] = 120;//Fecha de Compra
        tamaños[5] = 120;//Numero de Lote
        tamaños[6] = 80;//Sexo
        tamaños[7] = 120;//Fecha de Ingreso
        tamaños[8] = 100;//Arete Siniiga
        tamaños[9] = 100;//Arete Campaña
        tamaños[10] = 80;//peso actual
        tamaños[11] = 80; //temperatura
        tamaños[12] = 120;//corral         
        tabla.tamañoColumna(tamaños);

        return tabla;
    }

    public static Table registrosEmpadre(Animal hembra, Table tabla) {

        tabla = crearTablaRegistroEmpadre(tabla);

        manejadorBD.consulta(""
                + "SELECT   r.id_registro_empadre,  COALESCE(r.fecha,'1900-01-01 00:00'),\n"
                + "         r.id_hembra,            r.id_semental,\n"
                + "         s.arete_visual,         r.status_gestacional,\n"
                + "         r.aborto\n"
                + "FROM     registro_empadre r, animal s\n"
                + "WHERE    r.id_semental = s.id_animal\n"
                + "AND      r.id_hembra    = '" + hembra.id_animal + "'");

        manejadorBD.asignarTable(tabla);

        tabla.ocultarcolumna(0);
        tabla.ocultarcolumna(2);
        tabla.ocultarcolumna(3);

        // tabla.setValueAt(true, 0, 6);
        return tabla;
    }

    private static Table crearTablaRegistroEmpadre(Table tabla) {

        if (tabla == null) {

            tabla = new Table();
        }

        String titulos[] = {
            "id Registro Empadre", "Fecha", "id Hembra",
            "id Semental", "Semental", "Status Gestacional",
            "Aborto"};

        tabla.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                titulos
        ) {
            Class[] types = new Class[]{
                java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Boolean.class,
                java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        tabla.setTitulos(titulos);
        tabla.cambiarTitulos();
        tabla.setFormato(new int[]{
            Table.letra, Table.fecha, Table.letra,
            Table.letra, Table.letra, Table.letra,
            Table.letra});

        int[] tamaños = new int[]{
            0,//id Registro Empadre
            80,//Fecha
            0,//id Hembra
            0,//id Semental
            80,//ID Semental
            80,//Status Gestacional
            80//Aborto
        };

        tabla.tamañoColumna(tamaños);

        return tabla;
    }

    public static ArrayList cargarEidsTodos() {

        ArrayList array = new ArrayList();

        manejadorBD.consulta(""
                + "SELECT   arete_electronico "
                + "FROM     animal, corral_animal   \n"
                + "WHERE    animal.id_animal    =   corral_animal.id_animal \n"
                + "AND      corral_animal.id_rancho    =   '" + rancho.id_rancho + "'");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }

        return array;
    }

    public static ArrayList cargarEidsStatus(String aStatus) {

        ArrayList array = new ArrayList();

        manejadorBD.consulta(""
                + "SELECT   arete_electronico \n"
                + "FROM     animal, corral_animal \n"
                + "WHERE    animal.id_animal = corral_animal.id_animal \n"
                + "AND      animal.status   =   '" + aStatus + "'\n"
                + "AND      corral_animal.id_rancho    =   '" + rancho.id_rancho + "'");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }

        return array;
    }

    public static ArrayList cargararete_visualsTodos() {

        ArrayList array = new ArrayList();

        manejadorBD.consulta(""
                + "SELECT   arete_visual "
                + "FROM     animal, corral_animal \n"
                + "WHERE    animal.id_animal = corral_animal.id_animal \n"
                + "AND      corral_animal.id_rancho    =   '" + rancho.id_rancho + "'");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }

        return array;
    }

    public static ArrayList cargararete_visuals() {

        ArrayList array = new ArrayList();

        manejadorBD.consulta(""
                + "SELECT   arete_visual "
                + "FROM     animal, corral_animal \n"
                + "WHERE    status = 'A' \n"
                + "AND      animal.id_animal = corral_animal.id_animal \n"
                + "AND      corral_animal.id_rancho    =   '" + rancho.id_rancho + "'");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }

        return array;
    }

    public static ArrayList cargararete_visuals_2() {

        ArrayList array = new ArrayList();

        array.add("");

        manejadorBD.consulta(""
                + "SELECT   arete_visual "
                + "FROM     animal, corral_animal \n"
                + "WHERE    status = 'A' \n"
                + "AND      animal.id_animal = corral_animal.id_animal \n"
                + "AND      corral_animal.id_rancho    =   '" + rancho.id_rancho + "'");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }

        return array;
    }

    public static ArrayList cargararete_visualshembrasSinEmparejar() {

        ArrayList array = new ArrayList();

        array.add("");

        manejadorBD.consulta(""
                + "SELECT   arete_visual                    \n"
                + "FROM     animal  LEFT OUTER JOIN sexo "
                + "         ON animal.id_sexo = sexo.id_sexo, \n"
                + "         corral_animal  \n"
                + "WHERE    status = 'A' \n"
                + "AND	    sexo.descripcion = 'Hembra'\n"
                + "AND   ( COALESCE(animal.id_semental,'0') = '0' \n"
                + "       OR animal.id_semental = '' ) \n"
                + "AND      animal.id_animal = corral_animal.id_animal \n"
                + "AND      animal.es_vientre = 'S' "
                + "AND      corral_animal.id_rancho    =   '" + rancho.id_rancho + "'");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }

        return array;
    }

    public static ArrayList cargararete_visualshembrasEmparejadas() {

        ArrayList array = new ArrayList();

        array.add("");

        manejadorBD.consulta(""
                + "SELECT arete_visual                    \n"
                + "FROM   animal  LEFT OUTER JOIN sexo "
                + "       ON animal.id_sexo = sexo.id_sexo, \n"
                + "       corral_animal  \n"
                + "WHERE  status = 'A' \n"
                + "AND	  sexo.descripcion = 'Hembra'\n"
                + "AND    COALESCE(animal.id_semental,'0') <> '0' \n"
                + "AND    animal.id_animal = corral_animal.id_animal\n"
                + "AND    es_vientre    =   'S'   \n"
                + "AND    corral_animal.id_rancho    =   '" + rancho.id_rancho + "'");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }

        return array;
    }

    public static ArrayList cargArareteVisualStatus(String aStatus) {

        ArrayList array = new ArrayList();

        manejadorBD.consulta(""
                + "SELECT   arete_visual "
                + "FROM     animal, corral_animal \n"
                + "WHERE    status = '" + aStatus + "' \n"
                + "AND      animal.id_animal = corral_animal.id_animal \n"
                + "AND      corral_animal.id_rancho    =   '" + rancho.id_rancho + "'");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }

        return array;
    }

    public static ArrayList cargararete_visualsSementales() {

        ArrayList array = new ArrayList();
        array.add("");
        manejadorBD.consulta(""
                + "SELECT   arete_visual "
                + "FROM     animal, corral_animal \n"
                + "WHERE    status = 'A' "
                + "AND      es_semental =   'S' \n"
                + "AND      animal.id_animal = corral_animal.id_animal \n"
                + "AND      corral_animal.id_rancho    =   '" + rancho.id_rancho + "'");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }

        return array;
    }

    public static ArrayList cargararete_visualsMuertos() {

        ArrayList array = new ArrayList();

        manejadorBD.consulta(""
                + "SELECT   arete_visual "
                + "FROM     animal, corral_animal \n"
                + "WHERE    status = 'M' \n"
                + "AND      animal.id_animal = corral_animal.id_animal \n"
                + "AND      corral_animal.id_rancho    =   '" + rancho.id_rancho + "'");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }

        return array;
    }

    public static String EEid(Integer id_animal) {

        manejadorBD.consulta(
                "SELECT arete_electronico  "
                + "FROM animal, corral_animal \n"
                + "WHERE status =   'A' "
                + "AND id_animal  =   '" + id_animal + "' \n"
                + "AND      animal.id_animal = corral_animal.id_animal \n"
                + "AND      corral_animal.id_rancho    =   '" + rancho.id_rancho + "'");

        if (manejadorBD.getColumnCount() > 0) {

            return manejadorBD.getValorString(0, 0);
        }
        return "";
    }

    public static String TagEidAnimal(Integer id_animal) {

        manejadorBD.consulta(""
                + "SELECT   arete_electronico "
                + "FROM     animal, corral_animal \n"
                + "WHERE    id_animal  =   '" + id_animal + "' "
                + "AND      status = 'A' \n"
                + "AND      animal.id_animal = corral_animal.id_animal \n"
                + "AND      corral_animal.id_rancho    =   '" + rancho.id_rancho + "'");

        if (manejadorBD.getColumnCount() > 0) {

            return manejadorBD.getValorString(0, 0);
        }

        return "";
    }

    public static Integer idAnimalAreteElectronico(String AAreteElectronico) {

        manejadorBD.consulta(""
                + "SELECT id_animal "
                + "FROM animal, corral_animal \n"
                + "WHERE status =   'A' "
                + "AND arete_electronico  =   '" + AAreteElectronico + "' \n"
                + "AND      animal.id_animal = corral_animal.id_animal \n"
                + "AND      corral_animal.id_rancho    =   '" + rancho.id_rancho + "'");

        if (manejadorBD.getColumnCount() > 0) {

            return manejadorBD.getValorInt(0, 0);
        }

        return 0;
    }

    public static String areteSiniiga(Integer id_animal) {

        manejadorBD.consulta(""
                + "SELECT   arete_siniiga  "
                + "FROM     animal, corral_animal \n"
                + "WHERE    status =   'A' "
                + "AND      id_animal  =   '" + id_animal + "' \n"
                + "AND      animal.id_animal = corral_animal.id_animal \n"
                + "AND      corral_animal.id_rancho    =   '" + rancho.id_rancho + "'");

        if (manejadorBD.getColumnCount() > 0) {

            //    System.out.println("arete_siniiga " + manejadorBD.getValorString(0, 0));
            return manejadorBD.getValorString(0, 0);
        }
        return "";
    }

    private boolean crearProveedor() {

        if (proveedor.id_proveedor.equals("") && !proveedor.descripcion.equals("")) {

            manejadorBD.parametrosSP = new ParametrosSP();
            manejadorBD.parametrosSP.agregarParametro(proveedor.descripcion, "varProveedor", "STRING", "IN");

            if (manejadorBD.ejecutarSP("{ call agregarProveedor(?) }") == 0) {
                proveedor.cargarPorDescripcion(proveedor.descripcion);
                return true;
            }
            return false;
        }
        proveedor.cargarPorDescripcion(proveedor.descripcion);
        return true;
    }

    private boolean crearRaza() {

        if (raza.id_raza.equals("") && !raza.descripcion.equals("")) {

            manejadorBD.parametrosSP = new ParametrosSP();
            manejadorBD.parametrosSP.agregarParametro(raza.descripcion, "varRaza", "STRING", "IN");

            if (manejadorBD.ejecutarSP("{ call agregarRaza(?) }") == 0) {
                raza.cargarPorDescripcion(raza.descripcion);
                return true;
            }
            return false;
        }
        raza.cargarPorDescripcion(raza.descripcion);
        return true;
    }

    public boolean grabar() {

        if (!crearProveedor()) {

            return false;
        }

        if (!crearRaza()) {

            return false;
        }

        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(rancho.id_rancho, "varIdRancho", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(corral.id_corral, "varIdCorral", "STRING", "IN");
        // manejadorBD.parametrosSP.agregarParametro(id_animal, "varIdAnimal", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(proveedor.id_proveedor, "varIdProveedor", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(fecha_compra), "varFechaCompra", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(compra, "varCompra", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(numero_lote, "varNumeroLote", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(peso_compra.toString(), "varPesoCompra", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(sexo.id_sexo, "varIdSexo", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(fecha_ingreso), "varFechaIngreso", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(arete_visual, "varAreteVisual", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(arete_electronico, "varAreteElectronico", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(arete_siniiga, "varAreteSiniiga", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(arete_campaña, "varAreteCampaña", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(peso_actual.toString(), "varPesoActual", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(temperatura.toString(), "varTemperatura", "DOUBLE", "IN");

        manejadorBD.parametrosSP.agregarParametro(es_semental, "varEsSemental", "STRING", "IN");

        if (semental != null) {
            manejadorBD.parametrosSP.agregarParametro(semental.id_animal, "varIdSemental", "STRING", "IN");
        } else {
            manejadorBD.parametrosSP.agregarParametro("0", "varIdSemental", "STRING", "IN");
        }
        manejadorBD.parametrosSP.agregarParametro(raza.id_raza, "varIdRaza", "STRING", "IN");

        manejadorBD.parametrosSP.agregarParametro("A", "varStatus", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro("", "varIdCria", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(es_vientre, "varEsVientre", "STRING", "IN");

        if (manejadorBD.ejecutarSP("{ call agregarAnimal(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }") == 0) {

            return true;
        }
        return false;
    }

    public boolean actualizar() {

        if (!crearProveedor()) {

            return false;
        }

        if (!crearRaza()) {

            return false;
        }

        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(id_animal, "varIdAnimal", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(proveedor.id_proveedor, "varIdProveedor", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(fecha_compra), "varFechaCompra", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(compra, "varCompra", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(numero_lote, "varNumeroLote", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(peso_compra.toString(), "varPesoCompra", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(sexo.id_sexo, "varIdSexo", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(fecha_ingreso), "varFechaIngreso", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(arete_visual, "varAreteVisual", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(arete_electronico, "varAreteElectronico", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(arete_siniiga, "varAreteSiniiga", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(arete_campaña, "varAreteCampaña", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(peso_actual.toString(), "varPesoActual", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(temperatura.toString(), "varTemperatura", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(es_semental, "varEsSemental", "STRING", "IN");
        if (semental != null) {
            manejadorBD.parametrosSP.agregarParametro(semental.id_animal, "varIdSemental", "STRING", "IN");
        } else {
            manejadorBD.parametrosSP.agregarParametro("0", "varIdSemental", "STRING", "IN");
        }
        manejadorBD.parametrosSP.agregarParametro(raza.id_raza, "varIdRaza", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro("A", "varStatus", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(es_vientre, "varEsVientre", "STRING", "IN");
        if (manejadorBD.ejecutarSP("{ call actualizarAnimal(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }") == 0) {
            return true;
        }
        return false;
    }

    public boolean crearRegistroEmpadre() {

        if (!this.sexo.descripcion.equals("Hembra")) {

            JOptionPane.showMessageDialog(null, "el Animal no es Hembra", gs_mensaje, JOptionPane.ERROR_MESSAGE);
            return true;
        }

        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(id_animal, "varIdHembra", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(this.semental.id_animal, "varIdSemental", "STRING", "IN");

        if (manejadorBD.ejecutarSP("{ call agregarRegistroEmpadre(?,?)}") == 0) {

            return true;
        }
        return false;
    }

    public boolean agregarAborto(Date fecha) {

        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(id_animal, "varIdHembra", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(fecha), "varFecha", "STRING", "IN");

        if (manejadorBD.ejecutarSP("{ call agregarAborto(?,?)}") == 0) {

            return true;
        }
        return false;
    }

    public boolean agregarStatusGestacional(String id_registro_empadre, String status, Date fechaChequeda, TipoParto tipo_parto) {

        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(id_registro_empadre, "varIdRegistroEmpadre", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(status, "varStatus", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(fechaChequeda), "varFechaChequeo", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(tipo_parto.descripcion, "varIdTipoParto", "STRING", "IN");

        if (manejadorBD.ejecutarSP("{ call agregarStatusGestacion(?,?,?,?)}") == 0) {
            return true;
        } else {
            return false;
        }

    }

}
