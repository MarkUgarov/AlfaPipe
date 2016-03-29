/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.datatypes;

import java.util.ArrayList;

/**
 *
 * @author Mark
 */
public interface Executable {

    public abstract String getProgramCommand(int index, String parentOutputDir);
    
    public abstract ArrayList<String> getToolCommands(String parentOutputDir);
    
    /**
     * Get the log
     * @param outputPath
     * @return the log
     */
    public abstract String getLog();
            
}
