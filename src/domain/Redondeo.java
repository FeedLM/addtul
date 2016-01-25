/*
 * Redondeo.java
 *
 * Created on 3 de febrero de 2009, 02:33 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package domain;

import java.util.StringTokenizer;

/**
 *
 * @author INGENIERIA 2
 */
public class Redondeo {
    
    /** Creates a new instance of Redondeo */
    public Redondeo() {
        
    }
    
     /**
    *Redondea un valor double en unos decimales especificados
    *
    */
    
   public String redondear( double num_ , int decimales ){
      
     float num =(float) num_;
      
      boolean negativo = false;
      if(num < 0){
         num *= -1;
         negativo = true;
      }
      
      //int decimales = 2;
      
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
      
      numero = quitar(numero,decimales);
      
      if(negativo){
         
         return "-"+numero;
      }else{
         
         return numero;
      }
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
   
    private  static String quitar(String numero,int decimal){
      
      StringTokenizer stt = new StringTokenizer(numero,".");
      String enteros;
      String decimales;
      
      if(stt.countTokens()>1){
         
         enteros = stt.nextToken();
         decimales = stt.nextToken();
         
         if(decimales.length() >= decimal+1){
            
            decimales = decimales.substring(0,decimal);
            return enteros+"."+decimales;
         }
         
      }else{
         numero+=".";
         for (int i = 0; i < decimal; i++) {
             numero+="0";
         }
         return numero+".";
      }
      return numero;
      
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
   /**
    *Obtiene el valor numerico de un textField con formato $ ##.##
    *retorna  ##.##
    */
   public  double obtenerValor(javax.swing.JTextField campo ){
      
      return obtenerValor(campo.getText());
   }
   
   
   /**
    *Obtiene el valor numerico de un textField con formato $ ##.##
    *retorna  ##.##
    */
   public  double obtenerValor(abstractt.TextField campo ){
      
      return obtenerValor(campo.getText());
   }
   
   /**
    *Obtiene el valor numerico de un String con formato $ #,###.##
    *retorna  ####.##
    */
   public  double obtenerValor(String campo ){
      
      StringTokenizer st;
      String stt;
      if(campo.startsWith("$")){
         
         st = new StringTokenizer( campo );
         st.nextToken();
         stt = st.nextToken();
         st = new StringTokenizer(stt,",");         
      }else{
         
         st = new StringTokenizer( campo,",");         
      }
      stt = "";
      
      while(st.hasMoreTokens()){
         
         stt += st.nextToken();
      }
      
      return Double.parseDouble( stt );
   }
   
   /**
    *Obtiene el valor numerico de un textField con formato  #,###.##
    *retorna  ####.##
    */
   /*
   public  double obtenerValor2(String campo ){
      
      StringTokenizer st = new StringTokenizer( campo );
      String stt = st.nextToken();      
      st = new StringTokenizer(stt,",");
      stt = "";
      while(st.hasMoreTokens()){
         
         stt += st.nextToken();
      }
      
      return  Double.parseDouble( stt );
   }
    */
    protected String quitarSimboloPesos(String moneda){
      
      StringTokenizer stt = new StringTokenizer(moneda," ");
      stt.nextToken();
      String s = stt.nextToken();
      return s;
   }
   
   /**
    *Si el numero tiene comas se las quita
    */
   protected String obtenerNumero(String numero){
      
      StringTokenizer num = new StringTokenizer(numero,",");
      
      numero = "";
      
      while(num.hasMoreTokens()) {
         numero += num.nextToken();
      }
      //numero += num.nextToken();
      return numero;
   }
   
   protected boolean tieneDecimales(double numero){
     
     int num = (int)(numero*100);
     int decimales = num % 100;
     
     if(decimales > 0){
        
        return true;
     }
     
     return false;
  }
   
}
