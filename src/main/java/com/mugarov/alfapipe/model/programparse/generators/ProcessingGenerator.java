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
public class ProcessingGenerator implements Generator{
    
    private final String localFilePath;
    private final GeneratorCore core;
    private final ArrayList<ParseableProgram> defaultList;
     
    public ProcessingGenerator(){
        this.defaultList = new ArrayList<>();       
        this.localFilePath = ParameterPool.PATH_PROCESSING_LIST;

        String[] endings = {".fastq"};
        String[] outputEnding = new String[]{".fa"};
        ParseableProgram miseq = new ParseableProgram( "MiSeqFASTQ4Newbler_v2.8b",
                                                                "SCRITPS/MiSeqFASTQ4Newbler_v2.8b_OutputOnArgs1.pl", 
                                                                null,
                                                                1,
                                                                null,
                                                                2,
                                                                endings,
                                                                outputEnding);
        miseq.setOnlyOutputDirectorySetable(false);
        
        ParseableProgram splitMiseq = new ParseableProgram( "split_MiSeq_MP_nextera",
                                                                "SCRITPS/split_MiSeq_MP_nextera_OutputOnArgs1.pl", 
                                                                null,
                                                                1,
                                                                null,
                                                                2,
                                                                endings,
                                                                outputEnding);
        splitMiseq.setOnlyOutputDirectorySetable(false);
        
        ParseableProgram nullProcessor= new ParseableProgram( "Choose non", 
                                                                null, 
                                                                null,
                                                                0,
                                                                null,
                                                                0,
                                                                null,
                                                                null); 
       
        this.defaultList.add(miseq);
        this.defaultList.add(splitMiseq);
        this.defaultList.add(nullProcessor);

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
