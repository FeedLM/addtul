/*
 * ClaseAbstracta.java
 *
 * Created on 20 de mayo de 2008, 10:39 AM
 *
 */

package abstractt;

import com.toedter.calendar.JDateChooser;
import domain.Fecha;
import domain.ManejadorBD;
import domain.Seguridad;
import java.awt.Component;
import java.sql.SQLException;
import java.util.Date;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

/**
 *
 * @author Gilberto Adan Gonz�lez Silva
 */
public class ClaseAbstracta {
  
  /** Creates a new instance of ClaseAbstracta */
  public ClaseAbstracta() {
  }
  
  public ManejadorBD getMbd() {
    
    return mbd;
  }
  
  public void setManejadorBD(ManejadorBD mbd) {
    
    this.mbd = mbd;
  }
  
  /**
   *Obtiene el Maximo valor de un CAMPO numerico de una TABLA especifica
   *
   */
  protected int obtenerMaximo(String tabla, String campo,String condicion){
    
    int idCampo = 0;
    String consulta = "SELECT * FROM "+tabla;
    
    ManejadorBD mbdS = mbd.nuevaConexion();
    
    mbdS.consulta(consulta);
    
    if( mbdS.getRowCount() != 0 ){
      
      mbdS.consulta("SELECT MAX("+campo+") FROM "+tabla+" WHERE "+condicion);
      idCampo = Integer.parseInt(mbdS.getValueAt(0,0).toString()) ;
    }
    
    mbdS.desconectar();
    
    return idCampo;
  }
  
  /**
   *Obtiene el Maximo valor de un CAMPO numerico de una TABLA especifica
   *
   */
  protected int obtenerSiguiente(String tabla, String campo,String condicion){
    
    int idCampo = 1;
    String consulta = "SELECT * FROM "+tabla+" WHERE "+condicion;
    ManejadorBD mbdS = mbd.nuevaConexion();
    
    mbdS.consulta(consulta);
    
    if( mbdS.getRowCount() != 0 ){
      
      mbdS.consulta("SELECT MAX("+campo+") FROM "+tabla+" WHERE "+condicion);
      idCampo = Integer.parseInt(mbdS.getValueAt(0,0).toString()) + 1;
    }
    
    mbdS.desconectar();
    
    return idCampo;
  }
  
  /**
   *Obtiene el Maximo valor de un CAMPO numerico de una TABLA especifica
   *
   */
  protected int obtenerSiguiente(String tabla, String campo){
    
    int idCampo = 1;
    String consulta = "SELECT * FROM "+tabla;
    ManejadorBD mbdS = mbd.nuevaConexion();
    
    mbdS.consulta(consulta);
    
    if( mbdS.getRowCount() != 0 ){
      
      mbdS.consulta("SELECT MAX("+campo+") FROM "+tabla);
      idCampo = Integer.parseInt(mbdS.getValueAt(0,0).toString()) + 1;
    }
    
    mbdS.desconectar();
    
    return idCampo;
  }
  
  /**
   *
   */
  public static int  dialogoConfirmacionSiNo(Object ventana,String Mensaje,String titulo, int s){
    
    Object[] botones = {"  Si  ", "  No  " };
    
    return JOptionPane.showOptionDialog((Component) ventana, Mensaje, titulo,
            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, botones, botones[s]);
  }
  
  public String nombreProceso(){
    
    return nombreProceso;
  }
  
  protected String nombreProceso = "";
  
  protected ManejadorBD mbd;
  protected Seguridad seguridad;
  protected Fecha fecha;
}