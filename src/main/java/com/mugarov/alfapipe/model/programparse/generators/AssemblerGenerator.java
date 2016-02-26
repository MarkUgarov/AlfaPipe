/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.programparse.generators;

import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.programparse.datatypes.NameField;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgramParameters;
import java.util.ArrayList;

/**
 *
 * @author Mark
 */
public class AssemblerGenerator implements Generator{
    
    private final String localFilePath;
    private final GeneratorCore core;
    private final ArrayList<ParseableProgramParameters> defaultList;
     
    public AssemblerGenerator(){
        this.defaultList = new ArrayList<>();       
        this.localFilePath = ParameterPool.PATH_ASSEMBLER_LIST;

        String[] endings = {".fa", ".fastq"};
        String[] outputEnding = new String[]{".fna", ".fa", ".txt"};
        ParseableProgramParameters newbler = new ParseableProgramParameters( "Newbler",
                                                                "/vol/454/.old/2.8/bin/runAssembly", 
                                                                null,
                                                                -1,
                                                                "-o",
                                                                0,
                                                                endings,
                                                                outputEnding);
        newbler.setOnlyOutputDirectorySetable(true);
        newbler.addParameter("large", "-large", ParameterPool.PROGRAM_EMPTY_PARAMETER_VALUE, 1, true);
        newbler.addParameter("CPU", "-cpu", "10", 2, true);
        newbler.addParameter("Force", "-force", ParameterPool.PROGRAM_EMPTY_PARAMETER_VALUE, 3, false);
        
        NameField  essential1 = new NameField();
        essential1.setName("454AllContigs.fna");
        essential1.setEssentialFor("Prokka");
        essential1.setUseOnly(true);
        newbler.addEssentialOutput(essential1);
        
        NameField essential2 = new NameField();
        essential2.setName("454AllContigs.fna");
        essential2.setEssentialFor("Extract Header Info");
        essential2.setUseOnly(true);
        newbler.addEssentialOutput(essential2);
        
        NameField essential3 = new NameField();
        essential3.setName(ParameterPool.PROGRAM_DIRECTORY_VALUE);
        essential3.setEssentialFor("Assembly Statistics");
        essential3.setUseOnly(true);
        newbler.addEssentialOutput(essential3);
        
        NameField essential4 = new NameField();
        essential4.setName("454AllContigs.fna");
        essential4.setEssentialFor(ParameterPool.NAME_DEFAULT_PRODIGAL);
        essential4.setUseOnly(true);
        newbler.addEssentialOutput(essential4);
        

        ParseableProgramParameters allpath=  new ParseableProgramParameters( "Allpath",
                                                                "apply startCommand", 
                                                                "apply inputPathCommand",
                                                                -1,
                                                                "apply outputPathCommand",
                                                                0,
                                                                endings,
                                                                outputEnding);
        ParseableProgramParameters nullAss= new ParseableProgramParameters( "Choose non", 
                                                                null, 
                                                                null,
                                                                0,
                                                                null,
                                                                0,
                                                                null,
                                                                null); 
        this.defaultList.add(newbler);
        this.defaultList.add(allpath);
        this.defaultList.add(nullAss);
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
