/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.programparse.generators;

import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgram;

/**
 *
 * @author Mark
 */
public interface Generator {
    
    public String[] getAvailableNames();
 
    public ParseableProgram get(String name);
    
    public void parseOut() ;
    
    public void parseIn();

}
