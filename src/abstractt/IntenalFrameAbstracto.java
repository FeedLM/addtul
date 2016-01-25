/*
 * frameAbstracto.java
 *
 * Created on 11 de febrero de 2008, 09:40 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package abstractt;

import domain.Fecha;
import domain.ManejadorBD;
import domain.Redondeo;
import domain.Seguridad;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Gilberto Adan Gonz�lez Silva
 */
public class IntenalFrameAbstracto extends javax.swing.JInternalFrame{
   
   /** Creates a new instance of frameAbstracto */
   public IntenalFrameAbstracto() {
      
      setClosable(true);
      setIconifiable(true);
      setMaximizable(true);
      setResizable(true);
      fecha = new Fecha();
      seguridad=new Seguridad();
      redondeo = new Redondeo();
   }
   
   public Redondeo redondeo;
   
   public void setEscritoio(javax.swing.JDesktopPane esc){
      
      escritorio = esc;
   }
   
   public javax.swing.JDesktopPane escritorio;
   
   public void centrado(Dimension d){
      
      Dimension frameSize = getSize();
      
      if(frameSize.height > d.height){
         
         frameSize.height = d.height;
      }
      
      if(frameSize.width > d.width){
         
         frameSize.width = d.width;
      }
      
      setLocation((d.width - frameSize.width)/2,(d.height - frameSize.height)/2);
   }
   
   /**
    *A�ade el evento de busqueda en el combobox atravez de escritura
    */
   
   protected void selectorKeyReleased(java.awt.event.KeyEvent e, JTextField editor, JComboBox selector){
      
      int m=0;
      
      int tecla = e.getKeyCode();
      
      switch(tecla){
         //enter;
         case 10:
            selector.setSelectedItem(editor.getText());
            
            break;
            //back space
         case 8: break;
         
         default:
            String search = editor.getText();
            Boolean found = false;
            m=0;
            int tam = search.length();
            
            for ( int n = 0; n < selector.getItemCount() ; n ++ ) {
               if (!found) {
                  
                  String s = selector.getItemAt(n).toString().toLowerCase();
                  try {
                     if (s.startsWith(search.toLowerCase())) {
                        
                        found = true;
                        m = n;
                        editor.setText(selector.getItemAt(m).toString());
                        editor.select(tam,editor.getText().length());
                     }
                  } catch (Exception ex) {
                     
                  }
               }
            }
            
            break;
      }
   }
   
   public void setManejadorBD(ManejadorBD m ){
      
      mbd = m;
      seguridad.setManejadorBD(mbd);
   }
   
   /**
    *
    */
   protected int dialogoConfirmacionSiNo(Object ventana,String Mensaje,String titulo, int s){
      
      Object[] botones = {"  Si  ", "  No  " };
      
      return JOptionPane.showOptionDialog((Component) ventana, Mensaje.toUpperCase(), titulo,
         JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, botones, botones[s]);
   }
   
   /**
    *
    */
   protected int dialogoConfirmacionSiNoCancelar(Object ventana,String Mensaje,String titulo, int s){
      
      Object[] botones = {"  Si  ", "  Cancelar  "," No " };
      
      return JOptionPane.showOptionDialog((Component) ventana, Mensaje.toUpperCase(), titulo,
         JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, botones, botones[s]);
   }
   
   /**
    *Regresa el id del corte actual
    */
   protected int obtenerIdCorte(int idCaja){
      
      int idCorte = 1;
      String consulta = "SELECT MAX(idCorte) FROM Corte WHERE idCaja = "+idCaja;
      mbd.consulta(consulta);
      
      if( mbd.getRowCount() > 0 ){
         
         idCorte = mbd.getValorInt(0,0);
      }
      
      return idCorte;
   }
   
   /**
    *Redondea un valor double en unos decimales especificados
    *
    */
   /*
   protected double redondear( double num , int decimales ){
      
      double expo = Math.pow( 10 , decimales );
      //siguiente valor despues de los decimales
      double expo2 = Math.pow(10, decimales + 1 );
      
      //numero siguiente al redondeado
      int num1 = ((int)(num * expo2)) - (((int)(num * expo2))/ 10 ) * 10 ;
      
      //si el numero es menor que 5 no aumenta el redondeo
      if(num1 < 5){
         num = ( (double)( (int)(num * expo)  ) )/ expo;
      } else{
         //si el numero es 5 o mayor se le suma uno al menor decimal
         num = ( (double)( (int)(num * expo)+1  ) )/ expo;
      }
      return num;
   }
   */
   /**
    *Redondea un valor double en unos decimales especificados
    *
    */
   /*
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
   */
   /*
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
   */
   /*
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
    */
   /**
    *Obtiene el valor numerico de un textField con formato $ ##.##
    *retorna  ##.##
    */
   /*
   protected double obtenerValor(javax.swing.JTextField campo ){
      
      return obtenerValor(campo.getText());
   }
   */
   /**
    *Obtiene el valor numerico de un String con formato $ #,###.##
    *retorna  ####.##
    */
   /*
   protected double obtenerValor(String campo ){
      
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
   */
   /**
    *Obtiene el valor numerico de un textField con formato  #,###.##
    *retorna  ####.##
    */
   /*
   protected double obtenerValor2(String campo ){
      
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
   /**
    *Dado un TextFiled el texto se muestra seleccionado para su edicion rapida
    */
   protected String seleccionarTexto(javax.swing.JTextField tf){
      
      String texto = tf.getText();
      tf.select(0,texto.length());
      return texto;
   }
   
