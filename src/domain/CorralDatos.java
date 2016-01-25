/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import static gui.Splash.formatoDateTime;
import static gui.Desktop.manejadorBD;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Developer GAGS
 */
public class CorralDatos {

    public String id_rancho;
    public String id_corral;
    public String categoria;
    public String ganado_amedias;
    public Color color;
    public Date fecha_nacimiento;
    public String numero_lote;
    public String compra;
    public Double porcentaje;
    public Proveedor proveedor;

    public CorralDatos() {

        this.id_rancho = gui.Desktop.rancho.id_rancho;
        id_corral = "";
        color = new Color();
        porcentaje = 0.0;
        fecha_nacimiento = new Date();        
        proveedor = new Proveedor();        
    }

    public void cargarPorId(String id_corral) {

        manejadorBD.consulta(
                "SELECT	 id_rancho,      id_corral,   categoria, \n"
                + "      ganado_amedias, color_arete, fecha_nacimiento, \n"
                + "      numero_lote,    compra,      porcentaje, \n"
                + "      ifnull(id_proveedor, '')  "
                + "FROM  corral_datos "
                + "WHERE id_corral = '" + id_corral+"'");

        if (manejadorBD.getRowCount() > 0) {
            asignarValores();
        }
    }

    private void asignarValores() {

        Date fecha = null;
        Integer id_color;
        String id_proveedor;

        id_rancho = manejadorBD.getValorString(0, 0);
        id_corral = manejadorBD.getValorString(0, 1);
        categoria = manejadorBD.getValorString(0, 2);
        
        ganado_amedias = manejadorBD.getValorString(0, 3);
        id_color = manejadorBD.getValorInt(0, 4);

        try {

            fecha_nacimiento = formatoDateTime.parse(manejadorBD.getValorString(0, 5));
        } catch (ParseException ex) {

            Logger.getLogger(CorralDatos.class.getName()).log(Level.SEVERE, null, ex);
        }

        numero_lote = manejadorBD.getValorString(0, 6);
        compra = manejadorBD.getValorString(0, 7);
        porcentaje = manejadorBD.getValorDouble(0, 8);
        id_proveedor   = manejadorBD.getValorString(0, 9);
        
        proveedor.cargarPorId(id_proveedor);
        
        color.cargarPorId(id_color);
    }
}
