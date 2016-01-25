/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import static gui.Splash.formatoDate;
import static gui.Splash.formatoDateTime;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Developer GAGS
 */
public class Log {

    private static FileWriter archivo;

    public static void Log(String mensaje) {
        
        String fileName = Log.class.getProtectionDomain().getCodeSource().getLocation()+"FeedLot.log";
/*
        try {
            //Pregunta el archivo existe, caso contrario crea uno con el nombre log.txt
            if (new File(fileName).exists() == false) {
                
                archivo = new FileWriter(new File(fileName), false);
            }
            
            archivo = new FileWriter(new File(fileName), true);
            Calendar fechaActual = Calendar.getInstance(); //Para poder utilizar el paquete calendar
            //Empieza a escribir en el archivo
            archivo.write(formatoDateTime.format(fechaActual.getTime()) + ";" + mensaje + "\r\n");
            archivo.close(); //Se cierra el archivo
        } catch (IOException ex) {
            
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
    }   
}