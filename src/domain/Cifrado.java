/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Developer GAGS
 */
public class Cifrado {

    /*
     nombreArchivo
     dat y properties
     */
    static String nombreArchivo;

    /**
     *
     * @param archivo
     */
    public Cifrado(String archivo) {

        nombreArchivo = archivo;
    }

    /**
     *
     * @return
     */
    public Properties leerProperties() {

        String letras_codificadas = "", letras_decodificadas = "";
        Properties prop;
        FileInputStream file;

        prop = new Properties();
       
        letras_codificadas = leerDat();

        
        
        if (!letras_codificadas.equals("")) {

            letras_decodificadas = decodifica(letras_codificadas);
        }

        crearArchivoProperties(letras_decodificadas);

//        System.out.println("Properties:/************************/\n"
        //              + letras_decodificadas
        //            + "\n/************************/");
        try {
            //leer properties
            file = new FileInputStream(nombreArchivo + ".properties");
            prop.load(file);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Cifrado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Cifrado.class.getName()).log(Level.SEVERE, null, ex);
        }

        return prop;
    }

    /**
     *
     * @return retorna el id_del disco duro donde se esta ejecutando el jar
     * Windows
     */
    public String obtenerIdDisco() {

        String[] cmd = new String[3];
        cmd[0] = "cmd.exe";
        cmd[1] = "/c";
        cmd[2] = "vol.bat";
        Process proc = null;

        try {

            proc = Runtime.getRuntime().exec("cmd /c vol");
        } catch (IOException ex) {

            ex.printStackTrace();
        }

        InputStreamReader isr = new InputStreamReader(proc.getInputStream());
        BufferedReader br = new BufferedReader(isr);
        String text = "";
        String line;

        try {

            while ((line = br.readLine()) != null) {

                text = text + line + "\n";
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        StringTokenizer stt = new StringTokenizer(text);

        int to = stt.countTokens();

        for (int i = 0; i < to - 1; i++) {
            stt.nextToken();
        }

        String id = stt.nextToken();

        return id;
    }

    public static String leerPropertiesString() {

        String letras_decodificadas = "";

        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File(nombreArchivo + ".properties");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null) {
                letras_decodificadas += linea + "\n";
            }
            //System.out.println(linea);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {

                    fr.close();
                }
            } catch (Exception e2) {

                e2.printStackTrace();
            }
        }

        return letras_decodificadas;
    }

    private void crearArchivoProperties(String letras_decodificadas) {

        FileWriter fichero = null;
        PrintWriter pw = null;

        try {

            fichero = new FileWriter(nombreArchivo + ".properties");
            pw = new PrintWriter(fichero);
            pw.println(letras_decodificadas);

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
    }

    public static String leerDat() {

        String letras_codificadas = "";
 
        //validarExistenciaDAT();

        try {                                    
            //Stream para leer archivo
            ObjectInputStream file = new ObjectInputStream(new FileInputStream(nombreArchivo + ".dat"));
             
            //Se lee el objeto de archivo y este debe convertirse al tipo de clase que corresponde
            letras_codificadas = (String) file.readObject();
            //se cierra archivo
            file.close();

        } catch (ClassNotFoundException ex) {
            System.out.println("1.- "+ex);
        } catch (IOException ex) {
            System.out.println("2.- "+ex);
        }

        return letras_codificadas;
    }

    public static boolean validarExistenciaDAT() {

        File fichero = new File(nombreArchivo + ".dat");

        if (fichero.exists()) {
            return true;
        } else {
/*
            try {
                //Crear Arhivo DAT Vacio
                System.out.println("Creando DAT");
                BufferedWriter bw;
                bw = new BufferedWriter(new FileWriter(nombreArchivo + ".dat"));
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(Cifrado.class.getName()).log(Level.SEVERE, null, ex);
            }
            */
            return false;
        }

    }

    public static void guardarDat(String letras_codificadas) {

        try {

            ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(nombreArchivo + ".dat"));
            file.writeObject(letras_codificadas);
            file.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static String decodifica(String Cadena) {

        String Binario;
        String[][] matriz;
        // Binario = convierteTextoABinario(Cadena);
        Binario = Cadena;

        matriz = convierteMatriz(Binario);

        Binario = leerMatriz(matriz);

        Cadena = convierteBinarioATexto(Binario);

        return Cadena;
    }

    /**
     *
     * @param Cadena
     * @return
     */
    public static String codifica(String Cadena) {

        String matriz[][];
        String Binario;
        String Cadena_codificada;

        Binario = convierteTextoABinario(Cadena);

        //   System.out.println("codifica binario " + Binario);
        matriz = rellenarMatriz(Binario);
        /**/
        // System.out.println("Binario a Matriz");
        // imprimirMatriz(matriz);
        /**/
        Binario = convierteMatriz(matriz);

        // System.out.println("Binario convertido " + Binario);
        // Cadena_codificada = convierteBinarioATexto(Binario);
        Cadena_codificada = Binario;

        // System.out.println("Cadena Codificada: " + Cadena_codificada);
        return Cadena_codificada;
    }

    /**
     *
     * @param Binario
     * @return String[][]
     */
    private static String[][] rellenarMatriz(String Binario) {

        Integer tamaño_total_real, tamaño_total_esperado;
        Integer l;
        String matriz[][];

        String cadena;
        String caracter;
        tamaño_total_real = Binario.length();
        l = ((Double) Math.floor(Math.sqrt(tamaño_total_real) + 0.99)).intValue();
        tamaño_total_esperado = l * l;
        Binario = rellenar_final(Binario, tamaño_total_esperado, "0");

        matriz = new String[l][l];

        for (int i = 0; i < matriz.length; i++) {

            cadena = Binario.substring(0, (int) l);

            Binario = Binario.substring((int) (l));

            for (int j = 0; j < matriz.length; j++) {

                caracter = cadena.substring(0, 1);
                cadena = cadena.substring(1);
                matriz[i][j] = caracter;
            }
        }

        return matriz;
    }

    private static String[][] convierteMatriz(String Binario) {

        int j, i;
        String[][] matriz;
        int fila, columna;
        Integer tamaño_total_real, tamaño_total_esperado, l;
        String caracter;

        tamaño_total_real = Binario.length();

        //System.out.println("tamaño real: "+tamaño_total_real);
        l = ((Double) Math.floor(Math.sqrt(tamaño_total_real) + 0.99)).intValue();

        //System.out.println("tamaño por lado: "+l);
        tamaño_total_esperado = l * l;

        //System.out.println("tamaño esperado: "+tamaño_total_esperado);/
        Binario = rellenar_final(Binario, tamaño_total_esperado, "0");
        matriz = new String[l][l];

        for (i = 0; i < matriz.length; i++) {

            fila = i;
            columna = 0;

            do {

                caracter = Binario.substring(0, 1);
                Binario = Binario.substring(1, Binario.length());
                matriz[fila][columna] = caracter;
                columna++;
                fila--;
            } while (fila >= 0);
        }
        for (j = 1; j < matriz.length; j++) {

            fila = matriz.length - 1;
            columna = j;

            do {

                caracter = Binario.substring(0, 1);

                Binario = Binario.substring(1, Binario.length());

                matriz[fila][columna] = caracter;
                columna++;
                fila--;
            } while (fila > 0 && columna < matriz.length);
        }

        return matriz;
    }

    private static String convierteBinarioATexto(String binario) {

        String secuencia;
        String cadena = "";

        do {
            secuencia = binario.substring(0, 8);

            if (secuencia.length() > 0 && !secuencia.equals("00000000")) {

                cadena += String.valueOf((char) Integer.parseInt(secuencia, 2));
            }

            binario = binario.substring(8);

        } while (binario.length() > 7);

        return cadena;
    }

    private static String leerMatriz(String[][] matriz) {

        long l;
        l = matriz.length;
        String binario = "";

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {

                binario += matriz[i][j];
            }
        }
        return binario;
    }

    private static String convierteTextoABinario(String Cadenas) {
        String n;

        int x = 0;
        String binario = "";

        for (int i = 0; i < Cadenas.length(); i++) {
            x = Cadenas.charAt(i);
            n = Integer.toBinaryString(x).toString();

            binario += String.format("%08d", Integer.parseInt(n));
        }

        return binario;
    }

    private static String rellenar_final(String cadena, Integer n, String caracter) {

        long caracteres;

        caracteres = n - cadena.length();

        for (int i = 0; i < caracteres; i++) {

            cadena += caracter;
        }

        return cadena;
    }

    private static String convierteMatriz(String[][] matriz) {

        int j, i;
        int fila, columna;
        String Binario = "";

        for (i = 0; i < matriz.length; i++) {

            fila = i;
            columna = 0;

            do {

                Binario += matriz[fila][columna];
                columna++;
                fila--;
            } while (fila >= 0);
        }
        for (j = 1; j < matriz.length; j++) {

            fila = matriz.length - 1;
            columna = j;

            do {

                Binario += matriz[fila][columna];
                columna++;
                fila--;
            } while (fila > 0 && columna < matriz.length);
        }

        return Binario;
    }

    private static void imprimirMatriz(String[][] matriz) {

        for (int i = 0; i < matriz.length; i++) {
            //System.out.print("- ");
            for (int j = 0; j < matriz.length; j++) {

                System.out.print(matriz[i][j]);

            }
            System.out.println("");
        }
    }
}
