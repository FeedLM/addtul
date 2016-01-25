/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import static gui.Desktop.manejadorBD;

/**
 *
 * @author Developer GAGS
 */
public class Configuracion {

    public String id_configuracion;
    public String puerto_baston;
    public String pueto_bascula;
    public String envio_com;
    public String rec_com_bascula;
    public String rec_com_baston;
    public Integer tiempo_espera_com;
    public Double precio_carne;
    public Double costo_alimento;

    public Configuracion() {

        id_configuracion = "";
        puerto_baston = "";
        pueto_bascula = "";
        envio_com = "";
        rec_com_bascula = "";
        rec_com_baston = "";
        tiempo_espera_com = 0;
        precio_carne = 0.00;
        costo_alimento = 0.00;
    }

    public void cargarConfiguracion() {

        manejadorBD.consulta(""
                + "SELECT id_configuracion,   puerto_baston,\n"
                + "       puerto_bascula,     envio_com,\n"
                + "       rec_com_bascula,    rec_com_baston,\n"
                + "       tiempo_espera_com,  COALESCE(precio_carne,0.00), \n"
                + "        COALESCE(costo_alimento,0.00) \n"
                + "FROM   configuracion ");

        if (manejadorBD.getRowCount() > 0) {
            asignarValores();
        } else {
            id_configuracion = "";
        }
    }

    private void asignarValores() {

        id_configuracion = manejadorBD.getValorString(0, 0);
        puerto_baston = manejadorBD.getValorString(0, 1);
        pueto_bascula = manejadorBD.getValorString(0, 2);
        envio_com = manejadorBD.getValorString(0, 3);
        rec_com_bascula = manejadorBD.getValorString(0, 4);
        rec_com_baston = manejadorBD.getValorString(0, 5);
        tiempo_espera_com = manejadorBD.getValorInt(0, 6);
        precio_carne = manejadorBD.getValorDouble(0, 7);
        costo_alimento = manejadorBD.getValorDouble(0, 8);
    }
    
    public boolean actualizar() {

        manejadorBD.parametrosSP = new ParametrosSP();
       
        manejadorBD.parametrosSP.agregarParametro(id_configuracion, "varIdConfiguracion", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(puerto_baston, "varPuertoBaston", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(pueto_bascula, "varPuertoBascula", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(envio_com, "varEnvioCom", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(rec_com_bascula, "varRecComBascula", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(rec_com_baston, "varRecComBaston", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(tiempo_espera_com.toString(), "varTiempoEsperaCom", "INT", "IN");
        manejadorBD.parametrosSP.agregarParametro(precio_carne.toString(), "varPrecioCarne", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(costo_alimento.toString(), "varCostoAlimento", "DOUBLE", "IN");

        if (manejadorBD.ejecutarSP("{ call actualizarConfiguracion(?,?,?,?,?,?,?,?,?) }") == 0) {

            return true;
        }
        return false;
    }

}
