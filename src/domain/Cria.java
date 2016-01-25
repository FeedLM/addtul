/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import abstractt.Table;
import static gui.Splash.formatoDate;
import static gui.Splash.formatoDateTime;
import static gui.Desktop.rancho;
import static gui.Desktop.manejadorBD;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Developer GAGS
 */
public class Cria {

    // public Integer id_rancho;
    public Animal madre;
    public String id_cria;
    public String arete;
    public Sexo sexo;//SEXO
    public Date fecha_nacimiento;
    public Raza raza;
    public String status;
    public Double peso;
    public TipoParto tipo_parto;
    public Animal animal;

    public Cria() {

        madre = new Animal();
        id_cria = "";
        arete = "";
        sexo = new Sexo();
        fecha_nacimiento = new Date();
        raza = new Raza();
        tipo_parto = new TipoParto();
        animal = new Animal();
    }

    public void cargarPorIdCria(Integer AidCria) {

        manejadorBD.consulta(""
                + "SELECT   id_madre,           id_cria, \n"
                + "         arete,              id_sexo,\n"
                + "         fecha_nacimiento,   id_raza,\n"
                + "         status,             peso,   \n"
                + "         id_tipo_parto,      id_animal\n"
                + "FROM     cria, animal \n"
                + "WHERE    cria.id_animal = animal.id_animal\n"
                + "AND      cria.id_rancho = '" + rancho.id_rancho + "' \n"
                + "AND      cria.id_cria   = '" + AidCria + "'");

        if (manejadorBD.getRowCount() > 0) {

            asignarValores();
        }
    }

    public void asignarValores() {

        String id_sexo;
        String id_raza;
        String id_madre;
        String id_tipo_parto;
        String id_animal;

        id_madre = manejadorBD.getValorString(0, 0);
        id_cria = manejadorBD.getValorString(0, 1);
        arete = manejadorBD.getValorString(0, 2);
        id_sexo = manejadorBD.getValorString(0, 3);

        try {

            fecha_nacimiento = formatoDateTime.parse(manejadorBD.getValorString(0, 4));
        } catch (ParseException ex) {

            Logger.getLogger(Animal.class.getName()).log(Level.SEVERE, null, ex);
        }

        id_raza = manejadorBD.getValorString(0, 5);
        status = manejadorBD.getValorString(0, 6);
        peso = manejadorBD.getValorDouble(0, 7);
        id_tipo_parto = manejadorBD.getValorString(0, 8);
        id_animal = manejadorBD.getValorString(0, 9);

        madre.cargarPorId(id_madre);
        sexo.cargarPorId(id_sexo);
        raza.cargarPorId(id_raza);
        tipo_parto.cargarPorId(id_tipo_parto);
        animal.cargarPorId(id_animal);
    }

    public static Table leerCrias(Table tabla, Animal madre) {

        tabla = crearTablaCrias(tabla);

        manejadorBD.consulta(""
                + "SELECT   id_madre,   id_cria,    arete, \n"
                + "         sexo.descripcion sexo, raza.descripcion raza,\n"
                + "         DATE_FORMAT(fecha_nacimiento, '%Y/%m/%d'),   peso, tipo_parto.descripcion,\n"
                + "         cria.id_animal\n"
                + "FROM     cria LEFT OUTER JOIN tipo_parto ON cria.id_tipo_parto  =   tipo_parto.id_tipo_parto,\n"
                + "         sexo, raza, animal\n"
                + "WHERE    cria.id_animal  = animal.id_animal \n"
                + "AND      cria.id_sexo    = sexo.id_sexo\n"
                + "AND      cria.id_raza    = raza.id_raza \n"
                + "AND      cria.id_rancho  = '" + rancho.id_rancho + "' \n"
                + "AND      cria.id_madre   = '" + madre.id_animal + "' \n"
                + "AND      cria.status     =   'A'");

       // if (manejadorBD.getRowCount() > 0) {

            manejadorBD.asignarTable(tabla);
        //}

        tabla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

        tabla.ocultarcolumna(0);
        tabla.ocultarcolumna(1);
        tabla.ocultarcolumna(8);

        return tabla;
    }

    private static Table crearTablaCrias(Table tabla) {

        if (tabla == null) {

            tabla = new Table();
        }

        String titulos[] = {"Id Madre", "Id Cria", "arete", "Sexo",
            "Raza", "Fecha de Nacimiento", "Peso al Nacer", "Tipo de Parto", "id_animal"};

        tabla.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                titulos));

        tabla.setTitulos(titulos);
        tabla.cambiarTitulos();
        tabla.setFormato(new int[]{
            tabla.letra,
            tabla.letra,
            tabla.letra,
            tabla.letra,
            tabla.fecha,
            tabla.letra,
            tabla.numero_double,
            tabla.letra,
            tabla.letra});

        int[] tamaños = new int[]{
            80,//id Madre
            100,//id_cria
            80,//arete
            120,//sexo
            140,//fecha_de_Nacimiento
            120,//Raza
            140,//peso al nacer
            120,//tipo de parto
            0// id_animal
        };

        tabla.tamañoColumna(tamaños);

        return tabla;
    }

    public boolean grabar() {

        manejadorBD.parametrosSP = new ParametrosSP();
        manejadorBD.parametrosSP.agregarParametro(rancho.id_rancho, "varIdRancho", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(madre.id_animal, "varIdMadre", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(arete, "varArete", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(fecha_nacimiento), "varFechaNacimiento", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(sexo.id_sexo, "varIdSexo", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(raza.id_raza, "varIdRaza", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(peso.toString(), "varPeso", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(tipo_parto.id_tipo_parto, "varIdTipoParto", "STRING", "IN");

        if (manejadorBD.ejecutarSP("{ call agregarCria(?,?,?,?,?,?,?,?) }") == 0) {

            return true;
        } else {

            return false;
        }
    }

    public boolean actualizar() {

        manejadorBD.parametrosSP = new ParametrosSP();
        manejadorBD.parametrosSP.agregarParametro(rancho.id_rancho, "varIdRancho", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(madre.id_animal, "varIdMadre", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_cria, "varIdCria", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(arete, "varArete", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(fecha_nacimiento), "varFechaNacimiento", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(sexo.id_sexo, "varIdSexo", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(raza.id_raza, "varIdRaza", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(peso.toString(), "varPeso", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(tipo_parto.id_tipo_parto, "varIdTipoParto", "STRING", "IN");

        if (manejadorBD.ejecutarSP("{ call actualizarCria(?,?,?,?,?,?,?,?,?) }") == 0) {

            return true;
        } else {

            return false;
        }
    }

    public boolean eliminar() {

        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(rancho.id_rancho, "varIdRancho", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_cria, "varIdCria", "STRING", "IN");

        if (manejadorBD.ejecutarSP("{ call eliminarCria(?,?) }") == 0) {

            return true;
        } else {

            return false;
        }
    }
}
