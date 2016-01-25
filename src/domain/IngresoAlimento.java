/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import static gui.Desktop.manejadorBD;
import static gui.Splash.formatoDateTime;
import java.util.Date;

/**
 *
 * @author CONTABILIDAD ID
 */
public class IngresoAlimento {

    public String id_ingreso_alimento;
    public String numero_lote;
    public Corral corral;
    public Double total_alimento;
    public Date fecha;
    public Double costo_unitario;
    public Double costo_total;
    public String carro;

    public IngresoAlimento() {

        id_ingreso_alimento = "";
        numero_lote = "";
        corral = new Corral();
        total_alimento = 0.0;
        fecha = null;
        costo_unitario = 0.0;
        costo_total = 0.0;
        carro = "";
    }

    public boolean grabar() {

        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(numero_lote, "varNumeroLote", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(corral.id_corral, "varIdCorral", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(total_alimento.toString(), "varTotalAlimento", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(fecha), "varFecha", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(costo_unitario.toString(), "varCostoUnitario", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(costo_total.toString(), "varCostoTotal", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(carro, "varCarro", "STRING", "IN");
        
        if (manejadorBD.ejecutarSP("{ call agregarIngresoAlimento(?,?,?,?,?,?,?) }") == 0) {
     
            return true;
        }
        
        return false;
    }
}
