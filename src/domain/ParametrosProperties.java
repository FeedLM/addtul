/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import static domain.Cifrado.nombreArchivo;
import static domain.Seguridad.setFecha_ultima_corrida;
import gui.Login;
import static gui.Splash.formatoDateTime;
import static gui.Splash.setMensaje;
import static gui.Splash.setPorcentaje;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Home
 */
public class ParametrosProperties {

    public static String licencia;
    public static String disco_duro;
    public static Date fecha_instalacion;
    public static Date fecha_ultima_corrida;
    public static Date fecha_limite;
    public static String database;
    public static boolean muestraSQL;
    /*
     public static Date fecha_actual;
     public static String disco_duro_fisico;
     public static String licencia_nueva;
     */
    //public static Integer dias_vigencia;
    public static String file_properties;
    public static Properties properties;

    static Cifrado cifrado;

    public static boolean cambios = false;

    private static boolean asignarFecha_ultima_corrida = true;

 
    
    public static boolean leerProperties() {

        // Calendar fecha = Calendar.getInstance();
        String Sfecha = "";
        // fecha_actual = fecha.getTime();

        properties = new Properties();

        cifrado = new Cifrado("feedLotManager");

        file_properties = "feedLotManager.properties";

        /**
         * Validar Si existe Archivo DAT
         */
        if(!cifrado.validarExistenciaDAT()){
            return false;
        }

        properties = cifrado.leerProperties();

        setPorcentaje(0);

        licencia = properties.getProperty("licencia");

        disco_duro = properties.getProperty("disco_duro");
        setMensaje("disco duro: " + disco_duro);
        System.out.println("disco_duro: " + disco_duro);

        setPorcentaje(2);

        Sfecha = properties.getProperty("fecha_instalacion");

        if (Sfecha != null && !Sfecha.equals("")) {
            try {

                fecha_instalacion = formatoDateTime.parse(Sfecha);

            } catch (ParseException ex) {

                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println("fecha_instalacion:  [" + Sfecha + "] " + fecha_instalacion);

        setPorcentaje(4);

        Sfecha = properties.getProperty("fecha_ultima_corrida");

        if (Sfecha != null && !Sfecha.equals("")) {
            try {

                fecha_ultima_corrida = formatoDateTime.parse(Sfecha);

            } catch (ParseException ex) {

                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println("fecha_ultima_corrida: [" + Sfecha + "] " + fecha_ultima_corrida);

        setPorcentaje(6);

        Sfecha = properties.getProperty("fecha_limite");

  
        if (Sfecha != null && !Sfecha.equals("")) {
            try {

                fecha_limite = formatoDateTime.parse(Sfecha);

            } catch (ParseException ex) {

                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println("        fecha_limite: [" + Sfecha + "] " + fecha_limite);

        setPorcentaje(8);

        database = properties.getProperty("database");
        setMensaje("DataBase: " + database);
        System.out.println("database: " + database);

        setPorcentaje(10);
        String SmuestraSQL;
        
        SmuestraSQL = properties.getProperty("muestraSQL");
      
        if (SmuestraSQL == null ){
            
            SmuestraSQL = "N";
        }
        
        if (SmuestraSQL.equals("S")) {

            muestraSQL = true;
        } else {

            muestraSQL = false;
        }

        System.out.println("muesta sql: " + muestraSQL);

        setPorcentaje(12);
        
        return true;
    }

    public static void aplicar_cambios_properties() {

        FileOutputStream fos;

        try {

            File file = new File(file_properties);
            fos = new FileOutputStream(file);

            properties.store(fos, null);

            properties.clear();
            fos.close();

        } catch (FileNotFoundException ex) {

            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {

            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    public static void guardarProperties() {

        String letras_decodificadas = "", letras_codificadas = "";
        File file_properties;

        if (isAsignarFecha_ultima_corrida()) {

            setFecha_ultima_corrida();
        }

        aplicar_cambios_properties();

        //Leer properties en  letras_decodificadas
        letras_decodificadas = Cifrado.leerPropertiesString();

        //Codificar letras_decodificadas en letras_codificadas
        letras_codificadas = Cifrado.codifica(letras_decodificadas);

        //Guardar en dat
        Cifrado.guardarDat(letras_codificadas);

        borrarProperties();
    }

    public static void borrarProperties() {

        File file_properties;

        //Eliminar Properties
        file_properties = new File(nombreArchivo + ".properties");

        if (file_properties.delete()) {

            System.out.println("El properties ha sido borrado satisfactoriamente");
        } else {

            System.out.println("El properties no puede ser borrado");
        }
    }

    /**
     * @return the asignarFecha_ultima_corrida
     */
    public static boolean isAsignarFecha_ultima_corrida() {
        return asignarFecha_ultima_corrida;
    }

    /**
     * @param asignarFecha_ultima_corrida the asignarFecha_ultima_corrida to set
     */
    public static void setAsignarFecha_ultima_corrida(boolean asignarFechaUltimaCorrida) {
        asignarFecha_ultima_corrida = asignarFechaUltimaCorrida;
    }

    public static String getLicencia() {
        
        if(licencia == null){
            Seguridad.licencia();
        }
        
        return licencia;
    }
}
