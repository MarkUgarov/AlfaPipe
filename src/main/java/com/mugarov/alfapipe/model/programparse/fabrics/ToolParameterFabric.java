/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.programparse.fabrics;

import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgramList;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgram;
import java.util.ArrayList;

/**
 *
 * @author mugarov
 */
public class ToolParameterFabric {
    private final ArrayList<ParseableProgram> defaultList;
    private final int index;
    private final String name;
    
    private final ParseableProgramList parameterList;
            
    public ToolParameterFabric(){
        this.defaultList = new ArrayList<>();       
        this.name = ParameterPool.LABEL_TOOLS;
        this.index = 5;
      
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
        prokka.setOnlyOutputDirectorySetable(true);
        prokka.addParameter("Force", "--force", ParameterPool.PROGRAM_EMPTY_PARAMETER_VALUE, 1, false);
        this.defaultList.add(prokka);
        
        String[] outputEndings2 = new String[]{".txt"};
        ParseableProgram extractHeader = new ParseableProgram( "Extract Header Info", 
                                                    "SCRITPS/extract_header_info.plx", 
                                                    null,
                                                    2,
                                                    "|tee",
                                                    3,
                                                    endings,
                                                    outputEndings2);  
        extractHeader.addParameter("prepare Excel", "-E", ParameterPool.PROGRAM_EMPTY_PARAMETER_VALUE, 1, true);
        this.defaultList.add(extractHeader);
        
        String[] endingsB = {".txt"};
        ParseableProgram assemblyStatistics = new ParseableProgram( "Assembly Statistics", 
                                                    "SCRITPS/assembly_statistics.plx", 
                                                    null,
                                                    2,
                                                    "|tee",
                                                    3,
                                                    endingsB,
                                                    outputEndings2);  
        assemblyStatistics.addParameter("Skip listprint", "-l", ParameterPool.PROGRAM_EMPTY_PARAMETER_VALUE, 1, true);
        this.defaultList.add(assemblyStatistics);
        
        this.parameterList = new ParseableProgramList();
        this.parameterList.setIndex(this.index);
        this.parameterList.setName(this.name);
        this.parameterList.setPrograms(this.defaultList);
    }
    
    private ParseableProgramList getList(){
        return this.parameterList;
    }
}
