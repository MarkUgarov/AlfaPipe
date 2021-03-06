/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.programparse.fabrics;

import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.programparse.datatypes.NameField;
import com.mugarov.alfapipe.model.programparse.datatypes.PairedInputConditions;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgramList;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgram;
import java.util.ArrayList;

/**
 *
 * @author mugarov
 */
    public class AssemblerParameterFabric {
    private final ArrayList<ParseableProgram> defaultList;
    private final int index;
    private final String name;
    
    private final ParseableProgramList parameterList;
            
    public AssemblerParameterFabric(){
        this.defaultList = new ArrayList<>();       
        this.name = ParameterPool.LABEL_ASSEMBLER;
        this.index = 2;
        
        String[] endings = {".fastq"};;
        String[] outputEnding =  {".fna", ".txt"};
        ParseableProgram newbler = new ParseableProgram( "Newbler",
                                                                "/vol/454/.old/2.8/bin/runAssembly", 
                                                                null,
                                                                -1,
                                                                "-o",
                                                                0,
                                                                endings,
                                                                outputEnding);
        newbler.setOutputSettings(true, true);
        newbler.setPairedConditions(new PairedInputConditions(true, "_" , -1));
        newbler.addParameter("large", "-large", ParameterPool.PROGRAM_EMPTY_PARAMETER_VALUE, 1, true, "For large files.");
        newbler.addParameter("CPU", "-cpu", "10", 2, true, "Number of used cores. Set 0 if you want to use as many as possible (may cause to slow down the whole System).");
        newbler.addParameter("Force", "-force", ParameterPool.PROGRAM_EMPTY_PARAMETER_VALUE, 3, false, "Forces to overwrite old data.");
        newbler.addAdditionalClusterParameter(ParameterPool.PROGRAM_BINARAY_NAME, ParameterPool.PROGRAM_BINARY_COMMAND, ParameterPool.PROGRAM_BINARY_CONFIRM, ParameterPool.PROGRAM_BINARY_POSITION, ParameterPool.PROGRAM_BINARY_OPTIONAL, ParameterPool.PROGRAM_BINARY_DESCRIPTION);
        
        NameField  essential1 = new NameField();
        essential1.setFileName("454AllContigs.fna");
        essential1.setEssentialFor("Prokka");
        essential1.setUseOnly(true);
        newbler.addEssentialOutput(essential1);
        
        NameField essential2 = new NameField();
        essential2.setFileName("454AllContigs.fna");
        essential2.setEssentialFor("Extract Header Info");
        essential2.setUseOnly(true);
        newbler.addEssentialOutput(essential2);
        
        NameField essential3 = new NameField();
        essential3.setFileName(ParameterPool.PROGRAM_DIRECTORY_VALUE);
        essential3.setEssentialFor("Assembly Statistics");
        essential3.setUseOnly(true);
        newbler.addEssentialOutput(essential3);
        
        NameField essential4 = new NameField();
        essential4.setFileName("454AllContigs.fna");
        essential4.setEssentialFor("Prodigal");
        essential4.setUseOnly(true);
        newbler.addEssentialOutput(essential4);
        
        NameField essential5 = new NameField();
        essential5.setFileName("454AllContigs.fna");
        essential5.setEssentialFor("NeView");
        essential5.setUseOnly(true);
        newbler.addEssentialOutput(essential5);
        
        NameField essential6 = new NameField();
        essential6.setFileName("454Scaffolds.txt");
        essential6.setEssentialFor("NeView");
        essential6.setUseOnly(true);
        newbler.addEssentialOutput(essential6);
        

        ParseableProgram nullAss= new ParseableProgram( "Skip", 
                                                                null, 
                                                                null,
                                                                0,
                                                                null,
                                                                0,
                                                                null,
                                                                null); 
        this.defaultList.add(nullAss);
        this.defaultList.add(newbler);
        
        
        this.parameterList = new ParseableProgramList();
        this.parameterList.setIndex(this.index);
        this.parameterList.setName(this.name);
        this.parameterList.setPrograms(this.defaultList);
    }
    
    public ParseableProgramList getList(){
        return this.parameterList;
    }
}