   /**
    *Cambia un Numero # a ##
    */
   protected String obtenerNumeroDoble(int dato){
      
      String num = "";
      if( dato < 10){
         
         num += "0";
      }
      num += dato;
      return num;
   }
   
   /**
    *Obtiene el Siguiente valor de un CAMPO numerico de una TABLA especifica
    *si no hay ninguno se le asigna el uno
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
    *Obtiene el Siguiente valor de un CAMPO numerico de una TABLA especifica
    *si no hay ninguno se le asigna el uno
    */
   protected int obtenerSiguiente(String tabla, String campo, String condicion){
      
      int idCampo = 1;
      String consulta = "SELECT MAX("+campo+") FROM "+tabla+" WHERE "+condicion;
      //ManejadorBD mbdS = mbd.nuevaConexion();
      mbd.consulta(consulta);
      
      if( mbd.getRowCount() != 0 ){
         
         idCampo = mbd.getValorInt(0,0) + 1;
      }
      return idCampo;
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
    *Quita todas las filas de un tabla
    *
    */
   protected void limpiarTabla(javax.swing.JTable tabla){
      
      DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
      int filas = tabla.getRowCount();
      
      for( int i = 0; i < filas ; i ++ ){
         
         modelo.removeRow(0);
      }
      
      tabla.setModel(modelo);
   }
   
   /**
    *Quita todas las filas de un tabla menos una
    *
    */
   protected void limpiarTabla1(javax.swing.JTable tabla){
      
      DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
      int filas = tabla.getRowCount();
      
      for( int i = 0; i < filas-1 ; i ++ ){
         
         modelo.removeRow(0);
      }
      
      tabla.setModel(modelo);
   }
   
   protected void quitaFilaTabla(javax.swing.JTable tabla, int fila){
      
      DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
      modelo.removeRow(fila);
      tabla.setModel(modelo);
   }
   
   /**
    *Da tamaño las columnas de una tabla con con un arreglo de tamaños
    *
    */
   protected void tamañoColumna(JTable tabla, int [] tamaños){
      
      TableColumn nColumn;
      
      for( int i = 0 ; i < tamaños.length ; i ++){
         
         nColumn = tabla.getColumnModel().getColumn(i);
         nColumn.setPreferredWidth(tamaños[i]);
      }
   }
   
   protected void mueveVista(int fila, JTable tabla, JScrollPane scroll){
   
      if(fila < tabla.getRowCount()){
         
         Rectangle r = tabla.getCellRect(fila, 0, false);
         scroll.getViewport().scrollRectToVisible(r);         
         tabla.changeSelection(fila,0,false,true);
      }
   }
   
    protected void mueveVista(int fila, int columna, JTable tabla, JScrollPane scroll){
   
      if(fila < tabla.getRowCount()){
         
         Rectangle r = tabla.getCellRect(fila, columna, false);
         scroll.getViewport().scrollRectToVisible(r);         
         tabla.changeSelection(fila,columna,false,true);
      }
   }
   
   public String nombreProceso(){
      
      return nombreProceso;
   }
   
   public boolean validaAcceso(){
      
      int idProceso = obtenerIdProceso();
      int idPerfil = seguridad.obtenerPerfilUsuario();
      
      String consulta = "SELECT * FROM Acceso WHERE idProceso = "+idProceso+" AND idPerfil = "+idPerfil;
      
      mbd.consulta(consulta);
      
      if(mbd.getRowCount() != 0){
         
         return true;
      } else{
         
         return false;
      }
   }
   
   private int obtenerIdProceso(){
      
      String consulta = "SELECT idProceso FROM Procesos WHERE descripcion = '"+nombreProceso+"'";
      mbd.consulta(consulta);
      
      return mbd.getValorInt(0,0);
   }
   
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
   
   protected ManejadorBD mbd = null;
   
   protected String nombreProceso = "";
   
   protected String consulta;
   protected String insercion;
   protected String actualizacion;
   protected String eliminacion;
   
   protected Seguridad seguridad;
   protected Fecha fecha;
   
}