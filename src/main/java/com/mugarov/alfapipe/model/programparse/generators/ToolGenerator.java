/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.programparse.generators;

import com.mugarov.alfapipe.model.ComponentPool;
import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgram;
import java.util.ArrayList;

/**
 *
 * @author Mark
 */
public class ToolGenerator implements Generator{
    
    private final String localFilePath;
    private final GeneratorCore core;
    private final ArrayList<ParseableProgram> defaultList;
    
     public ToolGenerator(){
        this.defaultList = new ArrayList<>();
        this.localFilePath = ParameterPool.PATH_OBLIGATORY_TOOLS;
        
        String[] endings =  ParameterPool.ENDINGS_FASTA;
        String[] outputEnding = new String[]{".fa", ".fna"};
        
        
        String[] outputEndings2 = new String[]{".txt"};
        ParseableProgram extractHeader = new ParseableProgram( "Extract Header Info", 
                                                    ParameterPool.FILE_SCRIPT_PATH+"writeToFile.sh "+ParameterPool.FILE_SCRIPT_PATH+"extract_header_info.plx", 
                                                    null,
                                                    2,
                                                    null,
                                                    3,
                                                    endings,
                                                    outputEndings2);  
        extractHeader.addParameter("prepare Excel", "-E", ParameterPool.PROGRAM_EMPTY_PARAMETER_VALUE, 1, true);
        extractHeader.addLocalPrependParameter("Run on Shell", "sh", ParameterPool.PROGRAM_EMPTY_PARAMETER_VALUE, 1, false, "Starts this as a shell script.");
        extractHeader.setDisableCluster(true);
        extractHeader.setOutputSettings(false, true);
        this.defaultList.add(extractHeader);
        
        String[] endingsB = ParameterPool.ENDINGS_FASTA;
        ParseableProgram assemblyStatistics = new ParseableProgram( "Assembly Statistics", 
                                                    ParameterPool.FILE_SCRIPT_PATH+"writeToFile.sh " + ParameterPool.FILE_SCRIPT_PATH+"assembly_statistics.plx ", 
                                                    null,
                                                    2,
                                                    null,
                                                    3,
                                                    endingsB,
                                                    outputEndings2);  
        assemblyStatistics.addParameter("Skip listprint", "-l", ParameterPool.PROGRAM_EMPTY_PARAMETER_VALUE, 1, true);
        assemblyStatistics.setDisableCluster(true);
        assemblyStatistics.addLocalPrependParameter("Run on Shell", "sh", ParameterPool.PROGRAM_EMPTY_PARAMETER_VALUE, 1, false, "Starts this as a shell script.");
        assemblyStatistics.setOutputSettings(false, true);
        this.defaultList.add(assemblyStatistics);
        
        this.core = new GeneratorCore(this.localFilePath, this.defaultList);
        
        
    }
    
    @Override
    public String[] getAvailableNames(){
        return this.core.getAvailableNames();
    }
 
    @Override
    public ParseableProgram get(String name){
        return this.core.get(name);
    }
    
    @Override
    public void parseOut() {
        this.core.parseOut();
    }
    
    @Override
     public void parseIn(){ 
         this.core.parseIn();
         
    }
}
