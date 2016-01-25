/*
 * Fecha.java
 *
 * Created on 8 de mayo de 2008, 12:00 PM
 *
 */
package domain;

import com.toedter.calendar.JDateChooser;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import javax.swing.JComboBox;

/**
 *
 * @author Gilberto Adan Gonz�lez Silva
 */
public class Fecha {

    /**
     * Creates a new instance of Fecha
     */
    public Fecha() {
    }

    /**
     * Devuelve la fecha actual en un String en formato aaaa/mm/dd
     */
    public String obtenerFechaActual() {

        return obtenerFecha3(new JDateChooser(new Date()));
    }

//   /**
//    *
//    */
//   public String obtenerFecha2(JDateChooser fecha){
//
//      String fech ="";
//      fech += fecha.getJCalendar().getYearChooser().getYear()+ "/";
//      fech += obtenerMesDia(fecha.getJCalendar().getMonthChooser().getMonth()+1) +"/";
//      fech += obtenerMesDia(fecha.getJCalendar().getDayChooser().getDay()) ;
//      return fech;
//   }
    /**
     * Devuelve la fecha en un String en formato aaaa/mm/dd dado un JDateChooser
     */
    public String obtenerFecha3(JDateChooser fecha) {

        String sFecha = "";
        javax.swing.JTextField editor = (javax.swing.JTextField) fecha.getDateEditor();
        StringTokenizer st = new StringTokenizer(editor.getText(), "/");

        String dia = obtenerMesDia(Integer.parseInt(st.nextToken().toString()));

        String mes = st.nextToken();
        String año = st.nextToken();
        sFecha = año + "/" + mes + "/" + dia;

        return sFecha;
    }

    /**
     * Devuelve la fecha actual en formato DD/MMM/AAAA
     */
    public String obtenerFechaActual2() {

        JDateChooser DCfecha = new JDateChooser(new Date());
        String Sfecha = "";
        Sfecha += obtenerNumeroDoble(DCfecha.getJCalendar().getDayChooser().getDay()) + "/";
        Sfecha += nombreMes(DCfecha.getJCalendar().getMonthChooser().getMonth() + 1) + "/";
        Sfecha += DCfecha.getJCalendar().getYearChooser().getYear();
        return Sfecha;
    }

    /**
     * Devuelve la fecha en formato DD/MMM/AAAA
     */
    public String obtenerFecha(String fecha) {

        String Sfecha = "";

        StringTokenizer STfecha = new StringTokenizer(fecha, "/");
        String año = STfecha.nextToken();
        String mes = STfecha.nextToken();
        String dia = STfecha.nextToken();

        Sfecha += obtenerNumeroDoble(Integer.parseInt(dia)) + "/";
        Sfecha += nombreMes(Integer.parseInt(mes)) + "/";
        Sfecha += año;

        return Sfecha;
    }

    /**
     * Devuelve la fecha actual en formato DD/MMM/AAAA dado un JDateChooser
     */
    public String obtenerFecha(JDateChooser JDfecha) {

        String Sfecha = "";

        StringTokenizer fecha = new StringTokenizer(obtenerFecha3(JDfecha), "/");

        String año = fecha.nextToken();
        String mes = fecha.nextToken();
        String dia = fecha.nextToken();

        Sfecha += obtenerNumeroDoble(Integer.parseInt(dia)) + "/";
        Sfecha += nombreMes(Integer.parseInt(mes)) + "/";
        Sfecha += año;

        return Sfecha;
    }

    /**
     * fecha formato dd/MMM/aaa
     */
    public String cambiarFormato(String fecha) {

        String Sfecha = "";

        StringTokenizer STfecha = new StringTokenizer(fecha, "/");
        String dia = STfecha.nextToken();
        String mes = "";// = obtenerMes(STfecha.nextToken());
        String año = STfecha.nextToken();
        return año + "/" + mes + "/" + dia;
    }

    private String obtenerMes(String mes) {

        if (mes.equals("Ene")) {
            return "01";
        }
        if (mes.equals("Feb")) {
            return "02";
        }
        if (mes.equals("Mar")) {
            return "03";
        }
        if (mes.equals("Abr")) {
            return "04";
        }
        if (mes.equals("May")) {
            return "05";
        }
        if (mes.equals("Jun")) {
            return "06";
        }
        if (mes.equals("Jul")) {
            return "07";
        }
        if (mes.equals("Ago")) {
            return "08";
        }
        if (mes.equals("Sep")) {
            return "09";
        }
        if (mes.equals("Oct")) {
            return "10";
        }
        if (mes.equals("Nov")) {
            return "11";
        }
        return "12";
    }

