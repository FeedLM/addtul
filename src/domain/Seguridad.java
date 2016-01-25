/*
 * Seguridad.java
 *
 * Created on 30 de abril de 2008, 07:05 AM
 *
 */
package domain;

import static domain.ParametrosProperties.disco_duro;
import static domain.ParametrosProperties.fecha_instalacion;
import static domain.ParametrosProperties.fecha_limite;
import static domain.ParametrosProperties.fecha_ultima_corrida;
import static domain.ParametrosProperties.file_properties;
import static domain.ParametrosProperties.guardarProperties;
import static domain.ParametrosProperties.licencia;
import static domain.ParametrosProperties.properties;
import static domain.ParametrosProperties.cambios;
import gui.Login;
import static gui.Login.gs_mensaje;
import gui.PasswordDialogo;
import static gui.Login.usuario_activo;
import static gui.Splash.formatoDateTime;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Gilberto Adan Gonzalez Silva
 */
public class Seguridad extends abstractt.ClaseAbstracta {

    /**
     * Creates a new instance of Seguridad
     */
    public Seguridad() {

        //   passwordDialogo = new PasswordDialogo(null, true);
    }

    public static void licencia() {

        if (licencia == null || licencia.equals("")) {

            UUID uuid = UUID.randomUUID();

            licencia = uuid.toString();

            System.out.println("Licencia NVA " + licencia);

            properties.setProperty("licencia", licencia);
        }
    }

    public static void fecha_instalacion() {

        if (fecha_instalacion == null) {

            Calendar fecha = Calendar.getInstance();
            fecha_instalacion = fecha.getTime();
            properties.setProperty("fecha_instalacion", formatoDateTime.format(fecha_instalacion));
        }
    }

