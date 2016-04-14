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
        this.localFilePath = ParameterPool.PATH_TOOLS_LIST;
        
        String[] endings = {".fna"};
        String[] outputEnding = new String[]{".fa", ".fna"};
        ParseableProgram prokka = new ParseableProgram( "Prokka", 
                                                    "prokka", 
                                                    null,
                                                    -1,
                                                    "--outdir",
                                                    0,
                                                    endings,
                                                    outputEnding);  
        prokka.setOutputSettings(true, false);
        //prokka.setEnterCommand("lqxterm -l vf=128G -cwd ");
        //prokka.setExitCommand("exit");
        prokka.addParameter("Force", "--force", ParameterPool.PROGRAM_EMPTY_PARAMETER_VALUE, 1, false);
        prokka.addAdditionalClusterParameter(ParameterPool.PROGRAM_BINARAY_NAME, ParameterPool.PROGRAM_BINARY_COMMAND, ParameterPool.PROGRAM_BINARY_CONFIRM, ParameterPool.PROGRAM_BINARY_POSITION, ParameterPool.PROGRAM_BINARY_OPTIONAL, ParameterPool.PROGRAM_BINARY_DESCRIPTION);
        this.defaultList.add(prokka);
        
        String[] outputEndings2 = new String[]{".txt"};
        ParseableProgram extractHeader = new ParseableProgram( "Extract Header Info", 
                                                    "sh SCRIPTS/writeToFile.sh SCRIPTS/extract_header_info.plx", 
                                                    null,
                                                    2,
                                                    null,
                                                    3,
                                                    endings,
                                                    outputEndings2);  
        extractHeader.addParameter("prepare Excel", "-E", ParameterPool.PROGRAM_EMPTY_PARAMETER_VALUE, 1, true);
        extractHeader.setOutputSettings(false, true);
        this.defaultList.add(extractHeader);
        
        String[] endingsB = {".txt"};
        ParseableProgram assemblyStatistics = new ParseableProgram( "Assembly Statistics", 
                                                    "sh SCRIPTS/writeToFile.sh SCRIPTS/assembly_statistics.plx ", 
                                                    null,
                                                    2,
                                                    null,
                                                    3,
                                                    endingsB,
                                                    outputEndings2);  
        assemblyStatistics.addParameter("Skip listprint", "-l", ParameterPool.PROGRAM_EMPTY_PARAMETER_VALUE, 1, true);
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