    /**
     * Obtiene el numero de dia dado un jDatechooser (Editor)
     */
    public int obtenerDia(JDateChooser JDfecha) {

        StringTokenizer fecha = new StringTokenizer(obtenerFecha3(JDfecha), "/");
        String dia = fecha.nextToken();
        return Integer.parseInt(dia);
    }

    /**
     * Obtiene el numero de mes dado un jDatechooser (Editor)
     */
    public int obtenerMes(JDateChooser JDfecha) {

        StringTokenizer fecha = new StringTokenizer(obtenerFecha3(JDfecha), "/");
        String dia = fecha.nextToken();
        String mes = fecha.nextToken();

        return Integer.parseInt(mes);
    }

    /**
     * Obtiene el año dado un jDatechooser (Editor)
     */
    public int obtenerAño(JDateChooser JDfecha) {

        StringTokenizer fecha = new StringTokenizer(obtenerFecha3(JDfecha), "/");
        String dia = fecha.nextToken();
        String mes = fecha.nextToken();
        String año = fecha.nextToken();

        return Integer.parseInt(año);
    }

    /**
     * Devuelve la fecha n dias despues de la fecha actual en formato aaaa/mm/dd
     */
    public String crearFechaLimite(int dias, JDateChooser jDateChooser) {

        jDateChooser.setDate(addDate(jDateChooser.getDate(), dias));
        String fecha = obtenerFecha3(jDateChooser);
        jDateChooser.setDate(addDate(jDateChooser.getDate(), -dias));
        return fecha;
    }

    /**
     * Devuelve la fecha n dias despues de la fecha actual en formato
     * dd/MMM/aaaa
     */
    public String crearFechaLimiteT(int dias, JDateChooser jDateChooser) {

        jDateChooser.setDate(addDate(jDateChooser.getDate(), dias));
        String fecha = obtenerFecha(jDateChooser);
        jDateChooser.setDate(addDate(jDateChooser.getDate(), -dias));
        return fecha;
    }

    /**
     * Dado una fecha y unos dias devuelve la fecha sumado el numero de dias
     */
    public static Date addDate(Date date, int dias) {

        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(date.getTime());
        calendar.add(Calendar.DATE, dias);
        date = new Date(calendar.getTimeInMillis());
        return date;
    }

    /**
     * Retorna el Dia o el Mes en Formato de Doble numero si el dato es menor
     * que Diez le antepone un cero
     *
     * 1 = 01 5 = 05 9 = 09 11 = 11
     */
    protected String obtenerMesDia(int dato) {

        String sdia = "";
        if (dato < 10) {

            sdia += "0";
        }
        sdia += dato;
        return sdia;
    }

    /**
     * asigna fecha con formato aaaa/mm/dd a un JDateChooser
     */
    public void asignarFecha(String f, JDateChooser fecha) {

        StringTokenizer st = new StringTokenizer(f, "/");

        int año = Integer.parseInt(st.nextToken());
        int mes = Integer.parseInt(st.nextToken());
        int dia = Integer.parseInt(st.nextToken());

        String fe = año + "/" + this.obtenerMesDia(mes) + "/" + this.obtenerMesDia(dia);
        Date d = new Date(fe);
        fecha.setDate(d);
    }

    /**
     * Obtener Mes Actual
     */
    public int mesActual() {

        JDateChooser fecha = new JDateChooser(new Date());
        return Integer.parseInt(obtenerMesDia(fecha.getJCalendar().getMonthChooser().getMonth() + 1));
    }

    /**
     * obteniene el A�o Actual
     */
    public int añoActual() {

        JDateChooser fecha = new JDateChooser(new Date());
        return fecha.getJCalendar().getYearChooser().getYear();
    }

