/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import static gui.Splash.formatoDateTime;
import java.util.Date;

/**
 *
 * @author Developer GAGS
 */
public class RegistroSesion {

    public  String arete_visual;
    public Double peso;
    public Date fecha;
    public String arete_electronico;

    public RegistroSesion(String aArete_visual, Double aPeso, Date aFecha, String aArete_electronico) {

        arete_visual = aArete_visual;
        peso = aPeso;
        fecha = aFecha;
        arete_electronico = aArete_electronico;
    }

    public String toString() {

        String data;

        data = "{"
                + arete_visual + ", "
                + peso.toString() + ", "
                + formatoDateTime.format(fecha) + ", "
                + arete_electronico +"}";

        return data;

    }

}
