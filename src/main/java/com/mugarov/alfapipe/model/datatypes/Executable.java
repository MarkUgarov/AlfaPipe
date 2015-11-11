/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.datatypes;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author Mark
 */
public interface Executable {

    public abstract String getPreprocessingCommand(String parentOutputDir);

    public abstract String getProcessingCommand(String parentOutputDir);
    
    public abstract String getAssemblerCommand(String parentOutputDir);
    
    public abstract String getReadsVsContigsCommand(String parentOutputDir);
    
    public abstract String getProdigalCommand(String parentOutputDir);
    
    public abstract String getToolCommands(String parentOutputDir);
    
    /**
     * Get the log
     * @param outputPath
     * @return the log
     */
    public abstract String getLog();
            
}