    /**
     * Asigna PrimerDia del Mes a un JDateChooser
     */
    public void asignaPrimerDiaMes(JDateChooser fecha) {

        String f = this.obtenerFechaActual();
        StringTokenizer st = new StringTokenizer(f, "/");
        int año = Integer.parseInt(st.nextToken());
        int mes = Integer.parseInt(st.nextToken());
        int dia = 1;
        String fe = año + "/" + this.obtenerMesDia(mes) + "/" + this.obtenerMesDia(dia);
        Date d = new Date(fe);
        fecha.setDate(d);
    }

    /**
     * Asigna Ultimo dia del mes a un JDateChooser
     */
    public void asignarUltimoDiaMes(JDateChooser fecha) {

        String f = this.obtenerFechaActual();
        StringTokenizer st = new StringTokenizer(f, "/");
        int año = Integer.parseInt(st.nextToken());
        int mes = Integer.parseInt(st.nextToken());
        int dia = ultimoDiaMes(mes);
        String fe = año + "/" + this.obtenerMesDia(mes) + "/" + this.obtenerMesDia(dia);
        Date d = new Date(fe);
        fecha.setDate(d);
    }

    private int ultimoDiaMes(int iMes) {

        switch (iMes) {

            case 1:
                return 31;
            case 2:
                return mesFebrero();
            case 3:
                return 31;
            case 4:
                return 30;
            case 5:
                return 31;
            case 6:
                return 30;
            case 7:
                return 31;
            case 8:
                return 31;
            case 9:
                return 30;
            case 10:
                return 31;
            case 11:
                return 30;
            case 12:
                return 31;
            default:
                return 0;
        }
    }

    /**
     * Combierte el formato de la fecha de ( aaaa-mm-dd 00:00:00.0 ) a (
     * dd/mm/aaaa )
     */
    protected String cambiaFormatofecha(String fechaMediana) {

        StringTokenizer fecha = new StringTokenizer(fechaMediana, "-");
        String año = fecha.nextToken();
        String mes = fecha.nextToken();
        StringTokenizer sTDia = new StringTokenizer(fecha.nextToken());
        String dia = sTDia.nextToken();
        return dia + "/" + mes + "/" + año;
    }

    /**
     * Combierte el formato de la fecha de ( dd/mm/aaaa ) a ( aaaa-mm-dd
     * 00:00:00.0 )
     */
    protected String cambiaFormatofecha2(String fechaCorta) {

        StringTokenizer fecha = new StringTokenizer(fechaCorta, "/");
        String dia = fecha.nextToken();
        String mes = fecha.nextToken();
        String año = fecha.nextToken();
        return año + "-" + mes + "-" + dia + " 00:00:00.0";
    }

    /**
     * Dado dos JDateChooser inicial y final, y un String fecha con el formato
     * de fecha dd/mm/aaaa
     *
     * Evalua que la fecha se encuentre entre la fecha inicial y la fecha final
     */
    protected boolean rangoFechas(JDateChooser fechaInicial, JDateChooser fechaFinal, String fech) {

        JDateChooser fecha = new JDateChooser();
        asignarFecha(fech, fecha);

        if (rangoFechaValido(fechaInicial, fecha) && rangoFechaValido(fecha, fechaFinal)) {

            return true;
        }

        return false;
    }

