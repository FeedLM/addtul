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
 * @author Developer GAGS
 */
public class Usuario {
    
    public String id_usuario;
    public String log;
    public String password;
    public String nombre;
    public String apellido;
    public Estado estado;
    public Ciudad ciudad;
    public String correo;
    public String fecha;
    public String telefono;
    
    public Usuario(){
        id_usuario = "";
        log = "";
        password = "";
        nombre = "";
        apellido = "";
        estado = new Estado();
        ciudad = new Ciudad();
        correo = "";
        fecha = "";
        telefono = "";
    }
    
    public void cargarUsuario(String n, String pw) {

        manejadorBD.consulta(""
                + "SELECT id_usuario, password "
                + "FROM usuario "
                + "WHERE log = '" + n + "' AND password = '"  + pw + "'");

        if (manejadorBD.getRowCount() > 0) {
            
           id_usuario = manejadorBD.getValorString(0, 0);
           password   = manejadorBD.getValorString(0, 1);           
        }else{
            id_usuario = null;
            password = null;
        }
    }    
    
    /**
     * @return the log
     */
    public String getLog() {
        return log;
    }

    /**
     * @param log the log to set
     */
    public void setLog(String log) {
        this.log = log;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the id_usuario
     */
    public String getId_usuario() {
        return id_usuario;
    }

    /**
     * @param id_usuario the id_usuario to set
     */
    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String getFecha() {
        return fecha.toString();
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
