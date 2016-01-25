/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import static gui.Splash.formatoDate;
import static gui.Splash.formatoDateTime;
import static gui.Desktop.rancho;
import static gui.Desktop.manejadorBD;
import java.util.LinkedList;

/**
 *
 * @author Developer GAGS
 */
public class AnimalGrupo {
    
    public Usuario  usuario;
    public LinkedList animales;
    public String tipo;
    
    public boolean grabar(Animal animal) {
        
        manejadorBD.parametrosSP = new ParametrosSP();
        
        manejadorBD.parametrosSP.agregarParametro(rancho.id_rancho, "varIdRancho", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(usuario.id_usuario, "varIdUsuario", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(animal.id_animal, "varIdAnimal", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(tipo, "vartipo", "STRING", "IN");
        
        if (manejadorBD.ejecutarSP("{ call agregarAnimalGrupo(?,?,?,?) }") == 0) {
            
            return true;
        }
        
        return false;
    }
}