    /**
     * Dado dos fechas inicial y final evalua que le fecha inicial sea menor o
     * igual que la fecha final
     *
     * retornando un valor verdadero o falso segun el caso
     */
    public boolean rangoFechaValido(JDateChooser fechaInicial, JDateChooser fechaFinal) {

        int añoInicial = obtenerAño(fechaInicial);
        int añoFinal = obtenerAño(fechaFinal);

        if (añoInicial < añoFinal) {
            return true;
        } else {
            if (añoInicial == añoFinal) {

                int mesInicial = obtenerMes(fechaInicial);
                int mesFinal = obtenerMes(fechaFinal);

                if (mesInicial < mesFinal) {
                    return true;
                } else {
                    if (mesInicial == mesFinal) {

                        int diaInicial = obtenerDia(fechaInicial);
                        int diaFinal = obtenerDia(fechaFinal);

                        if (diaInicial <= diaFinal) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Devuelve la hora actual en formato hh mm en formato de 12 horas
     */
    public String obtenerHoraActual2() {

        Date date = new Date();
        StringTokenizer st = new StringTokenizer(date.toString());
        st.nextToken();
        st.nextElement();
        st.nextToken();
        StringTokenizer st2 = new StringTokenizer(st.nextToken(), ":");
        int hora = Integer.parseInt(st2.nextToken());
        String minuto = st2.nextToken();

        if (hora >= 0 && hora < 12) {

            return hora + ":" + minuto + " a.m.";
        } else {

            return hora + ":" + minuto + " p.m.";
        }
    }

    /**
     * Cambia un Numero # a ##
     */
    protected String obtenerNumeroDoble(int dato) {

        String num = "";
        if (dato < 10) {

            num += "0";
        }
        num += dato;
        return num;
    }

    private String nombreMes(int iMes) {

        switch (iMes) {

            case 1:
                return "Ene";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Abr";
            case 5:
                return "May";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Ago";
            case 9:
                return "Sep";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dic";
            default:
                return "";
        }
    }

    private int mesFebrero() {

        int año = añoActual();

        if (esBisiesto(año)) {

            return 29;
        } else {

            return 28;
        }
    }

    private boolean esBisiesto(int año) {

        if (((año % 4 == 0) && (año % 100 != 0)) || (año % 400 == 0)) {
            return true;
        } else {
            return false;
        }
    }

    public void asignarHora(String sHora, JComboBox hora, JComboBox minuto, JComboBox rango) {

        StringTokenizer st = new StringTokenizer(sHora);
        int iHora = Integer.parseInt(st.nextToken());
        int iMinuto = Integer.parseInt(st.nextToken());
        String sRango = "am";

        if (iHora == 00) {

            iHora = 12;
        } else {
            if (iHora == 12) {
                sRango = "pm";
            } else {
                if (iHora > 12) {

                    iHora -= 12;
                    sRango = "pm";
                }
            }
        }
        hora.setSelectedItem(iHora + "");
        minuto.setSelectedItem(iMinuto + "");
        rango.setSelectedItem(sRango + "");
    }

    /**
     * Dado un String con un Dia (Mon Thue ... Sun) se encuentra en un String
     * con varios Dias ( )
     */
    public boolean checarDias(String dias1, String dias) {

        String dia, dia1;
        StringTokenizer sTdias1 = new StringTokenizer(dias1);
        StringTokenizer sTdias = new StringTokenizer(dias);
        int numDias1 = sTdias1.countTokens();
        int numDias = sTdias.countTokens();

        for (int i = 0; i < numDias1; i++) {

            dia1 = sTdias1.nextToken();

            for (int j = 0; j < numDias; j++) {

                dia = sTdias.nextToken();

                if (dia1.equals(dia)) {

                    return true;
                }
            }

            sTdias = new StringTokenizer(dias);
        }

        return false;
    }

    public boolean rangoHoraValido(String horaInicial, String horaFinal) {

        StringTokenizer st1 = new StringTokenizer(horaInicial);
        int horaIni = Integer.parseInt(st1.nextToken());
        int minutoIni = Integer.parseInt(st1.nextToken());
        StringTokenizer st2 = new StringTokenizer(horaFinal);
        int horaFin = Integer.parseInt(st2.nextToken());
        int minutoFin = Integer.parseInt(st2.nextToken());

        if (horaIni < horaFin) {

            return true;
        } else {
            if (horaIni == horaFin) {
                if (minutoIni < minutoFin) {

                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Combierte hora de formato 12 horas en formato de 24 horas
     */
    public String horaFormato24(int hora, String r) {

        if (r.equals("am")) {
            if (hora == 12) {

                hora = 0;
            }
        } else {

            if (hora < 12) {

                hora += 12;
            }
        }

        return obtenerNumeroDoble(hora);
    }

    /**
     * Devuelve la hora actual en formato hh mm de 24 horas
     */
    public String obtenerHoraActual() {

        Date date = new Date();
        StringTokenizer st = new StringTokenizer(date.toString());
        st.nextToken();
        st.nextElement();
        st.nextToken();
        StringTokenizer st2 = new StringTokenizer(st.nextToken(), ":");
        String hora = st2.nextToken();
        String minuto = st2.nextToken();
        return hora + " " + minuto;
    }

    /**
     * Si el dia actual se encuentra en el String dias devuelve un verdadero.
     * falso en caso contrario
     */
    public boolean correspondeDia(String dias) {

        Date date = new Date();
        StringTokenizer st = new StringTokenizer(date.toString());
        return checarDias(st.nextToken(), dias);
    }

}
