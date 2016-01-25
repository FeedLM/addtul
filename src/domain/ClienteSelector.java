/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import abstractt.ComboBox;

/**
 *
 * @author Developer GAGS
 */
public class ClienteSelector extends ComboBox{
    
    public ClienteSelector(){
        
    }
    
    public void cargar(){
        
        addArray(domain.Cliente.cargarClientes());        
    }
}