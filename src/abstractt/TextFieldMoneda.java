/*
 * TextField.java
 *
 * Created on 8 de mayo de 2008, 09:07 AM
 *
 */

package abstractt;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.StringTokenizer;
import javax.swing.JTextField;

/**
 *
 * @author Gilberto Adan Gonz�lez Silva
 */
public class TextFieldMoneda extends TextField {
    
    /** Creates a new instance of TextField */
    public TextFieldMoneda() {
        
        this.textFieldDouble();
        
        this.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                FocusLost(evt);
            }
        });
        
        
        setText("$ 0.00");
        setHorizontalAlignment(javax.swing.JTextField.CENTER);
        setFont(new java.awt.Font("Calibri", 0, 12));
    }
    
    private void FocusLost(java.awt.event.FocusEvent evt) {
        
        if(!getText().startsWith("$")){
            
            double cantidad = Double.parseDouble(this.getText());
            
            this.setText("$ "+redondear(cantidad));
        }
    }
    
    private void FocusGained(java.awt.event.FocusEvent evt) {
        
        this.setText(this.obtenerValor()+"");
        this.seleccionarTexto();
    }
    
    /**
     *Obtiene el valor numerico de un textField con formato $ ##.##
     *retorna  ##.##
     */
    public double obtenerValor(){
        
        StringTokenizer st = new StringTokenizer( getText() );
        st.nextToken();
        String stt = st.nextToken();
        st = new StringTokenizer(stt,",");
        stt = "";
        while(st.hasMoreTokens()){
            
            stt += st.nextToken();
        }
        
        return Double.parseDouble( stt );
    }
    
    /**
     *Redondea un valor double en unos decimales especificados
     *
     */    
      public static String redondear( double num_  ){
      
      float num =(float) num_;
      
      boolean negativo = false;
      if(num < 0){
         num *= -1;
         negativo = true;
      }
      
      int decimales = 2;
      
      float expo = (float) Math.pow( 10 , decimales );
      //siguiente valor despues de los decimales
      float expo2 = (float)Math.pow(10, decimales + 1 );
      
      int iNum = (int)(num*expo2);
      num = (iNum)/(expo2);
      
      //numero siguiente al redondeado
      int num1 = ((int)(num * expo2)) - (((int)(num * expo2))/ 10 ) * 10 ;
      //si el numero es menor que 5 no aumenta el redondeo
      if(num1 < 5){
         //num = ( (float)( (int)(num * expo)  ) )/ expo;
         //iNum = (int)(num*expo);
         //num = (iNum)/(expo);
      } else{
         //si el numero es 5 o mayor se le suma uno al menor decimal
         
         num = ( (float)( (int)(num * expo)+1  ) )/ expo;
      }
      
      int segundoDecimal = ((int)(num * expo)) - (((int)(num * expo))/ 10 ) * 10 ;
      
      String numero;
      if(segundoDecimal == 0 )
         numero =  ponerComas(num+"0");
      else
         numero = ponerComas(num+"");
      
      numero = quitarTercerDecimal(numero);
      
      if(negativo){
         
         return "-"+numero;
      }else{
         
         return numero;
      }
   }
       private static String ponerComas(String num){
      
      StringTokenizer stt = new StringTokenizer(num,".");
      
      String numero = stt.nextToken();
      String decimales = stt.nextToken();
      
      String orenum="";
      int cont = 0;
      for(int i = numero.length()-1 ; i  >=  0; i --){
         
         if(cont == 3){
            orenum+=",";
            cont = 0;
         }
         orenum += numero.charAt(i);
         
         cont++;
      }
      
      numero = "";
      for(int i = orenum.length()-1 ; i  >=  0; i --){
         
         numero += orenum.charAt(i);
      }
      
      return numero+"."+decimales;
   }
       private  static String quitarTercerDecimal(String numero){
      
      StringTokenizer stt = new StringTokenizer(numero,".");
      String enteros;
      String decimales;
      
      if(stt.countTokens()>1){
         
         enteros = stt.nextToken();
         decimales = stt.nextToken();
         
         if(decimales.length() >= 3){
            
            decimales = decimales.substring(0,2);
            return enteros+"."+decimales;
         }
         
      }else{
         
         return numero+".00";
      }
      return numero;
      
   }
}