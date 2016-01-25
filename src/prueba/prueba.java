/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba;

import domain.Cifrado;
import static domain.Cifrado.decodifica;
import static domain.Cifrado.leerDat;
import domain.FormatoNumero;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

/**
 *
 * @author Developer GAGS
 */
public class prueba {

    public static void main(String args[]) throws IOException {

        Cifrado cifrado;

        cifrado = new Cifrado("feedLotManager");

        String secuencia;
        String letras = "disco_duro=A001-1889\n"
                + "database=feedlotmanager\n"
                + "muestraSQL=S\n"
                // + "dias_vigencia=999\n"
                // + "fecha_instalacion=2015-06-07 22:09\n"
                + "fecha_limite=2025-06-07 14:17\n"
                + "fecha_ultima_corrida=2015-06-15 11:46";
        String letras_codificadas;
        String letras_decodificadas;

        System.out.println("1.- " + letras);

        letras_codificadas = cifrado.codifica(letras);
        //  System.out.println("2.-");
        System.out.println(letras_codificadas);

        try {
            
            ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream("feedLotManager.dat"));
            file.writeObject(letras_codificadas);
            file.close();
        } catch (Exception e) {
            
            e.printStackTrace();
        }

        letras_codificadas = leerDat();

        System.out.println("/**********/\n" + letras_codificadas + "\n/********************/");
        
        letras_decodificadas = decodifica(letras_codificadas);

        System.out.println("/**********/\n" + letras_decodificadas + "\n/********************/");

        /*
         letras_codificadas = "";

         try {
         //Stream para leer archivo
         ObjectInputStream file = new ObjectInputStream(new FileInputStream("feedLotManager.dat"));
         //Se lee el objeto de archivo y este debe convertirse al tipo de clase que corresponde
         letras_codificadas = (String) file.readObject();
         //se cierra archivo
         file.close();

         } catch (ClassNotFoundException ex) {
         System.out.println(ex);
         } catch (IOException ex) {
         System.out.println(ex);
         }

         System.out.println(letras_codificadas);
         //     letras_decodificadas = cifrado.decodifica(letras_codificadas);
         //    System.out.println(letras_decodificadas);

         FileWriter fichero = null;
         PrintWriter pw = null;
         try {
         fichero = new FileWriter("feedLotManager.properties");
         pw = new PrintWriter(fichero);

         //          pw.println(letras_decodificadas);

         } catch (Exception e) {
         e.printStackTrace();
         } finally {
         try {
         // Nuevamente aprovechamos el finally para 
         // asegurarnos que se cierra el fichero.
         if (null != fichero) {
         fichero.close();
         }
         } catch (Exception e2) {
         e2.printStackTrace();
         }
         }

         File file_properties = new File("feedLotManager.properties");

         if (file_properties.delete()) {
         System.out.println("El fichero ha sido borrado satisfactoriamente");
         } else {
         System.out.println("El fichero no puede ser borrado");
         }
         */
    }
}
