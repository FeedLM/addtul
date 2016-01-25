/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import static gui.Desktop.manejadorBD;
import java.util.Date;

/**
 *
 * @author Home
 */
public class Compra {
    
    public String id_compra;
    public String id_rancho;
    public Proveedor id_proveedor;
    public Date fecha;
    public String factura;
    public String orden;
    public double subtotal;
    public double iva;
    public double total;

    public Compra() {
        id_compra = "";
        id_rancho = "";
        factura = "";
        orden = "";
        subtotal = 0.0;
        iva = 0.0;
        total = 0.0;
    }

    public void cargarPorFacturaYOrden(String factura, String orden) {
        manejadorBD.consulta("SELECT id_compra  "
                + "FROM compra     "
                + "WHERE   factura = '" + factura + "' "
                + "AND orden = '" + orden + "' ;");
        if (manejadorBD.getRowCount() > 0) {
            id_compra = manejadorBD.getValorString(0, 0);
        }
    }

    public String getId_rancho() {
        return id_rancho;
    }

    public void setId_rancho(String id_rancho) {
        this.id_rancho = id_rancho;
    }

    public Proveedor getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(Proveedor id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
