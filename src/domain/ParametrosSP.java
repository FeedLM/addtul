/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Gilberto Adan González Silva
 */
public class ParametrosSP {

    private ArrayList<Parametro> parametros;

    public ParametrosSP() {
        parametros = new ArrayList<>();
    }

    public Parametro get(Integer i){
        
        return parametros.get(i);
    }
    
    /**
     * @return the parametros
     */
    public List<Parametro> getParametros() {
        return parametros;
    }

    /**
     * @param parametros the parametros to set
     */
    public void setParametros(ArrayList<Parametro> parametros) {
        this.parametros = parametros;
    }

    //private Parametro[] parametros;
    public void agregarParametro(String valor, String nombre, String tipo, String aInOut) {

        Parametro parametro;

        parametro = new Parametro();

        parametro.setTipo(tipo);
        parametro.setNombre(nombre);
        parametro.setValor(valor);
        parametro.setInOut(aInOut);

        parametros.add(parametro);
    }

    public void agregarParametro(Calendar calendar, String nombre, String tipo, String aInOut) {

        Parametro parametro;

        parametro = new Parametro();

        parametro.setTipo(tipo);
        parametro.setNombre(nombre);
        parametro.setCalendar(calendar);
        parametro.setInOut(aInOut);

        parametros.add(parametro);
    }
}
