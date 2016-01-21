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
public class ProdigalGenerator implements Generator{
    
    private final String localFilePath;
    private final GeneratorCore core;
    private final ArrayList<ParseableProgramParameters> defaultList;
     
    public ProdigalGenerator(){
        this.defaultList = new ArrayList<>();       
        this.localFilePath = Pool.PATH_PRODIGAL_LIST;

        String[] endings = {".fa", ".fasta", ".fna"};
        String[] outputEnding = new String[]{".fa"};
        ParseableProgramParameters prodigal = new ParseableProgramParameters( Pool.NAME_DEFAULT_PRODIGAL,
                                                                "prodigal", 
                                                                "-i",
                                                                -2,
                                                                "-o",
                                                                -1,
                                                                endings,
                                                                outputEnding);
        prodigal.addParameter("add right protein translations","-a" , Pool.PROGRAM_EMPTY_PARAMETER_VALUE, 1, true);
        prodigal.addParameter("write nucleotide sequence", "-d", Pool.PROGRAM_EMPTY_PARAMETER_VALUE, 2, true);
        prodigal.setOnlyOutputDirectorySetable(false);
        prodigal.setEnterCommand("prodigal enter command");
        prodigal.setExitCommand("prodigal exit command");
        
        ParseableProgramParameters nullProdigal= new ParseableProgramParameters( "Choose non", 
                                                                null, 
                                                                null,
                                                                0,
                                                                null,
                                                                0,
                                                                null,
                                                                null); 
       
        this.defaultList.add(prodigal);
        this.defaultList.add(nullProdigal);

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
