/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import abstractt.TextField;
import giovynet.nativelink.SerialPort;
import giovynet.serial.Baud;
import giovynet.serial.Com;
import giovynet.serial.Parameters;
import gui.Desktop;
import gui.EspecificacionesAnimal;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SR232 extends Thread {

    private boolean seguir;
    public boolean abierto;
    private String puerto;
    private Com com1;
    /*  1   EID
     2   PESO
     3   EID,PESO
     */
    private Integer eidPeso;
    /*
     1 = especificaciones
     2 = Salida
     3 = Medicina
     4 = Traspasos
     5 = Bajas Muerte 
     6 = Parto
     7 = Busqueda
     8 = Sementales
     9 = BusquedaAnimalSelector
     10 = especificaciones
    
     */
    private Integer Espec_Salida;
    private TextField tf_Eid;
    private TextField tf_Peso;
    private String mensaje_error;

    public SR232() {

    }

    public SR232(String puertoStick, Integer aEidPeso, Desktop Aparent, Integer AEspecSalida) {
        setValues(puertoStick, aEidPeso, Aparent, AEspecSalida);
//        puerto = puertoStick;
//        seguir = true;
//        eidPeso = aEidPeso;
//        parent = Aparent;
//        Espec_Salida = AEspecSalida;
    }

    public void setValues(String puertoStick, Integer aEidPeso, Desktop Aparent, Integer AEspecSalida) {
        puerto = puertoStick;
        seguir = true;
        eidPeso = aEidPeso;
        parent = Aparent;
        Espec_Salida = AEspecSalida;
    }

    public static ArrayList cargarPuertosSerie() {

        ArrayList array = new ArrayList();
        SerialPort free = new SerialPort();

        try {

            List<String> portsList = free.getFreeSerialPort();

            for (String sPuerto : portsList) {

                array.add(sPuerto);
            }

        } catch (Exception ex) {

            Logger.getLogger(SR232.class.getName()).log(Level.SEVERE, null, ex);
        }

        return array;
    }

    public boolean puertoDisponible() {

        SerialPort free = new SerialPort();

        try {

            List<String> portsList = free.getFreeSerialPort();

            for (String sPuerto : portsList) {

                System.out.println(sPuerto);

                if (sPuerto.equals(puerto)) {
                    System.out.println("puerto encontrado");
                    return true;
                }
            }

        } catch (Exception ex) {

            Logger.getLogger(SR232.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("puerto no encontrado");
        return false;
    }

    public boolean abrirPuerto(String puerto) {

        Parameters configuracion;

        try {
            configuracion = new Parameters();
            configuracion.setPort(puerto);
            configuracion.setBaudRate(Baud._9600);

            com1 = new Com(configuracion);
            abierto = true;
        } catch (Exception ex) {
            Logger.getLogger(SR232.class.getName()).log(Level.SEVERE, null, ex);
            mensaje_error = ex.getMessage();
            return false;
        }
        return true;
    }

    public void run() {

        setPuerto(puerto);

        if (!seguir) {
            return;
        }

        if (!puertoDisponible()) {

            this.seguir = false;
            cerrarPuerto();
            return;
        }

        String recibido = "";
        int a = 0;
        String caracter = "";
        System.out.println("Iniciando Lectura de Puerto");

        try {

            abierto = true;

            abrirPuerto(getPuerto());

            do {

                while (a != 13 && isSeguir() && abierto) {

                    a = com1.receiveSingleCharAsInteger();

                    if (a != 0 && (a >= 48 && a <= 57) || a == 32 || a == 44 || a == 46) {

                        caracter = ((char) a) + "";
                    } else {
                        caracter = "";
                    }

                    recibido += caracter;
                }

                recibido = recibido.replace("\r", "");

                recibido = recibido.trim();

                System.out.println("recibido: " + recibido);
                    
                //                if (Espec_Salida == 1 && this.parent.administracionGrupos != null) {
                //
                //                    this.parent.administracionGrupos.status.setText(" " + recibido);
                //                }
                //Si el primer caracter es una coma
                if (recibido.indexOf(',') == 0) {
                    //quitar el primer caracter
                    recibido = recibido.substring(1);
                }

                //si no tiene coma
                if (recibido.indexOf(',') <= 1) {
                    //si no tiene punto
                    if (recibido.indexOf('.') == -1) {
                        /*
                         SOLO EID
                         recibido    =   9999900000123
                         */
                        eidPeso = 1;
                    } else {
                        /*
                         SOLO PESO
                         recibido   = 123.5                           
                         */
                        eidPeso = 2;
                    }
                } else {
                    /*
                     EID y PESO
                     recibido    =   9999900000123,123.5
                     */
                    eidPeso = 3;
                }

                switch (eidPeso) {
                    case 1:

                        agregar_EID(recibido);
                        break;

                    case 2:
                        agregar_PESO(recibido);
                        break;

                    case 3:
                        agregar_EIDPESO(recibido);
                        break;
                }

                /*
                 etiqueta.setText(recibido);
                 etiqueta.setCaretPosition(1);
                 */
                caracter = "";
                recibido = "";
                a = 0;
                //   this.seguir = false;
            } while (isSeguir());

            cerrarPuerto();

        } catch (Exception e1) {

            e1.printStackTrace();
        }
    }

    public void cerrarPuerto() {

        if (abierto) {
            try {

                System.out.println("cerrando Puerto " + com1.getPort());
                com1.close();
                abierto = false;
                seguir = false;

            } catch (Exception ex) {

                Logger.getLogger(SR232.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String validandoPuerto(String Scom, String envio_com, Integer tiempoEspera) {

        String recibido = "";
        int a = 0;
        String caracter = "";
        if (!abrirPuerto(Scom)) {

        }
        Integer conteo = 0;

        System.out.println("Puerto " + Scom);
        try {
            System.out.println("Enviando al puerto " + envio_com);
            com1.sendArrayChar(envio_com.toCharArray());
            //recibido = "919901105,152.71";
            while (a != 13 && conteo < tiempoEspera * 10) {

                a = com1.receiveSingleCharAsInteger();

                if (a != 0) {

                    caracter = ((char) a) + "";
                }

                recibido += caracter;

                conteo++;
            }
        } catch (Exception ex) {
            Logger.getLogger(SR232.class.getName()).log(Level.SEVERE, null, ex);
        }
        recibido = recibido.replace("\r", "");
        recibido = recibido.replace("[", "");
        recibido = recibido.replace("]", "");
        System.out.println("recibido: " + recibido);

        cerrarPuerto();
        return recibido;
    }

    public void agregar_EIDPESO(String recibido) {

        String eid, peso;

        StringTokenizer st = new StringTokenizer(recibido, ",");

        eid = st.nextToken();
        peso = st.nextToken();

        agregar_PESO(peso);
        agregar_EID(eid);
    }

    public void agregar_EID(String recibido) {

        if (!recibido.equals("")) {

            switch (this.Espec_Salida) {
                case 1:

                    if (parent.especificacionesAnimal == null) {

                        parent.especificacionesAnimal = new EspecificacionesAnimal(parent);
                    }
                    this.parent.especificacionesAnimal.setEid(recibido);

                    break;
                case 2:

                    this.parent.salidaGanadoGrupo.setEid(recibido);
                    break;
                case 3:

                    this.parent.medicinasAnimalGrupo.setEid(recibido);
                    break;
                case 4:

                    this.parent.traslados.setEid(recibido);
                    break;
                case 5:

                    this.parent.bajasMuerte.setEid(recibido);
                    break;
                case 6:

                    this.parent.partos.setEid(recibido);
                    break;
                case 7:

                    this.parent.busquedaAnimal.setEid(recibido);
                    break;
                case 8:

                    this.parent.sementales.setEid(recibido);
                    break;
                case 9:

                    this.parent.busquedaAnimalSelector.setEid(recibido);
                    break;
                case 10:

                    if (parent.especificacionesAnimal == null) {

                        parent.especificacionesAnimal = new EspecificacionesAnimal(parent);
                    }
                    this.parent.especificacionesAnimal.setEid(recibido);

                    break;
            }
        }
    }

    public void agregar_PESO(String recibido) {

        switch (this.Espec_Salida) {
            case 1:
                if (parent.especificacionesAnimal == null) {

                    parent.especificacionesAnimal = new EspecificacionesAnimal(parent);
                }
                this.parent.especificacionesAnimal.setPeso(Double.parseDouble(recibido));
                break;
            case 2:
                this.tf_Peso.setText(recibido);
                break;
        }
    }

    /**
     * @return the seguir
     */
    public boolean isSeguir() {

        return seguir;
    }

    /**
     * @param seguir the seguir to set
     */
    public void setSeguir(boolean seguir) {

        this.seguir = seguir;
    }

    /**
     * @return the etiqueta
     */
    public TextField getEID() {

        return tf_Eid;
    }

    /**
     * @param etiqueta the etiqueta to set
     */
    public void setEID(TextField etiqueta) {

        this.tf_Eid = etiqueta;
    }

    /**
     * @return the puerto
     */
    public String getPuerto() {

        return puerto;
    }

    /**
     * @param puerto the puerto to set
     */
    public void setPuerto(String puerto) {

        this.puerto = puerto;
    }

    /**
     * @return the tf_Peso
     */
    public TextField getTf_Peso() {

        return tf_Peso;
    }

    /**
     * @param tf_Peso the tf_Peso to set
     */
    public void setTf_Peso(TextField tf_Peso) {

        this.tf_Peso = tf_Peso;
    }

    Desktop parent;
}