    public static void fecha_limite() {

        if (fecha_limite == null) {

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fecha_instalacion); // Configuramos la fecha que se recibe
            calendar.add(Calendar.DAY_OF_YEAR, 30);  // numero de días a añadir, o restar en caso de días<0
            fecha_limite = calendar.getTime();
            properties.setProperty("fecha_limite", formatoDateTime.format(fecha_limite));
        }
    }

    public static void fecha_ultima_corrida() {

        if (fecha_ultima_corrida == null) {

            setFecha_ultima_corrida();
        }
    }

    public static void setFecha_ultima_corrida() {

        Calendar fecha = Calendar.getInstance();
        fecha_ultima_corrida = fecha.getTime();
        properties.setProperty("fecha_ultima_corrida", formatoDateTime.format(fecha_ultima_corrida));
    }

    public static boolean validar_fecha_ultima_corrida_periodo_prueba() {

        return fecha_ultima_corrida.compareTo(fecha_instalacion) >= 0 && fecha_ultima_corrida.compareTo(fecha_limite) <= 0;
        /*
        if (fecha_ultima_corrida.after(fecha_instalacion) && fecha_ultima_corrida.before(fecha_limite)) {

            return true;
        }

        return false;
        */
    }

    public static boolean valida_vigencia() {

        Date fecha_actual;
        Calendar fecha = Calendar.getInstance();
        String Sfecha;
        fecha_actual = fecha.getTime();

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

        cambios = true;

        //  }
        System.out.println("        fecha_actual: " + formatoDateTime.format(fecha_actual));
        //  System.out.println("   fecha_instalacion vpp: " + formatoDateTime.format(fecha_instalacion));
        System.out.println("    fecha_limite vpp: " + formatoDateTime.format(fecha_limite));
        //System.out.println("fecha_ultima_corrida vpp: " + formatoDateTime.format(fecha_ultima_corrida));

        /*
         if ( cambios ) {

         
         }
         */
        guardarProperties();

        if (fecha_actual.before(fecha_limite)) {
            System.out.println("Esta en vigencia");
            return true;
        }

        System.out.println("Se Termino la vigencia");
        return false;
    }

    public static boolean validaUltimaCorrida() {

        Date fechaActual;
        Calendar fecha = Calendar.getInstance();
        fechaActual = fecha.getTime();

        /**/
        /*
        Date fechaFinal = fecha.getTime();;
        try {
            fechaFinal = formatoDateTime.parse("2015-09-09 23:59");
        } catch (ParseException ex) {
            //Logger.getLogger(Seguridad.class.getName()).log(Level.SEVERE, null, ex);
        }
  
        if(fechaActual.after(fecha_ultima_corrida)){//fechaFinal)){
            return false;
        }
        return true;
*/          
        /**/

        if (fechaActual.after(fecha_ultima_corrida) || fechaActual.equals(fecha_ultima_corrida)) {

            System.out.println("Fecha actual correcta");
            return true;
        }

        System.out.println("fecha actual incorrecta");
        return false;

    }

    public static boolean pideContraseña() {

        String password;

        if (passwordDialogo != null) {

            passwordDialogo.dispose();
        }

        passwordDialogo = new PasswordDialogo(null, true);
        passwordDialogo.setVisible(true);

        if (passwordDialogo.opcion == 1) {

            password = passwordDialogo.getpassword();

            if (usuario_activo.getPassword().equals(password)) {
                return true;
            }

            JOptionPane.showMessageDialog(null, "Contraseña Invalida", gs_mensaje, JOptionPane.ERROR_MESSAGE);

            return false;
        } else {
            return false;
        }
    }

    public static boolean equipoValido() {

        String idDisco = obtenerIdDisco();

        System.out.println(idDisco + " id_disco en properties: " + disco_duro);

        /**
         * Si no Existe Disco Duro en properties se agrega el de la maquina
         */
        if (disco_duro == null){
            disco_duro = "";
        }
        
        if (disco_duro.equals("")) {

            disco_duro = idDisco;
            properties.setProperty("disco_duro", disco_duro);
        }

        if (!idDisco.equals(disco_duro)) {

            return false;
        }

        return true;
    }

    public static void agregarDiscoDuroProperties() {
        disco_duro = obtenerIdDisco();
        properties.setProperty("disco_duro", disco_duro);
    }

    public static String obtenerIdDisco() {

        Process proc = null;

        try {
            //proc = Runtime.getRuntime().exec(cmd);
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
        // System.out.println(id);
        return id;
    }

    public int obtenerIdEquipo() {

        String idDisco = this.obtenerIdDisco();
        String consulta = "SELECT idEquipo FROM Equipo WHERE numeroSerieHDD = '" + idDisco + "'";
        //System.out.println(consulta);
        mbd.consulta(consulta);
        return mbd.getValorInt(0, 0);
    }

    public int obtenerIdCaja() {

        String idDisco = obtenerIdDisco();
        String consulta = "SELECT idCaja FROM Equipo WHERE numeroSerieHDD = '" + idDisco + "'";
        mbd.consulta(consulta);
        return mbd.getValorInt(0, 0);
    }

    /**
     * Devuelve el Id del Usuario Activo
     */
    public int obtenerIdUsuario() {

        int id = 0;
        String consulta = "SELECT idUsuario FROM Usuario WHERE idEquipo = " + obtenerIdEquipo();
        mbd.consulta(consulta);
        id = Integer.parseInt(mbd.getValueAt(0, 0).toString());
        return id;
    }

    /**
     * Devuelve el Id del Usuario Activo
     */
    public String obtenerNombreUsuario() {

        String nombre = "";
        String consulta = "SELECT nombre FROM Usuario WHERE idEquipo = " + obtenerIdEquipo();
        mbd.consulta(consulta);
        nombre = mbd.getValorString(0, 0);
        return nombre;
    }

    /**
     *
     */
    public int obtenerPerfilUsuario() {

        int idPerfil = 0;
        String consulta = "SELECT idPerfil FROM Usuario WHERE idEquipo = " + obtenerIdEquipo();
        //System.out.println(consulta);
        mbd.consulta(consulta);
        idPerfil = Integer.parseInt(mbd.getValueAt(0, 0).toString());
        return idPerfil;
    }
    private static PasswordDialogo passwordDialogo;
}