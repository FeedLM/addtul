/*
 * moneda.java
 *
 * Created on 15 de mayo de 2008, 12:30 PM
 *
 */

package abstractt;

import java.util.StringTokenizer;

/**
 *
 * @author Gilberto Adan Gonz�lez Silva
 */
public class ConversorMoneda {
  
  /** Creates a new instance of moneda */
  public ConversorMoneda() {
  }
  
  public String moneda(String cant){
     
    double cantidad = Double.parseDouble(cant);
    n2t numero;
    int num=(int)cantidad;
    String res;
    numero = new n2t(num);
    res = numero.convertirLetras(num);
    
    res+=" "+decimales(cantidad);
    
    res+="/100 M.N.";
    return res;
  }
 
  private String decimales(double cantidad){
    
    StringTokenizer st = new StringTokenizer(cantidad+"",".");
    st.nextToken();
    String decimales = st.nextToken();
    
    
    if(decimales.length() == 1){
      
      decimales = decimales+"0";
    }
    
    return decimales;
  }
}
