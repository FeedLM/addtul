package domain;

public class FormatoNumero {
  
  public FormatoNumero(String valor) {
    
    separacion = ",";    
    p = valor;
  }
  
  public String convierte(double N){
    
    p = (int)N+"";
    
    return convierte()+"."+Math.abs((int)(( N*100)%100)) ;
  }
  
  private  String Invertir(String numeroString) {
    
    String res = "";
    for(int i = numeroString.length() - 1;i >= 0 ;i--){
      
      res = res + String.valueOf(numeroString.charAt(i));
    }
    return res;
  }
  
  public String convierte() {
    
    String numeroString = "";
    int posicion = -1;
    int inf,inf1,i,j = 0;
    
    if( p == "0" ){
      
      return p;
    }
    
    for( i = 0 ; i < p.length() ; i ++ ){
      if( (int)p.charAt(i) == 44 ){
        
        posicion = i;
      }
    }
    
    if( posicion != -1) {
      
      for( i = p.length() - 1 ; i > posicion ; i -- ){
        
        numeroString = numeroString + String.valueOf( p.charAt(i) );
      }
      
      numeroString = numeroString + separacion;
      posicion--;
    } else{
      
      posicion = p.length() - 1;
    }
    
    inf = ( posicion + 1 ) / 3;
    inf1 = ( posicion + 1 ) % 3;
    
    for( i = 1 ; i <= inf ; i ++ ) {
     
      if(i < 3 ){
        if(i==2){
          
          numeroString = numeroString + separacion;
        }
        
        for( j = posicion ; j > ( posicion - 3 ) ; j -- ){
          
          numeroString = numeroString + String.valueOf( p.charAt( j ) );
        }
        if( i != inf ){
          
          posicion = posicion - 3;
        }
        if( i == inf  &&  inf1 != 0 ){
          
          numeroString = numeroString + separacion;
        }
      }
     
      if( i >= 3 ) {
        
        numeroString = numeroString + separacion;
        for( j = posicion ; j > ( posicion - 3 ) ; j -- ){
          
          numeroString = numeroString + String.valueOf( p.charAt( j ) );
        }
        if( i != inf ){
          
          posicion = posicion - 3;
        }
        if( i == inf && inf1 != 0 ){
          
          numeroString = numeroString + separacion;
        }
      }      
    }//fin for
    
    if( inf1 != 0 ) {      
      if( inf != 0 ){
        
        posicion = posicion - 3;
      }
      numeroString = numeroString + String.valueOf( p.charAt( posicion ) );
      
      while(posicion != 0) {
        
        posicion--;
        numeroString = numeroString + String.valueOf( p.charAt( posicion ) );
      }
    }
    
    return Invertir( numeroString );    
  }
  
  private String p;
  private String separacion;
  
}