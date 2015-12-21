/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.programparse.generators;

import com.mugarov.alfapipe.model.Pool;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgramParameters;
import java.util.ArrayList;

/**
 *
 * @author Mark
 */
public class ToolGenerator implements Generator{
    
    private final String localFilePath;
    private final GeneratorCore core;
    private final ArrayList<ParseableProgramParameters> defaultList;
    
     public ToolGenerator(){
        this.defaultList = new ArrayList<>();
        this.localFilePath = Pool.PATH_TOOLS_LIST;

//        String[] endings = {".fa"};
//        String[] outputEnding = new String[]{".fa"};
//        ParseableProgramParameters Test1 = new ParseableProgramParameters( "Example Tool 1", 
//                                                    "start Tool 1", 
//                                                    "apply inputCommand Tool 1",
//                                                    0,
//                                                    "apply outputCommand Tool 1",
//                                                    -1,
//                                                    endings,
//                                                    outputEnding);  
//        Test1.addParameter("Test Boolean", "boolean value", Pool.PROGRAM_EMPTY_PARAMETER_VALUE, 1, true);
//        Test1.addParameter("Test optional", "optional value", "Test optional default", 2, true);
//        Test1.addParameter("Test obligatory", "obligatory value", "Test obligatory default", 3, false);
//        this.defaultList.add(Test1);
        
        String[] endings = {".fna"};
        String[] outputEnding = new String[]{".fa", ".fna"};
        ParseableProgramParameters prokka = new ParseableProgramParameters( "Prokka", 
                                                    "prokka", 
                                                    null,
                                                    -1,
                                                    "--outdir",
                                                    0,
                                                    endings,
                                                    outputEnding);  
        prokka.setOnlyOutputDirectorySetable(true);
        prokka.addParameter("Force", "--force", Pool.PROGRAM_EMPTY_PARAMETER_VALUE, 1, false);
        this.defaultList.add(prokka);
        
        String[] outputEndings2 = new String[]{".txt"};
        ParseableProgramParameters extractHeader = new ParseableProgramParameters( "Extract Header Info", 
                                                    "SCRITPS/extract_header_info.plx", 
                                                    null,
                                                    2,
                                                    "|tee",
                                                    3,
                                                    endings,
                                                    outputEndings2);  
        extractHeader.addParameter("prepare Excel", "-E", Pool.PROGRAM_EMPTY_PARAMETER_VALUE, 1, true);
        this.defaultList.add(extractHeader);
        
        String[] endingsB = {".txt"};
        ParseableProgramParameters assemblyStatistics = new ParseableProgramParameters( "Assembly Statistics", 
                                                    "SCRITPS/assembly_statistics.plx", 
                                                    null,
                                                    2,
                                                    "|tee",
                                                    3,
                                                    endingsB,
                                                    outputEndings2);  
        assemblyStatistics.addParameter("Skip listprint", "-l", Pool.PROGRAM_EMPTY_PARAMETER_VALUE, 1, true);
        this.defaultList.add(assemblyStatistics);
        
        this.core = new GeneratorCore(this.localFilePath, this.defaultList);
        
        
    }
    
    @Override
    public String[] getAvailableNames(){
        return this.core.getAvailableNames();
    }
 
    @Override
    public ParseableProgramParameters get(String name){
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
