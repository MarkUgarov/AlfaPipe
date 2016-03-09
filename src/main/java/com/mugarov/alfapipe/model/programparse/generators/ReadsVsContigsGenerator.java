/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.programparse.generators;

import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgram;
import java.util.ArrayList;

/**
 *
 * @author Mark
 */
public class ReadsVsContigsGenerator implements Generator {
    
    private final String localFilePath;
    private final GeneratorCore core;
    private final ArrayList<ParseableProgram> defaultList;
     
    public ReadsVsContigsGenerator(){
        this.defaultList = new ArrayList<>();       
        this.localFilePath = ParameterPool.PATH_READS_VS_CONTIGS_LIST;

        String[] endings = {".fa", ".fq"};
        String[] outputEnding = new String[]{".fa"};
        ParseableProgram readsVsContigs = new ParseableProgram( ParameterPool.NAME_DEFAULT_READS_VS_CONTIGS,
                                                                null, 
                                                                "-in something",
                                                                -1,
                                                                "-out something",
                                                                0,
                                                                endings,
                                                                outputEnding);
        readsVsContigs.setOnlyOutputDirectorySetable(false);
        readsVsContigs.setEnterCommand("reads vs contigs enter command");
        readsVsContigs.setExitCommand(" reads vs contigs exit command");
        
        ParseableProgram nullReadsVsContigs= new ParseableProgram( "Choose non", 
                                                                null, 
                                                                null,
                                                                0,
                                                                null,
                                                                0,
                                                                null,
                                                                null); 
       
        this.defaultList.add(readsVsContigs);
        this.defaultList.add(nullReadsVsContigs);

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
